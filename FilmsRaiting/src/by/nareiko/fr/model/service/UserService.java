package by.nareiko.fr.model.service;

import by.nareiko.fr.entity.User;

public interface UserService {
    boolean checkUser(String login, String password);
    User findByLoginAndPassword(String login, String password);
}
