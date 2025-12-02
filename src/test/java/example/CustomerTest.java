package example;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static example.Movie.MovieType.*;
import static org.junit.Assert.*;

/**
 * Unit tests for the Customer class.
 * Tests cover pricing logic, frequent renter points, and statement formatting.
 */
public class CustomerTest {

    // ============ Customer Name Tests ============

    @Test
    public void shouldReturnCustomerName() {
        Customer customer = new Customer("John Doe", List.of());

        assertEquals("John Doe", customer.getName());
    }

    @Test
    public void shouldHandleEmptyCustomerName() {
        Customer customer = new Customer("", List.of());

        assertEquals("", customer.getName());
    }

    // ============ Empty Rental Tests ============

    @Test
    public void shouldGenerateStatementForCustomerWithNoRentals() {
        Customer customer = new Customer("John", List.of());

        String statement = customer.statement();

        assertTrue(statement.contains("Rental Record for John"));
        assertTrue(statement.contains("Amount owed is 0.0"));
        assertTrue(statement.contains("You earned 0 frequent renter points"));
    }

    // ============ Regular Movie Pricing Tests ============

    @Test
    public void shouldChargeBasePriceForRegularMovieRentedForTwoDays() {
        Movie movie = new Movie("The Matrix", REGULAR);
        Rental rental = new Rental(movie, 2);
        Customer customer = new Customer("John", List.of(rental));

        String statement = customer.statement();

        assertTrue(statement.contains("The Matrix\t2.0"));
        assertTrue(statement.contains("Amount owed is 2.0"));
    }

    @Test
    public void shouldChargeBasePriceForRegularMovieRentedForOneDay() {
        Movie movie = new Movie("The Matrix", REGULAR);
        Rental rental = new Rental(movie, 1);
        Customer customer = new Customer("John", List.of(rental));

        String statement = customer.statement();

        assertTrue(statement.contains("The Matrix\t2.0"));
        assertTrue(statement.contains("Amount owed is 2.0"));
    }

    @Test
    public void shouldAddExtraChargeForRegularMovieRentedOverTwoDays() {
        Movie movie = new Movie("The Matrix", REGULAR);
        Rental rental = new Rental(movie, 5);
        Customer customer = new Customer("John", List.of(rental));

        String statement = customer.statement();

        // 2 + (5-2) * 1.5 = 2 + 4.5 = 6.5
        assertTrue(statement.contains("The Matrix\t6.5"));
        assertTrue(statement.contains("Amount owed is 6.5"));
    }

    @Test
    public void shouldChargeCorrectlyForRegularMovieRentedForThreeDays() {
        Movie movie = new Movie("The Matrix", REGULAR);
        Rental rental = new Rental(movie, 3);
        Customer customer = new Customer("John", List.of(rental));

        String statement = customer.statement();

        // 2 + (3-2) * 1.5 = 2 + 1.5 = 3.5
        assertTrue(statement.contains("The Matrix\t3.5"));
        assertTrue(statement.contains("Amount owed is 3.5"));
    }

    // ============ New Release Movie Pricing Tests ============

    @Test
    public void shouldChargePerDayForNewReleaseMovie() {
        Movie movie = new Movie("Avatar 3", NEW_RELEASE);
        Rental rental = new Rental(movie, 1);
        Customer customer = new Customer("John", List.of(rental));

        String statement = customer.statement();

        // 1 * 3 = 3
        assertTrue(statement.contains("Avatar 3\t3.0"));
        assertTrue(statement.contains("Amount owed is 3.0"));
    }

    @Test
    public void shouldChargeCorrectlyForNewReleaseRentedMultipleDays() {
        Movie movie = new Movie("Avatar 3", NEW_RELEASE);
        Rental rental = new Rental(movie, 5);
        Customer customer = new Customer("John", List.of(rental));

        String statement = customer.statement();

        // 5 * 3 = 15
        assertTrue(statement.contains("Avatar 3\t15.0"));
        assertTrue(statement.contains("Amount owed is 15.0"));
    }

    // ============ Children's Movie Pricing Tests ============

