package model.service.impl;

import entity.User;
import model.dao.impl.UserDaoImpl;
import model.service.UserService;
import validator.UserValidator;

public class UserServiceImpl implements UserService {
    UserValidator validator;
private boolean isCorrectUser;
    @Override
    public boolean checkUser(String login, String password) {
        validator = new UserValidator();
        if (validator.checkLoginData(login, password)) {
            isCorrectUser = true;
        }

        // Создать ДАО через фабрику

        UserDaoImpl userDao = new UserDaoImpl();
        userDao.findByLoginAndPassword(login, password);
        return isCorrectUser;
    }

    public User findByLoginAndPassword(String login, String password) {
        UserDaoImpl userDao = new UserDaoImpl();
        User user = userDao.findByLoginAndPassword(login, password);
        StringBuilder builder = new StringBuilder();
        return user;
    }

    public static void main(String[] args) {
        UserServiceImpl service = new UserServiceImpl();
        User user = service.findByLoginAndPassword("tom40@gmail.com", "40tom40");
        System.out.print(user.getName());
    }
}
