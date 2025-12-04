package example.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Represents a customer with rentals.
 */
public class Customer {
    private final String name;
    private final List<Rental> rentals;

    public Customer(String name) {
        this.name = name;
        this.rentals = new ArrayList<>();
    }

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