    @Test
    public void shouldChargeBasePriceForChildrensMovieRentedForThreeDays() {
        Movie movie = new Movie("Finding Nemo", CHILDRENS);
        Rental rental = new Rental(movie, 3);
        Customer customer = new Customer("John", List.of(rental));

        String statement = customer.statement();

        // Base price: 1.5
        assertTrue(statement.contains("Finding Nemo\t1.5"));
        assertTrue(statement.contains("Amount owed is 1.5"));
    }

    @Test
    public void shouldChargeBasePriceForChildrensMovieRentedForOneDay() {
        Movie movie = new Movie("Finding Nemo", CHILDRENS);
        Rental rental = new Rental(movie, 1);
        Customer customer = new Customer("John", List.of(rental));

        String statement = customer.statement();

        // Base price: 1.5
        assertTrue(statement.contains("Finding Nemo\t1.5"));
        assertTrue(statement.contains("Amount owed is 1.5"));
    }

    @Test
    public void shouldAddExtraChargeForChildrensMovieRentedOverThreeDays() {
        Movie movie = new Movie("Finding Nemo", CHILDRENS);
        Rental rental = new Rental(movie, 5);
        Customer customer = new Customer("John", List.of(rental));

        String statement = customer.statement();

        // 1.5 + (5-3) * 1.5 = 1.5 + 3 = 4.5
        assertTrue(statement.contains("Finding Nemo\t4.5"));
        assertTrue(statement.contains("Amount owed is 4.5"));
    }

    @Test
    public void shouldChargeCorrectlyForChildrensMovieRentedForFourDays() {
        Movie movie = new Movie("Finding Nemo", CHILDRENS);
        Rental rental = new Rental(movie, 4);
        Customer customer = new Customer("John", List.of(rental));

        String statement = customer.statement();

        // 1.5 + (4-3) * 1.5 = 1.5 + 1.5 = 3.0
        assertTrue(statement.contains("Finding Nemo\t3.0"));
        assertTrue(statement.contains("Amount owed is 3.0"));
    }

    // ============ Frequent Renter Points Tests ============

    @Test
    public void shouldEarnOnePointForRegularMovieRental() {
        Movie movie = new Movie("The Matrix", REGULAR);
        Rental rental = new Rental(movie, 5);
        Customer customer = new Customer("John", List.of(rental));

        String statement = customer.statement();

        assertTrue(statement.contains("You earned 1 frequent renter points"));
    }

    @Test
    public void shouldEarnOnePointForChildrensMovieRental() {
        Movie movie = new Movie("Finding Nemo", CHILDRENS);
        Rental rental = new Rental(movie, 5);
        Customer customer = new Customer("John", List.of(rental));

        String statement = customer.statement();

        assertTrue(statement.contains("You earned 1 frequent renter points"));
    }

    @Test
    public void shouldEarnOnePointForNewReleaseRentedForOneDay() {
        Movie movie = new Movie("Avatar 3", NEW_RELEASE);
        Rental rental = new Rental(movie, 1);
        Customer customer = new Customer("John", List.of(rental));

        String statement = customer.statement();

        assertTrue(statement.contains("You earned 1 frequent renter points"));
    }

    @Test
    public void shouldEarnBonusPointForNewReleaseRentedForMoreThanOneDay() {
        Movie movie = new Movie("Avatar 3", NEW_RELEASE);
        Rental rental = new Rental(movie, 2);
        Customer customer = new Customer("John", List.of(rental));

        String statement = customer.statement();

        assertTrue(statement.contains("You earned 2 frequent renter points"));
    }

    @Test
    public void shouldEarnBonusPointForNewReleaseRentedForMultipleDays() {
        Movie movie = new Movie("Avatar 3", NEW_RELEASE);
        Rental rental = new Rental(movie, 5);
        Customer customer = new Customer("John", List.of(rental));

        String statement = customer.statement();

        assertTrue(statement.contains("You earned 2 frequent renter points"));
    }

    // ============ Multiple Rentals Tests ============

