package example.service;

import example.dto.RentalRequest;
import example.dto.RentalResponse;
import example.dto.StatementRequest;
import example.dto.StatementResponse;
import example.model.Customer;
import example.model.Genre;
import example.model.Movie;
import example.model.Rental;
import example.model.StatementFormat;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Service for processing rental operations.
 */
@Service
public class RentalService {

    private final GenreService genreService;
    private final StatementService statementService;

    public RentalService(GenreService genreService, StatementService statementService) {
        this.genreService = genreService;
        this.statementService = statementService;
    }

    public StatementResponse generateStatement(StatementRequest request) {
        Customer customer = createCustomerFromRequest(request);
        StatementFormat format = parseFormat(request.getFormat());
        String statement = statementService.generateStatement(customer, format);

        List<RentalResponse> rentalResponses = createRentalResponses(customer);

        return new StatementResponse(
                customer.getName(),
                rentalResponses,
                customer.getTotalAmount(),
                customer.getTotalFrequentRenterPoints(),
                statement
        );
    }

    public double calculatePrice(String genreKey, int daysRented) {
        Genre genre = genreService.getGenreOrThrow(genreKey);
        return genre.calculatePrice(daysRented);
    }

    public int calculateFrequentRenterPoints(String genreKey, int daysRented) {
        Genre genre = genreService.getGenreOrThrow(genreKey);
        return genre.calculateFrequentRenterPoints(daysRented);
    }

    private Customer createCustomerFromRequest(StatementRequest request) {
        List<Rental> rentals = new ArrayList<>();
        for (RentalRequest rentalRequest : request.getRentals()) {
            Genre genre = genreService.getGenreOrThrow(rentalRequest.getGenre());
            Movie movie = new Movie(rentalRequest.getMovieTitle(), genre);
            Rental rental = new Rental(movie, rentalRequest.getDaysRented());
            rentals.add(rental);
        }
        return new Customer(request.getCustomerName(), rentals);
    }

    private StatementFormat parseFormat(String format) {
        if (format == null || format.isBlank()) {
            return StatementFormat.PLAIN_TEXT;
        }
        try {
            return StatementFormat.valueOf(format.toUpperCase());
        } catch (IllegalArgumentException e) {
            return StatementFormat.PLAIN_TEXT;
        }
    }

    private List<RentalResponse> createRentalResponses(Customer customer) {
        List<RentalResponse> responses = new ArrayList<>();
        for (Rental rental : customer.getRentals()) {
            RentalResponse response = new RentalResponse(
                    rental.getMovie().getTitle(),
                    rental.getMovie().getGenre().getName(),
                    rental.getDaysRented(),
                    rental.calculatePrice(),
                    rental.calculateFrequentRenterPoints()
            );
            responses.add(response);
        }
        return responses;
    }
}
