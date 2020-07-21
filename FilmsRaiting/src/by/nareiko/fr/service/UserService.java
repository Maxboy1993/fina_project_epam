package by.nareiko.fr.service;

import by.nareiko.fr.entity.AbstractEntity;
import by.nareiko.fr.exception.ServiceException;

import java.util.Optional;

public interface UserService<T extends AbstractEntity> {
    boolean checkUser(String login, String password);

    Optional<T> findByLoginAndPassword(String login, String password) throws ServiceException;

    boolean checkUserRegistrationData(String firstName, String lastName, String login, String password, String[] birthday);

    Optional<T> registrateUser(String firstName, String lastName, String login, String password, String[] birthday) throws ServiceException;
}
