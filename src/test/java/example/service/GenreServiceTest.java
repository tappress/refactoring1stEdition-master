package example.service;

import example.model.Genre;
import example.model.Genres;
import org.junit.Before;
import org.junit.Test;

import java.util.Optional;
import java.util.Set;

import static org.junit.Assert.*;

/**
 * Unit tests for GenreService.
 */
public class GenreServiceTest {

    private GenreService genreService;

    @Before
    public void setUp() {
        genreService = new GenreService();
    }

    @Test
    public void shouldReturnRegularGenre() {
        Optional<Genre> genre = genreService.getGenre("REGULAR");

        assertTrue(genre.isPresent());
        assertEquals("Regular", genre.get().getName());
    }

    @Test
    public void shouldReturnNewReleaseGenre() {
        Optional<Genre> genre = genreService.getGenre("NEW_RELEASE");

        assertTrue(genre.isPresent());
        assertEquals("New Release", genre.get().getName());
    }

    @Test
    public void shouldReturnChildrensGenre() {
        Optional<Genre> genre = genreService.getGenre("CHILDRENS");

        assertTrue(genre.isPresent());
        assertEquals("Children's", genre.get().getName());
    }

    @Test
    public void shouldBeCaseInsensitive() {
        Optional<Genre> genre1 = genreService.getGenre("regular");
        Optional<Genre> genre2 = genreService.getGenre("Regular");
        Optional<Genre> genre3 = genreService.getGenre("REGULAR");

        assertTrue(genre1.isPresent());
        assertTrue(genre2.isPresent());
        assertTrue(genre3.isPresent());
    }

    @Test
    public void shouldReturnEmptyForUnknownGenre() {
        Optional<Genre> genre = genreService.getGenre("UNKNOWN");

        assertFalse(genre.isPresent());
    }

    @Test
    public void shouldThrowExceptionForUnknownGenre() {
        try {
            genreService.getGenreOrThrow("UNKNOWN");
            fail("Expected IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            assertTrue(e.getMessage().contains("Unknown genre"));
        }
    }

    @Test
    public void shouldReturnAvailableGenres() {
        Set<String> genres = genreService.getAvailableGenres();

        assertTrue(genres.contains("REGULAR"));
        assertTrue(genres.contains("NEW_RELEASE"));
        assertTrue(genres.contains("CHILDRENS"));
    }

    @Test
    public void shouldRegisterCustomGenre() {
        Genre premium = new Genre() {
            @Override
            public String getName() {
                return "Premium";
            }

            @Override
            public double calculatePrice(int daysRented) {
                return 5.0 + daysRented * 2.0;
            }

            @Override
            public int calculateFrequentRenterPoints(int daysRented) {
                return 3;
            }
        };

        genreService.registerGenre("PREMIUM", premium);

        assertTrue(genreService.hasGenre("PREMIUM"));
        assertEquals("Premium", genreService.getGenreOrThrow("PREMIUM").getName());
    }

    @Test
    public void shouldCheckIfGenreExists() {
        assertTrue(genreService.hasGenre("REGULAR"));
        assertFalse(genreService.hasGenre("NONEXISTENT"));
    }
}
