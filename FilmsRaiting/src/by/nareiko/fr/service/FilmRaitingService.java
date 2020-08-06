package by.nareiko.fr.service;

import by.nareiko.fr.entity.AbstractEntity;
import by.nareiko.fr.exception.ServiceException;

import java.util.List;
import java.util.Optional;

/**
 * The interface Film raiting service.
 *
 * @param <T> the type parameter
 */
public interface FilmRaitingService<T extends AbstractEntity> {
    /**
     * Find by film id list.
     *
     * @param filmId the film id
     * @return the list
     * @throws ServiceException the service exception
     */
    List<T> findByFilmId(int filmId) throws ServiceException;

    /**
     * Create optional.
     *
     * @param filmId      the film id
     * @param userId      the user id
     * @param userRaiting the user raiting
     * @return the optional
     * @throws ServiceException the service exception
     */
    Optional<T> create(int filmId, int userId, int userRaiting) throws ServiceException;

    /**
     * Countfinal film raiting double.
     *
     * @param filmId the film id
     * @return the double
     * @throws ServiceException the service exception
     */
    double countfinalFilmRaiting(int filmId) throws ServiceException;
}
