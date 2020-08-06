package by.nareiko.fr.service;

import by.nareiko.fr.exception.ServiceException;

import javax.servlet.http.Part;
import java.io.InputStream;
import java.util.List;
import java.util.Optional;

/**
 * The interface Poster service.
 *
 * @param <T> the type parameter
 */
public interface PosterService<T> {
    /**
     * Check poster extension boolean.
     *
     * @param filePart the file part
     * @return the boolean
     */
    boolean checkPosterExtension(Part filePart);

    /**
     * Add poster optional.
     *
     * @param filmId      the film id
     * @param inputStream the input stream
     * @return the optional
     * @throws ServiceException the service exception
     */
    Optional<T> addPoster(int filmId, InputStream inputStream) throws ServiceException;

    /**
     * Find poster by id input stream.
     *
     * @param filmId the film id
     * @return the input stream
     * @throws ServiceException the service exception
     */
    InputStream findPosterById(int filmId) throws ServiceException;

    /**
     * Find all posters list.
     *
     * @param filmsId the films id
     * @return the list
     * @throws ServiceException the service exception
     */
    List<InputStream> findAllPosters(List<Integer> filmsId) throws ServiceException;

}
