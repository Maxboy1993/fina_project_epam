package by.nareiko.films_raiting.model.service;

import by.nareiko.films_raiting.entity.User;

public interface UserService {
    boolean checkUser(String login, String password);
    User findByLoginAndPassword(String login, String password);
}
