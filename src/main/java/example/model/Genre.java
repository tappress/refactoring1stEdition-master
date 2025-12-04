package example.model;

/**
 * Represents a movie genre with its pricing strategy.
 * Allows dynamic addition of new genres without modifying existing code.
 */
public interface Genre {

    String getName();

    double calculatePrice(int daysRented);

    int calculateFrequentRenterPoints(int daysRented);
}