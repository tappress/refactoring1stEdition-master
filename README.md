# Movie Rental System

A classic movie rental billing system - a famous refactoring kata from Martin Fowler's "Refactoring" book (1st Edition).

## Project Structure

```
src/
├── main/java/example/
│   ├── Genre.java          - Interface for movie genres with pricing strategy
│   ├── Genres.java         - Predefined genres (REGULAR, NEW_RELEASE, CHILDRENS)
│   ├── Movie.java          - Movie entity with title and genre
│   ├── Rental.java         - Rental entity with price/points calculation
│   ├── Customer.java       - Customer with rentals and statement generation
│   ├── StatementFormat.java - Enum for output formats (PLAIN_TEXT, HTML)
│   └── Main.java           - Demo application
└── test/java/example/
    ├── GenreTest.java      - Genre interface and Genres tests (18 tests)
    ├── MovieTest.java      - Movie class tests (7 tests)
    ├── RentalTest.java     - Rental class tests (13 tests)
    └── CustomerTest.java   - Customer and pricing logic tests (42 tests)
```

## Key Features

- **Dynamic Genres**: Create custom genres by implementing the `Genre` interface
- **Multiple Output Formats**: Generate statements in plain text or HTML
- **Strategy Pattern**: Pricing logic encapsulated in genre implementations
- **Extensible Design**: Add new genres without modifying existing code

## Business Rules

### Predefined Genres
- **REGULAR** - Standard movies
- **NEW_RELEASE** - New releases with premium pricing
- **CHILDRENS** - Children's movies with discounted rates

### Pricing Logic
| Genre | Base Price | Extra Charge |
|-------|------------|--------------|
| REGULAR | $2.00 | +$1.50/day after 2 days |
| NEW_RELEASE | $3.00/day | - |
| CHILDRENS | $1.50 | +$1.50/day after 3 days |

### Frequent Renter Points
- 1 point per rental
- +1 bonus point for NEW_RELEASE rentals over 1 day

## Creating Custom Genres

```java
Genre documentary = new Genre() {
    @Override
    public String getName() {
        return "Documentary";
    }

    @Override
    public double calculatePrice(int daysRented) {
        return 1.0 + daysRented * 0.5;
    }

    @Override
    public int calculateFrequentRenterPoints(int daysRented) {
        return daysRented > 5 ? 3 : 1;
    }
};

Movie movie = new Movie("Planet Earth", documentary);
```

## Statement Formats

### Plain Text (default)
```java
customer.statement(); // or
customer.statement(StatementFormat.PLAIN_TEXT);
```

### HTML
```java
customer.statement(StatementFormat.HTML);
```

## Requirements

- Java 16+
- Maven (optional) or JUnit 4.13.2

## Running Tests

### With Maven
```bash
mvn test
```

### Without Maven
```bash
# Download dependencies
wget -O junit.jar https://repo1.maven.org/maven2/junit/junit/4.13.2/junit-4.13.2.jar
wget -O hamcrest.jar https://repo1.maven.org/maven2/org/hamcrest/hamcrest-core/1.3/hamcrest-core-1.3.jar

# Compile
mkdir -p target/classes target/test-classes
javac -d target/classes src/main/java/example/*.java
javac -cp target/classes:junit.jar:hamcrest.jar -d target/test-classes src/test/java/example/*.java

# Run tests
java -cp target/classes:target/test-classes:junit.jar:hamcrest.jar \
    org.junit.runner.JUnitCore example.GenreTest example.MovieTest example.RentalTest example.CustomerTest
```

## Test Coverage

**Total: 80 tests**

### GenreTest (18 tests)
- Predefined genres pricing and points calculation
- Custom genre creation
- Edge cases: zero days

### MovieTest (7 tests)
- Creation of all movie types
- Custom genre support
- Edge cases: empty title, special characters, Unicode

### RentalTest (13 tests)
- Rental creation with movie and duration
- Price and points calculation delegation to genre
- Edge cases: 0 days, 1 day, long rentals

### CustomerTest (42 tests)
- **Pricing tests**: All movie types with various rental durations
- **Points tests**: Regular points and bonus points for new releases
- **Format tests**: Plain text and HTML statement generation
- **Rentals management**: getRentals(), addRental()
- **Edge cases**: Empty rentals, zero days, large data sets
- **Custom genre tests**: Dynamic genre support
- **Integration tests**: Complete statement generation scenarios
