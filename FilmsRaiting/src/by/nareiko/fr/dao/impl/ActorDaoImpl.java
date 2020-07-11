package by.nareiko.fr.dao.impl;

import by.nareiko.fr.entity.Actor;
import by.nareiko.fr.exception.DaoException;
import by.nareiko.fr.dao.ColumnName;
import by.nareiko.fr.dao.PersonDao;
import by.nareiko.fr.dao.SQLQuery;
import by.nareiko.fr.pool.ConnectionPool;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;


public class ActorDaoImpl extends EntityInitializer<Actor> implements PersonDao<Actor> {

    public ActorDaoImpl() {
    }

    @Override
    public List<Actor> findByLastName(String lastName) throws DaoException {
        List<Actor> actors = new ArrayList<>();
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(SQLQuery.FIND_ACTORS_BY_LAST_NAME)) {
            statement.setString(1, lastName);
            ResultSet resultSet = statement.executeQuery();
            actors = initItems(resultSet);
        } catch (SQLException e) {
            throw new DaoException("Actors aren't found by last name: ", e);
        }
        return actors;
    }

    @Override
    public Actor findByLastNameAndFirstName(String lastName, String firstName) throws DaoException {
        Actor actor = new Actor();
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(SQLQuery.FIND_ACTOR_BY_LAST_NAME_AND_FIRST_NAME)) {
            statement.setString(1, lastName);
            statement.setString(2, firstName);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                actor = initItem(resultSet);
            }
        } catch (SQLException e) {
            throw new DaoException("Actor isn't found by last name and first name: ", e);
        }
        return actor;
    }

    @Override
    public List<Actor> findAll() throws DaoException {
        List<Actor> actors = new ArrayList<>();
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(SQLQuery.FIND_ALL_ACTORS);
            actors = initItems(resultSet);
        } catch (SQLException e) {
            throw new DaoException("Actors aren't found: ", e);
        }
        return actors;
    }

    @Override
    public Actor findById(int id) throws DaoException {
        Actor actor = new Actor();
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(SQLQuery.FIND_ACTOR_BY_ID)) {
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                actor = initItem(resultSet);
            }
        } catch (SQLException e) {
            throw new DaoException("Actor isn't found by id: ", e);
        }
        return actor;
    }

    public List<Actor> findByFilmId(int id) throws DaoException {
        List<Actor> actors = new ArrayList<>();
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(SQLQuery.FIND_ACTORS_BY_FILM_ID)) {
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
                actors = initItems(resultSet);
        } catch (SQLException e) {
            throw new DaoException("Actors aren't found by film id: ", e);
        }
        return actors;
    }

    @Override
    public Actor delete(String lastName, String firstName) throws DaoException {
        Actor actor = new Actor();
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(SQLQuery.DELETE_ACTOR_BY_LAST_NAME_AND_FIRST_NAME)) {
            actor = findByLastNameAndFirstName(lastName, firstName);
            statement.setString(1, lastName);
            statement.setString(2, firstName);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException("Actor isn't deleted by last name and first name: ", e);
        }
        return actor;
    }

    @Override
    public Actor delete(Actor actor) throws DaoException {
        delete(actor.getId());
        return actor;
    }

    @Override
    public Actor delete(int id) throws DaoException {
        Actor actor = new Actor();
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(SQLQuery.DELETE_ACTOR_BY_ID)) {
            actor = findById(id);
            statement.setInt(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException("Actor isn't deleted by id: ", e);
        }
        return actor;
    }

    @Override
    public boolean create(Actor actor) throws DaoException {
        boolean isCreated = false;
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(SQLQuery.CREATE_ACTOR, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, actor.getFirstName());
            statement.setString(2, actor.getLastName());
            long birthday = actor.getBirthday().getTimeInMillis();
            statement.setLong(3, birthday);
            statement.executeUpdate();
            ResultSet resultSet = statement.getGeneratedKeys();
            if (resultSet.next()) {
                int id = resultSet.getInt(1);
                actor.setId(id);
            }
            isCreated = true;
        } catch (SQLException e) {
            throw new DaoException("Actor isn't created: ", e);
        }
        return isCreated;
    }

    @Override
    public Actor update(Actor actor) throws DaoException {
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(SQLQuery.UPDATE_ACTOR)) {
            statement.setString(1, actor.getFirstName());
            statement.setString(2, actor.getLastName());
            long birtgday = actor.getBirthday().getTimeInMillis();
            statement.setLong(3, birtgday);
            int id = actor.getId();
            statement.setInt(4, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException("Actor isn't updated: ", e);
        }
        return actor;
    }

    private Calendar getDateFromLong(long dateMillis) {
        Calendar date = new GregorianCalendar();
        date.setTimeInMillis(dateMillis);
        return date;
    }

     List<Actor> initItems(ResultSet resultSet) throws DaoException {
        List<Actor> actors = new ArrayList<>();
        try {
            while (resultSet.next()) {
                Actor actor = initItem(resultSet);
                actors.add(actor);
            }
        } catch (SQLException e) {
            throw new DaoException("Actors aren't inizialized: ", e);
        }
        return actors;
    }

     Actor initItem(ResultSet resultSet) throws DaoException {
        Actor actor = new Actor();
        try {
            int id = resultSet.getInt(ColumnName.PERSON_ID);
            actor.setId(id);
            String firstName = resultSet.getString(ColumnName.FIRST_NAME);
            actor.setFirstName(firstName);
            String lastName = resultSet.getString(ColumnName.LAST_NAME);
            actor.setLastName(lastName);
            long longBirthday = resultSet.getLong(ColumnName.BIRTHDAY);
            Calendar birthday = getDateFromLong(longBirthday);
            actor.setBirthday(birthday);
        } catch (SQLException e) {
            throw new DaoException("Actor isn't inizialized: ", e);
        }
        return actor;
    }

    //TODO delete main method
    public static void main(String[] args) {

        ActorDaoImpl actorDao = new ActorDaoImpl();


//        Calendar date = new GregorianCalendar();
//        date.set(1956, 8, 26);
//        DateFormat dateFormat = new SimpleDateFormat("yyyy.MM.dd");
//        System.out.println(dateFormat.format(date.getTime()));
//        System.out.println(date.getTimeInMillis());
    }
}
