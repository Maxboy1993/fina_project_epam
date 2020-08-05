package by.nareiko.fr.dao.impl;

import by.nareiko.fr.dao.FilmPersonDao;
import by.nareiko.fr.dao.SqlQuery;
import by.nareiko.fr.dao.impl.entittymapper.DirectorMapper;
import by.nareiko.fr.entity.Director;
import by.nareiko.fr.exception.DaoException;
import by.nareiko.fr.pool.ConnectionPool;

import java.sql.*;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Optional;

public class DirectorDaoImpl implements FilmPersonDao<Director> {
    private static final FilmPersonDao<Director> INSTANCE = new DirectorDaoImpl();
    private static final String SPLIT_REGEX = "-";
    private static final int YEAR_INDEX = 0;
    private static final int MONTH_INDEX = 1;
    private static final int DAY_INDEX = 2;


    private DirectorDaoImpl() {
    }

    public static FilmPersonDao<Director> getInstance() {
        return INSTANCE;
    }

    @Override
    public List<Director> findByLastName(String name) throws DaoException {
        List<Director> directors;
        DirectorMapper directorMapper = new DirectorMapper();
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(SqlQuery.FIND_DIRECTOR_BY_LAST_NAME)) {
            statement.setString(1, name);
            ResultSet resultSet = statement.executeQuery();
            directors = directorMapper.initEntities(resultSet);
        } catch (SQLException e) {
            throw new DaoException("Error while searching director by last name: ", e);
        }
        return directors;
    }

    @Override
    public Optional<Director> findByLastNameAndFirstName(String lastName, String firstName) throws DaoException {
        Director director = null;
        DirectorMapper directorMapper = new DirectorMapper();
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(SqlQuery.FIND_DIRECTOR_BY_LAST_NAME_AND_FIRSTNAME)) {
            statement.setString(1, lastName);
            statement.setString(2, firstName);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                director = directorMapper.initEntity(resultSet);
            }
        } catch (SQLException e) {
            throw new DaoException("Error while searching director by last name and first name: ", e);
        }
        return Optional.ofNullable(director);
    }

    @Override
    public List<Director> findAll() throws DaoException {
        List<Director> directors;
        DirectorMapper directorMapper = new DirectorMapper();
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(SqlQuery.FIND_ALL_DIRECTORS);
            directors = directorMapper.initEntities(resultSet);
        } catch (SQLException e) {
            throw new DaoException("Error while searching all directors: ", e);
        }
        return directors;
    }

    @Override
    public Optional<Director> findById(int id) throws DaoException {
        Director director = null;
        DirectorMapper directorMapper = new DirectorMapper();
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(SqlQuery.FIND_DIRECTOR_BY_ID)) {
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                director = directorMapper.initEntity(resultSet);
            }
        } catch (SQLException e) {
            throw new DaoException("Error while searching director by id: ", e);
        }
        return Optional.ofNullable(director);
    }

    public Optional<Director> findByFilmId(int id) throws DaoException {
        Director director = null;
        DirectorMapper directorMapper = new DirectorMapper();
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(SqlQuery.FIND_ACTORS_BY_FILM_ID)) {
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                director = directorMapper.initEntity(resultSet);
            }
        } catch (SQLException e) {
            throw new DaoException("Error while searching director by film id: ", e);
        }
        return Optional.ofNullable(director);
    }

    @Override
    public boolean delete(String lastName, String firstName) throws DaoException {
        boolean isDeleted;
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(SqlQuery.DELETE_DIRECTOR_BY_LAST_NAME_AND_FIRSTNAME)) {
            statement.setString(1, lastName);
            statement.setString(2, firstName);
            statement.executeUpdate();
            isDeleted = true;
        } catch (SQLException e) {
            throw new DaoException("Error while deleting director by last name and first name: ", e);
        }
        return isDeleted;
    }

    @Override
    public boolean delete(Director director) throws DaoException {
        boolean isDeleted = delete(director.getId());
        return isDeleted;
    }

    @Override
    public boolean delete(int id) throws DaoException {
        boolean idDeleted;
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(SqlQuery.DELETE_DIRECTOR_BY_ID)) {
            statement.setInt(1, id);
            statement.executeUpdate();
            idDeleted = true;
        } catch (SQLException e) {
            throw new DaoException("Error while deleting director by id: ", e);
        }
        return idDeleted;
    }

    @Override
    public Optional<Director> create(Director director) throws DaoException {
        Optional<Director> optionalDirector;
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(SqlQuery.CREATE_DIRECTOR, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, director.getFirstName());
            statement.setString(2, director.getLastName());
            String birthday = director.getBirthday();
            long date = modifyDate(birthday);
            statement.setLong(3, date);
            statement.executeUpdate();
            ResultSet resultSet = statement.getGeneratedKeys();
            if (resultSet.next()) {
                int id = resultSet.getInt(1);
                director.setId(id);
            }
            optionalDirector = findById(director.getId());
        } catch (SQLException e) {
            throw new DaoException("Error while creating director: ", e);
        }
        return optionalDirector;
    }

    @Override
    public Optional<Director> update(Director director) throws DaoException {
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(SqlQuery.UPDATE_DIRECTOR)) {
            statement.setString(1, director.getFirstName());
            statement.setString(2, director.getLastName());
            String birthday = director.getBirthday();
            long date = modifyDate(birthday);
            statement.setLong(3, date);
            statement.setInt(4, director.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException("Error while updating director: ", e);
        }
        return Optional.ofNullable(director);
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
