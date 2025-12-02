# Movie Rental System

A classic movie rental billing system - a famous refactoring kata from Martin Fowler's "Refactoring" book (1st Edition).

## Project Structure

```
src/
├── main/java/example/
│   ├── Movie.java      - Movie entity with title and price code
│   ├── Rental.java     - Rental entity linking movie to rental duration
│   ├── Customer.java   - Customer with rentals and statement generation
│   └── Main.java       - Demo application
└── test/java/example/
    ├── MovieTest.java    - Movie class tests (6 tests)
    ├── RentalTest.java   - Rental class tests (7 tests)
    └── CustomerTest.java - Customer and pricing logic tests (33 tests)
```

## Business Rules

### Movie Types
- **REGULAR** - Standard movies
- **NEW_RELEASE** - New releases with premium pricing
- **CHILDRENS** - Children's movies with discounted rates

### Pricing Logic
| Type | Base Price | Extra Charge |
|------|------------|--------------|
| REGULAR | $2.00 | +$1.50/day after 2 days |
| NEW_RELEASE | $3.00/day | - |
| CHILDRENS | $1.50 | +$1.50/day after 3 days |

### Frequent Renter Points
- 1 point per rental
- +1 bonus point for NEW_RELEASE rentals over 1 day

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
    org.junit.runner.JUnitCore example.MovieTest example.RentalTest example.CustomerTest
```

## Test Coverage

**Total: 46 tests**

### MovieTest (6 tests)
- Creation of all movie types
- Getter methods verification
- Edge cases: empty title, special characters, Unicode

### RentalTest (7 tests)
- Rental creation with movie and duration
- Getter methods verification
- Edge cases: 0 days, 1 day, long rentals

### CustomerTest (33 tests)
- **Pricing tests**: All movie types with various rental durations
- **Points tests**: Regular points and bonus points for new releases
- **Format tests**: Statement header, rental lines, footer
- **Edge cases**: Empty rentals, zero days, large data sets
- **Integration tests**: Complete statement generation scenarios