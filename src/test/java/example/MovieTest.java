package example;

import org.junit.Test;

import static example.Movie.MovieType.*;
import static org.junit.Assert.*;

/**
 * Unit tests for the Movie class.
 */
public class MovieTest {

    @Test
    public void shouldCreateRegularMovie() {
        Movie movie = new Movie("The Matrix", REGULAR);

        assertEquals("The Matrix", movie.getTitle());
        assertEquals(REGULAR, movie.getPriceCode());
    }

    @Test
    public void shouldCreateNewReleaseMovie() {
        Movie movie = new Movie("Avatar 3", NEW_RELEASE);

        assertEquals("Avatar 3", movie.getTitle());
        assertEquals(NEW_RELEASE, movie.getPriceCode());
    }

    @Test
    public void shouldCreateChildrensMovie() {
        Movie movie = new Movie("Finding Nemo", CHILDRENS);

        assertEquals("Finding Nemo", movie.getTitle());
        assertEquals(CHILDRENS, movie.getPriceCode());
    }

    @Test
    public void shouldHandleEmptyTitle() {
        Movie movie = new Movie("", REGULAR);

        assertEquals("", movie.getTitle());
        assertEquals(REGULAR, movie.getPriceCode());
    }

    @Test
    public void shouldHandleTitleWithSpecialCharacters() {
        Movie movie = new Movie("Alien: Romulus (2024) - Director's Cut!", NEW_RELEASE);

        assertEquals("Alien: Romulus (2024) - Director's Cut!", movie.getTitle());
    }

    @Test
    public void shouldHandleUnicodeTitle() {
        Movie movie = new Movie("Українське кіно", REGULAR);

        assertEquals("Українське кіно", movie.getTitle());
    }
}