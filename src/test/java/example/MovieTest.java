package example;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Unit tests for the Movie class.
 */
public class MovieTest {

    @Test
    public void shouldCreateRegularMovie() {
        Movie movie = new Movie("The Matrix", Genres.REGULAR);

        assertEquals("The Matrix", movie.getTitle());
        assertSame(Genres.REGULAR, movie.getGenre());
    }

    @Test
    public void shouldCreateNewReleaseMovie() {
        Movie movie = new Movie("Avatar 3", Genres.NEW_RELEASE);

        assertEquals("Avatar 3", movie.getTitle());
        assertSame(Genres.NEW_RELEASE, movie.getGenre());
    }

    @Test
    public void shouldCreateChildrensMovie() {
        Movie movie = new Movie("Finding Nemo", Genres.CHILDRENS);

        assertEquals("Finding Nemo", movie.getTitle());
        assertSame(Genres.CHILDRENS, movie.getGenre());
    }

    @Test
    public void shouldHandleEmptyTitle() {
        Movie movie = new Movie("", Genres.REGULAR);

        assertEquals("", movie.getTitle());
        assertSame(Genres.REGULAR, movie.getGenre());
    }

    @Test
    public void shouldHandleTitleWithSpecialCharacters() {
        Movie movie = new Movie("Alien: Romulus (2024) - Director's Cut!", Genres.NEW_RELEASE);

        assertEquals("Alien: Romulus (2024) - Director's Cut!", movie.getTitle());
    }

    @Test
    public void shouldHandleUnicodeTitle() {
        Movie movie = new Movie("Українське кіно", Genres.REGULAR);

        assertEquals("Українське кіно", movie.getTitle());
    }

    @Test
    public void shouldAcceptCustomGenre() {
        Genre customGenre = new Genre() {
            @Override
            public String getName() {
                return "Documentary";
            }

            @Override
            public double calculatePrice(int daysRented) {
                return daysRented * 2.5;
            }

            @Override
            public int calculateFrequentRenterPoints(int daysRented) {
                return 1;
            }
        };

        Movie movie = new Movie("Planet Earth", customGenre);

        assertEquals("Planet Earth", movie.getTitle());
        assertEquals("Documentary", movie.getGenre().getName());
    }
}
