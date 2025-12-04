package example.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;

import java.util.List;

/**
 * DTO for statement generation request.
 */
public class StatementRequest {

    @NotBlank(message = "Customer name is required")
    private String customerName;

    @NotEmpty(message = "At least one rental is required")
    @Valid
    private List<RentalRequest> rentals;

    private String format = "PLAIN_TEXT";

    public StatementRequest() {
    }

    public StatementRequest(String customerName, List<RentalRequest> rentals, String format) {
        this.customerName = customerName;
        this.rentals = rentals;
        this.format = format;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public List<RentalRequest> getRentals() {
        return rentals;
    }

    public void setRentals(List<RentalRequest> rentals) {
        this.rentals = rentals;
    }

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }
}
