package by.nareiko.fr.service;

import by.nareiko.fr.entity.AbstractEntity;
import by.nareiko.fr.entity.User;
import by.nareiko.fr.exception.ServiceException;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface UserService<T extends AbstractEntity> {
    boolean checkUserByLoginAdPassword(String login, String password);

    Optional<T> findByLoginAndPassword(String login, String password) throws ServiceException;

    Optional<T> findById(int userId) throws ServiceException;

    Optional<T> changeRole(User user) throws ServiceException;

    boolean checkUserRegistrationData(String firstName, String lastName, String login, String password, String birthday);

    Optional<T> registrateUser(String firstName, String lastName, String login, String password, String birthday) throws ServiceException;

    void activateUser(String login) throws ServiceException;

    List<T> findAllUsers() throws ServiceException;

    boolean deleteUser(int userId) throws ServiceException;

    Set<String> getUserErrorMessages();
}
