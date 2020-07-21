package by.nareiko.fr.dao.impl.entittymapper;

import by.nareiko.fr.dao.SqlColumnName;
import by.nareiko.fr.dao.EntityMapper;
import by.nareiko.fr.entity.Review;
import by.nareiko.fr.exception.DaoException;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

public class ReviewMapper extends EntityMapper<Review> {
    @Override
    public Review initEntity(ResultSet resultSet) throws DaoException {
        Review review = new Review();
        try {
            int reviewId = resultSet.getInt(SqlColumnName.REVIEW_ID);
            review.setId(reviewId);
            int filmId = resultSet.getInt(SqlColumnName.FILM_ID);
            review.setFilmId(filmId);
            int userId = resultSet.getInt(SqlColumnName.USER_ID);
            review.setUserId(userId);
            String reviewText = resultSet.getString(SqlColumnName.REVIEW);
            review.setReview(reviewText);
            long reviewDate = resultSet.getLong(SqlColumnName.REVIEW_DATE);
            Calendar date = getDateFromLong(reviewDate);
            review.setReviewDate(date);
        } catch (SQLException e) {
            throw new DaoException("Review isn't inizialized: ", e);
        }
        return review;
    }

    @Override
    public List<Review> initEntities(ResultSet resultSet) throws SQLException, DaoException {
        List<Review> reviews = new ArrayList<>();
        try {
            while (resultSet.next()) {
                Review review = initEntity(resultSet);
                reviews.add(review);
            }
        } catch (SQLException e) {
            throw new DaoException("Reviews aren't inizialized: ", e);
        }
        return reviews;
    }

    private Calendar getDateFromLong(long dateMillis) {
        Calendar date = new GregorianCalendar();
        date.setTimeInMillis(dateMillis);
        return date;
    }
}
