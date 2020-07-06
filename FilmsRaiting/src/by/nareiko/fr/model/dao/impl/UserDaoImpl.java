package by.nareiko.fr.model.dao.impl;

import by.nareiko.fr.entity.*;
import by.nareiko.fr.exception.EntityException;
import by.nareiko.fr.model.dao.UserDao;
import by.nareiko.fr.exception.DaoException;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import by.nareiko.fr.pool.ConnectionPool;

import java.sql.*;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
// реализовать методы

public class UserDaoImpl extends AbstractEntity implements UserDao<User> {
    private List<User> users;
    private User user;
    private static final String ADMIN_ROLE = "admin";
    private static final int ADMIN_ROLE_ID = 1;
    private static final String USER_ROLE = "user";
    private static final int USER_ROLE_ID = 2;
    private static final String ACTIVE_STATUS = "active";
    private static final int ACTIVE_STATUS_ID = 1;
    private static final String INACTIVE_STATUS = "inactive";
    private static final int INACTIVE_STATUS_ID = 2;
    private static final String FIND_ALL_USERS = "SELECT User.userId, User.firstName, User.lastName, User.birthday, User.roleId, UserRole.role, " +
            "User.login, User.password, User.statusId, Status.status FROM User JOIN Status ON User.statusId = Status.statusId " +
            "JOIN UserRole ON User.roleId = UserRole.roleId";
    private static final String FIND_USER_BY_LOGIN = "SELECT User.userId, User.firstName, User.lastName, User.birthday, User.roleId, UserRole.role, " +
            "User.login, User.password, User.statusId, Status.status FROM User JOIN Status ON User.statusId = Status.statusId " +
            "JOIN UserRole ON User.roleId = UserRole.roleId WHERE User.login = ?";
    private static final String FIND_USER_BY_LOGIN_AND_PASSWORD = "SELECT User.userId, User.firstName, User.lastName, User.birthday, User.roleId, UserRole.role, " +
            "User.login, User.password, User.statusId, Status.status FROM User JOIN Status ON User.statusId = Status.statusId " +
            "JOIN UserRole ON User.roleId = UserRole.roleId WHERE User.login = ? AND User.password = ?";
    private static final String FIND_USER_BY_ID = "SELECT User.userId, User.firstName, User.lastName, User.birthday, User.roleId, UserRole.role, " +
            "User.login, User.password, User.statusId, Status.status FROM User JOIN Status ON User.statusId = Status.statusId " +
            "JOIN UserRole ON User.roleId = UserRole.roleId WHERE User.userId = ?";
    private static final String DELETE_USER_BY_ID = "UPDATE user SET statusId = 2 WHERE userId = ?";
    private static final String DELETE_USER_BY_LOGIN = "UPDATE user SET statusId = 2 WHERE login = ?";
    private static final String CREATE_USER = "INSERT INTO user (firstName, lastName, birthday, roleId, " +
            "login, password, statusId) VALUES (?, ?, ?, ?, ?, ?, ?)";
    private static final String UPDATE_USER = "UPDATE user SET firstNmae = ?, lastName = ?, birthday = ?" +
            "roleId = ?, login = ?, password = ?, statusId = ? WHERE userId = ?";
    private static final Logger LOGGER = LogManager.getLogger();
    boolean isCreated;

    public UserDaoImpl() {
        users = new ArrayList<>();
    }

    @Override
    public User findByLogin(String login) {
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(FIND_USER_BY_LOGIN)) {
            statement.setString(1, login);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                user = initUser(resultSet);
            }
        } catch (SQLException | EntityException e) {
            LOGGER.error(e);
        }
        return user;
    }

    @Override
    public User findByLoginAndPassword(String login, String password) {
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(FIND_USER_BY_LOGIN_AND_PASSWORD)) {
            statement.setString(1, login);
            String md5HexPassword = DigestUtils.md5Hex(password);
            statement.setString(2, md5HexPassword);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                user = initUser(resultSet);
            }
        } catch (SQLException | EntityException e) {
            LOGGER.error(e);
        }
        return user;
    }

    @Override
    public User delete(String login) throws DaoException {
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(DELETE_USER_BY_LOGIN)) {
            // if (user.getStatusType().equals("active")){                вынести проверку в сервис
            statement.setString(1, login);
            statement.executeUpdate();
//            }else {
//                throw new DaoException("User with login: " + login + ", is alredy deleted");
//            }
            user = findByLogin(login);
        } catch (SQLException e) {
            LOGGER.error("SQLException: ", e);
        }
        return user;
    }

    @Override
    public List<User> findAll() {
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(FIND_ALL_USERS);
            users = initUsers(resultSet);
        } catch (SQLException | EntityException e) {
            LOGGER.error(e);
        }
        return users;
    }

    @Override
    public User findById(int id) {
        return null;
    }

