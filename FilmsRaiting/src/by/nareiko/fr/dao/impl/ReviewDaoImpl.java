package by.nareiko.fr.dao.impl;

import by.nareiko.fr.dao.ReviewDao;
import by.nareiko.fr.dao.SqlQuery;
import by.nareiko.fr.dao.impl.entittymapper.ReviewMapper;
import by.nareiko.fr.entity.Review;
import by.nareiko.fr.exception.DaoException;
import by.nareiko.fr.pool.ConnectionPool;

import java.sql.*;
import java.util.*;

public class ReviewDaoImpl implements ReviewDao<Review> {
    private static final ReviewDao INSTANCE = new ReviewDaoImpl();

    private ReviewDaoImpl() {
    }

    public static ReviewDao getInstance() {
        return INSTANCE;
    }

    @Override
    public List<Review> findAll() throws DaoException {
        List<Review> reviews;
        ReviewMapper reviewMapper = new ReviewMapper();
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(SqlQuery.FIND_ALL_REVIEWS);
            reviews = reviewMapper.initEntities(resultSet);
        } catch (SQLException e) {
            throw new DaoException("Reviews aren't found: ", e);
        }
        return reviews;
    }

    @Override
    public List<Review> findByFilmId(int filmId) throws DaoException {
        List<Review> reviews;
        ReviewMapper reviewMapper = new ReviewMapper();
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(SqlQuery.FIND_REVIEW_BY_ID_FILM)) {
            statement.setInt(1, filmId);
            ResultSet resultSet = statement.executeQuery();
            reviews = reviewMapper.initEntities(resultSet);
        } catch (SQLException e) {
            throw new DaoException("Reviews aren't found by film id: ", e);
        }
        return reviews;
    }

    @Override
    public Optional<Review> findById(int id) throws DaoException {
        Review review = null;
        ReviewMapper reviewMapper = new ReviewMapper();
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(SqlQuery.FIND_REVIEW_BY_ID)) {
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                review = reviewMapper.initEntity(resultSet);
            }
        } catch (SQLException e) {
            throw new DaoException("Review isn't found by id: ", e);
        }
        return Optional.ofNullable(review);
    }

    @Override
    public Optional<Review> delete(Review review) throws DaoException {
        int id = review.getId();
        Optional<Review> foundReview = findById(id);
        delete(id);
        return foundReview;
    }

    @Override
    public Optional<Review> delete(int id) throws DaoException {
        Optional<Review> review;
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(SqlQuery.DELETE_REVIEW_BY_ID)) {
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
        boolean isCreated;
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(SqlQuery.CREATE_REVIEW)) {
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
    public Optional<Review> update(Review review) throws DaoException {
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(SqlQuery.UPDATE_REVIEW)) {
            statement.setString(1, review.getReview());
            long reviewDate = review.getReviewDate().getTimeInMillis();
            statement.setLong(2, reviewDate);
            statement.setInt(3, review.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException("Review isn't updated: ", e);
        }
        return Optional.ofNullable(review);
    }
}
