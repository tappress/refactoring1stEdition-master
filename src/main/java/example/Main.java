package example;

import java.util.List;

/**
 * Demo application for the movie rental system.
 */
public class Main {
    public static final Movie REMBO = new Movie("Rembo", Genres.REGULAR);
    public static final Movie LOTR = new Movie("Lord of the Rings", Genres.NEW_RELEASE);
    public static final Movie HARRY_POTTER = new Movie("Harry Potter", Genres.CHILDRENS);

    public static void main(String[] args) {
        List<Rental> rentals = List.of(
                new Rental(REMBO, 1),
                new Rental(LOTR, 4),
                new Rental(HARRY_POTTER, 5)
        );

        Customer customer = new Customer("John Doe", rentals);

        System.out.println("=== Plain Text Statement ===");
        System.out.println(customer.statement());

        System.out.println("\n=== HTML Statement ===");
        System.out.println(customer.statement(StatementFormat.HTML));
    }
}
