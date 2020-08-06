package by.nareiko.fr.validator;

import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.*;

public class ReviewValidatorTest {
    private static final String REVIEW = "The film is exited. Everybody should see it! ";
    private static ReviewValidator reviewValidator;

    @BeforeClass
    public static void setUp() {
        reviewValidator = new ReviewValidator();
    }

    @Test
    public void validateReview() {
        boolean expected = true;
        boolean actual = reviewValidator.validateReview(REVIEW);
        assertEquals(expected, actual);
    }
}