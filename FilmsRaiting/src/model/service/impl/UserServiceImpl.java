package model.service.impl;

import model.service.UserService;

public class UserServiceImpl implements UserService {
    @Override
    public boolean checkUser(String login, String password) {
        //сделать проверку на наличие в БД
        return false;
    }
}
