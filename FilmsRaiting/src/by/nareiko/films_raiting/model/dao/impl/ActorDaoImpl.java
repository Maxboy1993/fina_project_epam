package by.nareiko.films_raiting.model.dao.impl;

import by.nareiko.films_raiting.entity.Actor;
import by.nareiko.films_raiting.model.dao.PersonDao;
import by.nareiko.films_raiting.pool.ConnectionPool;
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
    private static final String FIND_ALL_ACTORS = "SELECT personId, profession, firstName, lastName, birthday FROM FilmPerson WHERE profession  = 'actor'";
    private static final String FIND_ACTORS_BY_LAST_NAME = "SELECT personId, profession, firstName, lastName, birthday " +
            "FROM FilmPerson WHERE profession  = 'actor' AND lastName =?";
    private static final String FIND_ACTOR_BY_ID = "SELECT personId, profession, firstName, lastName, birthday " +
            "FROM FilmPerson WHERE profession  = 'actor' AND personId = ?";
    private static final String DELETE_ACTOR_BY_ID = "DELETE FROM FilmPerson WHERE personId = ?";
    private static final String DELETE_ACTOR_BY_LAST_NAME = "DELETE FROM FilmPerson WHERE profession  = 'actor' AND lastName = ?";
    private static final String CREATE_ACTOR = "INSERT INTO FilmPerson (profession, firstName, lastName, birthday) VALUES ('actor', ?, ?, ?)";
    private static final String UPDATE_ACTOR = "UPDATE FilmPerson SET profession = 'actor', firstName = ?, lastName = ? birthday = ? WHERE personId = ?";
    private static final Logger LOGGER = LogManager.getLogger();
    boolean isCreated;


    public ActorDaoImpl() {
        actors = new ArrayList<>();
    }

    @Override
    public List<Actor> findByLastName(String name) {
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(FIND_ACTORS_BY_LAST_NAME)) {
            statement.setString(1, name);
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

//    @Override
//    public Actor findById(int id) {
//        try (Connection connection = ConnectionPool.getInstance().getConnection();
//             PreparedStatement statement = connection.prepareStatement(FIND_ACTOR_BY_ID)) {
//            statement.setInt(1, id);
//            ResultSet resultSet = statement.executeQuery();
//            if (resultSet.next()) {
//                actor = initActor(resultSet);
//            }
//        } catch (SQLException e) {
//            LOGGER.error("SQLException: ", e);
//        }
//        return actor;
//    }

    @Override
    public List<Actor> delete(String name) {
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(DELETE_ACTOR_BY_LAST_NAME)) {
            actors = findByLastName(name);
            statement.setString(1, name);
            statement.executeUpdate();
        } catch (SQLException e) {
            LOGGER.error("SQLException: ", e);
        }
        return actors;
    }

    @Override
    public Actor delete(Actor actor) {
        delete(actor.getLastName());
        return actor;
    }

//    @Override
//    public Actor delete(int id) {
//        try (Connection connection = ConnectionPool.getInstance().getConnection();
//             PreparedStatement statement = connection.prepareStatement(DELETE_ACTOR_BY_ID)) {
//            actor = findById(id);
//            statement.setInt(1, id);
//            statement.executeUpdate();
//        } catch (SQLException e) {
//            LOGGER.error("SQLException: ", e);
//        }
//        return actor;
//    }

    @Override
    public boolean create(Actor actor) {
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(CREATE_ACTOR)) {
            statement.setString(1, actor.getFirstName());
            statement.setString(2, actor.getLastName());
            long birtgday = actor.getBirthday().getTimeInMillis();
            statement.setLong(3, birtgday);
            statement.executeUpdate();
            isCreated = true;
        } catch (SQLException e) {
            LOGGER.error("SQLException: ", e);
        }
        return isCreated;
    }

    @Override
    public Actor update(Actor actor) {
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(UPDATE_ACTOR)) {
            statement.setString(1, actor.getFirstName());
            statement.setString(2, actor.getLastName());
            long birtgday = actor.getBirthday().getTimeInMillis();
            statement.setLong(3, birtgday);
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
        ActorDaoImpl dao = new ActorDaoImpl();
        List<Actor> list = new ArrayList<>();
        list = dao.findByLastName("Schwarzenegger");
//        list = dao.findAll();
        for (Actor actor : list) {
            System.out.println(actor.toString());
        }
//        Actor act = new Actor();
//        act = dao.findById(1);
//        System.out.println(act.toString());
    }
}
