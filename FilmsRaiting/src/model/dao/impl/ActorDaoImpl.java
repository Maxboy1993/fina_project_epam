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


public class ActorDaoImpl implements PersonDao<Actor> {
    private List<Actor> actors;
    private Actor actor;
    private static final String FIND_ALL_ACTORS = "SELECT actorId, name, birthday FROM Actor";
    private static final String FIND_ACTOR_BY_NAME = "SELECT actorId, name, birthday " +
            "FROM Actor WHERE name = ?";
    private static final String FIND_ACTOR_BY_ID = "SELECT actorId, name, birthday " +
            "FROM Actor WHERE actorId = ?";
    private static final String DELETE_ACTOR_BY_ID = "DELETE FROM actor WHERE actorId = ?";
    private static final String DELETE_ACTOR_BY_NAME = "DELETE FROM actor WHERE name = ?";
    private static final String CREATE_ACTOR = "INSERT INTO actor (name, birhtday) VALUES (?, ?)";
    private static final String UPDATE_ACTOR = "UPDATE actor SET name = ?, birthday = ?" +
            " WHERE actorId = ?";
    private static final Logger LOGGER = LogManager.getLogger();
    boolean isCreated;


    public ActorDaoImpl() {
        actors = new ArrayList<>();
    }

    @Override
    public List<Actor> findByName(String name) {
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(FIND_ACTOR_BY_NAME)) {
            statement.setString(1, name);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()){
                actor = initActor(resultSet);
            }
        } catch (SQLException e) {
            LOGGER.error("SQLException: ", e);
        }
        return actors;
    }

    @Override
    public List<Actor> findAll() {
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(FIND_ALL_ACTORS);
            actors = initActors(resultSet);
        } catch (SQLException e) {
            LOGGER.error("SQLException: ", e);
        }
        return actors;
    }

    @Override
    public Actor findById(int id) {
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(FIND_ACTOR_BY_ID)) {
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()){
                actor = initActor(resultSet);
            }
        } catch (SQLException e) {
            LOGGER.error("SQLException: ", e);
        }
        return actor;
    }

    @Override
    public List<Actor> delete(String name) {
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(DELETE_ACTOR_BY_NAME)) {
            actors = findByName(name);
            statement.setString(1, name);
            statement.executeUpdate();
        } catch (SQLException e) {
            LOGGER.error("SQLException: ", e);
        }
        return actors;
    }

    @Override
    public Actor delete(Actor actor) {
        delete(actor.getActorId());
        return actor;
    }

    @Override
    public Actor delete(int id) {
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(DELETE_ACTOR_BY_ID)) {
            actor = findById(id);
            statement.setInt(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            LOGGER.error("SQLException: ", e);
        }
        return actor;
    }

    @Override
    public boolean create(Actor actor) {
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(CREATE_ACTOR)) {
            statement.setString(1, actor.getName());
            long birtgday = actor.getBirthday().getTimeInMillis();
            statement.setLong(2, birtgday);
            statement.executeUpdate();
            isCreated = true;
        } catch (SQLException e) {
            LOGGER.error("SQLException: ", e);
        }
        return isCreated;
    }

    @Override
    public Actor update(Actor actor) {
        Actor actor1 = new Actor();
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(UPDATE_ACTOR)) {
            statement.setString(1, actor.getName());
            long birtgday = actor.getBirthday().getTimeInMillis();
            statement.setLong(2, birtgday);
            statement.setInt(3, actor.getActorId());
           statement.executeUpdate();
           actor1 = findById(actor.getActorId()); // так правильно при update???
        } catch (SQLException e) {
            LOGGER.error("SQLException: ", e);
        }
        return actor1;
    }

    private Calendar getDateFromLong(long dateMillis) {
        Calendar date = new GregorianCalendar();
        date.setTimeInMillis(dateMillis);
        return date;
    }

    private List<Actor> initActors(ResultSet resultSet) throws SQLException {
        while (resultSet.next()) {
           actor = initActor(resultSet);
            actors.add(actor);
        }
        return actors;
    }

    private Actor initActor(ResultSet resultSet) throws SQLException {
            actor = new Actor();
            int id = resultSet.getInt(1);
            actor.setActorId(id);
            String name = resultSet.getString(2);
            actor.setName(name);
            long longBirthday = resultSet.getLong(3);
            Calendar birthday = getDateFromLong(longBirthday);
            actor.setBirthday(birthday);
        return actor;
    }

    public static void main(String[] args) {
        ActorDaoImpl dao = new ActorDaoImpl();
        List<Actor> list = new ArrayList<>();
        list = dao.findByName("Arnold Alois Schwarzenegger");
        for (Actor actor : list) {
            System.out.println(actor.toString());
        }
        Actor act = new Actor();
        act = dao.findById(1);
        System.out.println(act.toString());
    }
}
