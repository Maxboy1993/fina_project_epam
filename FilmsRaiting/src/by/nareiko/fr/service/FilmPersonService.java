package by.nareiko.fr.service;

import by.nareiko.fr.entity.AbstractEntity;
import by.nareiko.fr.exception.ServiceException;

import java.util.Optional;
import java.util.Set;

/**
 * The interface Film person service.
 *
 * @param <T> the type parameter
 */
public interface FilmPersonService<T extends AbstractEntity> {
    /**
     * Check person data boolean.
     *
     * @param firstName the first name
     * @param lastName  the last name
     * @param birthday  the birthday
     * @return the boolean
     */
    boolean checkPersonData(String firstName, String lastName, String birthday);

    /**
     * Create person optional.
     *
     * @param firstName the first name
     * @param lastName  the last name
     * @param birthday  the birthday
     * @return the optional
     * @throws ServiceException the service exception
     */
    Optional<T> createPerson(String firstName, String lastName, String birthday) throws ServiceException;

    /**
     * Find by last name and first name optional.
     *
     * @param lastName  the last name
     * @param firstName the first name
     * @return the optional
     * @throws ServiceException the service exception
     */
    Optional<T> findByLastNameAndFirstName(String lastName, String firstName) throws ServiceException;

    /**
     * Gets film person errors.
     *
     * @return the film person errors
     */
    Set<String> getFilmPersonErrors();
}
