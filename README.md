# Movie Rental System - Spring Boot

A movie rental billing system refactored to Spring Boot architecture.

## Project Structure

```
src/
├── main/
│   ├── java/example/
│   │   ├── MovieRentalApplication.java   - Spring Boot entry point
│   │   ├── model/
│   │   │   ├── Genre.java                - Genre interface with pricing strategy
│   │   │   ├── Genres.java               - Predefined genres
│   │   │   ├── Movie.java                - Movie entity
│   │   │   ├── Rental.java               - Rental entity with price/points
│   │   │   ├── Customer.java             - Customer with rentals
│   │   │   └── StatementFormat.java      - Output format enum
│   │   ├── dto/
│   │   │   ├── RentalRequest.java        - Rental input DTO
│   │   │   ├── RentalResponse.java       - Rental output DTO
│   │   │   ├── StatementRequest.java     - Statement request DTO
│   │   │   └── StatementResponse.java    - Statement response DTO
│   │   ├── service/
│   │   │   ├── GenreService.java         - Genre registry and management
│   │   │   ├── StatementService.java     - Statement generation
│   │   │   └── RentalService.java        - Rental operations
│   │   └── controller/
│   │       ├── RentalController.java     - Rental REST endpoints
│   │       ├── GenreController.java      - Genre REST endpoints
│   │       └── GlobalExceptionHandler.java - Error handling
│   └── resources/
│       └── application.properties
└── test/java/example/
    ├── model/                            - Model unit tests
    ├── service/                          - Service unit tests
    └── controller/                       - Integration tests
```

## REST API Endpoints

### Generate Statement
```
POST /api/rentals/statement
Content-Type: application/json

{
  "customerName": "John Doe",
  "rentals": [
    {"movieTitle": "The Matrix", "genre": "REGULAR", "daysRented": 3},
    {"movieTitle": "Avatar 3", "genre": "NEW_RELEASE", "daysRented": 2}
  ],
  "format": "PLAIN_TEXT"
}
```

Response:
```json
{
  "customerName": "John Doe",
  "rentals": [...],
  "totalAmount": 9.5,
  "totalFrequentRenterPoints": 3,
  "statement": "Rental Record for John Doe..."
}
```

### Get Available Genres
```
GET /api/genres
```

### Calculate Price for Genre
```
GET /api/genres/{genre}/price?daysRented=5
```

## Requirements

- Java 17+
- Maven 3.6+

## Running the Application

```bash
# Run with Maven
mvn spring-boot:run

# Or build and run JAR
mvn clean package
java -jar target/refactoring1stEdition-1.0-SNAPSHOT.jar
```

The application starts on `http://localhost:8080`

## Running Tests

```bash
mvn test
```

## Architecture

### Service Layer
- **GenreService**: Manages genre registry, allows runtime registration of custom genres
- **StatementService**: Generates plain text and HTML statements
- **RentalService**: Orchestrates rental operations, price calculations

### Model Layer
- **Genre**: Strategy interface for pricing logic
- **Genres**: Predefined genres (REGULAR, NEW_RELEASE, CHILDRENS)
- **Movie**: Immutable movie entity
- **Rental**: Rental with delegated price/points calculation
- **Customer**: Customer aggregate with rentals

### Controller Layer
- **RentalController**: Statement generation endpoint
- **GenreController**: Genre information and price calculation
- **GlobalExceptionHandler**: Centralized error handling

## Pricing Rules

| Genre | Base Price | Extra Charge |
|-------|------------|--------------|
| REGULAR | $2.00 | +$1.50/day after 2 days |
| NEW_RELEASE | $3.00/day | - |
| CHILDRENS | $1.50 | +$1.50/day after 3 days |

## Frequent Renter Points

- 1 point per rental
- +1 bonus point for NEW_RELEASE rentals over 1 day
