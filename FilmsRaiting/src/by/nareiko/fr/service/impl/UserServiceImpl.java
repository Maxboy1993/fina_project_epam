package by.nareiko.fr.service.impl;

import by.nareiko.fr.dao.DaoFactory;
import by.nareiko.fr.dao.UserDao;
import by.nareiko.fr.entity.RoleType;
import by.nareiko.fr.entity.StatusType;
import by.nareiko.fr.entity.User;
import by.nareiko.fr.exception.DaoException;
import by.nareiko.fr.exception.ServiceException;
import by.nareiko.fr.service.UserService;
import by.nareiko.fr.util.PasswordHasher;
import by.nareiko.fr.validator.UserValidator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Optional;

public class UserServiceImpl implements UserService<User> {
    private static final RoleType DEFAULT_ROLE = RoleType.USER;
    private static final StatusType DEFAULT_STATUS = StatusType.ACTIVE;
    private static final UserService INSTANCE = new UserServiceImpl();
    private static final Logger LOGGER = LogManager.getLogger();
    private static int YEAR_INDEX = 0;
    private static int MONTH_INDEX = 1;
    private static int DAY_INDEX = 2;

    private UserServiceImpl() {
    }

    public static UserService getInstance() {
        return INSTANCE;
    }

    @Override
    public boolean checkUserByLoginAdPassword(String login, String password) {
        boolean isCorrectUser = false;
        UserValidator validator = new UserValidator();
        if (validator.validateLoginAndPassword(login, password)) {
            isCorrectUser = true;
        }
        return isCorrectUser;
    }

    public boolean checkUserRegistrationData(String firstName, String lastName, String login, String password, String birthday) {
        boolean isCorrectUser = false;
        UserValidator validator = new UserValidator();
        if (validator.validateRegistrationData(firstName, lastName, login, password, birthday)) {
            isCorrectUser = true;
        }
        return isCorrectUser;
    }

    public Optional<User> findByLoginAndPassword(String login, String password) throws ServiceException {
        UserDao userDao = DaoFactory.getInstance().getUserDao();
        Optional<User> user;
        try {
            user = userDao.findByLoginAndPassword(login, password);
        } catch (DaoException e) {
            LOGGER.error("Error while seaching by id: ", e);
            throw new ServiceException("Error while seaching by id: ", e);
        }
        return user;
    }

    public Optional<User> registrateUser(String firstName, String lastName, String login, String password, String birthday) throws ServiceException {
        UserDao userDao = DaoFactory.getInstance().getUserDao();
        User user = new User();
        user.setFirstName(firstName);
        user.setLastName(lastName);
        Calendar calendar = new GregorianCalendar();
        String[] birhtdaySeparator = birthday.split("-");
        int year = Integer.parseInt(birhtdaySeparator[YEAR_INDEX]);
        int month = Integer.parseInt(birhtdaySeparator[MONTH_INDEX]) - 1;
        int day = Integer.parseInt(birhtdaySeparator[DAY_INDEX]);
        calendar.set(year, month, day);
        user.setBirthday(calendar);
        user.setLogin(login);
        String md5HexPassword = PasswordHasher.hashPassword(password);
        user.setPassword(md5HexPassword);
        user.setRoleType(DEFAULT_ROLE);
        user.setStatusType(DEFAULT_STATUS);
        try {
            userDao.create(user);
        } catch (DaoException e) {
            LOGGER.error("Error while creating user: ", e);
            throw new ServiceException("Error while creating user: ", e);
        }
        return Optional.ofNullable(user);
    }

    @Override
    public void activateUser(String login) throws ServiceException {
        UserDao userDao = DaoFactory.getInstance().getUserDao();
        try {
            userDao.verifyUser(login);
        } catch (DaoException e) {
            LOGGER.error("User isn't verified: ", e);
            throw new ServiceException("Error while user verification", e);
        }

    }
}
