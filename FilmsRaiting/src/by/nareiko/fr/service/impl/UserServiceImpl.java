package by.nareiko.fr.service.impl;

import by.nareiko.fr.entity.RoleType;
import by.nareiko.fr.entity.StatusType;
import by.nareiko.fr.entity.User;
import by.nareiko.fr.dao.impl.UserDaoImpl;
import by.nareiko.fr.service.UserService;
import by.nareiko.fr.util.PasswordHasher;
import by.nareiko.fr.validator.UserValidator;

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
        return isCorrectUser;
    }

    public boolean checkUserRegistrationData(String firstName, String lastName, String login, String password, String[] birthday) {
        boolean isCorrectUser = false;
        UserValidator validator = new UserValidator();
        if (validator.checkRegistrationData(firstName, lastName, login, password, birthday)) {
            isCorrectUser = true;
        }
        return isCorrectUser;
    }

    public User findByLoginAndPassword(String login, String password) {
        UserDaoImpl userDao = new UserDaoImpl(); // Change on factory
        User user = userDao.findByLoginAndPassword(login, password);
        return user;
    }

    public User registrateUser(String firstName, String lastName, String login, String password, String[] birthday) {
        UserDaoImpl userDao = new UserDaoImpl(); // Change on factory
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
        String md5HexPassword = PasswordHasher.hashPassword(password);
        user.setPassword(md5HexPassword);
        user.setRoleType(DEFAULT_ROLE);
        user.setStatusType(DEFAULT_STATUS);
        userDao.create(user);
        return user;
    }

    public static void main(String[] args) {
        UserServiceImpl service = new UserServiceImpl(); // Change on factory
//        User user = service.registrateUser("max", "Bill","max@gmail.com", "20max20", new String[]{"11", "12", "2000"});
        User user = service.findByLoginAndPassword("tom40@gmail.com", "40tom40");
        System.out.println(user.getFirstName());
        System.out.println(user.getPassword());
    }
}
