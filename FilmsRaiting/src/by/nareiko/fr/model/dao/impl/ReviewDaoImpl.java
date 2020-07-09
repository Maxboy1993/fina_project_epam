package by.nareiko.fr.model.dao.impl;

import by.nareiko.fr.entity.AbstractEntity;
import by.nareiko.fr.entity.FilmRaiting;
import by.nareiko.fr.entity.Review;
import by.nareiko.fr.exception.DaoException;
import by.nareiko.fr.model.dao.BaseDao;
import by.nareiko.fr.model.dao.ReviewDao;
import by.nareiko.fr.model.dao.request.FilmRaitingRequest;
import by.nareiko.fr.model.dao.request.ReviewRequest;
import by.nareiko.fr.pool.ConnectionPool;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

public class ReviewDaoImpl implements ReviewDao<Review> {
    private List<Review> reviews;
    private Review review;
    private static final Logger LOGGER = LogManager.getLogger();
    boolean isCreated;

    public ReviewDaoImpl() {
        reviews = new ArrayList<>();
    }

    @Override
    public List<Review> findAll() {
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(ReviewRequest.FIND_ALL_REVIEWS);
            reviews = initReviews(resultSet);
        } catch (SQLException e) {
            LOGGER.error("SQLException: ", e);
        }
        return reviews;
    }

    @Override
    public List<Review> findByFilmId(int filmId) {
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(ReviewRequest.FIND_REVIEW_BY_ID_FILM)) {
            statement.setInt(1, filmId);
            ResultSet resultSet = statement.executeQuery();
                reviews = initReviews(resultSet);
        } catch (SQLException e) {
            LOGGER.error("SQLException: ", e);
        }
        return reviews;
    }

    @Override
    public Review findById(int id) {
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(ReviewRequest.FIND_REVIEW_BY_ID)) {
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()){
                review = initReview(resultSet);
            }
        } catch (SQLException e) {
            LOGGER.error("SQLException: ", e);
        }
        return review;
    }

    @Override
    public Review delete(Review review) throws DaoException {
        delete(review.getId());
        return review;
    }

    @Override
    public Review delete(int id) {
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(ReviewRequest.DELETE_REVIEW_BY_ID)) {
            statement.setInt(1, id);
            review = findById(id);
            statement.executeUpdate();
        } catch (SQLException e) {
            LOGGER.error("SQLException: ", e);
        }
        return review;
    }

    @Override
    public boolean create(Review review) {
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(ReviewRequest.CREATE_REVIEW)) {
            statement.setInt(1, review.getFilmId());
            statement.setInt(2, review.getUserId());
            statement.setString(3, review.getReview());
            long reviewDate = review.getReviewDate().getTimeInMillis();
            statement.setLong(4, reviewDate);
            statement.executeUpdate();
            ResultSet resultSet = statement.getGeneratedKeys();
            if (resultSet.next()){
                int id = resultSet.getInt(1);
                review.setId(id);
            }
            isCreated = true;
        } catch (SQLException e) {
            LOGGER.error("SQLException: ", e);
        }
        return isCreated;
    }

    @Override
    public Review update(Review review) {
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(ReviewRequest.UPDATE_REVIEW)) {
            statement.setString(1, review.getReview());
            long reviewDate = review.getReviewDate().getTimeInMillis();
            statement.setLong(2, reviewDate);
            statement.setInt(3, review.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            LOGGER.error("SQLException: ", e);
        }
        return review;
    }

    private Calendar getDateFromLong(long dateMillis) {
        Calendar date = new GregorianCalendar();
        date.setTimeInMillis(dateMillis);
        return date;
    }

    private List<Review> initReviews(ResultSet resultSet) throws SQLException {
        while (resultSet.next()) {
            review = initReview(resultSet);
            reviews.add(review);
        }
        return reviews;
    }

    private Review initReview(ResultSet resultSet) throws SQLException {
        int reviewId = resultSet.getInt("reviewId");
        review.setId(reviewId);
        int filmId = resultSet.getInt("filmId");
        review.setFilmId(filmId);
        int userId = resultSet.getInt("userId");
        review.setUserId(userId);
        String reviewText = resultSet.getString("review");
        review.setReview(reviewText);
        long reviewDate = resultSet.getLong("reviewDate");
        Calendar date = getDateFromLong(reviewDate);
        review.setReviewDate(date);
        return review;
    }
}
