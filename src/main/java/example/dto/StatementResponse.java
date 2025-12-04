package example.dto;

import java.util.List;

/**
 * DTO for statement response.
 */
public class StatementResponse {

    private String customerName;
    private List<RentalResponse> rentals;
    private double totalAmount;
    private int totalFrequentRenterPoints;
    private String statement;

    public StatementResponse() {
    }

    public StatementResponse(String customerName, List<RentalResponse> rentals,
                             double totalAmount, int totalFrequentRenterPoints, String statement) {
        this.customerName = customerName;
        this.rentals = rentals;
        this.totalAmount = totalAmount;
        this.totalFrequentRenterPoints = totalFrequentRenterPoints;
        this.statement = statement;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public List<RentalResponse> getRentals() {
        return rentals;
    }

    public void setRentals(List<RentalResponse> rentals) {
        this.rentals = rentals;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public int getTotalFrequentRenterPoints() {
        return totalFrequentRenterPoints;
    }

    public void setTotalFrequentRenterPoints(int totalFrequentRenterPoints) {
        this.totalFrequentRenterPoints = totalFrequentRenterPoints;
    }

    public String getStatement() {
        return statement;
    }

    public void setStatement(String statement) {
        this.statement = statement;
    }
}
