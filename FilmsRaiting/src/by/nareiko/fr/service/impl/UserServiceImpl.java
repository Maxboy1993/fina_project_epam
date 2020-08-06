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

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

/**
 * The type User service.
 */
public class UserServiceImpl implements UserService<User> {
    private static final RoleType DEFAULT_ROLE = RoleType.USER;
    private static final StatusType DEFAULT_STATUS = StatusType.ACTIVE;
    private static final Logger LOGGER = LogManager.getLogger();
    private static final UserService INSTANCE = new UserServiceImpl();
    private Set<String> errorMessages = new HashSet<>();


    private UserServiceImpl() {
    }

    /**
     * Gets instance.
     *
     * @return the instance
     */
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
        if (!isCorrectUser) {
            errorMessages.addAll(validator.getUserErrorMessages());
        }
        return isCorrectUser;
    }

    public boolean checkUserRegistrationData(String firstName, String lastName, String login, String password, String birthday) {
        boolean isCorrectUser = false;
        UserValidator validator = new UserValidator();
        if (validator.validateRegistrationData(firstName, lastName, login, password, birthday)) {
            isCorrectUser = true;
        }
        if (!isCorrectUser) {
            errorMessages.addAll(validator.getUserErrorMessages());
        }
        return isCorrectUser;
    }

    public Optional<User> findByLoginAndPassword(String login, String password) throws ServiceException {
        UserDao userDao = DaoFactory.getInstance().getUserDao();
        Optional<User> user;
        try {
            user = userDao.findByLoginAndPassword(login, password);
        } catch (DaoException e) {
            LOGGER.error("Error while searching user: ", e);
            throw new ServiceException("Error while searching user: ", e);
        }
        return user;
    }

    @Override
    public Optional<User> findById(int userId) throws ServiceException {
        UserDao userDao = DaoFactory.getInstance().getUserDao();
        Optional<User> user;
        try {
            user = userDao.findById(userId);
        } catch (DaoException e) {
            LOGGER.error("Error while searching user: ", e);
            throw new ServiceException("Error while searching user: ", e);
        }
        return user;
    }

    @Override
    public Optional<User> changeRole(User user) throws ServiceException {
        UserDao userDao = DaoFactory.getInstance().getUserDao();
        Optional<User> optionalUser;
        try {
            optionalUser = userDao.update(user);
        } catch (DaoException e) {
            LOGGER.error("Error while changing role: ", e);
            throw new ServiceException("Error while changing role: ", e);
        }
        return optionalUser;
    }

    public Optional<User> registrateUser(String firstName, String lastName, String login, String password, String birthday) throws ServiceException {
        UserDao userDao = DaoFactory.getInstance().getUserDao();
        User user = new User();
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setBirthday(birthday);
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

    @Override
    public List<User> findAllUsers() throws ServiceException {
        UserDao userDao = DaoFactory.getInstance().getUserDao();
        List<User> users;
        try {
            users = userDao.findAll();
        } catch (DaoException e) {
            LOGGER.error("Error while users searching: ", e);
            throw new ServiceException("Error while users searching: ", e);
        }
        return users;
    }

    @Override
    public boolean deleteUser(int userId) throws ServiceException {
        UserDao userDao = DaoFactory.getInstance().getUserDao();
        boolean isDeleted;
        try {
            isDeleted = userDao.delete(userId);
        } catch (DaoException e) {
            LOGGER.error("Error while users deleting: ", e);
            throw new ServiceException("Error while users deleting:: ", e);
        }
        return isDeleted;
    }

    @Override
    public Set<String> getUserErrorMessages() {
        Set<String> errors = new HashSet<>();
        errors.addAll(errorMessages);
        errorMessages.removeAll(errorMessages);
        return errors;
    }
}
