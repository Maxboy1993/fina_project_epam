package model.dao.impl;

import entity.AbstractEntity;
import entity.Actor;
import model.dao.PersonDao;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import pool.ConnectionPool;

import java.sql.*;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;


public class ActorDaoImpl implements PersonDao {
    private List<Actor> actors;
    private Actor actor;
    private static final String SQL_REQUEST_ALL_ACTORS = "SELECT actorId, name, birthday FROM Actor";
    private static final String SQL_REQUEST_ALL_ACTORS_BY_NAME = "SELECT actorId, name, birthday " +
            "FROM Actor WHERE name = ?";
    private static final String SQL_REQUEST_ALL_ACTORS_BY_AGE_YOUNGER = "SELECT actorId, name, birthday" +
            " FROM Actor WHERE age < ?";
    private static final String SQL_REQUEST_ALL_ACTORS_BY_AGE_OLDER = "SELECT actorId, name, birthday" +
            " FROM Actor WHERE age > ?";
    private static final String SQL_REQUEST_ALL_ACTORS_BY_ID = "SELECT actorId, name, birthday " +
            "FROM Actor WHERE id = ?";
    private static final Logger LOGGER = LogManager.getLogger();


    public ActorDaoImpl() {
        actors = new ArrayList<>();
    }

    @Override
    public List<Actor> findAllByName(String patternName) {
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_REQUEST_ALL_ACTORS_BY_NAME)) {
            statement.setString(1, patternName);
            ResultSet resultSet = statement.executeQuery();
                actors = initActors(resultSet);
        } catch (SQLException e) {
            LOGGER.error("SQLException: ", e);
        }
        return actors;
    }

    @Override
    public List<Actor> findAllByAgeYounger(int maxAge) {
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_REQUEST_ALL_ACTORS_BY_AGE_YOUNGER)) {
            statement.setInt(1, maxAge);
            ResultSet resultSet = statement.executeQuery();
            actors = initActors(resultSet);
        } catch (SQLException e) {
            LOGGER.error("SQLException: ", e);
        }
        return actors;
    }

    @Override
    public List<Actor> findAllByAgeOlder(int minAge) {
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_REQUEST_ALL_ACTORS_BY_AGE_OLDER)) {
            statement.setInt(1, minAge);
            ResultSet resultSet = statement.executeQuery();
            actors = initActors(resultSet);
        } catch (SQLException e) {
            LOGGER.error("SQLException: ", e);
        }
        return actors;
    }

    @Override
    public List<Actor> findAll() {
        try (Connection connection = ConnectionPool.getInstance().getConnection();
        Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(SQL_REQUEST_ALL_ACTORS);
            actors = initActors(resultSet);
        } catch (SQLException e) {
            LOGGER.error("SQLException: ", e);
        }
        return actors;
    }

    @Override
    public Actor findById(int id) {
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_REQUEST_ALL_ACTORS_BY_ID)) {
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            actor = initActor(resultSet);
        } catch (SQLException e) {
            LOGGER.error("SQLException: ", e);
        }
        return actor;
    }

    @Override
    public boolean delete(AbstractEntity abstractEntity) {
        
        return false;
    }

    @Override
    public boolean delete(int id) {
        return false;
    }

    @Override
    public boolean create(AbstractEntity abstractEntity) {
        return false;
    }

    @Override
    public Actor update(AbstractEntity abstractEntity) {
        return null;
    }

    private Calendar getDateFromLong(long dateMillis) {
        Calendar date = new GregorianCalendar();
        date.setTimeInMillis(dateMillis);
        return date;
    }

    private List<Actor> initActors(ResultSet resultSet) throws SQLException {
        while (resultSet.next()) {
            actor = new Actor();
            int id = resultSet.getInt(1);
            actor.setActorId(id);
            String name = resultSet.getString(2);
            actor.setName(name);
            long longBirthday = resultSet.getLong(3);
            Calendar birthday = getDateFromLong(longBirthday);
            actor.setBirthday(birthday);
            actors.add(actor);
        }
        return actors;
    }

    private Actor initActor(ResultSet resultSet) throws SQLException {
        if (resultSet.next()) {
            actor = new Actor();
            int id = resultSet.getInt(1);
            actor.setActorId(id);
            String name = resultSet.getString(2);
            actor.setName(name);
            long longBirthday = resultSet.getLong(3);
            Calendar birthday = getDateFromLong(longBirthday);
            actor.setBirthday(birthday);
        }
        return actor;
    }

    public static void main(String[] args) {
        ActorDaoImpl dao = new ActorDaoImpl();
        List<Actor> list = new ArrayList<>();
        list = dao.findAllByName("Arnold Alois Schwarzenegger");
        for (Actor actor : list) {
            System.out.println(actor.toString());
        }
    }
}
