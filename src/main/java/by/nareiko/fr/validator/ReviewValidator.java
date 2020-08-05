package by.nareiko.fr.validator;

public class ReviewValidator {
    private static final int MAX_LENGTH = 300;

    public boolean validateReview(String review) {
        boolean isCorrect = false;
        if (review.length() <= MAX_LENGTH) {
            isCorrect = true;
        }
        return isCorrect;
    }
}
