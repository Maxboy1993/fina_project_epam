package by.nareiko.fr.dao.impl;

import by.nareiko.fr.dao.PersonDao;
import by.nareiko.fr.dao.SqlQuery;
import by.nareiko.fr.dao.impl.entittymapper.DirectorMapper;
import by.nareiko.fr.entity.Director;
import by.nareiko.fr.exception.DaoException;
import by.nareiko.fr.pool.ConnectionPool;

import java.sql.*;
import java.util.*;

public class DirectorDaoImpl implements PersonDao<Director> {
    private static final PersonDao INSTANCE = new DirectorDaoImpl();

    private DirectorDaoImpl() {
    }

    public static PersonDao getInstance() {
        return INSTANCE;
    }

    @Override
    public List<Director> findByLastName(String name) throws DaoException {
        List<Director> directors;
        DirectorMapper directorMapper = new DirectorMapper();
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(SqlQuery.FIND_DIRECTOR_BY_LAST_NAME)) {
            statement.setString(1, name);
            ResultSet resultSet = statement.executeQuery();
            directors = directorMapper.initEntities(resultSet);
        } catch (SQLException e) {
            throw new DaoException("Error while searching director by last name: ", e);
        }
        return directors;
    }

    @Override
    public Optional<Director> findByLastNameAndFirstName(String lastName, String firstName) throws DaoException {
        Director director = null;
        DirectorMapper directorMapper = new DirectorMapper();
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(SqlQuery.FIND_DIRECTOR_BY_LAST_NAME_AND_FIRSTNAME)) {
            statement.setString(1, lastName);
            statement.setString(2, firstName);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                director = directorMapper.initEntity(resultSet);
            }
        } catch (SQLException e) {
            throw new DaoException("Error while searching director by last name and first name: ", e);
        }
        return Optional.ofNullable(director);
    }

    @Override
    public List<Director> findAll() throws DaoException {
        List<Director> directors;
        DirectorMapper directorMapper = new DirectorMapper();
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(SqlQuery.FIND_ALL_DIRECTORS);
            directors = directorMapper.initEntities(resultSet);
        } catch (SQLException e) {
            throw new DaoException("Error while searching all directors: ", e);
        }
        return directors;
    }

    @Override
    public Optional<Director> findById(int id) throws DaoException {
        Director director = null;
        DirectorMapper directorMapper = new DirectorMapper();
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(SqlQuery.FIND_DIRECTOR_BY_ID)) {
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                director = directorMapper.initEntity(resultSet);
            }
        } catch (SQLException e) {
            throw new DaoException("Error while searching director by id: ", e);
        }
        return Optional.ofNullable(director);
    }

    public Optional<Director> findByFilmId(int id) throws DaoException {
        Director director = null;
        DirectorMapper directorMapper = new DirectorMapper();
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(SqlQuery.FIND_ACTORS_BY_FILM_ID)) {
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                director = directorMapper.initEntity(resultSet);
            }
        } catch (SQLException e) {
            throw new DaoException("Error while searching director by film id: ", e);
        }
        return Optional.ofNullable(director);
    }

    @Override
    public Optional<Director> delete(String lastName, String firstName) throws DaoException {
        Optional<Director> director;
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(SqlQuery.DELETE_DIRECTOR_BY_LAST_NAME_AND_FIRSTNAME)) {
            director = findByLastNameAndFirstName(lastName, firstName);
            statement.setString(1, lastName);
            statement.setString(2, firstName);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException("Error while deleting director by last name and first name: ", e);
        }
        return director;
    }

    @Override
    public Optional<Director> delete(Director director) throws DaoException {
        Optional<Director> foundDirector = delete(director.getId());
        return foundDirector;
    }

    @Override
    public Optional<Director> delete(int id) throws DaoException {
        Optional<Director> director;
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(SqlQuery.DELETE_DIRECTOR_BY_ID)) {
            director = findById(id);
            statement.setInt(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException("Error while deleting director by id: ", e);
        }
        return director;
    }

    @Override
    public boolean create(Director director) throws DaoException {
        boolean isCreated;
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(SqlQuery.CREATE_DIRECTOR)) {
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
            throw new DaoException("Error while creating director: ", e);
        }
        return isCreated;
    }

    @Override
    public Optional<Director> update(Director director) throws DaoException {
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(SqlQuery.UPDATE_DIRECTOR)) {
            statement.setString(1, director.getFirstName());
            statement.setString(2, director.getLastName());
            long birtgday = director.getBirthday().getTimeInMillis();
            statement.setLong(3, birtgday);
            statement.setInt(4, director.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException("Error while updating director: ", e);
        }
        return Optional.ofNullable(director);
    }
}
