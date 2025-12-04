package example.model;

/**
 * Represents a movie with title and genre.
 */
public class Movie {
    private final String title;
    private final Genre genre;

    public Movie(String title, Genre genre) {
        this.title = title;
        this.genre = genre;
    }

    public Genre getGenre() {
        return genre;
    }

    public String getTitle() {
        return title;
    }
}