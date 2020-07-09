package by.nareiko.fr.model.dao.impl;

import by.nareiko.fr.entity.*;
import by.nareiko.fr.exception.EntityException;
import by.nareiko.fr.model.dao.UserDao;
import by.nareiko.fr.exception.DaoException;
import by.nareiko.fr.model.dao.request.UserRequest;
import by.nareiko.fr.util.PasswordHasher;
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

    private static final Logger LOGGER = LogManager.getLogger();
    boolean isCreated;

    public UserDaoImpl() {
        users = new ArrayList<>();
    }

    @Override
    public User findByLogin(String login) {
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(UserRequest.FIND_USER_BY_LOGIN)) {
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
             PreparedStatement statement = connection.prepareStatement(UserRequest.FIND_USER_BY_LOGIN_AND_PASSWORD)) {
            statement.setString(1, login);
            String md5HexPassword = PasswordHasher.hashPassword(password);
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
             PreparedStatement statement = connection.prepareStatement(UserRequest.DELETE_USER_BY_LOGIN)) {
            statement.setString(1, login);
            user = findByLogin(login);
            statement.executeUpdate();
        } catch (SQLException e) {
            LOGGER.error("SQLException: ", e);
        }
        return user;
    }

    @Override
    public List<User> findAll() {
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(UserRequest.FIND_ALL_USERS);
            users = initUsers(resultSet);
        } catch (SQLException | EntityException e) {
            LOGGER.error(e);
        }
        return users;
    }

    @Override
    public User findById(int id) {
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(UserRequest.FIND_USER_BY_ID)) {
            statement.setInt(1, id);
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
    public User delete(User user) throws DaoException {
       delete(user.getLogin());
        return user;
    }

    @Override
    public User delete(int id) {
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(UserRequest.DELETE_USER_BY_ID)) {
            user = findById(id);
            statement.setInt(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            LOGGER.error("SQLException: ", e);
        }
        return user;
    }


// add id autoincremented
    @Override
    public boolean create(User user) {
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(UserRequest.CREATE_USER)) {
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
            ResultSet resultSet = statement.getGeneratedKeys();
            if (resultSet.next()){
                int id = resultSet.getInt(1);
                user.setId(id);
            }
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
             PreparedStatement statement = connection.prepareStatement(UserRequest.UPDATE_USER)) {
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
            statement.setInt(7, user.getId());
            //delete if unusful
//            int statusId = ACTIVE_STATUS_ID;
//            if (user.getStatusType().equals(RoleType.getRoleTypeByValue(INACTIVE_STATUS))){
//                statusId = INACTIVE_STATUS_ID;
//            }
//            statement.setInt(7, statusId);
        } catch (SQLException | EntityException e) {
            LOGGER.error("SQLException: ", e);
        }
        return user;
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

