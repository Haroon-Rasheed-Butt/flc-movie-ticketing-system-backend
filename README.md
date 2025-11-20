# MovieTicketing_Haroon Backend

This Spring Boot service powers the MovieTicketing_Haroon experience. It exposes a CORS-friendly `/api/v1` REST surface, persists to MySQL via Spring Data JPA, and ships with a companion Dockerfile + `docker-compose` to start the backend and database together.

## Getting started

### Requirements
- JDK 17
- Maven 3.9+
- Docker (optional but required for `docker-compose`)

### Build with Maven

```bash
mvn -q clean package
```

### Run locally without Docker

```bash
export MOVIE_DB_URL=jdbc:mysql://localhost:3306/movie_ticketing
export MOVIE_DB_USERNAME=admin
export MOVIE_DB_PASSWORD=admin
mvn spring-boot:run
```

Or on Windows `cmd.exe`:

```cmd
set MOVIE_DB_URL=jdbc:mysql://localhost:3306/movie_ticketing
set MOVIE_DB_USERNAME=admin
set MOVIE_DB_PASSWORD=admin
mvn spring-boot:run
```

### Run with Docker Compose

```bash
docker compose up --build
```

The compose stack launches MySQL (credentials `admin/admin`) and the backend configured to seed `schema.sql` / `data.sql` from `src/main/resources`.

### Run tests

```bash
mvn test
```

## API overview (`/api/v1`)

| Endpoint | Description |
| --- | --- |
| `POST /accounts` | Create an account; uses `CreateAccountRequest` for input. |
| `GET /accounts/{id}` | Fetch account details. |
| `GET /accounts/{id}/transactions` | List historical transactions for the account. |
| `GET /movies` | Paginated movie list returned as `PageResponse<MovieResponse>`. |
| `POST /movies` | Create movie (movie + type). Provide `typeId`, optional `accountId`. |
| `PUT /movies/{id}` | Update movie metadata. |
| `DELETE /movies/{id}` | Delete a movie. |
| `POST /cart` | Add item to cart (account + movie). |
| `GET /accounts/{id}/cart` | List cart (joins cart items with movies). |
| `POST /checkout` | Checkout pending items or provided cart IDs. Returns `CheckoutResponse`. |
| `POST /movies/{id}/ratings` | Add rating for a movie. |
| `GET /movies/{id}/ratings` | List ratings for a movie. |

All responses are wrapped with `ApiResponse` (status/message/data), and validation errors are translated into friendly messages by `GlobalExceptionHandler`.

## Database

- Schema + seed data live in `src/main/resources/schema.sql` and `data.sql`.
- The tables align with the brief (accounts, movie_type, movie, cart_item, transaction_history, rating).
- Spring boot automatically executes the SQL files because `spring.sql.init.mode=always`.

## Docker

- Use the multi-stage `Dockerfile` for a lean runtime image (build stage uses Maven, final stage runs the jar).
- The `docker-compose.yml` spins up MySQL + backend and exposes port `8080` on the host.

## Postman & Screenshots

- Import `docs/postman/movie-ticketing.postman_collection.json` and configure `baseUrl` (e.g., `http://localhost:8080/api/v1`) to execute the 11+ scenarios.
- Save request snapshots or terminal captures inside `docs/postman/screenshots/`. A placeholder README explains where to drop the PNGs.

## Grading checklist

1. **Report** – Refer to `PROJECT_REPORT.md` for architecture diagrams, endpoint lists, and test coverage notes.
2. **Postman** – The collection lives under `docs/postman/`, and the `screenshots` folder highlights the requested flows.
3. **Docker images** – Build/push instructions are documented in the report, and the `docker-compose.yml` confirms the MySQL/backend pairing.
4. **Tests** – `mvn test` exercises unit mocks plus a Testcontainers-backed checkout integration test.

## Example curl commands

```bash
curl -X POST http://localhost:8080/api/v1/accounts -H "Content-Type: application/json" -d '{"firstName":"Haroon","lastName":"Butt","username":"haroon","password":"secret"}'

curl http://localhost:8080/api/v1/movies?page=0&size=5

curl -X POST http://localhost:8080/api/v1/cart -H "Content-Type: application/json" -d '{"accountId":1,"movieId":1,"quantity":2}'

curl -X POST http://localhost:8080/api/v1/checkout -H "Content-Type: application/json" -d '{"accountId":1}'
```

## Next steps
- Add UI consumers (Angular) to match the documented endpoints.
- Enrich seed data with more genres and transactions before shipping to production.