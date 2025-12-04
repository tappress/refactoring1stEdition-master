package example;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Unit tests for the Genre interface and Genres class.
 */
public class GenreTest {

    // ============ Regular Genre Tests ============

    @Test
    public void regularGenreShouldHaveCorrectName() {
        assertEquals("Regular", Genres.REGULAR.getName());
    }

    @Test
    public void regularGenreShouldCalculateBasePriceForTwoDays() {
        assertEquals(2.0, Genres.REGULAR.calculatePrice(2), 0.001);
    }

    @Test
    public void regularGenreShouldCalculateBasePriceForOneDay() {
        assertEquals(2.0, Genres.REGULAR.calculatePrice(1), 0.001);
    }

    @Test
    public void regularGenreShouldAddExtraChargeAfterTwoDays() {
        // 2 + (5-2) * 1.5 = 6.5
        assertEquals(6.5, Genres.REGULAR.calculatePrice(5), 0.001);
    }

    @Test
    public void regularGenreShouldReturnOnePoint() {
        assertEquals(1, Genres.REGULAR.calculateFrequentRenterPoints(5));
    }

    // ============ New Release Genre Tests ============

    @Test
    public void newReleaseGenreShouldHaveCorrectName() {
        assertEquals("New Release", Genres.NEW_RELEASE.getName());
    }

    @Test
    public void newReleaseGenreShouldCalculatePricePerDay() {
        assertEquals(3.0, Genres.NEW_RELEASE.calculatePrice(1), 0.001);
        assertEquals(15.0, Genres.NEW_RELEASE.calculatePrice(5), 0.001);
    }

    @Test
    public void newReleaseGenreShouldReturnOnePointForOneDay() {
        assertEquals(1, Genres.NEW_RELEASE.calculateFrequentRenterPoints(1));
    }

    @Test
    public void newReleaseGenreShouldReturnTwoPointsForMoreThanOneDay() {
        assertEquals(2, Genres.NEW_RELEASE.calculateFrequentRenterPoints(2));
        assertEquals(2, Genres.NEW_RELEASE.calculateFrequentRenterPoints(5));
    }

    // ============ Children's Genre Tests ============

    @Test
    public void childrensGenreShouldHaveCorrectName() {
        assertEquals("Children's", Genres.CHILDRENS.getName());
    }

    @Test
    public void childrensGenreShouldCalculateBasePriceForThreeDays() {
        assertEquals(1.5, Genres.CHILDRENS.calculatePrice(3), 0.001);
    }

    @Test
    public void childrensGenreShouldCalculateBasePriceForOneDay() {
        assertEquals(1.5, Genres.CHILDRENS.calculatePrice(1), 0.001);
    }

    @Test
    public void childrensGenreShouldAddExtraChargeAfterThreeDays() {
        // 1.5 + (5-3) * 1.5 = 4.5
        assertEquals(4.5, Genres.CHILDRENS.calculatePrice(5), 0.001);
    }

    @Test
    public void childrensGenreShouldReturnOnePoint() {
        assertEquals(1, Genres.CHILDRENS.calculateFrequentRenterPoints(5));
    }

    // ============ Custom Genre Tests ============

    @Test
    public void shouldAllowCreationOfCustomGenre() {
        Genre premium = new Genre() {
            @Override
            public String getName() {
                return "Premium";
            }

            @Override
            public double calculatePrice(int daysRented) {
                return 5.0 + daysRented * 2.0;
            }

            @Override
            public int calculateFrequentRenterPoints(int daysRented) {
                return 3;
            }
        };

        assertEquals("Premium", premium.getName());
        assertEquals(15.0, premium.calculatePrice(5), 0.001);
        assertEquals(3, premium.calculateFrequentRenterPoints(5));
    }

    // ============ Edge Cases ============

    @Test
    public void regularGenreShouldHandleZeroDays() {
        assertEquals(2.0, Genres.REGULAR.calculatePrice(0), 0.001);
    }

    @Test
    public void newReleaseGenreShouldHandleZeroDays() {
        assertEquals(0.0, Genres.NEW_RELEASE.calculatePrice(0), 0.001);
    }

    @Test
    public void childrensGenreShouldHandleZeroDays() {
        assertEquals(1.5, Genres.CHILDRENS.calculatePrice(0), 0.001);
    }

    @Test
    public void newReleaseGenreShouldReturnOnePointForZeroDays() {
        assertEquals(1, Genres.NEW_RELEASE.calculateFrequentRenterPoints(0));
    }
}
