package by.nareiko.fr.service;

import by.nareiko.fr.entity.AbstractEntity;
import by.nareiko.fr.entity.User;
import by.nareiko.fr.exception.ServiceException;

import java.util.List;
import java.util.Optional;
import java.util.Set;

/**
 * The interface User service.
 *
 * @param <T> the type parameter
 */
public interface UserService<T extends AbstractEntity> {
    /**
     * Check user by login ad password boolean.
     *
     * @param login    the login
     * @param password the password
     * @return the boolean
     */
    boolean checkUserByLoginAdPassword(String login, String password);

    /**
     * Find by login and password optional.
     *
     * @param login    the login
     * @param password the password
     * @return the optional
     * @throws ServiceException the service exception
     */
    Optional<T> findByLoginAndPassword(String login, String password) throws ServiceException;

    /**
     * Find by id optional.
     *
     * @param userId the user id
     * @return the optional
     * @throws ServiceException the service exception
     */
    Optional<T> findById(int userId) throws ServiceException;

    /**
     * Change role optional.
     *
     * @param user the user
     * @return the optional
     * @throws ServiceException the service exception
     */
    Optional<T> changeRole(User user) throws ServiceException;

    /**
     * Check user registration data boolean.
     *
     * @param firstName the first name
     * @param lastName  the last name
     * @param login     the login
     * @param password  the password
     * @param birthday  the birthday
     * @return the boolean
     */
    boolean checkUserRegistrationData(String firstName, String lastName, String login, String password, String birthday);

    /**
     * Registrate user optional.
     *
     * @param firstName the first name
     * @param lastName  the last name
     * @param login     the login
     * @param password  the password
     * @param birthday  the birthday
     * @return the optional
     * @throws ServiceException the service exception
     */
    Optional<T> registrateUser(String firstName, String lastName, String login, String password, String birthday) throws ServiceException;

    /**
     * Activate user.
     *
     * @param login the login
     * @throws ServiceException the service exception
     */
    void activateUser(String login) throws ServiceException;

    /**
     * Find all users list.
     *
     * @return the list
     * @throws ServiceException the service exception
     */
    List<T> findAllUsers() throws ServiceException;

    /**
     * Delete user boolean.
     *
     * @param userId the user id
     * @return the boolean
     * @throws ServiceException the service exception
     */
    boolean deleteUser(int userId) throws ServiceException;

    /**
     * Gets user error messages.
     *
     * @return the user error messages
     */
    Set<String> getUserErrorMessages();
}
