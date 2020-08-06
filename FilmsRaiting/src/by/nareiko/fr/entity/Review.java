package by.nareiko.fr.entity;

/**
 * The type Review.
 */
public class Review extends AbstractEntity {
    private int id;
    private int filmId;
    private int userId;
    private String review;
    private String reviewDate;

    /**
     * Instantiates a new Review.
     */
    public Review() {
    }

    /**
     * Instantiates a new Review.
     *
     * @param id         the id
     * @param filmId     the film id
     * @param userId     the user id
     * @param review     the review
     * @param reviewDate the review date
     */
    public Review(int id, int filmId, int userId, String review, String reviewDate) {
        this.id = id;
        this.filmId = filmId;
        this.userId = userId;
        this.review = review;
        this.reviewDate = reviewDate;
    }

    /**
     * Gets id.
     *
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * Sets id.
     *
     * @param id the id
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Gets film id.
     *
     * @return the film id
     */
    public int getFilmId() {
        return filmId;
    }

    /**
     * Sets film id.
     *
     * @param filmId the film id
     */
    public void setFilmId(int filmId) {
        this.filmId = filmId;
    }

    /**
     * Gets user id.
     *
     * @return the user id
     */
    public int getUserId() {
        return userId;
    }

    /**
     * Sets user id.
     *
     * @param userId the user id
     */
    public void setUserId(int userId) {
        this.userId = userId;
    }

    /**
     * Gets review.
     *
     * @return the review
     */
    public String getReview() {
        return review;
    }

    /**
     * Sets review.
     *
     * @param review the review
     */
    public void setReview(String review) {
        this.review = review;
    }

    /**
     * Gets review date.
     *
     * @return the review date
     */
    public String getReviewDate() {
        return reviewDate;
    }

    /**
     * Sets review date.
     *
     * @param reviewDate the review date
     */
    public void setReviewDate(String reviewDate) {
        this.reviewDate = reviewDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Review review1 = (Review) o;
        return id == review1.getId() &&
                filmId == review1.getFilmId() &&
                userId == review1.getUserId() &&
                review.equals(review1.getReview()) &&
                reviewDate.equals(review1.getReviewDate());
    }

    @Override
    public int hashCode() {
        int prime = 31;
        int result = 1;
        result += prime * result + id;
        result += prime * result + filmId;
        result += prime * result + userId;
        result += prime * result + review.hashCode();
        result += prime * result + reviewDate.hashCode();
        return result;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append(getClass().getName())
                .append(", id='" + id)
                .append(", filmId='" + filmId)
                .append(", userId='" + userId)
                .append(", review='" + review)
                .append(", reviewDate='" + reviewDate);

        return builder.toString();
    }
}
