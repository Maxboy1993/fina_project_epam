package by.nareiko.fr.dao.impl;

import by.nareiko.fr.dao.FilmPersonDao;
import by.nareiko.fr.dao.SqlQuery;
import by.nareiko.fr.dao.impl.entittymapper.ActorMapper;
import by.nareiko.fr.entity.Actor;
import by.nareiko.fr.exception.DaoException;
import by.nareiko.fr.pool.ConnectionPool;

import java.sql.*;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Optional;


public class ActorDaoImpl implements FilmPersonDao<Actor> {
    private static final FilmPersonDao INSTANCE = new ActorDaoImpl();
    private static final String SPLIT_REGEX = "-";
    private static final int YEAR_INDEX = 0;
    private static final int MONTH_INDEX = 1;
    private static final int DAY_INDEX = 2;


    private ActorDaoImpl() {
    }

    public static FilmPersonDao getInstance() {
        return INSTANCE;
    }

    @Override
    public List<Actor> findByLastName(String lastName) throws DaoException {
        List<Actor> actors;
        ActorMapper actorMapper = new ActorMapper();
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(SqlQuery.FIND_ACTORS_BY_LAST_NAME)) {
            statement.setString(1, lastName);
            ResultSet resultSet = statement.executeQuery();
            actors = actorMapper.initEntities(resultSet);
        } catch (SQLException e) {
            throw new DaoException("Error while searching actors by last name: ", e);
        }
        return actors;
    }

    @Override
    public Optional<Actor> findByLastNameAndFirstName(String lastName, String firstName) throws DaoException {
        Actor actor = null;
        ActorMapper actorMapper = new ActorMapper();
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(SqlQuery.FIND_ACTOR_BY_LAST_NAME_AND_FIRST_NAME)) {
            statement.setString(1, lastName);
            statement.setString(2, firstName);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                actor = actorMapper.initEntity(resultSet);
            }
        } catch (SQLException e) {
            throw new DaoException("Error while searching actor by last name and first name: ", e);
        }
        return Optional.ofNullable(actor);
    }

    @Override
    public List<Actor> findAll() throws DaoException {
        List<Actor> actors;
        ActorMapper actorMapper = new ActorMapper();
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(SqlQuery.FIND_ALL_ACTORS);
            actors = actorMapper.initEntities(resultSet);
        } catch (SQLException e) {
            throw new DaoException("Error while searching all actors: ", e);
        }
        return actors;
    }

    @Override
    public Optional<Actor> findById(int id) throws DaoException {
        Actor actor = null;
        ActorMapper actorMapper = new ActorMapper();
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(SqlQuery.FIND_ACTOR_BY_ID)) {
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                actor = actorMapper.initEntity(resultSet);
            }
        } catch (SQLException e) {
            throw new DaoException("Error while searching actor by id: ", e);
        }
        return Optional.ofNullable(actor);
    }

    public List<Actor> findByFilmId(int id) throws DaoException {
        List<Actor> actors;
        ActorMapper actorMapper = new ActorMapper();
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(SqlQuery.FIND_ACTORS_BY_FILM_ID)) {
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            actors = actorMapper.initEntities(resultSet);
        } catch (SQLException e) {
            throw new DaoException("Error while searching actors by film id: ", e);
        }
        return actors;
    }

    @Override
    public boolean delete(String lastName, String firstName) throws DaoException {
        boolean isDeleted;
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(SqlQuery.DELETE_ACTOR_BY_LAST_NAME_AND_FIRST_NAME)) {
            statement.setString(1, lastName);
            statement.setString(2, firstName);
            statement.executeUpdate();
            isDeleted = true;
        } catch (SQLException e) {
            throw new DaoException("Error while deleting actor by last name and first name: ", e);
        }
        return isDeleted;
    }

    @Override
    public boolean delete(Actor actor) throws DaoException {
        boolean isDeleted = delete(actor.getId());
        return isDeleted;
    }

    @Override
    public boolean delete(int id) throws DaoException {
        boolean isDeleted;
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(SqlQuery.DELETE_ACTOR_BY_ID)) {
            statement.setInt(1, id);
            statement.executeUpdate();
            isDeleted = true;
        } catch (SQLException e) {
            throw new DaoException("Error while deleting actor by id: ", e);
        }
        return isDeleted;
    }

    @Override
    public Optional<Actor> create(Actor actor) throws DaoException {
        Optional<Actor> optionalActor;
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(SqlQuery.CREATE_ACTOR, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, actor.getFirstName());
            statement.setString(2, actor.getLastName());
            String birthday = actor.getBirthday();
            long date = modifyDate(birthday);
            statement.setLong(3, date);
            statement.executeUpdate();
            ResultSet resultSet = statement.getGeneratedKeys();
            if (resultSet.next()) {
                int id = resultSet.getInt(1);
                actor.setId(id);
            }
            optionalActor = findById(actor.getId());
        } catch (SQLException e) {
            throw new DaoException("Error while creating actor: ", e);
        }
        return optionalActor;
    }

    @Override
    public Optional<Actor> update(Actor actor) throws DaoException {
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(SqlQuery.UPDATE_ACTOR)) {
            statement.setString(1, actor.getFirstName());
            statement.setString(2, actor.getLastName());
            String birthday = actor.getBirthday();
            long date = modifyDate(birthday);
            statement.setLong(3, date);
            int id = actor.getId();
            statement.setInt(4, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException("Error while deleting updating actor: ", e);
        }
        return Optional.ofNullable(actor);
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
