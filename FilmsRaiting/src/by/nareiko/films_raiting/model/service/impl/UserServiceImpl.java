package by.nareiko.films_raiting.model.service.impl;

import by.nareiko.films_raiting.entity.RoleType;
import by.nareiko.films_raiting.entity.StatusType;
import by.nareiko.films_raiting.entity.User;
import by.nareiko.films_raiting.model.dao.impl.UserDaoImpl;
import by.nareiko.films_raiting.model.service.UserService;
import by.nareiko.films_raiting.validator.UserValidator;

import java.util.Calendar;
import java.util.GregorianCalendar;

public class UserServiceImpl implements UserService {
    private static final RoleType DEFAULT_ROLE = RoleType.USER;
    private static final StatusType DEFAULT_STATUS = StatusType.ACTIVE;
    private static int YEAR_INDEX = 0;
    private static int MONTH_INDEX = 1;
    private static int DAY_INDEX = 2;

    @Override
    public boolean checkUser(String login, String password) {
        boolean isCorrectUser = false;
        UserValidator validator = new UserValidator();
        if (validator.checkLoginData(login, password)) {
            isCorrectUser = true;
        }
        User user = findByLoginAndPassword(login, password);
        if (user == null){
            isCorrectUser = false;
        }
        return isCorrectUser;
    }

    public boolean checkUserRegistrationData(String firstName, String lastName, String login, String password,String[] birthday ) {
        boolean isCorrectUser = false;
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

    public User registrateUser(String firstName, String lastName, String login, String password, String[] birthday) {
        UserDaoImpl userDao = new UserDaoImpl();
        User user = new User();
        user.setFirstName(firstName);
        user.setLastName(lastName);
        Calendar calendar = new GregorianCalendar();
        int year = Integer.parseInt(birthday[YEAR_INDEX]);
        int month = Integer.parseInt(birthday[MONTH_INDEX]) - 1;
        int day = Integer.parseInt(birthday[DAY_INDEX]);
        calendar.set(year, month, day);
        user.setBirthday(calendar);
        user.setLogin(login);
        user.setPassword(password);
        user.setRoleType(DEFAULT_ROLE);
        user.setStatusType(DEFAULT_STATUS);
        userDao.create(user);
        return user;
    }

    public static void main(String[] args) {
        UserServiceImpl service = new UserServiceImpl();
        User user = service.registrateUser("max", "Bill","max@gmail.com", "20max20", new String[]{"11", "12", "2000"});
        System.out.print(user.getFirstName());
    }
}
