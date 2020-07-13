package by.nareiko.fr.dao.impl;

import by.nareiko.fr.dao.*;
import by.nareiko.fr.entity.Review;
import by.nareiko.fr.exception.DaoException;
import by.nareiko.fr.pool.ConnectionPool;

import java.sql.*;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

public class ReviewDaoImpl extends EntityInitializer<Review> implements ReviewDao<Review> {
    private static final ReviewDao INSTANCE = new ReviewDaoImpl();

    private ReviewDaoImpl(){}

    public static ReviewDao getInstance(){
        return INSTANCE;
    }

    @Override
    public List<Review> findAll() throws DaoException {
        List<Review> reviews = new ArrayList<>();
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(SQLQuery.FIND_ALL_REVIEWS);
            reviews = initItems(resultSet);
        } catch (SQLException e) {
            throw new DaoException("Reviews aren't found: ", e);
        }
        return reviews;
    }

    @Override
    public List<Review> findByFilmId(int filmId) throws DaoException {
        List<Review> reviews = new ArrayList<>();
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(SQLQuery.FIND_REVIEW_BY_ID_FILM)) {
            statement.setInt(1, filmId);
            ResultSet resultSet = statement.executeQuery();
            reviews = initItems(resultSet);
        } catch (SQLException e) {
            throw new DaoException("Reviews aren't found by film id: ", e);
        }
        return reviews;
    }

    @Override
    public Review findById(int id) throws DaoException {
        Review review = new Review();
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(SQLQuery.FIND_REVIEW_BY_ID)) {
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                review = initItem(resultSet);
            }
        } catch (SQLException e) {
            throw new DaoException("Review isn't found by id: ", e);
        }
        return review;
    }

    @Override
    public Review delete(Review review) throws DaoException {
        delete(review.getId());
        return review;
    }

    @Override
    public Review delete(int id) throws DaoException {
        Review review = new Review();
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(SQLQuery.DELETE_REVIEW_BY_ID)) {
            statement.setInt(1, id);
            review = findById(id);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException("Review isn't deleted by id: ", e);
        }
        return review;
    }

    @Override
    public boolean create(Review review) throws DaoException {
        boolean isCreated = false;
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(SQLQuery.CREATE_REVIEW)) {
            statement.setInt(1, review.getFilmId());
            statement.setInt(2, review.getUserId());
            statement.setString(3, review.getReview());
            long reviewDate = review.getReviewDate().getTimeInMillis();
            statement.setLong(4, reviewDate);
            statement.executeUpdate();
            ResultSet resultSet = statement.getGeneratedKeys();
            if (resultSet.next()) {
                int id = resultSet.getInt(1);
                review.setId(id);
            }
            isCreated = true;
        } catch (SQLException e) {
            throw new DaoException("Review isn't created: ", e);
        }
        return isCreated;
    }

    @Override
    public Review update(Review review) throws DaoException {
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(SQLQuery.UPDATE_REVIEW)) {
            statement.setString(1, review.getReview());
            long reviewDate = review.getReviewDate().getTimeInMillis();
            statement.setLong(2, reviewDate);
            statement.setInt(3, review.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException("Review isn't updated: ", e);
        }
        return review;
    }

    private Calendar getDateFromLong(long dateMillis) {
        Calendar date = new GregorianCalendar();
        date.setTimeInMillis(dateMillis);
        return date;
    }

    protected List<Review> initItems(ResultSet resultSet) throws DaoException {
        List<Review> reviews = new ArrayList<>();
        try {
            while (resultSet.next()) {
                Review review = initItem(resultSet);
                reviews.add(review);
            }
        } catch (SQLException e) {
            throw new DaoException("Reviews aren't inizialized: ", e);
        }
        return reviews;
    }

    protected Review initItem(ResultSet resultSet) throws DaoException {
        Review review = new Review();
        try {
            int reviewId = resultSet.getInt(ColumnName.REVIEW_ID);
            review.setId(reviewId);
            int filmId = resultSet.getInt(ColumnName.FILM_ID);
            review.setFilmId(filmId);
            int userId = resultSet.getInt(ColumnName.USER_ID);
            review.setUserId(userId);
            String reviewText = resultSet.getString(ColumnName.REVIEW);
            review.setReview(reviewText);
            long reviewDate = resultSet.getLong(ColumnName.REVIEW_DATE);
            Calendar date = getDateFromLong(reviewDate);
            review.setReviewDate(date);
        } catch (SQLException e) {
            throw new DaoException("Review isn't inizialized: ", e);
        }
        return review;
    }
}
