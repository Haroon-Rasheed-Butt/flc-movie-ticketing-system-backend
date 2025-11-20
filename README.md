# FLC Movie Ticketing System Backend

A comprehensive RESTful API backend service for a movie ticketing system built with Spring Boot.

## Features

- **Movie Management**: CRUD operations for movies with search and filter capabilities
- **Theater Management**: Manage theaters and their locations
- **Show Scheduling**: Create and manage movie shows across theaters
- **User Management**: User registration and profile management
- **Booking System**: Book tickets with seat selection and automatic inventory management
- **OpenAPI/Swagger Documentation**: Interactive API documentation

## Technology Stack

- **Java 17**
- **Spring Boot 3.3.5**
- **Spring Data JPA**
- **MySQL Database**
- **Lombok** for boilerplate code reduction
- **SpringDoc OpenAPI** for API documentation

## Prerequisites

- Java 17 or higher
- Maven 3.6+
- MySQL 8.0+

## Database Setup

1. Create a MySQL database:
```sql
CREATE DATABASE movieticketdb;
```

2. Update database credentials in `src/main/resources/application.properties`:
```properties
spring.datasource.url=jdbc:mysql://localhost:3306/movieticketdb?allowPublicKeyRetrieval=true&useSSL=false&serverTimezone=UTC
spring.datasource.username=your_username
spring.datasource.password=your_password
```

## Building and Running

### Build the project
```bash
mvn clean package
```

### Run the application
```bash
java -jar target/flc-movie-ticketing-system-backend-0.0.1-SNAPSHOT.jar
```

Or use Maven:
```bash
mvn spring-boot:run
```

The application will start on `http://localhost:8080`

## API Documentation

Once the application is running, access the interactive API documentation at:
- **Swagger UI**: http://localhost:8080/swagger-ui
- **OpenAPI JSON**: http://localhost:8080/api-docs

## API Endpoints

### Movies
- `GET /api/movies` - Get all active movies
- `GET /api/movies/{id}` - Get movie by ID
- `GET /api/movies/genre/{genre}` - Get movies by genre
- `GET /api/movies/language/{language}` - Get movies by language
- `GET /api/movies/search?title={title}` - Search movies by title
- `POST /api/movies` - Create a new movie
- `PUT /api/movies/{id}` - Update a movie
- `DELETE /api/movies/{id}` - Soft delete a movie

### Theaters
- `GET /api/theaters` - Get all active theaters
- `GET /api/theaters/{id}` - Get theater by ID
- `GET /api/theaters/city/{city}` - Get theaters by city
- `GET /api/theaters/search?name={name}` - Search theaters by name
- `POST /api/theaters` - Create a new theater
- `PUT /api/theaters/{id}` - Update a theater
- `DELETE /api/theaters/{id}` - Soft delete a theater

### Shows
- `GET /api/shows` - Get all active shows
- `GET /api/shows/{id}` - Get show by ID
- `GET /api/shows/movie/{movieId}` - Get shows by movie
- `GET /api/shows/theater/{theaterId}` - Get shows by theater
- `GET /api/shows/movie/{movieId}/theater/{theaterId}` - Get shows by movie and theater
- `GET /api/shows/date-range?start={start}&end={end}` - Get shows by date range
- `POST /api/shows` - Create a new show
- `PUT /api/shows/{id}` - Update a show
- `DELETE /api/shows/{id}` - Soft delete a show

### Users
- `GET /api/users` - Get all users
- `GET /api/users/{id}` - Get user by ID
- `GET /api/users/email/{email}` - Get user by email
- `POST /api/users` - Create a new user
- `PUT /api/users/{id}` - Update a user
- `DELETE /api/users/{id}` - Soft delete a user

### Bookings
- `GET /api/bookings` - Get all bookings
- `GET /api/bookings/{id}` - Get booking by ID
- `GET /api/bookings/user/{userId}` - Get bookings by user
- `GET /api/bookings/show/{showId}` - Get bookings by show
- `POST /api/bookings` - Create a new booking
- `PUT /api/bookings/{id}/cancel` - Cancel a booking

## Data Models

### Movie
- Title, Description, Duration (minutes)
- Genre, Language, Release Date
- Director, Cast
- Poster URL, Trailer URL
- Rating (0.0-10.0)

### Theater
- Name, Location, City, Address
- Total Screens

### Show
- Movie, Theater
- Show Time, Screen Number
- Total Seats, Available Seats
- Ticket Price

### User
- Name, Email, Phone
- Password (Note: In production, implement proper authentication)

### Booking
- User, Show
- Number of Seats, Seat Numbers
- Total Amount
- Booking Time, Status (CONFIRMED, CANCELLED, PENDING)
- Payment ID

## Development Notes

- All entities support soft deletion (isActive flag)
- Show seats are automatically managed during booking
- Bookings are transactional to prevent race conditions
- Database schema is auto-generated/updated via Hibernate

## CORS Configuration

CORS is configured to allow all origins, headers, and methods for development. Update `CorsConfig.java` for production environments.

## Docker Support

A Dockerfile is included. To build and run with Docker:

```bash
docker build -t movie-ticketing-backend .
docker run -p 8080:8080 movie-ticketing-backend
```

## Future Enhancements

- Authentication & Authorization (JWT)
- Payment gateway integration
- Email notifications
- Seat map visualization
- Reviews and ratings
- Admin dashboard
- Integration tests

## License

This project is part of the FLC Movie Ticketing System.