package model.service;

import entity.User;

public interface UserService {
    boolean checkUser(String login, String password);
    User findByLoginAndPassword(String login, String password);
}
