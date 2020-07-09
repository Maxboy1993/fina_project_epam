package by.nareiko.fr.model.dao.request;

public class ReviewRequest {
    private ReviewRequest(){}

    public static final String FIND_ALL_REVIEWS = "SELECT reviewId, filmId, userId, review, reviewDate FROM Review";
    public static final String FIND_REVIEW_BY_ID_FILM = "SELECT reviewId, filmId, userId, review, reviewDate FROM Review WHERE filmId = ?";
    public static final String FIND_REVIEW_BY_ID = "SELECT reviewId, filmId, userId, review, reviewDate FROM Review WHERE reviewId = ?";
    public static final String DELETE_REVIEW_BY_ID = "DELETE FROM Review WHERE reviewId = ?";
    public static final String CREATE_REVIEW = "INSERT INTO Review (filmId, userId, review, reviewDate) VALUES (?, ?, ?, ?)";
    public static final String UPDATE_REVIEW = "UPDATE Review SET review = ?, reviewDate = ? WHERE reviewId = ?";

}
