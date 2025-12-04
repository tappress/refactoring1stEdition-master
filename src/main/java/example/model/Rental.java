package example.model;

/**
 * Represents a rental transaction with a movie and rental duration.
 */
public class Rental {
    private final Movie movie;
    private final int daysRented;

    public Rental(Movie movie, int daysRented) {
        this.movie = movie;
        this.daysRented = daysRented;
    }

    public int getDaysRented() {
        return daysRented;
    }

    public Movie getMovie() {
        return movie;
    }

    public double calculatePrice() {
        return movie.getGenre().calculatePrice(daysRented);
    }

    public int calculateFrequentRenterPoints() {
        return movie.getGenre().calculateFrequentRenterPoints(daysRented);
    }
}