    @Test
    public void shouldCalculateTotalForMultipleRentals() {
        Movie regularMovie = new Movie("The Matrix", REGULAR);
        Movie newReleaseMovie = new Movie("Avatar 3", NEW_RELEASE);
        Movie childrensMovie = new Movie("Finding Nemo", CHILDRENS);

        Rental rental1 = new Rental(regularMovie, 3);    // 2 + 1.5 = 3.5
        Rental rental2 = new Rental(newReleaseMovie, 2); // 2 * 3 = 6
        Rental rental3 = new Rental(childrensMovie, 4);  // 1.5 + 1.5 = 3.0

        Customer customer = new Customer("John", List.of(rental1, rental2, rental3));

        String statement = customer.statement();

        // Total: 3.5 + 6 + 3.0 = 12.5
        assertTrue(statement.contains("Amount owed is 12.5"));
    }

    @Test
    public void shouldCalculatePointsForMultipleRentals() {
        Movie regularMovie = new Movie("The Matrix", REGULAR);
        Movie newReleaseMovie = new Movie("Avatar 3", NEW_RELEASE);
        Movie childrensMovie = new Movie("Finding Nemo", CHILDRENS);

        Rental rental1 = new Rental(regularMovie, 3);    // 1 point
        Rental rental2 = new Rental(newReleaseMovie, 2); // 2 points (bonus for >1 day)
        Rental rental3 = new Rental(childrensMovie, 4);  // 1 point

        Customer customer = new Customer("John", List.of(rental1, rental2, rental3));

        String statement = customer.statement();

        // Total: 1 + 2 + 1 = 4 points
        assertTrue(statement.contains("You earned 4 frequent renter points"));
    }

    @Test
    public void shouldListAllRentalsInStatement() {
        Movie movie1 = new Movie("The Matrix", REGULAR);
        Movie movie2 = new Movie("Avatar 3", NEW_RELEASE);

        Rental rental1 = new Rental(movie1, 2);
        Rental rental2 = new Rental(movie2, 3);

        Customer customer = new Customer("John", List.of(rental1, rental2));

        String statement = customer.statement();

        assertTrue(statement.contains("The Matrix"));
        assertTrue(statement.contains("Avatar 3"));
    }

    // ============ Statement Format Tests ============

    @Test
    public void shouldFormatStatementWithCorrectHeader() {
        Customer customer = new Customer("Jane Doe", List.of());

        String statement = customer.statement();

        assertTrue(statement.startsWith("Rental Record for Jane Doe\n"));
    }

    @Test
    public void shouldFormatStatementWithCorrectFooter() {
        Movie movie = new Movie("Test Movie", REGULAR);
        Rental rental = new Rental(movie, 2);
        Customer customer = new Customer("John", List.of(rental));

        String statement = customer.statement();

        assertTrue(statement.contains("Amount owed is"));
        assertTrue(statement.endsWith("frequent renter points"));
    }

    @Test
    public void shouldFormatRentalLineWithTabSeparators() {
        Movie movie = new Movie("Test Movie", REGULAR);
        Rental rental = new Rental(movie, 2);
        Customer customer = new Customer("John", List.of(rental));

        String statement = customer.statement();

        assertTrue(statement.contains("\tTest Movie\t2.0\n"));
    }

    // ============ Edge Cases Tests ============

    @Test
    public void shouldHandleZeroDayRental() {
        Movie movie = new Movie("Free Preview", REGULAR);
        Rental rental = new Rental(movie, 0);
        Customer customer = new Customer("John", List.of(rental));

        String statement = customer.statement();

        // Base price: 2, no extra charge
        assertTrue(statement.contains("Free Preview\t2.0"));
        assertTrue(statement.contains("You earned 1 frequent renter points"));
    }

    @Test
    public void shouldHandleZeroDayNewRelease() {
        Movie movie = new Movie("Free Preview", NEW_RELEASE);
        Rental rental = new Rental(movie, 0);
        Customer customer = new Customer("John", List.of(rental));

        String statement = customer.statement();

        // 0 * 3 = 0
        assertTrue(statement.contains("Free Preview\t0.0"));
        // No bonus point for <= 1 day
        assertTrue(statement.contains("You earned 1 frequent renter points"));
    }

