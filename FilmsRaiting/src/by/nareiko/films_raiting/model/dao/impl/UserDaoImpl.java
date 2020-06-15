package by.nareiko.films_raiting.model.dao.impl;

import by.nareiko.films_raiting.entity.*;
import by.nareiko.films_raiting.exception.EntityException;
import by.nareiko.films_raiting.model.dao.UserDao;
import by.nareiko.films_raiting.exception.DaoException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import by.nareiko.films_raiting.pool.ConnectionPool;

import java.sql.*;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

public class UserDaoImpl extends AbstractEntity implements UserDao<User> {
    private List<User> users;
    private User user;
    private static final String FIND_ALL_USERS = "SELECT User.userId, User.name, User.birthday, User.roleId, UserRole.role, " +
            "User.login, User.password, User.statusId, Status.status FROM User JOIN Status ON User.statusId = Status.statusId " +
            "JOIN UserRole ON User.roleId = UserRole.roleId";
    private static final String FIND_USER_BY_LOGIN = "SELECT User.userId, User.name, User.birthday, User.roleId, UserRole.role, " +
            "User.login, User.password, User.statusId, Status.status FROM User JOIN Status ON User.statusId = Status.statusId " +
            "JOIN UserRole ON User.roleId = UserRole.roleId WHERE User.login = ?";
    private static final String FIND_USER_BY_LOGIN_AND_PASSWORD = "SELECT User.userId, User.name, User.birthday, User.roleId, UserRole.role, " +
            "User.login, User.password, User.statusId, Status.status FROM User JOIN Status ON User.statusId = Status.statusId " +
            "JOIN UserRole ON User.roleId = UserRole.roleId WHERE User.login = ? AND User.password = ?";
    private static final String FIND_USER_BY_ID = "SELECT User.userId, User.name, User.birthday, User.roleId, UserRole.role, " +
            "User.login, User.password, User.statusId, Status.status FROM User JOIN Status ON User.statusId = Status.statusId " +
            "JOIN UserRole ON User.roleId = UserRole.roleId WHERE User.userId = ?";
    private static final String DELETE_USER_BY_ID = "UPDATE user SET statusId = 2 WHERE userId = ?";
    private static final String DELETE_USER_BY_LOGIN = "UPDATE user SET statusId = 2 WHERE login = ?";
    private static final String CREATE_USER = "INSERT INTO user (name, birthday, roleId, " +
            "login, password, statusId) VALUES (?, ?, ?, ?, ?, ?)";
    private static final String UPDATE_USER = "UPDATE user SET name = ?, birthday = ?" +
            "roleId = ?, login = ?, password = ?, statusId = ? WHERE userId = ?";
//    private static final Logger LOGGER = LogManager.getLogger();
    boolean isCreated;
    private static final String ADMIN_ROLE = "admin";
    private static final int ADMIN_ROLE_ID = 1;
    private static final String USER_ROLE = "user";
    private static final int USER_ROLE_ID = 2;
    private static final String ACTIVE_STATUS = "active";
    private static final int ACTIVE_STATUS_ID = 1;
    private static final String INACTIVE_STATUS = "inactive";
    private static final int INACTIVE_STATUS_ID = 2;

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
//            LOGGER.error(e);
        }
        return user;
    }

    @Override
    public User findByLoginAndPassword(String login, String password) {
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(FIND_USER_BY_LOGIN_AND_PASSWORD)) {
            statement.setString(1, login);
            statement.setString(2, password);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                user = initUser(resultSet);
            }
        } catch (SQLException | EntityException e) {
//            LOGGER.error(e);
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
//            LOGGER.error("SQLException: ", e);
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
//            LOGGER.error(e);
        }
        return users;
    }

    @Override
    public User findById(int id) {
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(FIND_USER_BY_ID)) {
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                user = initUser(resultSet);
            }
        } catch (SQLException | EntityException e) {
//            LOGGER.error(e);
        }
        return user;
    }

    @Override
    public User delete(User user) {
        delete(user.getUserId());
        return user;
    }

    @Override
    public User delete(int id) {
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(DELETE_USER_BY_ID)) {
            statement.setInt(1, id);
            statement.executeUpdate();
            user = findById(id); //?????????????????????
            // протестить
        } catch (SQLException e) {
//            LOGGER.error("SQLException: ", e);
        }
        return user;
    }

    @Override
    public boolean create(User user) {
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(CREATE_USER)) {
            statement.setString(1, user.getName());
            long birtgday = user.getBirthday().getTimeInMillis();
            statement.setLong(2, birtgday);
            int roleId = USER_ROLE_ID;
            if (user.getRoleType().equals(RoleType.valueOf(ADMIN_ROLE))) {
                roleId = ADMIN_ROLE_ID;
            }
            statement.setInt(3, roleId);
            statement.setString(4, user.getLogin());
            statement.setString(5, user.getPassword());
            statement.setInt(6, ACTIVE_STATUS_ID);
            statement.executeUpdate();
            isCreated = true;
        } catch (SQLException e) {
//            LOGGER.error("SQLException: ", e);
        }
        return isCreated;
    }

    @Override
    public User update(User user) {
        User user1 = new User();
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(CREATE_USER)) {
            statement.setString(1, user.getName());
            long birtgday = user.getBirthday().getTimeInMillis();
            statement.setLong(2, birtgday);
            int roleId = USER_ROLE_ID;
            if (user.getRoleType().equals(RoleType.valueOf(ADMIN_ROLE))) {
                roleId = ADMIN_ROLE_ID;
            }
            statement.setInt(3, roleId);
            statement.setString(4, user.getLogin());
            statement.setString(5, user.getPassword());
            int statusId = ACTIVE_STATUS_ID;
            if (user.getStatusType().equals(RoleType.valueOf(INACTIVE_STATUS))){
                statusId = INACTIVE_STATUS_ID;
            }
            statement.setInt(6, statusId);
            user1 = findById(user.getUserId());       // так правильно при update???
        } catch (SQLException e) {
//            LOGGER.error("SQLException: ", e);
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
        int id = resultSet.getInt(1);
        user.setUserId(id);
        String name = resultSet.getString(2);
        user.setName(name);
        long longBirthday = resultSet.getLong(3);
        Calendar birthday = getDateFromLong(longBirthday);
        user.setBirthday(birthday);
        RoleType roleType = RoleType.getRoleTypeByValue(resultSet.getString(5).trim());
        user.setRoleType(roleType);
        String login = resultSet.getString(6);
        user.setLogin(login);
        String password = resultSet.getString(7);
        user.setPassword(password);
        StatusType statusType = StatusType.getStatusTypeByValue(resultSet.getString(9).trim());
        user.setStatusType(statusType);
        return user;
    }

    public static void main(String[] args) {
        UserDaoImpl userDao = new UserDaoImpl();
//        List<User> list = new ArrayList<>();
//        list = userDao.findAll();
//        for (User user : list) {
//            System.out.println(user.toString());
//        }
        User user1 = new User();
        user1 = userDao.findById(2);
        System.out.println(user1.toString());
    }
}
