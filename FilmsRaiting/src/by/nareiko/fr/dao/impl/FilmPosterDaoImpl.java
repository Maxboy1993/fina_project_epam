package by.nareiko.fr.dao.impl;

import by.nareiko.fr.dao.PosterDao;
import by.nareiko.fr.dao.SqlColumnName;
import by.nareiko.fr.dao.SqlQuery;
import by.nareiko.fr.exception.DaoException;
import by.nareiko.fr.pool.ConnectionPool;

import java.io.InputStream;
import java.sql.*;
import java.util.Optional;

/**
 * The type Film poster dao.
 */
public class FilmPosterDaoImpl implements PosterDao {
    private static final PosterDao INSTANCE = new FilmPosterDaoImpl();

    private FilmPosterDaoImpl() {
    }

    /**
     * Gets instance.
     *
     * @return the instance
     */
    public static PosterDao getInstance() {
        return INSTANCE;
    }

    @Override
    public Optional<Blob> findPoster(int filmId) throws DaoException {
        Blob poster = null;
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(SqlQuery.FIND_FILM_POSTER)) {
            statement.setInt(1, filmId);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                poster = resultSet.getBlob(SqlColumnName.PSOTER);
            }
        } catch (SQLException e) {
            throw new DaoException("Error while poster searching: ", e);
        }
        return Optional.ofNullable(poster);
    }

    @Override
    public Optional<Blob> updatePoster(int filmId, Blob poster) throws DaoException {
        Optional<Blob> foundPoster;
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(SqlQuery.UPDATE_FILM_POSTER)) {
            statement.setBlob(1, poster);
            statement.setInt(2, filmId);
            statement.executeUpdate();
            foundPoster = findPoster(filmId);
        } catch (SQLException e) {
            throw new DaoException("Error while poster deleting: ", e);
        }
        return foundPoster;
    }

    @Override
    public Optional<Blob> addPoster(int filmId, InputStream inputStream) throws DaoException {
        Optional<Blob> optionalBlob;
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(SqlQuery.ADD_FILM_POSTER)) {
            statement.setInt(1, filmId);
            statement.setBlob(2, inputStream);
            statement.executeUpdate();
            optionalBlob = findPoster(filmId);
        } catch (SQLException e) {
            throw new DaoException("Error while poster adding: ", e);
        }
        return optionalBlob;
    }

    @Override
    public boolean deletePoster(int filmId) throws DaoException {
        boolean isDeleted;
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(SqlQuery.DELETE_POSTER_BY_FILM_ID)) {
            statement.setInt(1, filmId);
            statement.executeUpdate();
            isDeleted = true;
        } catch (SQLException e) {
            throw new DaoException("Error while poster deleting: ", e);
        }
        return isDeleted;
    }
}