    @Test
    public void shouldHandleZeroDayChildrens() {
        Movie movie = new Movie("Free Preview", CHILDRENS);
        Rental rental = new Rental(movie, 0);
        Customer customer = new Customer("John", List.of(rental));

        String statement = customer.statement();

        // Base price: 1.5
        assertTrue(statement.contains("Free Preview\t1.5"));
    }

    @Test
    public void shouldHandleLargeNumberOfRentals() {
        List<Rental> rentals = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            Movie movie = new Movie("Movie " + i, REGULAR);
            rentals.add(new Rental(movie, 1));
        }
        Customer customer = new Customer("John", rentals);

        String statement = customer.statement();

        // 100 rentals * $2 each = $200
        assertTrue(statement.contains("Amount owed is 200.0"));
        // 100 rentals * 1 point each = 100 points
        assertTrue(statement.contains("You earned 100 frequent renter points"));
    }

    @Test
    public void shouldHandleLongRentalPeriod() {
        Movie movie = new Movie("Long Rental", REGULAR);
        Rental rental = new Rental(movie, 365);
        Customer customer = new Customer("John", List.of(rental));

        String statement = customer.statement();

        // 2 + (365-2) * 1.5 = 2 + 544.5 = 546.5
        assertTrue(statement.contains("Long Rental\t546.5"));
    }

    @Test
    public void shouldHandleMovieTitleWithSpecialCharacters() {
        Movie movie = new Movie("Alien: Romulus (2024)", NEW_RELEASE);
        Rental rental = new Rental(movie, 2);
        Customer customer = new Customer("John", List.of(rental));

        String statement = customer.statement();

        assertTrue(statement.contains("Alien: Romulus (2024)"));
    }

    @Test
    public void shouldHandleCustomerNameWithSpecialCharacters() {
        Customer customer = new Customer("O'Brien-Smith", List.of());

        String statement = customer.statement();

        assertTrue(statement.contains("Rental Record for O'Brien-Smith"));
    }

    // ============ Integration Tests ============

    @Test
    public void shouldGenerateCompleteStatementForTypicalScenario() {
        Movie regularMovie = new Movie("The Matrix", REGULAR);
        Movie newReleaseMovie = new Movie("Avatar 3", NEW_RELEASE);
        Movie childrensMovie = new Movie("Finding Nemo", CHILDRENS);

        Rental rental1 = new Rental(regularMovie, 3);
        Rental rental2 = new Rental(newReleaseMovie, 2);
        Rental rental3 = new Rental(childrensMovie, 4);

        Customer customer = new Customer("John Smith", List.of(rental1, rental2, rental3));

        String expected = """
                Rental Record for John Smith
                \tThe Matrix\t3.5
                \tAvatar 3\t6.0
                \tFinding Nemo\t3.0
                Amount owed is 12.5
                You earned 4 frequent renter points""";

        assertEquals(expected, customer.statement());
    }

    @Test
    public void shouldMatchExpectedOutputFromMainDemo() {
        Movie regularMovie = new Movie("Gone with the Wind", REGULAR);
        Movie newReleaseMovie = new Movie("Avengers Endgame", NEW_RELEASE);
        Movie childrensMovie = new Movie("Snow White", CHILDRENS);

        Rental rental1 = new Rental(regularMovie, 3);
        Rental rental2 = new Rental(newReleaseMovie, 1);
        Rental rental3 = new Rental(childrensMovie, 10);

        Customer customer = new Customer("John", List.of(rental1, rental2, rental3));

        String statement = customer.statement();

        // Regular: 2 + (3-2)*1.5 = 3.5
        // New Release: 1*3 = 3
        // Children's: 1.5 + (10-3)*1.5 = 1.5 + 10.5 = 12
        // Total: 3.5 + 3 + 12 = 18.5

        assertTrue(statement.contains("Gone with the Wind\t3.5"));
        assertTrue(statement.contains("Avengers Endgame\t3.0"));
        assertTrue(statement.contains("Snow White\t12.0"));
        assertTrue(statement.contains("Amount owed is 18.5"));
        // Points: 1 + 1 (no bonus for 1 day new release) + 1 = 3
        assertTrue(statement.contains("You earned 3 frequent renter points"));
    }
}