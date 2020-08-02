package by.nareiko.fr.dao.impl;

import by.nareiko.fr.dao.ReviewDao;
import by.nareiko.fr.dao.SqlQuery;
import by.nareiko.fr.dao.impl.entittymapper.ReviewMapper;
import by.nareiko.fr.entity.Review;
import by.nareiko.fr.exception.DaoException;
import by.nareiko.fr.pool.ConnectionPool;

import java.sql.*;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Optional;

public class ReviewDaoImpl implements ReviewDao<Review> {
    private static final String SPLIT_REGEX = "-";
    private static final int YEAR_INDEX = 0;
    private static final int MONTH_INDEX = 1;
    private static final int DAY_INDEX = 2;
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
    public boolean delete(Review review) throws DaoException {
        int id = review.getId();
        boolean isDeleted = delete(id);
        return isDeleted;
    }

    @Override
    public boolean delete(int id) throws DaoException {
        boolean isDeleted;
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(SqlQuery.DELETE_REVIEW_BY_ID)) {
            statement.setInt(1, id);
            statement.executeUpdate();
            isDeleted = true;
        } catch (SQLException e) {
            throw new DaoException("Review isn't deleted by id: ", e);
        }
        return isDeleted;
    }

    @Override
    public Optional<Review> create(Review review) throws DaoException {
        Optional<Review> optionalReview;
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(SqlQuery.CREATE_REVIEW, Statement.RETURN_GENERATED_KEYS)) {
            statement.setInt(1, review.getFilmId());
            statement.setInt(2, review.getUserId());
            statement.setString(3, review.getReview());
            Calendar reviewDate = new GregorianCalendar();
            long date = reviewDate.getTimeInMillis();
            statement.setLong(4, date);
            statement.executeUpdate();
            ResultSet resultSet = statement.getGeneratedKeys();
            if (resultSet.next()) {
                int id = resultSet.getInt(1);
                review.setId(id);
            }
            optionalReview = findById(review.getId());
        } catch (SQLException e) {
            throw new DaoException("Review isn't created: ", e);
        }
        return optionalReview;
    }

    @Override
    public Optional<Review> update(Review review) throws DaoException {
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(SqlQuery.UPDATE_REVIEW)) {
            statement.setString(1, review.getReview());
            String reviewDate = review.getReviewDate();
            statement.setInt(2, review.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException("Review isn't updated: ", e);
        }
        return Optional.ofNullable(review);
    }

    private long modifyDate(String birthday) {
        String[] date = birthday.split(SPLIT_REGEX);
        int year = Integer.parseInt(date[YEAR_INDEX]);
        int month = Integer.parseInt(date[MONTH_INDEX]) - 1;
        int day = Integer.parseInt(date[DAY_INDEX]);

        Calendar calendarBirthday = new GregorianCalendar();
        calendarBirthday.set(year, month, day);
        long birthdayMillis = calendarBirthday.getTimeInMillis();
        return birthdayMillis;
    }
}
