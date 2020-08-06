package by.nareiko.fr.dao;

import by.nareiko.fr.entity.AbstractEntity;
import by.nareiko.fr.exception.DaoException;

import java.util.Optional;

/**
 * The interface User dao.
 *
 * @param <T> the type parameter
 */
public interface UserDao<T extends AbstractEntity> extends BaseDao<T> {

    /**
     * Find by login optional.
     *
     * @param login the login
     * @return the optional
     * @throws DaoException the dao exception
     */
    Optional<T> findByLogin(String login) throws DaoException;

    /**
     * Find by login and password optional.
     *
     * @param login    the login
     * @param password the password
     * @return the optional
     * @throws DaoException the dao exception
     */
    Optional<T> findByLoginAndPassword(String login, String password) throws DaoException;

    /**
     * Delete boolean.
     *
     * @param login the login
     * @return the boolean
     * @throws DaoException the dao exception
     */
    boolean delete(String login) throws DaoException;

    /**
     * Verify user.
     *
     * @param login the login
     * @throws DaoException the dao exception
     */
    void verifyUser(String login) throws DaoException;
}
