package by.nareiko.fr.model.dao.impl;

import by.nareiko.fr.entity.Director;
import by.nareiko.fr.model.dao.PersonDao;
import by.nareiko.fr.model.dao.request.DirectorRequest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import by.nareiko.fr.pool.ConnectionPool;

import java.sql.*;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

public class DirectorDaoImpl implements PersonDao<Director> {
    private List<Director> directors;
    private Director director;
    private static final Logger LOGGER = LogManager.getLogger();
    boolean isCreated;

    public DirectorDaoImpl() {
        directors = new ArrayList<>();
    }

    @Override
    public List<Director> findByLastName(String name) {
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(DirectorRequest.FIND_DIRECTOR_BY_LAST_NAME)) {
            statement.setString(1, name);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()){
                director = initDirector(resultSet);
                directors.add(director);
            }
        } catch (SQLException e) {
            LOGGER.error("SQLException: ", e);
        }
        return directors;
    }

    @Override
    public Director findByLastNameAndFirstName(String lastName, String firstName) {
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(DirectorRequest.FIND_DIRECTOR_BY_LAST_NAME_AND_FIRSTNAME)) {
            statement.setString(1, lastName);
            statement.setString(2, firstName);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                director = initDirector(resultSet);
            }
        } catch (SQLException e) {
            LOGGER.error("SQLException: ", e);
        }
        return director;
    }

    @Override
    public List<Director> findAll() {
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(DirectorRequest.FIND_ALL_DIRECTORS);
            directors = initDirectors(resultSet);
        } catch (SQLException e) {
            LOGGER.error("SQLException: ", e);
        }
        return directors;
    }

    @Override
    public Director findById(int id) {
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(DirectorRequest.FIND_DIRECTOR_BY_ID)) {
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()){
                director = initDirector(resultSet);
            }
        } catch (SQLException e) {
            LOGGER.error("SQLException: ", e);
        }
        return director;
    }

    @Override
    public Director delete(String lastName, String firstName) {
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(DirectorRequest.DELETE_DIRECTOR_BY_LAST_NAME_AND_FIRSTNAME)) {
            director = findByLastNameAndFirstName(lastName, firstName);
            statement.setString(1, lastName);
            statement.setString(2, firstName);
            statement.executeUpdate();
        } catch (SQLException e) {
            LOGGER.error("SQLException: ", e);
        }
        return director;
    }

    @Override
    public Director delete(Director director) {
       delete(director.getId());
        return director;
    }

    @Override
    public Director delete(int id) {
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(DirectorRequest.DELETE_DIRECTOR_BY_ID)) {
            director = findById(id);
            statement.setInt(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            LOGGER.error("SQLException: ", e);
        }
        return director;
    }

    @Override
    public boolean create(Director director) {
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(DirectorRequest.CREATE_DIRECTOR)) {
            statement.setString(1, director.getFirstName());
            statement.setString(2, director.getLastName());
            long birtgday = director.getBirthday().getTimeInMillis();
            statement.setLong(3, birtgday);
            statement.executeUpdate();
            ResultSet resultSet = statement.getGeneratedKeys();
            if (resultSet.next()){
                int id = resultSet.getInt(1);
                director.setId(id);
            }
            isCreated = true;
        } catch (SQLException e) {
            LOGGER.error("SQLException: ", e);
        }
        return isCreated;
    }

    @Override
    public Director update(Director director) {
        Director director1 = new Director();
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(DirectorRequest.UPDATE_DIRECTOR)) {
            statement.setString(1, director.getFirstName());
            statement.setString(2, director.getLastName());
            long birtgday = director.getBirthday().getTimeInMillis();
            statement.setLong(3, birtgday);
            statement.setInt(4, director.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            LOGGER.error("SQLException: ", e);
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
        int id = resultSet.getInt("personId");
        director.setId(id);
        String firstName = resultSet.getString("firstName");
        director.setFirstName(firstName);
        String lastName = resultSet.getString("lastName");
        director.setLastName(lastName);
        long longBirthday = resultSet.getLong("birthday");
        Calendar birthday = getDateFromLong(longBirthday);
        director.setBirthday(birthday);
        return director;
    }

    public static void main(String[] args) {
        DirectorDaoImpl dao = new DirectorDaoImpl();
        List<Director> list = new ArrayList<>();
        list = dao.findByLastName("McGinty");
////        list = dao.findAll();
        for (Director director : list) {
            System.out.println(director.toString());
        }
//        Director act = new Director();
//        System.out.println(act.toString());
    }
}
