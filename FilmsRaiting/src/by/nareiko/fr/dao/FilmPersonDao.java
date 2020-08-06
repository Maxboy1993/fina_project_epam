package by.nareiko.fr.dao;

import by.nareiko.fr.entity.AbstractEntity;
import by.nareiko.fr.exception.DaoException;

import java.util.List;
import java.util.Optional;

/**
 * The interface Film person dao.
 *
 * @param <T> the type parameter
 */
public interface FilmPersonDao<T extends AbstractEntity> extends BaseDao<T> {
    /**
     * Find by last name list.
     *
     * @param name the name
     * @return the list
     * @throws DaoException the dao exception
     */
    List<T> findByLastName(String name) throws DaoException;

    /**
     * Find by last name and first name optional.
     *
     * @param lastName  the last name
     * @param firstName the first name
     * @return the optional
     * @throws DaoException the dao exception
     */
    Optional<T> findByLastNameAndFirstName(String lastName, String firstName) throws DaoException;

    /**
     * Delete boolean.
     *
     * @param lastName  the last name
     * @param firstName the first name
     * @return the boolean
     * @throws DaoException the dao exception
     */
    boolean delete(String lastName, String firstName) throws DaoException;
}
