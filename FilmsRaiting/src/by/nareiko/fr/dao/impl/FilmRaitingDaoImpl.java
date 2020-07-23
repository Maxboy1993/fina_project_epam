package by.nareiko.fr.dao.impl;

import by.nareiko.fr.dao.FilmRaitingDao;
import by.nareiko.fr.dao.SqlQuery;
import by.nareiko.fr.dao.impl.entittymapper.FilmRaitingMapper;
import by.nareiko.fr.entity.FilmRaiting;
import by.nareiko.fr.exception.DaoException;
import by.nareiko.fr.pool.ConnectionPool;

import java.sql.*;
import java.util.List;
import java.util.Optional;

public class FilmRaitingDaoImpl implements FilmRaitingDao<FilmRaiting> {
    private static final FilmRaitingDao INSTANCE = new FilmRaitingDaoImpl();

    private FilmRaitingDaoImpl() {
    }

    public static FilmRaitingDao getInstance() {
        return INSTANCE;
    }

    @Override
    public List<FilmRaiting> findAll() throws DaoException {
        List<FilmRaiting> filmRaitings;
        FilmRaitingMapper raitingMapper = new FilmRaitingMapper();
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(SqlQuery.FIND_ALL_FILMS_RAITING);
            filmRaitings = raitingMapper.initEntities(resultSet);
        } catch (SQLException e) {
            throw new DaoException("Error while searching Film's raitings: ", e);
        }
        return filmRaitings;
    }

    @Override
    public List<FilmRaiting> findByFilmId(int filmId) throws DaoException {
        List<FilmRaiting> filmRaitings;
        FilmRaitingMapper raitingMapper = new FilmRaitingMapper();
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(SqlQuery.FIND_RAITING_BY_ID_FILM)) {
            statement.setInt(1, filmId);
            ResultSet resultSet = statement.executeQuery();
            filmRaitings = raitingMapper.initEntities(resultSet);
        } catch (SQLException e) {
            throw new DaoException("Error while searching Film's raitings by film id: ", e);
        }
        return filmRaitings;
    }

    @Override
    public Optional<FilmRaiting> findById(int id) throws DaoException {
        FilmRaiting filmRaiting = null;
        FilmRaitingMapper raitingMapper = new FilmRaitingMapper();
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(SqlQuery.FIND_RAITING_BY_ID)) {
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                filmRaiting = raitingMapper.initEntity(resultSet);
            }
        } catch (SQLException e) {
            throw new DaoException("Error while searching Film's raiting by id: ", e);
        }
        return Optional.ofNullable(filmRaiting);
    }

    @Override
    public Optional<FilmRaiting> delete(FilmRaiting filmRaiting) throws DaoException {
        int id = filmRaiting.getId();
        Optional<FilmRaiting> foundFilmRaiting = findById(id);
        delete(id);
        return foundFilmRaiting;
    }

    @Override
    public Optional<FilmRaiting> delete(int id) throws DaoException {
        Optional<FilmRaiting> filmRaiting;
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(SqlQuery.DELETE_RAITING_BY_ID)) {
            statement.setInt(1, id);
            filmRaiting = findById(id);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException("Error while deleting Film's raiting by id: ", e);
        }
        return filmRaiting;
    }

    @Override
    public boolean create(FilmRaiting filmRaiting) throws DaoException {
        boolean isCreated = false;
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(SqlQuery.CREATE_RAITING, Statement.RETURN_GENERATED_KEYS)) {
            statement.setInt(1, filmRaiting.getFilmId());
            statement.setInt(2, filmRaiting.getUserId());
            statement.setInt(3, filmRaiting.getRaiting());
            statement.executeUpdate();
            ResultSet resultSet = statement.getGeneratedKeys();
            if (resultSet.next()) {
                int id = resultSet.getInt(1);
                filmRaiting.setId(id);
            }
            isCreated = true;
        } catch (SQLException e) {
            throw new DaoException("Error while deleting Film's raitings: ", e);
        }
        return isCreated;
    }

    @Override
    public Optional<FilmRaiting> update(FilmRaiting filmRaiting) throws DaoException {
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(SqlQuery.UPDATE_RAITING)) {
            statement.setInt(1, filmRaiting.getRaiting());
            statement.setInt(2, filmRaiting.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException("Error while updating Film's raiting: ", e);
        }
        return Optional.ofNullable(filmRaiting);
    }
}
