package example.service;

import example.model.Customer;
import example.model.Genres;
import example.model.Movie;
import example.model.Rental;
import example.model.StatementFormat;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

/**
 * Unit tests for StatementService.
 */
public class StatementServiceTest {

    private StatementService statementService;

    @Before
    public void setUp() {
        statementService = new StatementService();
    }

    @Test
    public void shouldGeneratePlainTextStatement() {
        Movie movie = new Movie("The Matrix", Genres.REGULAR);
        Rental rental = new Rental(movie, 2);
        Customer customer = new Customer("John", List.of(rental));

        String statement = statementService.generateStatement(customer, StatementFormat.PLAIN_TEXT);

        assertTrue(statement.contains("Rental Record for John"));
        assertTrue(statement.contains("The Matrix\t2.0"));
        assertTrue(statement.contains("Amount owed is 2.0"));
        assertTrue(statement.contains("You earned 1 frequent renter points"));
    }

    @Test
    public void shouldGenerateHtmlStatement() {
        Movie movie = new Movie("The Matrix", Genres.REGULAR);
        Rental rental = new Rental(movie, 2);
        Customer customer = new Customer("John", List.of(rental));

        String statement = statementService.generateStatement(customer, StatementFormat.HTML);

        assertTrue(statement.contains("<html>"));
        assertTrue(statement.contains("</html>"));
        assertTrue(statement.contains("<h1>Rental Record for John</h1>"));
        assertTrue(statement.contains("<table>"));
        assertTrue(statement.contains("<td>The Matrix</td>"));
        assertTrue(statement.contains("<td>2.0</td>"));
        assertTrue(statement.contains("<strong>2.0</strong>"));
    }

    @Test
    public void shouldDefaultToPlainTextStatement() {
        Movie movie = new Movie("The Matrix", Genres.REGULAR);
        Rental rental = new Rental(movie, 2);
        Customer customer = new Customer("John", List.of(rental));

        String defaultStatement = statementService.generateStatement(customer);
        String plainTextStatement = statementService.generateStatement(customer, StatementFormat.PLAIN_TEXT);

        assertEquals(defaultStatement, plainTextStatement);
    }

    @Test
    public void shouldGenerateStatementForMultipleRentals() {
        Movie regularMovie = new Movie("The Matrix", Genres.REGULAR);
        Movie newReleaseMovie = new Movie("Avatar 3", Genres.NEW_RELEASE);
        Movie childrensMovie = new Movie("Finding Nemo", Genres.CHILDRENS);

        Rental rental1 = new Rental(regularMovie, 3);
        Rental rental2 = new Rental(newReleaseMovie, 2);
        Rental rental3 = new Rental(childrensMovie, 4);

        Customer customer = new Customer("John Smith", List.of(rental1, rental2, rental3));

        String statement = statementService.generateStatement(customer, StatementFormat.PLAIN_TEXT);

        assertTrue(statement.contains("The Matrix\t3.5"));
        assertTrue(statement.contains("Avatar 3\t6.0"));
        assertTrue(statement.contains("Finding Nemo\t3.0"));
        assertTrue(statement.contains("Amount owed is 12.5"));
        assertTrue(statement.contains("You earned 4 frequent renter points"));
    }

    @Test
    public void shouldGenerateStatementForCustomerWithNoRentals() {
        Customer customer = new Customer("John", List.of());

        String statement = statementService.generateStatement(customer);

        assertTrue(statement.contains("Rental Record for John"));
        assertTrue(statement.contains("Amount owed is 0.0"));
        assertTrue(statement.contains("You earned 0 frequent renter points"));
    }

    @Test
    public void shouldGenerateHtmlStatementForMultipleRentals() {
        Movie regularMovie = new Movie("The Matrix", Genres.REGULAR);
        Movie newReleaseMovie = new Movie("Avatar 3", Genres.NEW_RELEASE);

        Rental rental1 = new Rental(regularMovie, 2);
        Rental rental2 = new Rental(newReleaseMovie, 3);

        Customer customer = new Customer("John", List.of(rental1, rental2));

        String statement = statementService.generateStatement(customer, StatementFormat.HTML);

        assertTrue(statement.contains("<td>The Matrix</td>"));
        assertTrue(statement.contains("<td>Avatar 3</td>"));
        assertTrue(statement.contains("<strong>11.0</strong>")); // 2 + 9 = 11
        assertTrue(statement.contains("<strong>3</strong> frequent renter points")); // 1 + 2 = 3
    }
}
