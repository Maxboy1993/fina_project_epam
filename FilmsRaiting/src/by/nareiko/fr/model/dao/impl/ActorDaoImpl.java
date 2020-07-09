package by.nareiko.fr.model.dao.impl;

import by.nareiko.fr.entity.Actor;
import by.nareiko.fr.model.dao.PersonDao;
import by.nareiko.fr.model.dao.request.ActorRequest;
import by.nareiko.fr.pool.ConnectionPool;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;


public class ActorDaoImpl implements PersonDao<Actor> {
    private List<Actor> actors;
    private Actor actor;
    private static final Logger LOGGER = LogManager.getLogger();
    boolean isCreated;


    public ActorDaoImpl() {
        actors = new ArrayList<>();
    }

    @Override
    public List<Actor> findByLastName(String lastName) {
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(ActorRequest.FIND_ACTORS_BY_LAST_NAME)) {
            statement.setString(1, lastName);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                actor = initActor(resultSet);
                actors.add(actor);
            }
        } catch (SQLException e) {
            LOGGER.error("SQLException: ", e);
        }
        return actors;
    }

    @Override
    public Actor findByLastNameAndFirstName(String lastName, String firstName) {
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(ActorRequest.FIND_ACTORS_BY_LAST_NAME)) {
            statement.setString(1, lastName);
            statement.setString(2, firstName);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                actor = initActor(resultSet);
            }
        } catch (SQLException e) {
            LOGGER.error("SQLException: ", e);
        }
        return actor;
    }

    @Override
    public List<Actor> findAll() {
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(ActorRequest.FIND_ALL_ACTORS);
            actors = initActors(resultSet);
        } catch (SQLException e) {
            LOGGER.error("SQLException: ", e);
        }
        return actors;
    }

    @Override
    public Actor findById(int id) {
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(ActorRequest.FIND_ACTOR_BY_ID)) {
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                actor = initActor(resultSet);
            }
        } catch (SQLException e) {
            LOGGER.error("SQLException: ", e);
        }
        return actor;
    }

    @Override
    public Actor delete(String lastName, String firstName) {
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(ActorRequest.DELETE_ACTOR_BY_LAST_NAME_AND_FIRST_NAME)) {
            actor = findByLastNameAndFirstName(lastName, firstName);
            statement.setString(1, lastName);
            statement.setString(2, firstName);
            statement.executeUpdate();
        } catch (SQLException e) {
            LOGGER.error("SQLException: ", e);
        }
        return actor;
    }

    @Override
    public Actor delete(Actor actor) {
        delete(actor.getId());
        return actor;
    }

    @Override
    public Actor delete(int id) {
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(ActorRequest.DELETE_ACTOR_BY_ID)) {
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
             PreparedStatement statement = connection.prepareStatement(ActorRequest.CREATE_ACTOR, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, actor.getFirstName());
            statement.setString(2, actor.getLastName());
            long birthday = actor.getBirthday().getTimeInMillis();
            statement.setLong(3, birthday);
            statement.executeUpdate();
            ResultSet resultSet = statement.getGeneratedKeys();
            if (resultSet.next()){
                int id = resultSet.getInt(1);
                actor.setId(id);
            }
            isCreated = true;
        } catch (SQLException e) {
            LOGGER.error("SQLException: ", e);
        }
        return isCreated;
    }

    @Override
    public Actor update(Actor actor) {
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(ActorRequest.UPDATE_ACTOR)) {
            statement.setString(1, actor.getFirstName());
            statement.setString(2, actor.getLastName());
            long birtgday = actor.getBirthday().getTimeInMillis();
            statement.setLong(3, birtgday);
            int id = actor.getId();
            statement.setInt(4, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            LOGGER.error("SQLException: ", e);
        }
        return actor;
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
        int id = resultSet.getInt("personId");
        actor.setId(id);
        String firstName = resultSet.getString("firstName");
        actor.setFirstName(firstName);
        String lastName = resultSet.getString("lastName");
        actor.setLastName(lastName);
        long longBirthday = resultSet.getLong("birthday");
        Calendar birthday = getDateFromLong(longBirthday);
        actor.setBirthday(birthday);
        return actor;
    }

    public static void main(String[] args) {

        ActorDaoImpl actorDao = new ActorDaoImpl();


//        Calendar date = new GregorianCalendar();
//        date.set(1956, 8, 26);
//        DateFormat dateFormat = new SimpleDateFormat("yyyy.MM.dd");
//        System.out.println(dateFormat.format(date.getTime()));
//        System.out.println(date.getTimeInMillis());
    }
}
