package example.service;

import example.dto.RentalRequest;
import example.dto.RentalResponse;
import example.dto.StatementRequest;
import example.dto.StatementResponse;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

/**
 * Unit tests for RentalService.
 */
public class RentalServiceTest {

    private RentalService rentalService;

    @Before
    public void setUp() {
        GenreService genreService = new GenreService();
        StatementService statementService = new StatementService();
        rentalService = new RentalService(genreService, statementService);
    }

    @Test
    public void shouldGenerateStatementResponse() {
        RentalRequest rental = new RentalRequest("The Matrix", "REGULAR", 3);
        StatementRequest request = new StatementRequest("John", List.of(rental), "PLAIN_TEXT");

        StatementResponse response = rentalService.generateStatement(request);

        assertEquals("John", response.getCustomerName());
        assertEquals(1, response.getRentals().size());
        assertEquals(3.5, response.getTotalAmount(), 0.001);
        assertEquals(1, response.getTotalFrequentRenterPoints());
        assertNotNull(response.getStatement());
    }

    @Test
    public void shouldGenerateHtmlStatement() {
        RentalRequest rental = new RentalRequest("The Matrix", "REGULAR", 2);
        StatementRequest request = new StatementRequest("John", List.of(rental), "HTML");

        StatementResponse response = rentalService.generateStatement(request);

        assertTrue(response.getStatement().contains("<html>"));
        assertTrue(response.getStatement().contains("<h1>Rental Record for John</h1>"));
    }

    @Test
    public void shouldHandleMultipleRentals() {
        RentalRequest rental1 = new RentalRequest("The Matrix", "REGULAR", 3);
        RentalRequest rental2 = new RentalRequest("Avatar 3", "NEW_RELEASE", 2);
        RentalRequest rental3 = new RentalRequest("Finding Nemo", "CHILDRENS", 4);

        StatementRequest request = new StatementRequest("John", List.of(rental1, rental2, rental3), "PLAIN_TEXT");

        StatementResponse response = rentalService.generateStatement(request);

        assertEquals(3, response.getRentals().size());
        assertEquals(12.5, response.getTotalAmount(), 0.001); // 3.5 + 6 + 3
        assertEquals(4, response.getTotalFrequentRenterPoints()); // 1 + 2 + 1
    }

    @Test
    public void shouldCalculateCorrectRentalDetails() {
        RentalRequest rental = new RentalRequest("Avatar 3", "NEW_RELEASE", 5);
        StatementRequest request = new StatementRequest("John", List.of(rental), "PLAIN_TEXT");

        StatementResponse response = rentalService.generateStatement(request);
        RentalResponse rentalResponse = response.getRentals().get(0);

        assertEquals("Avatar 3", rentalResponse.getMovieTitle());
        assertEquals("New Release", rentalResponse.getGenre());
        assertEquals(5, rentalResponse.getDaysRented());
        assertEquals(15.0, rentalResponse.getPrice(), 0.001);
        assertEquals(2, rentalResponse.getFrequentRenterPoints());
    }

    @Test
    public void shouldDefaultToPlainTextFormat() {
        RentalRequest rental = new RentalRequest("The Matrix", "REGULAR", 2);
        StatementRequest request = new StatementRequest("John", List.of(rental), null);

        StatementResponse response = rentalService.generateStatement(request);

        assertFalse(response.getStatement().contains("<html>"));
    }

    @Test
    public void shouldHandleInvalidFormat() {
        RentalRequest rental = new RentalRequest("The Matrix", "REGULAR", 2);
        StatementRequest request = new StatementRequest("John", List.of(rental), "INVALID");

        StatementResponse response = rentalService.generateStatement(request);

        // Should default to plain text
        assertFalse(response.getStatement().contains("<html>"));
    }

    @Test
    public void shouldCalculatePrice() {
        assertEquals(2.0, rentalService.calculatePrice("REGULAR", 2), 0.001);
        assertEquals(6.5, rentalService.calculatePrice("REGULAR", 5), 0.001);
        assertEquals(9.0, rentalService.calculatePrice("NEW_RELEASE", 3), 0.001);
        assertEquals(4.5, rentalService.calculatePrice("CHILDRENS", 5), 0.001);
    }

    @Test
    public void shouldCalculateFrequentRenterPoints() {
        assertEquals(1, rentalService.calculateFrequentRenterPoints("REGULAR", 5));
        assertEquals(1, rentalService.calculateFrequentRenterPoints("NEW_RELEASE", 1));
        assertEquals(2, rentalService.calculateFrequentRenterPoints("NEW_RELEASE", 2));
        assertEquals(1, rentalService.calculateFrequentRenterPoints("CHILDRENS", 5));
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowExceptionForUnknownGenre() {
        RentalRequest rental = new RentalRequest("The Matrix", "UNKNOWN", 2);
        StatementRequest request = new StatementRequest("John", List.of(rental), "PLAIN_TEXT");

        rentalService.generateStatement(request);
    }

    @Test
    public void shouldHandleCaseInsensitiveGenre() {
        RentalRequest rental = new RentalRequest("The Matrix", "regular", 2);
        StatementRequest request = new StatementRequest("John", List.of(rental), "PLAIN_TEXT");

        StatementResponse response = rentalService.generateStatement(request);

        assertEquals(2.0, response.getTotalAmount(), 0.001);
    }
}
