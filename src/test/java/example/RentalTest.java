package example;

import org.junit.Test;

import static example.Movie.MovieType.*;
import static org.junit.Assert.*;

/**
 * Unit tests for the Rental class.
 */
public class RentalTest {

    @Test
    public void shouldCreateRentalWithMovieAndDays() {
        Movie movie = new Movie("The Matrix", REGULAR);
        Rental rental = new Rental(movie, 5);

        assertSame(movie, rental.getMovie());
        assertEquals(5, rental.getDaysRented());
    }

    @Test
    public void shouldReturnCorrectDaysRented() {
        Movie movie = new Movie("Avatar", NEW_RELEASE);
        Rental rental = new Rental(movie, 3);

        assertEquals(3, rental.getDaysRented());
    }

    @Test
    public void shouldReturnCorrectMovie() {
        Movie movie = new Movie("Finding Nemo", CHILDRENS);
        Rental rental = new Rental(movie, 7);

        assertEquals("Finding Nemo", rental.getMovie().getTitle());
        assertEquals(CHILDRENS, rental.getMovie().getPriceCode());
    }

    @Test
    public void shouldHandleOneDayRental() {
        Movie movie = new Movie("Short Film", REGULAR);
        Rental rental = new Rental(movie, 1);

        assertEquals(1, rental.getDaysRented());
    }

    @Test
    public void shouldHandleZeroDayRental() {
        Movie movie = new Movie("Not Rented", REGULAR);
        Rental rental = new Rental(movie, 0);

        assertEquals(0, rental.getDaysRented());
    }

    @Test
    public void shouldHandleLongRental() {
        Movie movie = new Movie("Long Movie", REGULAR);
        Rental rental = new Rental(movie, 365);

        assertEquals(365, rental.getDaysRented());
    }

    @Test
    public void shouldPreserveMovieReference() {
        Movie movie = new Movie("Test Movie", NEW_RELEASE);
        Rental rental = new Rental(movie, 2);

        assertSame(movie, rental.getMovie());
    }
}