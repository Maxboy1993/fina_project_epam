package by.nareiko.fr.validator;

/**
 * The type Review validator.
 */
public class ReviewValidator {
    private static final int MAX_LENGTH = 300;

    /**
     * Validate review boolean.
     *
     * @param review the review
     * @return the boolean
     */
    public boolean validateReview(String review) {
        boolean isCorrect = false;
        if (review.length() <= MAX_LENGTH) {
            isCorrect = true;
        }
        return isCorrect;
    }
}
