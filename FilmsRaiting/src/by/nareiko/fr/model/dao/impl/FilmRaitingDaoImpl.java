package by.nareiko.fr.model.dao.impl;

import by.nareiko.fr.entity.AbstractEntity;
import by.nareiko.fr.entity.FilmRaiting;
import by.nareiko.fr.exception.DaoException;
import by.nareiko.fr.model.dao.FilmRaitingDao;
import by.nareiko.fr.model.dao.request.FilmRaitingRequest;
import by.nareiko.fr.pool.ConnectionPool;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class FilmRaitingDaoImpl implements FilmRaitingDao<FilmRaiting> {
    private List<FilmRaiting> filmRaitings;
    private FilmRaiting filmRaiting;
    private static final Logger LOGGER = LogManager.getLogger();
    boolean isCreated;

    public FilmRaitingDaoImpl() {
        filmRaitings = new ArrayList<>();
    }

    @Override
    public List<FilmRaiting>  findAll() {
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(FilmRaitingRequest.FIND_ALL_FILMS_RAITING);
            filmRaitings = initRaitings(resultSet);
        } catch (SQLException e) {
            LOGGER.error("SQLException: ", e);
        }
        return filmRaitings;
    }

    @Override
    public List<FilmRaiting>  findByFilmId(int filmId) {
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(FilmRaitingRequest.FIND_RAITING_BY_ID_FILM)) {
            statement.setInt(1, filmId);
            ResultSet resultSet = statement.executeQuery();
               filmRaitings = initRaitings(resultSet);
        } catch (SQLException e) {
            LOGGER.error("SQLException: ", e);
        }
        return filmRaitings;
    }

    @Override
    public FilmRaiting findById(int id) {
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(FilmRaitingRequest.FIND_RAITING_BY_ID)) {
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()){
                filmRaiting = initRaiting(resultSet);
            }
        } catch (SQLException e) {
            LOGGER.error("SQLException: ", e);
        }
        return filmRaiting;
    }

    @Override
    public FilmRaiting delete(FilmRaiting filmRaiting) throws DaoException {
       delete(filmRaiting.getId());
        return filmRaiting;
    }

    @Override
    public FilmRaiting delete(int id) {
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(FilmRaitingRequest.DELETE_RAITING_BY_ID)) {
            statement.setInt(1, id);
            filmRaiting = findById(id);
            statement.executeUpdate();
        } catch (SQLException e) {
            LOGGER.error("SQLException: ", e);
        }
        return filmRaiting;
    }

    @Override
    public boolean create(FilmRaiting filmRaiting) {
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(FilmRaitingRequest.CREATE_RAITING)) {
            statement.setInt(1, filmRaiting.getFilmId());
            statement.setInt(2, filmRaiting.getUserId());
            statement.setInt(3, filmRaiting.getRaiting());
            statement.executeUpdate();
            ResultSet resultSet = statement.getGeneratedKeys();
            if (resultSet.next()){
                int id = resultSet.getInt(1);
                filmRaiting.setId(id);
            }
            isCreated = true;
        } catch (SQLException e) {
            LOGGER.error("SQLException: ", e);
        }
        return isCreated;
    }

    @Override
    public FilmRaiting update(FilmRaiting filmRaiting) {
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(FilmRaitingRequest.UPDATE_RAITING)) {
            statement.setInt(1, filmRaiting.getRaiting());
            statement.setInt(2, filmRaiting.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            LOGGER.error("SQLException: ", e);
        }
        return filmRaiting;
    }


    private List<FilmRaiting> initRaitings(ResultSet resultSet) throws SQLException {
        while (resultSet.next()) {
            filmRaiting = initRaiting(resultSet);
            filmRaitings.add(filmRaiting);
        }
        return filmRaitings;
    }

    private FilmRaiting initRaiting(ResultSet resultSet) throws SQLException {
        int raitingId = resultSet.getInt("raitingId");
        filmRaiting.setId(raitingId);
        int filmId = resultSet.getInt("filmId");
        filmRaiting.setFilmId(filmId);
        int userId = resultSet.getInt("userId");
        filmRaiting.setUserId(userId);
        int raiting = resultSet.getInt("raiting");
        filmRaiting.setRaiting(raiting);
        return filmRaiting;
    }
}
