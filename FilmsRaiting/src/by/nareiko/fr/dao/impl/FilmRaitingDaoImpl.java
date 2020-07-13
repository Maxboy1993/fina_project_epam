package by.nareiko.fr.dao.impl;

import by.nareiko.fr.dao.EntityInitializer;
import by.nareiko.fr.entity.FilmRaiting;
import by.nareiko.fr.exception.DaoException;
import by.nareiko.fr.dao.ColumnName;
import by.nareiko.fr.dao.FilmRaitingDao;
import by.nareiko.fr.dao.SQLQuery;
import by.nareiko.fr.pool.ConnectionPool;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class FilmRaitingDaoImpl extends EntityInitializer<FilmRaiting> implements FilmRaitingDao<FilmRaiting> {
    private static final FilmRaitingDao INSTANCE = new FilmRaitingDaoImpl();

    private FilmRaitingDaoImpl(){}

    public static FilmRaitingDao getInstance(){
        return INSTANCE;
    }

    @Override
    public List<FilmRaiting> findAll() throws DaoException {
        List<FilmRaiting> filmRaitings = new ArrayList<>();
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(SQLQuery.FIND_ALL_FILMS_RAITING);
            filmRaitings = initItems(resultSet);
        } catch (SQLException e) {
            throw new DaoException("Film's raitings aren't found: ", e);
        }
        return filmRaitings;
    }

    @Override
    public List<FilmRaiting> findByFilmId(int filmId) throws DaoException {
        List<FilmRaiting> filmRaitings = new ArrayList<>();
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(SQLQuery.FIND_RAITING_BY_ID_FILM)) {
            statement.setInt(1, filmId);
            ResultSet resultSet = statement.executeQuery();
            filmRaitings = initItems(resultSet);
        } catch (SQLException e) {
            throw new DaoException("Film's raitings aren't found by film id: ", e);
        }
        return filmRaitings;
    }

    @Override
    public FilmRaiting findById(int id) throws DaoException {
        FilmRaiting filmRaiting = new FilmRaiting();
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(SQLQuery.FIND_RAITING_BY_ID)) {
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                filmRaiting = initItem(resultSet);
            }
        } catch (SQLException e) {
            throw new DaoException("Film raiting isn't found by id: ", e);
        }
        return filmRaiting;
    }

    @Override
    public FilmRaiting delete(FilmRaiting filmRaiting) throws DaoException {
        delete(filmRaiting.getId());
        return filmRaiting;
    }

    @Override
    public FilmRaiting delete(int id) throws DaoException {
        FilmRaiting filmRaiting = new FilmRaiting();
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(SQLQuery.DELETE_RAITING_BY_ID)) {
            statement.setInt(1, id);
            filmRaiting = findById(id);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException("Film raiting isn't deleted by id: ", e);
        }
        return filmRaiting;
    }

    @Override
    public boolean create(FilmRaiting filmRaiting) throws DaoException {
        boolean isCreated = false;
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(SQLQuery.CREATE_RAITING)) {
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
            throw new DaoException("Film raiting isn't created: ", e);
        }
        return isCreated;
    }

    @Override
    public FilmRaiting update(FilmRaiting filmRaiting) throws DaoException {
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(SQLQuery.UPDATE_RAITING)) {
            statement.setInt(1, filmRaiting.getRaiting());
            statement.setInt(2, filmRaiting.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException("Film raiting isn't updated: ", e);
        }
        return filmRaiting;
    }

     protected List<FilmRaiting> initItems(ResultSet resultSet) throws DaoException {
        List<FilmRaiting> filmRaitings = new ArrayList<>();
        try {
            while (resultSet.next()) {
                FilmRaiting filmRaiting = initItem(resultSet);
                filmRaitings.add(filmRaiting);
            }
        } catch (SQLException e) {
            throw new DaoException("Films raitings aren't inizialized: ", e);
        }
        return filmRaitings;
    }

     protected FilmRaiting initItem(ResultSet resultSet) throws DaoException {
        FilmRaiting filmRaiting = new FilmRaiting();
        try {
            int raitingId = resultSet.getInt(ColumnName.RAITING_ID);
            filmRaiting.setId(raitingId);
            int filmId = resultSet.getInt(ColumnName.FILM_ID);
            filmRaiting.setFilmId(filmId);
            int userId = resultSet.getInt(ColumnName.USER_ID);
            filmRaiting.setUserId(userId);
            int raiting = resultSet.getInt(ColumnName.FILM_RAITING);
            filmRaiting.setRaiting(raiting);
        } catch (SQLException e) {
            throw new DaoException("Film raitings isn't inizialized: ", e);
        }
        return filmRaiting;
    }
}
