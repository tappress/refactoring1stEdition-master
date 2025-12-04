package example.dto;

/**
 * DTO for rental information in response.
 */
public class RentalResponse {

    private String movieTitle;
    private String genre;
    private int daysRented;
    private double price;
    private int frequentRenterPoints;

    public RentalResponse() {
    }

    public RentalResponse(String movieTitle, String genre, int daysRented, double price, int frequentRenterPoints) {
        this.movieTitle = movieTitle;
        this.genre = genre;
        this.daysRented = daysRented;
        this.price = price;
        this.frequentRenterPoints = frequentRenterPoints;
    }

    public String getMovieTitle() {
        return movieTitle;
    }

    public void setMovieTitle(String movieTitle) {
        this.movieTitle = movieTitle;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public int getDaysRented() {
        return daysRented;
    }

    public void setDaysRented(int daysRented) {
        this.daysRented = daysRented;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getFrequentRenterPoints() {
        return frequentRenterPoints;
    }

    public void setFrequentRenterPoints(int frequentRenterPoints) {
        this.frequentRenterPoints = frequentRenterPoints;
    }
}
