package example.service;

import example.model.Genre;
import example.model.Genres;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

/**
 * Service for managing movie genres.
 * Allows registration of custom genres at runtime.
 */
@Service
public class GenreService {

    private final Map<String, Genre> genreRegistry = new HashMap<>();

    public GenreService() {
        registerPredefinedGenres();
    }

    private void registerPredefinedGenres() {
        registerGenre("REGULAR", Genres.REGULAR);
        registerGenre("NEW_RELEASE", Genres.NEW_RELEASE);
        registerGenre("CHILDRENS", Genres.CHILDRENS);
    }

    public void registerGenre(String key, Genre genre) {
        genreRegistry.put(key.toUpperCase(), genre);
    }

    public Optional<Genre> getGenre(String key) {
        return Optional.ofNullable(genreRegistry.get(key.toUpperCase()));
    }

    public Genre getGenreOrThrow(String key) {
        return getGenre(key)
                .orElseThrow(() -> new IllegalArgumentException("Unknown genre: " + key));
    }

    public Set<String> getAvailableGenres() {
        return genreRegistry.keySet();
    }

    public boolean hasGenre(String key) {
        return genreRegistry.containsKey(key.toUpperCase());
    }
}
