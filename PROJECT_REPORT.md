# MovieTicketing_Haroon Backend Report

## Overview
- Spring Boot 3.3 backend targeting Java 17.
- REST surface exposed at `/api/v1`, backed by Spring Data JPA and MySQL.
- CORS filter enables Angular front-ends on `localhost:4200` and `host.docker.internal`.

## Database design
- `account`, `movie_type`, `movie`, `cart_item`, `transaction_history`, and `rating` tables match the brief exactly.
- SQL definitions live in `src/main/resources/schema.sql` and `data.sql` (5 rows per table, seeded when the app boots).
- Relationships enforce referential integrity with explicit foreign keys.

## Key endpoints
1. `POST /api/v1/accounts` – Creates accounts from `CreateAccountRequest`.
2. `GET /api/v1/accounts/{id}` – Returns `AccountResponse`.
3. `GET /api/v1/accounts/{id}/transactions` – Streams `TransactionResponse` list.
4. `GET /api/v1/movies` – Paginated `PageResponse<MovieResponse>` list.
5. `POST /api/v1/movies` – Creates movie records with type/owner relationships.
6. `PUT /api/v1/movies/{id}` – Updates movie metadata.
7. `DELETE /api/v1/movies/{id}` – Deletes movie records.
8. `POST /api/v1/cart` – Adds items to a cart (cart_item + movie + account).
9. `GET /api/v1/accounts/{id}/cart` – Fetches enriched cart items joined to movies.
10. `POST /api/v1/checkout` – Transactional checkout that creates transactions, marks cart items as purchased, and returns `CheckoutResponse`.
11. `POST /api/v1/movies/{id}/ratings` – Adds ratings with comments.
12. `GET /api/v1/movies/{id}/ratings` – Lists `RatingResponse` entries.

## Testing
- Unit tests cover `AccountService` behavior, validation, and mapping.
- Integration test (`CheckoutServiceIntegrationTest`) boots Spring Boot with a Testcontainers MySQL instance to verify the checkout flow and cart state.

## Docker & Postman
- `Dockerfile` uses a Maven build stage + Eclipse Temurin runtime; it injects database environment variables to align with Compose.
- `docker-compose.yml` runs MySQL + backend side-by-side with shared credentials.
- Postman collection: `docs/postman/movie-ticketing.postman_collection.json` plus README for screenshot capture guidance.

## Screenshots (placeholders)
- Drop UI/Postman screenshots inside `docs/postman/screenshots/` to demonstrate the API and workflows.
- Capture a multi-step flow (account → cart → checkout) plus otherwise highlight ratings/transactions.

## Build & push notes
1. `mvn -q -DskipTests package` produces `target/MovieTicketing_Haroon-0.0.1-SNAPSHOT.jar`.
2. `docker build -t movieticketing_haroon .`
3. `docker tag movieticketing_haroon <registry>/movieticketing_haroon:latest`
4. Push via `docker push <registry>/movieticketing_haroon:latest` after login.
