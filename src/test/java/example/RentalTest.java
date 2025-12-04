package example;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Unit tests for the Rental class.
 */
public class RentalTest {

    @Test
    public void shouldCreateRentalWithMovieAndDays() {
        Movie movie = new Movie("The Matrix", Genres.REGULAR);
        Rental rental = new Rental(movie, 5);

        assertSame(movie, rental.getMovie());
        assertEquals(5, rental.getDaysRented());
    }

    @Test
    public void shouldReturnCorrectDaysRented() {
        Movie movie = new Movie("Avatar", Genres.NEW_RELEASE);
        Rental rental = new Rental(movie, 3);

        assertEquals(3, rental.getDaysRented());
    }

    @Test
    public void shouldReturnCorrectMovie() {
        Movie movie = new Movie("Finding Nemo", Genres.CHILDRENS);
        Rental rental = new Rental(movie, 7);

        assertEquals("Finding Nemo", rental.getMovie().getTitle());
        assertSame(Genres.CHILDRENS, rental.getMovie().getGenre());
    }

    @Test
    public void shouldHandleOneDayRental() {
        Movie movie = new Movie("Short Film", Genres.REGULAR);
        Rental rental = new Rental(movie, 1);

        assertEquals(1, rental.getDaysRented());
    }

    @Test
    public void shouldHandleZeroDayRental() {
        Movie movie = new Movie("Not Rented", Genres.REGULAR);
        Rental rental = new Rental(movie, 0);

        assertEquals(0, rental.getDaysRented());
    }

    @Test
    public void shouldHandleLongRental() {
        Movie movie = new Movie("Long Movie", Genres.REGULAR);
        Rental rental = new Rental(movie, 365);

        assertEquals(365, rental.getDaysRented());
    }

    @Test
    public void shouldPreserveMovieReference() {
        Movie movie = new Movie("Test Movie", Genres.NEW_RELEASE);
        Rental rental = new Rental(movie, 2);

        assertSame(movie, rental.getMovie());
    }

    @Test
    public void shouldCalculatePriceForRegularMovie() {
        Movie movie = new Movie("The Matrix", Genres.REGULAR);
        Rental rental = new Rental(movie, 5);

        // 2 + (5-2) * 1.5 = 6.5
        assertEquals(6.5, rental.calculatePrice(), 0.001);
    }

    @Test
    public void shouldCalculatePriceForNewRelease() {
        Movie movie = new Movie("Avatar", Genres.NEW_RELEASE);
        Rental rental = new Rental(movie, 3);

        // 3 * 3 = 9
        assertEquals(9.0, rental.calculatePrice(), 0.001);
    }

    @Test
    public void shouldCalculatePriceForChildrensMovie() {
        Movie movie = new Movie("Finding Nemo", Genres.CHILDRENS);
        Rental rental = new Rental(movie, 5);

        // 1.5 + (5-3) * 1.5 = 4.5
        assertEquals(4.5, rental.calculatePrice(), 0.001);
    }

    @Test
    public void shouldCalculateFrequentRenterPointsForRegular() {
        Movie movie = new Movie("The Matrix", Genres.REGULAR);
        Rental rental = new Rental(movie, 5);

        assertEquals(1, rental.calculateFrequentRenterPoints());
    }

    @Test
    public void shouldCalculateFrequentRenterPointsForNewReleaseOneDay() {
        Movie movie = new Movie("Avatar", Genres.NEW_RELEASE);
        Rental rental = new Rental(movie, 1);

        assertEquals(1, rental.calculateFrequentRenterPoints());
    }

    @Test
    public void shouldCalculateBonusPointsForNewReleaseMultipleDays() {
        Movie movie = new Movie("Avatar", Genres.NEW_RELEASE);
        Rental rental = new Rental(movie, 2);

        assertEquals(2, rental.calculateFrequentRenterPoints());
    }
}
