package example.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for GenreController.
 */
@SpringBootTest
@AutoConfigureMockMvc
public class GenreControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void shouldReturnAvailableGenres() throws Exception {
        mockMvc.perform(get("/api/genres"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[?(@=='REGULAR')]").exists())
                .andExpect(jsonPath("$[?(@=='NEW_RELEASE')]").exists())
                .andExpect(jsonPath("$[?(@=='CHILDRENS')]").exists());
    }

    @Test
    public void shouldCalculatePriceForRegularGenre() throws Exception {
        mockMvc.perform(get("/api/genres/REGULAR/price")
                        .param("daysRented", "5"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.genre").value("REGULAR"))
                .andExpect(jsonPath("$.daysRented").value(5))
                .andExpect(jsonPath("$.price").value(6.5))
                .andExpect(jsonPath("$.frequentRenterPoints").value(1));
    }

    @Test
    public void shouldCalculatePriceForNewRelease() throws Exception {
        mockMvc.perform(get("/api/genres/NEW_RELEASE/price")
                        .param("daysRented", "3"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.genre").value("NEW_RELEASE"))
                .andExpect(jsonPath("$.daysRented").value(3))
                .andExpect(jsonPath("$.price").value(9.0))
                .andExpect(jsonPath("$.frequentRenterPoints").value(2));
    }

    @Test
    public void shouldCalculatePriceForChildrensGenre() throws Exception {
        mockMvc.perform(get("/api/genres/CHILDRENS/price")
                        .param("daysRented", "5"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.genre").value("CHILDRENS"))
                .andExpect(jsonPath("$.daysRented").value(5))
                .andExpect(jsonPath("$.price").value(4.5))
                .andExpect(jsonPath("$.frequentRenterPoints").value(1));
    }

    @Test
    public void shouldBeCaseInsensitive() throws Exception {
        mockMvc.perform(get("/api/genres/regular/price")
                        .param("daysRented", "2"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.price").value(2.0));
    }

    @Test
    public void shouldReturnBadRequestForUnknownGenre() throws Exception {
        mockMvc.perform(get("/api/genres/UNKNOWN/price")
                        .param("daysRented", "5"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error").value("Unknown genre: UNKNOWN"));
    }
}
