package by.nareiko.fr.dao.impl.entittymapper;

import by.nareiko.fr.dao.EntityMapper;
import by.nareiko.fr.dao.SqlColumnName;
import by.nareiko.fr.entity.Review;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 * The type Review mapper.
 */
public class ReviewMapper extends EntityMapper<Review> {
    @Override
    public Review initEntity(ResultSet resultSet) throws SQLException {
        Review review = new Review();
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
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String formattedDate = dateFormat.format(date.getTime());
        review.setReviewDate(formattedDate);
        return review;
    }

    private Calendar getDateFromLong(long dateMillis) {
        Calendar date = new GregorianCalendar();
        date.setTimeInMillis(dateMillis);
        return date;
    }
}
