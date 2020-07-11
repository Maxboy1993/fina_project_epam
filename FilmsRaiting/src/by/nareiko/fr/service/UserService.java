package by.nareiko.fr.service;

import by.nareiko.fr.entity.User;

public interface UserService {
    boolean checkUser(String login, String password);

    User findByLoginAndPassword(String login, String password);

    boolean checkUserRegistrationData(String firstName, String lastName, String login, String password, String[] birthday);

    User registrateUser(String firstName, String lastName, String login, String password, String[] birthday);
}
