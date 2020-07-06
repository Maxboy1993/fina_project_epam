package by.nareiko.fr.entity;

import java.util.Calendar;

public class Review extends AbstractEntity {
    private int id;
    private int filmId;
    private int userId;
    private String review;
    private Calendar reviewDate;

    public Review() {
    }

    public Review(int id, int filmId, int userId, String review, Calendar reviewDate) {
        this.id = id;
        this.filmId = filmId;
        this.userId = userId;
        this.review = review;
        this.reviewDate = reviewDate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getFilmId() {
        return filmId;
    }

    public void setFilmId(int filmId) {
        this.filmId = filmId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getReview() {
        return review;
    }

    public void setReview(String review) {
        this.review = review;
    }

    public Calendar getReviewDate() {
        return reviewDate;
    }

    public void setReviewDate(Calendar reviewDate) {
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
