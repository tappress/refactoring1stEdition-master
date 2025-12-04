package example;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Represents a customer with rentals and statement generation.
 */
public class Customer {
    private final String name;
    private final List<Rental> rentals;

    public Customer(String name, List<Rental> rentals) {
        this.name = name;
        this.rentals = new ArrayList<>(rentals);
    }

    public String getName() {
        return name;
    }

    public List<Rental> getRentals() {
        return Collections.unmodifiableList(rentals);
    }

    public void addRental(Rental rental) {
        rentals.add(rental);
    }

    public String statement() {
        return statement(StatementFormat.PLAIN_TEXT);
    }

    public String statement(StatementFormat format) {
        return switch (format) {
            case PLAIN_TEXT -> generatePlainTextStatement();
            case HTML -> generateHtmlStatement();
        };
    }

    private String generatePlainTextStatement() {
        StringBuilder result = new StringBuilder();
        result.append("Rental Record for ").append(getName()).append("\n");

        for (Rental rental : rentals) {
            result.append("\t")
                  .append(rental.getMovie().getTitle())
                  .append("\t")
                  .append(rental.calculatePrice())
                  .append("\n");
        }

        result.append("Amount owed is ").append(getTotalAmount()).append("\n");
        result.append("You earned ").append(getTotalFrequentRenterPoints()).append(" frequent renter points");

        return result.toString();
    }

    private String generateHtmlStatement() {
        StringBuilder result = new StringBuilder();
        result.append("<html>\n");
        result.append("<head><title>Rental Statement</title></head>\n");
        result.append("<body>\n");
        result.append("<h1>Rental Record for ").append(getName()).append("</h1>\n");
        result.append("<table>\n");
        result.append("<tr><th>Title</th><th>Price</th></tr>\n");

        for (Rental rental : rentals) {
            result.append("<tr><td>")
                  .append(rental.getMovie().getTitle())
                  .append("</td><td>")
                  .append(rental.calculatePrice())
                  .append("</td></tr>\n");
        }

        result.append("</table>\n");
        result.append("<p>Amount owed is <strong>").append(getTotalAmount()).append("</strong></p>\n");
        result.append("<p>You earned <strong>").append(getTotalFrequentRenterPoints()).append("</strong> frequent renter points</p>\n");
        result.append("</body>\n");
        result.append("</html>");

        return result.toString();
    }

    public double getTotalAmount() {
        double total = 0;
        for (Rental rental : rentals) {
            total += rental.calculatePrice();
        }
        return total;
    }

    public int getTotalFrequentRenterPoints() {
        int points = 0;
        for (Rental rental : rentals) {
            points += rental.calculateFrequentRenterPoints();
        }
        return points;
    }
}
