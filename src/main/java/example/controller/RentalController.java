package example.controller;

import example.dto.StatementRequest;
import example.dto.StatementResponse;
import example.service.RentalService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST controller for rental operations.
 */
@RestController
@RequestMapping("/api/rentals")
public class RentalController {

    private final RentalService rentalService;

    public RentalController(RentalService rentalService) {
        this.rentalService = rentalService;
    }

    @PostMapping("/statement")
    public ResponseEntity<StatementResponse> generateStatement(@Valid @RequestBody StatementRequest request) {
        StatementResponse response = rentalService.generateStatement(request);
        return ResponseEntity.ok(response);
    }
}
