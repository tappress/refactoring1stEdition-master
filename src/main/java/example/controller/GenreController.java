package example.controller;

import example.service.GenreService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import java.util.Set;

/**
 * REST controller for genre information.
 */
@RestController
@RequestMapping("/api/genres")
public class GenreController {

    private final GenreService genreService;

    public GenreController(GenreService genreService) {
        this.genreService = genreService;
    }

    @GetMapping
    public ResponseEntity<Set<String>> getAvailableGenres() {
        return ResponseEntity.ok(genreService.getAvailableGenres());
    }

    @GetMapping("/{genre}/price")
    public ResponseEntity<Map<String, Object>> calculatePrice(
            @PathVariable String genre,
            @RequestParam int daysRented) {
        double price = genreService.getGenreOrThrow(genre).calculatePrice(daysRented);
        int points = genreService.getGenreOrThrow(genre).calculateFrequentRenterPoints(daysRented);

        return ResponseEntity.ok(Map.of(
                "genre", genre,
                "daysRented", daysRented,
                "price", price,
                "frequentRenterPoints", points
        ));
    }
}
