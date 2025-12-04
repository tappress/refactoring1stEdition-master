package example.service;

import example.model.Customer;
import example.model.Rental;
import example.model.StatementFormat;
import org.springframework.stereotype.Service;

/**
 * Service for generating rental statements.
 */
@Service
public class StatementService {

    public String generateStatement(Customer customer, StatementFormat format) {
        return switch (format) {
            case PLAIN_TEXT -> generatePlainTextStatement(customer);
            case HTML -> generateHtmlStatement(customer);
        };
    }

    public String generateStatement(Customer customer) {
        return generateStatement(customer, StatementFormat.PLAIN_TEXT);
    }

    private String generatePlainTextStatement(Customer customer) {
        StringBuilder result = new StringBuilder();
        result.append("Rental Record for ").append(customer.getName()).append("\n");

        for (Rental rental : customer.getRentals()) {
            result.append("\t")
                  .append(rental.getMovie().getTitle())
                  .append("\t")
                  .append(rental.calculatePrice())
                  .append("\n");
        }

        result.append("Amount owed is ").append(customer.getTotalAmount()).append("\n");
        result.append("You earned ").append(customer.getTotalFrequentRenterPoints()).append(" frequent renter points");

        return result.toString();
    }

    private String generateHtmlStatement(Customer customer) {
        StringBuilder result = new StringBuilder();
        result.append("<html>\n");
        result.append("<head><title>Rental Statement</title></head>\n");
        result.append("<body>\n");
        result.append("<h1>Rental Record for ").append(customer.getName()).append("</h1>\n");
        result.append("<table>\n");
        result.append("<tr><th>Title</th><th>Price</th></tr>\n");

        for (Rental rental : customer.getRentals()) {
            result.append("<tr><td>")
                  .append(rental.getMovie().getTitle())
                  .append("</td><td>")
                  .append(rental.calculatePrice())
                  .append("</td></tr>\n");
        }

        result.append("</table>\n");
        result.append("<p>Amount owed is <strong>").append(customer.getTotalAmount()).append("</strong></p>\n");
        result.append("<p>You earned <strong>").append(customer.getTotalFrequentRenterPoints()).append("</strong> frequent renter points</p>\n");
        result.append("</body>\n");
        result.append("</html>");

        return result.toString();
    }
}
