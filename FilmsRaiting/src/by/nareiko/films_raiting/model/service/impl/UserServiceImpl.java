package by.nareiko.films_raiting.model.service.impl;

import by.nareiko.films_raiting.entity.User;
import by.nareiko.films_raiting.model.dao.impl.UserDaoImpl;
import by.nareiko.films_raiting.model.service.UserService;
import by.nareiko.films_raiting.validator.UserValidator;

public class UserServiceImpl implements UserService {
private boolean isCorrectUser;
    @Override
    public boolean checkUser(String login, String password) {
        UserValidator validator = new UserValidator();
        if (validator.checkLoginData(login, password)) {
            isCorrectUser = true;
        }
        return isCorrectUser;
    }

    public User findByLoginAndPassword(String login, String password) {
        UserDaoImpl userDao = new UserDaoImpl();
        User user = userDao.findByLoginAndPassword(login, password);
        return user;
    }

    public static void main(String[] args) {
        UserServiceImpl service = new UserServiceImpl();
        User user = service.findByLoginAndPassword("tom40@gmail.com", "40tom40");
        System.out.print(user.getName());
    }
}
