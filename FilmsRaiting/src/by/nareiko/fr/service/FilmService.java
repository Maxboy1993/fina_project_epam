package by.nareiko.fr.service;

import by.nareiko.fr.entity.AbstractEntity;
import by.nareiko.fr.entity.Actor;
import by.nareiko.fr.entity.Director;
import by.nareiko.fr.exception.ServiceException;

import java.util.List;
import java.util.Optional;
import java.util.Set;

/**
 * The interface Film service.
 *
 * @param <T> the type parameter
 */
public interface FilmService<T extends AbstractEntity> {
    /**
     * Find all films list.
     *
     * @return the list
     * @throws ServiceException the service exception
     */
    List<T> findAllFilms() throws ServiceException;

    /**
     * Check film data boolean.
     *
     * @param filmName    the film name
     * @param releaseDate the release date
     * @param genre       the genre
     * @return the boolean
     */
    boolean checkFilmData(String filmName, String releaseDate, String genre);

    /**
     * Check film name boolean.
     *
     * @param filmName the film name
     * @return the boolean
     */
    boolean checkFilmName(String filmName);

    /**
     * Delete film boolean.
     *
     * @param filmId the film id
     * @return the boolean
     * @throws ServiceException the service exception
     */
    boolean deleteFilm(int filmId) throws ServiceException;

    /**
     * Add film optional.
     *
     * @param filmName    the film name
     * @param releaseDate the release date
     * @param genre       the genre
     * @param actors      the actors
     * @param director    the director
     * @return the optional
     * @throws ServiceException the service exception
     */
    Optional<T> addFilm(String filmName, String releaseDate, String genre, List<Actor> actors, Director director) throws ServiceException;

    /**
     * Find by id optional.
     *
     * @param filmId the film id
     * @return the optional
     * @throws ServiceException the service exception
     */
    Optional<T> findById(int filmId) throws ServiceException;

    /**
     * Find films by name list.
     *
     * @param filmName the film name
     * @return the list
     * @throws ServiceException the service exception
     */
    List<T> findFilmsByName(String filmName) throws ServiceException;

    /**
     * Gets errors film message.
     *
     * @return the errors film message
     */
    Set<String> getErrorsFilmMessage();
}
