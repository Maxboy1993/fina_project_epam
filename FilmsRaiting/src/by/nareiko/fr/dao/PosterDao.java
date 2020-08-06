package by.nareiko.fr.dao;

import by.nareiko.fr.exception.DaoException;

import java.io.InputStream;
import java.sql.Blob;
import java.util.Optional;

/**
 * The interface Poster dao.
 */
public interface PosterDao {
    /**
     * Find poster optional.
     *
     * @param filmId the film id
     * @return the optional
     * @throws DaoException the dao exception
     */
    Optional<Blob> findPoster(int filmId) throws DaoException;

    /**
     * Update poster optional.
     *
     * @param filmId the film id
     * @param poster the poster
     * @return the optional
     * @throws DaoException the dao exception
     */
    Optional<Blob> updatePoster(int filmId, Blob poster) throws DaoException;

    /**
     * Add poster optional.
     *
     * @param filmId      the film id
     * @param inputStream the input stream
     * @return the optional
     * @throws DaoException the dao exception
     */
    Optional<Blob> addPoster(int filmId, InputStream inputStream) throws DaoException;

    /**
     * Delete poster boolean.
     *
     * @param filmId the film id
     * @return the boolean
     * @throws DaoException the dao exception
     */
    boolean deletePoster(int filmId) throws DaoException;
}
