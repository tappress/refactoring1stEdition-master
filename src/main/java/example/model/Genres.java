package example.model;

/**
 * Predefined genres with their pricing strategies.
 * New genres can be added here or created dynamically by implementing Genre interface.
 */
public final class Genres {

    private Genres() {
    }

    public static final Genre REGULAR = new Genre() {
        @Override
        public String getName() {
            return "Regular";
        }

        @Override
        public double calculatePrice(int daysRented) {
            double amount = 2;
            if (daysRented > 2) {
                amount += (daysRented - 2) * 1.5;
            }
            return amount;
        }

        @Override
        public int calculateFrequentRenterPoints(int daysRented) {
            return 1;
        }
    };

    public static final Genre NEW_RELEASE = new Genre() {
        @Override
        public String getName() {
            return "New Release";
        }

        @Override
        public double calculatePrice(int daysRented) {
            return daysRented * 3.0;
        }

        @Override
        public int calculateFrequentRenterPoints(int daysRented) {
            return daysRented > 1 ? 2 : 1;
        }
    };

    public static final Genre CHILDRENS = new Genre() {
        @Override
        public String getName() {
            return "Children's";
        }

        @Override
        public double calculatePrice(int daysRented) {
            double amount = 1.5;
            if (daysRented > 3) {
                amount += (daysRented - 3) * 1.5;
            }
            return amount;
        }

        @Override
        public int calculateFrequentRenterPoints(int daysRented) {
            return 1;
        }
    };
}