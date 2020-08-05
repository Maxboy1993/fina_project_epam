package by.nareiko.fr.dao;

import by.nareiko.fr.exception.DaoException;

import java.io.InputStream;
import java.sql.Blob;
import java.util.Optional;

public interface PosterDao {
    Optional<Blob> findPoster(int filmId) throws DaoException;

    Optional<Blob> updatePoster(int filmId, Blob poster) throws DaoException;

    Optional<Blob> addPoster(int filmId, InputStream inputStream) throws DaoException;

    boolean deletePoster(int filmId) throws DaoException;
}
