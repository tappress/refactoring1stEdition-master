package example.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import example.dto.RentalRequest;
import example.dto.StatementRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for RentalController.
 */
@SpringBootTest
@AutoConfigureMockMvc
public class RentalControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void shouldGenerateStatement() throws Exception {
        RentalRequest rental = new RentalRequest("The Matrix", "REGULAR", 3);
        StatementRequest request = new StatementRequest("John", List.of(rental), "PLAIN_TEXT");

        mockMvc.perform(post("/api/rentals/statement")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.customerName").value("John"))
                .andExpect(jsonPath("$.totalAmount").value(3.5))
                .andExpect(jsonPath("$.totalFrequentRenterPoints").value(1))
                .andExpect(jsonPath("$.rentals[0].movieTitle").value("The Matrix"));
    }

    @Test
    public void shouldGenerateHtmlStatement() throws Exception {
        RentalRequest rental = new RentalRequest("The Matrix", "REGULAR", 2);
        StatementRequest request = new StatementRequest("John", List.of(rental), "HTML");

        mockMvc.perform(post("/api/rentals/statement")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.statement").value(org.hamcrest.Matchers.containsString("<html>")));
    }

    @Test
    public void shouldHandleMultipleRentals() throws Exception {
        RentalRequest rental1 = new RentalRequest("The Matrix", "REGULAR", 3);
        RentalRequest rental2 = new RentalRequest("Avatar 3", "NEW_RELEASE", 2);
        RentalRequest rental3 = new RentalRequest("Finding Nemo", "CHILDRENS", 4);

        StatementRequest request = new StatementRequest("John", List.of(rental1, rental2, rental3), "PLAIN_TEXT");

        mockMvc.perform(post("/api/rentals/statement")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.totalAmount").value(12.5))
                .andExpect(jsonPath("$.totalFrequentRenterPoints").value(4))
                .andExpect(jsonPath("$.rentals.length()").value(3));
    }

    @Test
    public void shouldReturnBadRequestForInvalidGenre() throws Exception {
        RentalRequest rental = new RentalRequest("The Matrix", "UNKNOWN", 3);
        StatementRequest request = new StatementRequest("John", List.of(rental), "PLAIN_TEXT");

        mockMvc.perform(post("/api/rentals/statement")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void shouldReturnBadRequestForMissingCustomerName() throws Exception {
        RentalRequest rental = new RentalRequest("The Matrix", "REGULAR", 3);
        StatementRequest request = new StatementRequest("", List.of(rental), "PLAIN_TEXT");

        mockMvc.perform(post("/api/rentals/statement")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void shouldReturnBadRequestForEmptyRentals() throws Exception {
        StatementRequest request = new StatementRequest("John", List.of(), "PLAIN_TEXT");

        mockMvc.perform(post("/api/rentals/statement")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void shouldReturnBadRequestForInvalidDaysRented() throws Exception {
        RentalRequest rental = new RentalRequest("The Matrix", "REGULAR", 0);
        StatementRequest request = new StatementRequest("John", List.of(rental), "PLAIN_TEXT");

        mockMvc.perform(post("/api/rentals/statement")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }
}
