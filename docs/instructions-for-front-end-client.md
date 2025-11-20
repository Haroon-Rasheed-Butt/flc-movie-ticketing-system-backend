**General Contract**
- **Wrapper**: Every controller returns `ApiResponse<T>`; success responses set `status: "success"` (sometimes with a human-friendly `message`) and `data` carries the payload, while `ApiResponse.error(...)` yields `status: "error"` with `data: null`.
- **Headers**: Always send `Content-Type: application/json` and `Accept: application/json`. No auth header is required, so requests just need well-formed JSON and the required IDs/fields.
- **Pagination**: `/api/v1/movies` returns `ApiResponse<PageResponse<MovieResponse>>` where `PageResponse` exposes `content`, `page`, `size`, `totalElements`, and `totalPages`.

**Environment & Infrastructure**
- **DB config**: `application.properties` defaults to `MOVIE_DB_URL=jdbc:mysql://localhost:3306/movie_ticketing`, `MOVIE_DB_USERNAME=admin`, `MOVIE_DB_PASSWORD=admin`. Override via environment variables when running in containers or CI.
- **SQL init**: `spring.sql.init.mode=always` loads `schema.sql` + `data.sql` on startup, ensuring all tables plus seed records exist before your UI hits the APIs.
- **Swagger**: API docs are exposed at `/api-docs` and the interactive UI at `/swagger-ui`.
- **CORS**: `CorsFilter` allows `http://localhost:*`, `http://127.0.0.1:*`, and `http://host.docker.internal:*` on `/api/v1/**` with all headers, `GET/POST/PUT/PATCH/DELETE/OPTIONS`, and `allowCredentials=false`.

**Seed Data Snapshot**
- **Accounts**: Five named users (`haroon`, `sara`, `ali`, `maya`, `zain`) pre-loaded with `rememberMe` flags.
- **Movie types & movies**: Types cover Action, Drama, Sci-Fi, Comedy, Mystery; each movie is linked to a type and an owner account, with realistic `description`, `ticketPrice`, and `coverPhoto`.
- **Cart/Transactions/Ratings**: Sample cart items (`purchased=false`), completed transactions, and ratings exist for each seeded movie—great for immediately populating lists.

**Account APIs**
- **POST `/api/v1/accounts`**
  - Body: `{ "firstName": "...", "lastName": "...", "username": "...", "password": "...", "rememberMe": false }` (`rememberMe` optional, defaults `false`, all strings are `@NotBlank`).
  - Response: `ApiResponse.success("Account created", AccountResponse)`.
- **GET `/api/v1/accounts/{id}`**
  - Response: `ApiResponse.success(AccountResponse)` with `{id, firstName, lastName, username, rememberMe}`.
- **GET `/api/v1/accounts/{id}/transactions`**
  - Response: `ApiResponse.success(List<TransactionResponse>)`; each entry includes `id`, `totalAmount`, `transactionDate`, `status`, `movieId`, and `accountId`.

**Movie APIs**
- **GET `/api/v1/movies`**
  - Query params: `page` (default `0`), `size` (default `10`).
  - Response: `ApiResponse.success(PageResponse<MovieResponse>)`. Each `MovieResponse` adds `typeName`, `ownerId`, `ownerUsername`, and movie details (`ticketPrice`, `genre`, etc.).
- **POST `/api/v1/movies`**
  - Body: `name` (`@NotBlank`), `typeId` (`@NotNull`), optional `description`, `publicationYear`, `director`, `actor`, `genre`, `coverPhoto`, `accountId`, `ticketPrice` (`@DecimalMin("0.0")`).
  - Response: `"Movie saved"` message + created `MovieResponse`.
- **PUT `/api/v1/movies/{id}`**
  - Body mirrors POST; response message `"Movie updated"` with updated `MovieResponse`.
- **DELETE `/api/v1/movies/{id}`**
  - Response: `ApiResponse.success("Movie deleted", null)`.

**Rating APIs**
- **POST `/api/v1/movies/{id}/ratings`**
  - Body: `accountId`, `rating` (`@Min(1)` & `@Max(5)`), optional `comment`.
  - Response: `ApiResponse.success("Rating added", RatingResponse)`; includes `id`, `rating`, `comment`, `ratingDate`, `movieId`, `accountId`.
- **GET `/api/v1/movies/{id}/ratings`**
  - Response: `ApiResponse.success(List<RatingResponse>)`.

**Cart APIs**
- **POST `/api/v1/cart`**
  - Body: `accountId`, `movieId`, `quantity` (`@Min(1)`).
  - Response: `ApiResponse.success("Cart item added", CartItemResponse)` with `{id, quantity, purchased, purchaseDate, movieId, movieName, ticketPrice}`.
- **GET `/api/v1/accounts/{id}/cart`**
  - Response: `ApiResponse.success(List<CartItemResponse>)`. Use `purchased` & `purchaseDate` to determine whether an item is still pending.

**Checkout API**
- **POST `/api/v1/checkout`**
  - Body: `accountId`, optional `cartItemIds` list (defaults to `[]`). When empty, the service checks all unpurchased cart items for the account before building transactions.
  - Response: `ApiResponse.success("Checkout completed", CheckoutResponse)` containing `totalAmount`, `processedItems`, and `transactions` (each is a `TransactionResponse` as described above).

**Error Handling**
- Validation failures (`MethodArgumentNotValidException`, bean validation) return `400` with `ApiResponse.error` and combined field error messages.
- `ResourceNotFoundException` → `404` with the exception message.
- `ConstraintViolationException` → `400`.
- All other exceptions return `500` + `"Internal server error"`.
- Always inspect `status` & `message` before reading `data`, since error responses omit payloads.
