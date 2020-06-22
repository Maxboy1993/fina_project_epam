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
    private static final String FIND_ALL_DIRECTORS = "SELECT personId, profession, firstName, lastName, birthday FROM FilmPerson WHERE profession  = 'director'";
    private static final String FIND_DIRECTOR_BY_LAST_NAME = "SELECT personId, profession, firstName, lastName, birthday " +
            "FROM FilmPerson WHERE profession  = 'director' AND lastName = ?";
    private static final String FIND_DIRECTOR_BY_ID = "SELECT personId, profession, firstName, lastName, birthday " +
            "FROM FilmPerson WHERE profession  = 'director' AND personId = ?";
    private static final String DELETE_DIRECTOR_BY_ID = "DELETE FROM FilmPerson WHERE personId = ?";
    private static final String DELETE_DIRECTOR_BY_LAST_NAME = "DELETE FROM FilmPerson WHERE profession  = 'director' AND lastName = ?";
    private static final String CREATE_DIRECTOR = "INSERT INTO FilmPerson (profession, firstName, lastName, birthday) VALUES ('director', ?, ?, ?)";
    private static final String UPDATE_DIRECTOR = "UPDATE FilmPerson SET profession = 'director', firstName = ?, lastName = ? birthday = ? WHERE personId = ?";
    private static final Logger LOGGER = LogManager.getLogger();
    boolean isCreated;

    public DirectorDaoImpl() {
        directors = new ArrayList<>();
    }

    @Override
    public List<Director> findByLastName(String name) {
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(FIND_DIRECTOR_BY_LAST_NAME)) {
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
    public List<Director> findAll() {
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(FIND_ALL_DIRECTORS);
            directors = initDirectors(resultSet);
        } catch (SQLException e) {
            LOGGER.error("SQLException: ", e);
        }
        return directors;
    }

//    @Override
//    public Director findById(int id) {
//        try (Connection connection = ConnectionPool.getInstance().getConnection();
//             PreparedStatement statement = connection.prepareStatement(FIND_DIRECTOR_BY_ID)) {
//            statement.setInt(1, id);
//            ResultSet resultSet = statement.executeQuery();
//            if (resultSet.next()){
//                director = initDirector(resultSet);
//            }
//        } catch (SQLException e) {
//            LOGGER.error("SQLException: ", e);
//        }
//        return director;
//    }

    @Override
    public List<Director> delete(String name) {
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(DELETE_DIRECTOR_BY_LAST_NAME)) {
            directors = findByLastName(name);
            statement.setString(1, name);
            statement.executeUpdate();
        } catch (SQLException e) {
            LOGGER.error("SQLException: ", e);
        }
        return directors;
    }

    @Override
    public Director delete(Director director) {
       delete(director.getLastName());
        return director;
    }

//    @Override
//    public Director delete(int id) {
//        try (Connection connection = ConnectionPool.getInstance().getConnection();
//             PreparedStatement statement = connection.prepareStatement(DELETE_DIRECTOR_BY_ID)) {
//            director = findById(id);
//            statement.setInt(1, id);
//            statement.executeUpdate();
//        } catch (SQLException e) {
//            LOGGER.error("SQLException: ", e);
//        }
//        return director;
//    }

    @Override
    public boolean create(Director director) {
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(CREATE_DIRECTOR)) {
            statement.setString(1, director.getFirstName());
            statement.setString(2, director.getLastName());
            long birtgday = director.getBirthday().getTimeInMillis();
            statement.setLong(3, birtgday);
            statement.executeUpdate();
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
             PreparedStatement statement = connection.prepareStatement(UPDATE_DIRECTOR)) {
            statement.setString(1, director.getFirstName());
            statement.setString(2, director.getLastName());
            long birtgday = director.getBirthday().getTimeInMillis();
            statement.setLong(3, birtgday);
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
