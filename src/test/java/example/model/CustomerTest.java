package example.model;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Unit tests for the Customer class.
 */
public class CustomerTest {

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

    @Test
    public void shouldReturnRentals() {
        Movie movie = new Movie("The Matrix", Genres.REGULAR);
        Rental rental = new Rental(movie, 2);
        Customer customer = new Customer("John", List.of(rental));

        assertEquals(1, customer.getRentals().size());
        assertSame(rental, customer.getRentals().get(0));
    }

    @Test
    public void shouldAddRental() {
        Customer customer = new Customer("John", new ArrayList<>());
        Movie movie = new Movie("The Matrix", Genres.REGULAR);
        Rental rental = new Rental(movie, 2);

        customer.addRental(rental);

        assertEquals(1, customer.getRentals().size());
    }

    @Test
    public void shouldReturnTotalAmount() {
        Movie movie = new Movie("The Matrix", Genres.REGULAR);
        Rental rental = new Rental(movie, 5);
        Customer customer = new Customer("John", List.of(rental));

        assertEquals(6.5, customer.getTotalAmount(), 0.001);
    }

    @Test
    public void shouldReturnTotalAmountForMultipleRentals() {
        Movie regularMovie = new Movie("The Matrix", Genres.REGULAR);
        Movie newReleaseMovie = new Movie("Avatar 3", Genres.NEW_RELEASE);
        Movie childrensMovie = new Movie("Finding Nemo", Genres.CHILDRENS);

        Rental rental1 = new Rental(regularMovie, 3);    // 2 + 1.5 = 3.5
        Rental rental2 = new Rental(newReleaseMovie, 2); // 2 * 3 = 6
        Rental rental3 = new Rental(childrensMovie, 4);  // 1.5 + 1.5 = 3.0

        Customer customer = new Customer("John", List.of(rental1, rental2, rental3));

        // Total: 3.5 + 6 + 3.0 = 12.5
        assertEquals(12.5, customer.getTotalAmount(), 0.001);
    }

    @Test
    public void shouldReturnTotalFrequentRenterPoints() {
        Movie regularMovie = new Movie("The Matrix", Genres.REGULAR);
        Movie newReleaseMovie = new Movie("Avatar 3", Genres.NEW_RELEASE);

        Rental rental1 = new Rental(regularMovie, 3);
        Rental rental2 = new Rental(newReleaseMovie, 2);

        Customer customer = new Customer("John", List.of(rental1, rental2));

        // 1 + 2 = 3
        assertEquals(3, customer.getTotalFrequentRenterPoints());
    }

    @Test
    public void shouldHandleCustomerWithNoRentals() {
        Customer customer = new Customer("John", List.of());

        assertEquals(0.0, customer.getTotalAmount(), 0.001);
        assertEquals(0, customer.getTotalFrequentRenterPoints());
    }

    @Test
    public void shouldSupportCustomGenre() {
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
        Rental rental = new Rental(movie, 7);
        Customer customer = new Customer("John", List.of(rental));

        // 1.0 + 7 * 0.5 = 4.5
        assertEquals(4.5, customer.getTotalAmount(), 0.001);
        assertEquals(3, customer.getTotalFrequentRenterPoints());
    }
}
