# Copilot instructions for MovieTicketing_Haroon Backend

## High level
- The backend lives under `com.movieticketing.backend`; entry point `MovieTicketingHaroonApplication` boots Spring Boot 3.3 with Java 17.
- REST APIs all start with `/api/v1`. Controllers are grouped by concern: `AccountController`, `MovieController`, `CartController`, `CheckoutController`, and the `GlobalExceptionHandler`.
- Service implementations (under `service/`) orchestrate repository access and map entities to response DTOs.

## Data & persistence
- Entities (account, movie, movie_type, cart_item, transaction_history, rating) live in `entity/`. Repositories are Spring Data JPA interfaces.
- DTOs live inside `dto/request` and `dto/response`. Never return entities directly; always build the corresponding response DTO (`AccountResponse`, `MovieResponse`, `CartItemResponse`, etc.). `ApiResponse`/`PageResponse` are the standard wrappers, so every controller should wrap payloads using `ApiResponse.success(...)` or `ApiResponse.error(...)`.
- Validation is enabled via `@Valid` + Jakarta Bean Validation on request DTOs. The `GlobalExceptionHandler` centralizes errors (validation, constraint violations, `ResourceNotFoundException`, and generic fallbacks).

## Key flows
- Movie CRUD flows call `MovieService`. The service fetches `MovieType` and optional `Account` owners, then maps to `MovieResponse` including `typeName` + owner metadata.
- Cart flows live in `CartService`. `POST /api/v1/cart` adds cart items; `GET /api/v1/accounts/{id}/cart` reads them joined with movie metadata.
- Checkout (`CheckoutService`) is transactional: it fetches pending cart items (or the supplied list), multiplies ticket price × quantity, creates `TransactionHistory` rows, and flips `CartItem.purchased`. The controller responds with `CheckoutResponse` containing `TransactionResponse` entries.
- Ratings use `RatingService`: `POST /api/v1/movies/{id}/ratings` and `GET /api/v1/movies/{id}/ratings` use a dedicated repository to order by timestamp.
- `TransactionService` returns all transactions for an account via `GET /api/v1/accounts/{id}/transactions`.

## Conventions & tooling
- Use Lombok annotations (`@Data`, `@Builder`, `@RequiredArgsConstructor`) consistently across entities, DTOs, and services.
- Pagination for movies uses `MovieService.list` which returns `PageResponse<MovieResponse>`; clients pass `page`/`size` query parameters (defaults 0/10).
- All responses must respect the `ApiResponse` wrapper; follow existing controllers as examples.
- `CorsConfig` allows `http://localhost:*`, `http://127.0.0.1:*`, and `http://host.docker.internal:*` origins on `/api/v1/**` paths.

## Development & debugging
- SQL schema + data are in `src/main/resources/schema.sql` and `data.sql`. Spring Boot executes them on startup because `spring.sql.init.mode=always` in `application.properties` (environment variables for `MOVIE_DB_URL`, `MOVIE_DB_USERNAME`, `MOVIE_DB_PASSWORD`).
- Standard Maven commands:
  - `mvn -q clean package` to build the jar.
  - `mvn -q test` to run unit + integration tests (Testcontainers pulls `mysql:8.0`).
  - `mvn spring-boot:run` to launch locally, respecting `MOVIE_DB_*` env vars (default `jdbc:mysql://localhost:3306/movie_ticketing`).
- Docker: the repo includes a multi-stage `Dockerfile` and `docker-compose.yml` (MySQL + backend). `docker compose up --build` wires the services together.

## Documentation & assets
- README and PROJECT_REPORT outline build/run steps, endpoint descriptions, grading checkpoints, and Docker/push hints.
- Postman collection + placeholder screenshot guidance live in `docs/postman/` (collection file `movie-ticketing.postman_collection.json`, screenshot instructions in `screenshots/README.md`).

## Testing notes
- Unit tests use @ExtendWith(MockitoExtension.class) + Mockito for service logic (see `AccountServiceTest`).
- Integration test `CheckoutServiceIntegrationTest` boots Spring Boot with Testcontainers/`mysql:8.0` to verify checkout, so expect Docker to be available when running `mvn test` locally.

## When making changes
- Add new DTOs under `dto/request` or `dto/response` and wire them through services/controllers.
- Keep `ApiResponse` wrappers and `@Valid` annotations on controller methods.
- Seed data should live in `data.sql` and be limited to ~5 records per table for reproducibility.
- If modifying endpoints, update the Postman collection and mention the change in `PROJECT_REPORT.md` (requests/responses/documentation section).
