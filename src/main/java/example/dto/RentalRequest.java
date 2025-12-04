package example.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

/**
 * DTO for rental request.
 */
public class RentalRequest {

    @NotBlank(message = "Movie title is required")
    private String movieTitle;

    @NotBlank(message = "Genre is required")
    private String genre;

    @Min(value = 1, message = "Days rented must be at least 1")
    private int daysRented;

    public RentalRequest() {
    }

    public RentalRequest(String movieTitle, String genre, int daysRented) {
        this.movieTitle = movieTitle;
        this.genre = genre;
        this.daysRented = daysRented;
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
}