//    @Override
//    public User findById(int id) {
//        try (Connection connection = ConnectionPool.getInstance().getConnection();
//             PreparedStatement statement = connection.prepareStatement(FIND_USER_BY_ID)) {
//            statement.setInt(1, id);
//            ResultSet resultSet = statement.executeQuery();
//            if (resultSet.next()) {
//                user = initUser(resultSet);
//            }
//        } catch (SQLException | EntityException e) {
//            LOGGER.error(e);
//        }
//        return user;
//    }

    @Override
    public User delete(User user) throws DaoException {
        delete(user.getLogin());
        return user;
    }

    @Override
    public User delete(int id) {
        return null;
    }

//    @Override
//    public User delete(int id) {
//        try (Connection connection = ConnectionPool.getInstance().getConnection();
//             PreparedStatement statement = connection.prepareStatement(DELETE_USER_BY_ID)) {
//            user = findById(id); //?????????????????????
//            // протестить
//            statement.setInt(1, id);
//            statement.executeUpdate();
//        } catch (SQLException e) {
//            LOGGER.error("SQLException: ", e);
//        }
//        return user;
//    }


// add id autoincremented
    @Override
    public boolean create(User user) {
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(CREATE_USER)) {
            statement.setString(1, user.getFirstName());
            statement.setString(2, user.getLastName());
            long birtgday = user.getBirthday().getTimeInMillis();
            statement.setLong(3, birtgday);
            int roleId = USER_ROLE_ID;
            if (user.getRoleType().equals(RoleType.getRoleTypeByValue(ADMIN_ROLE))) {
                roleId = ADMIN_ROLE_ID;
            }
            statement.setInt(4, roleId);
            statement.setString(5, user.getLogin());
            statement.setString(6, user.getPassword());
            statement.setInt(7, ACTIVE_STATUS_ID);
            statement.executeUpdate();
            isCreated = true;
        } catch (SQLException | EntityException e) {
            LOGGER.error("SQLException: ", e);
        }
        return isCreated;
    }

    @Override
    public User update(User user) {
        User user1 = new User();
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(CREATE_USER)) {
            statement.setString(1, user.getFirstName());
            statement.setString(2, user.getLastName());
            long birtgday = user.getBirthday().getTimeInMillis();
            statement.setLong(3, birtgday);
            int roleId = USER_ROLE_ID;
            if (user.getRoleType().equals(RoleType.getRoleTypeByValue(ADMIN_ROLE))) {
                roleId = ADMIN_ROLE_ID;
            }
            statement.setInt(4, roleId);
            statement.setString(5, user.getLogin());
            statement.setString(6, user.getPassword());
            int statusId = ACTIVE_STATUS_ID;
            if (user.getStatusType().equals(RoleType.getRoleTypeByValue(INACTIVE_STATUS))){
                statusId = INACTIVE_STATUS_ID;
            }
            statement.setInt(7, statusId);
            user1 = findByLogin(user.getLogin());       // так правильно при update???
        } catch (SQLException | EntityException e) {
            LOGGER.error("SQLException: ", e);
        }
        return user1;
    }

    private Calendar getDateFromLong(long dateMillis) {
        Calendar date = new GregorianCalendar();
        date.setTimeInMillis(dateMillis);
        return date;
    }

    private List<User> initUsers(ResultSet resultSet) throws SQLException, EntityException {
        while (resultSet.next()) {
            user = initUser(resultSet);
            users.add(user);
        }
        return users;
    }

    private User initUser(ResultSet resultSet) throws SQLException, EntityException {
        user = new User();
        String firstName = resultSet.getString("firstName");
        user.setFirstName(firstName);
        String lastName = resultSet.getString("lastName");
        user.setLastName(lastName);
        long longBirthday = resultSet.getLong("birthday");
        Calendar birthday = getDateFromLong(longBirthday);
        user.setBirthday(birthday);
        RoleType roleType = RoleType.getRoleTypeByValue(resultSet.getString("role").trim());
        user.setRoleType(roleType);
        String login = resultSet.getString("login");
        user.setLogin(login);
        String password = resultSet.getString("password");
        user.setPassword(password);
        StatusType statusType = StatusType.getStatusTypeByValue(resultSet.getString("status").trim());
        user.setStatusType(statusType);
        return user;
    }

    public static void main(String[] args) {
//        UserDaoImpl userDao = new UserDaoImpl();
//        List<User> list = new ArrayList<>();
//        list = userDao.findAll();
//        for (User user : list) {
//            System.out.println(user.toString());
//        }
//        User user1 = new User();
//        User user2 = new User();
//        user1 = userDao.findByLoginAndPassword("tom40@gmail.com", "40tom40");
//        user2 = userDao.findByLogin("tom40@gmail.com");
//        System.out.println(user1.toString());
//        System.out.println(user2.toString());


            String md5Hex = DigestUtils.md5Hex("40tom40");
        System.out.println(md5Hex);

        }
    }

