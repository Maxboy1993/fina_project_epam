package by.nareiko.fr.dao.impl;

import by.nareiko.fr.dao.ColumnName;
import by.nareiko.fr.dao.PersonDao;
import by.nareiko.fr.dao.SQLQuery;
import by.nareiko.fr.entity.Actor;
import by.nareiko.fr.entity.Director;
import by.nareiko.fr.exception.DaoException;
import by.nareiko.fr.pool.ConnectionPool;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

public class DirectorDaoImpl extends EntityInitializer<Director> implements PersonDao<Director> {

    public DirectorDaoImpl() {
    }

    @Override
    public List<Director> findByLastName(String name) throws DaoException {
        List<Director> directors = new ArrayList<>();
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(SQLQuery.FIND_DIRECTOR_BY_LAST_NAME)) {
            statement.setString(1, name);
            ResultSet resultSet = statement.executeQuery();
            directors = initItems(resultSet);
        } catch (SQLException e) {
            throw new DaoException("Directors aren't found by last name: ", e);
        }
        return directors;
    }

    @Override
    public Director findByLastNameAndFirstName(String lastName, String firstName) throws DaoException {
        Director director = new Director();
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(SQLQuery.FIND_DIRECTOR_BY_LAST_NAME_AND_FIRSTNAME)) {
            statement.setString(1, lastName);
            statement.setString(2, firstName);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                director = initItem(resultSet);
            }
        } catch (SQLException e) {
            throw new DaoException("Director isn't found by last name and first name: ", e);
        }
        return director;
    }

    @Override
    public List<Director> findAll() throws DaoException {
        List<Director> directors = new ArrayList<>();
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(SQLQuery.FIND_ALL_DIRECTORS);
            directors = initItems(resultSet);
        } catch (SQLException e) {
            throw new DaoException("Directors aren't found: ", e);
        }
        return directors;
    }

    @Override
    public Director findById(int id) throws DaoException {
        Director director = new Director();
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(SQLQuery.FIND_DIRECTOR_BY_ID)) {
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                director = initItem(resultSet);
            }
        } catch (SQLException e) {
            throw new DaoException("Director isn't found by id: ", e);
        }
        return director;
    }

    public Director findByFilmId(int id) throws DaoException {
       Director director = new Director();
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(SQLQuery.FIND_ACTORS_BY_FILM_ID)) {
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()){
                director = initItem(resultSet);
            }
        } catch (SQLException e) {
            throw new DaoException("Actor isn't found by id: ", e);
        }
        return director;
    }

    @Override
    public Director delete(String lastName, String firstName) throws DaoException {
        Director director = new Director();
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(SQLQuery.DELETE_DIRECTOR_BY_LAST_NAME_AND_FIRSTNAME)) {
            director = findByLastNameAndFirstName(lastName, firstName);
            statement.setString(1, lastName);
            statement.setString(2, firstName);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException("Director isn't deleted by last name and first name: ", e);
        }
        return director;
    }

    @Override
    public Director delete(Director director) throws DaoException {
        delete(director.getId());
        return director;
    }

    @Override
    public Director delete(int id) throws DaoException {
        Director director = new Director();
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(SQLQuery.DELETE_DIRECTOR_BY_ID)) {
            director = findById(id);
            statement.setInt(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException("Director isn't deleted by id: ", e);
        }
        return director;
    }

    @Override
    public boolean create(Director director) throws DaoException {
        boolean isCreated = false;
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(SQLQuery.CREATE_DIRECTOR)) {
            statement.setString(1, director.getFirstName());
            statement.setString(2, director.getLastName());
            long birtgday = director.getBirthday().getTimeInMillis();
            statement.setLong(3, birtgday);
            statement.executeUpdate();
            ResultSet resultSet = statement.getGeneratedKeys();
            if (resultSet.next()) {
                int id = resultSet.getInt(1);
                director.setId(id);
            }
            isCreated = true;
        } catch (SQLException e) {
            throw new DaoException("Director isn't created: ", e);
        }
        return isCreated;
    }

    @Override
    public Director update(Director director) throws DaoException {
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(SQLQuery.UPDATE_DIRECTOR)) {
            statement.setString(1, director.getFirstName());
            statement.setString(2, director.getLastName());
            long birtgday = director.getBirthday().getTimeInMillis();
            statement.setLong(3, birtgday);
            statement.setInt(4, director.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException("Director isn't uodated: ", e);
        }
        return director;
    }

    private Calendar getDateFromLong(long dateMillis) {
        Calendar date = new GregorianCalendar();
        date.setTimeInMillis(dateMillis);
        return date;
    }

    List<Director> initItems(ResultSet resultSet) throws DaoException {
        List<Director> directors = new ArrayList<>();
        try {
            while (resultSet.next()) {
                Director director = initItem(resultSet);
                directors.add(director);
            }
        } catch (SQLException e) {
            throw new DaoException("Directors aren't inizialized: ", e);
        }
        return directors;
    }

    Director initItem(ResultSet resultSet) throws DaoException {
        Director director = new Director();
        try {
            int id = resultSet.getInt(ColumnName.PERSON_ID);
            director.setId(id);
            String firstName = resultSet.getString(ColumnName.FIRST_NAME);
            director.setFirstName(firstName);
            String lastName = resultSet.getString(ColumnName.LAST_NAME);
            director.setLastName(lastName);
            long longBirthday = resultSet.getLong(ColumnName.BIRTHDAY);
            Calendar birthday = getDateFromLong(longBirthday);
            director.setBirthday(birthday);
        } catch (SQLException e) {
            throw new DaoException("Director isn't inizialized: ", e);
        }
        return director;
    }

    //TODO delete main method
    public static void main(String[] args) {
//        DirectorDaoImpl dao = new DirectorDaoImpl();
//        List<Director> list = new ArrayList<>();
//        list = dao.findByLastName("McGinty");
////        list = dao.findAll();
//        for (Director director : list) {
//            System.out.println(director.toString());
//        }
//        Director act = new Director();
//        System.out.println(act.toString());
    }
}
