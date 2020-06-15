package by.nareiko.films_raiting.model.dao.impl;

import by.nareiko.films_raiting.entity.Director;
import by.nareiko.films_raiting.model.dao.PersonDao;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import by.nareiko.films_raiting.pool.ConnectionPool;

import java.sql.*;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

public class DirectorDaoImpl implements PersonDao<Director> {
    private List<Director> directors;
    private Director director;
    private static final String FIND_ALL_DIRECTORS = "SELECT directorId, name, birthday FROM Director";
    private static final String FIND_DIRECTOR_BY_NAME = "SELECT directorId, name, birthday " +
            "FROM Director WHERE name = ?";
    private static final String FIND_DIRECTOR_BY_ID = "SELECT directorId, name, birthday " +
            "FROM Director WHERE directorId = ?";
    private static final String DELETE_DIRECTOR_BY_ID = "DELETE FROM Director WHERE directorId = ?";
    private static final String DELETE_DIRECTOR_BY_NAME = "DELETE FROM Director WHERE name = ?";
    private static final String CREATE_DIRECTOR = "INSERT INTO Director (name, birhtday) VALUES (?, ?)";
    private static final String UPDATE_DIRECTOR = "UPDATE Director SET name = ?, birthday = ?" +
            " WHERE directorId = ?";
//    private static final Logger LOGGER = LogManager.getLogger();
    boolean isCreated;

    public DirectorDaoImpl() {
        directors = new ArrayList<>();
    }

    @Override
    public List<Director> findByName(String name) {
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(FIND_DIRECTOR_BY_NAME)) {
            statement.setString(1, name);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()){
                director = initDirector(resultSet);
            }
        } catch (SQLException e) {
//            LOGGER.error("SQLException: ", e);
        }
        return directors;
    }

    @Override
    public List<Director> findAll() {
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(FIND_ALL_DIRECTORS);
            directors = initDirectors(resultSet);
        } catch (SQLException e) {
//            LOGGER.error("SQLException: ", e);
        }
        return directors;
    }

    @Override
    public Director findById(int id) {
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(FIND_DIRECTOR_BY_ID)) {
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()){
                director = initDirector(resultSet);
            }
        } catch (SQLException e) {
//            LOGGER.error("SQLException: ", e);
        }
        return director;
    }

    @Override
    public List<Director> delete(String name) {
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(DELETE_DIRECTOR_BY_NAME)) {
            directors = findByName(name);
            statement.setString(1, name);
            statement.executeUpdate();
        } catch (SQLException e) {
//            LOGGER.error("SQLException: ", e);
        }
        return directors;
    }

    @Override
    public Director delete(Director director) {
       delete(director.getDirectorId());
        return director;
    }

    @Override
    public Director delete(int id) {
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(DELETE_DIRECTOR_BY_ID)) {
            director = findById(id);
            statement.setInt(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
//            LOGGER.error("SQLException: ", e);
        }
        return director;
    }

    @Override
    public boolean create(Director director) {
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(CREATE_DIRECTOR)) {
            statement.setString(1, director.getName());
            long birtgday = director.getBirthday().getTimeInMillis();
            statement.setLong(2, birtgday);
            statement.executeUpdate();
            isCreated = true;
        } catch (SQLException e) {
//            LOGGER.error("SQLException: ", e);
        }
        return isCreated;
    }

    @Override
    public Director update(Director director) {
        Director director1 = new Director();
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(UPDATE_DIRECTOR)) {
            statement.setString(1, director.getName());
            long birtgday = director.getBirthday().getTimeInMillis();
            statement.setLong(2, birtgday);
            statement.setInt(3, director.getDirectorId());
            statement.executeUpdate();
            director1 = findById(director.getDirectorId()); // так правильно при update???
        } catch (SQLException e) {
//            LOGGER.error("SQLException: ", e);
        }
        return director1;
    }

    private Calendar getDateFromLong(long dateMillis) {
        Calendar date = new GregorianCalendar();
        date.setTimeInMillis(dateMillis);
        return date;
    }

    private List<Director> initDirectors(ResultSet resultSet) throws SQLException {
        while (resultSet.next()) {
            director = initDirector(resultSet);
            directors.add(director);
        }
        return directors;
    }

    private Director initDirector(ResultSet resultSet) throws SQLException {
        director = new Director();
        int id = resultSet.getInt(1);
        director.setDirectorId(id);
        String name = resultSet.getString(2);
        director.setName(name);
        long longBirthday = resultSet.getLong(3);
        Calendar birthday = getDateFromLong(longBirthday);
        director.setBirthday(birthday);
        return director;
    }
}
