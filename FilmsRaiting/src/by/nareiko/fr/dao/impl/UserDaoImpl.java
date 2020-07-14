package by.nareiko.fr.dao.impl;

import by.nareiko.fr.dao.ColumnName;
import by.nareiko.fr.dao.EntityInitializer;
import by.nareiko.fr.dao.SqlQuery;
import by.nareiko.fr.dao.UserDao;
import by.nareiko.fr.entity.RoleType;
import by.nareiko.fr.entity.StatusType;
import by.nareiko.fr.entity.User;
import by.nareiko.fr.exception.DaoException;
import by.nareiko.fr.pool.ConnectionPool;
import by.nareiko.fr.util.PasswordHasher;
import org.apache.commons.codec.digest.DigestUtils;

import java.sql.*;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

public class UserDaoImpl extends EntityInitializer<User> implements UserDao<User> {
    private static final String ADMIN_ROLE = "admin";
    private static final String USER_ROLE = "user";
    private static final String ACTIVE_STATUS = "active";
    private static final UserDao INSTANCE = new UserDaoImpl();

    private UserDaoImpl() {
    }

    public static UserDao getInstance() {
        return INSTANCE;
    }

    @Override
    public User findByLogin(String login) throws DaoException {
        User user = new User();
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(SqlQuery.FIND_USER_BY_LOGIN)) {
            statement.setString(1, login);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                user = initItem(resultSet);
            }
        } catch (SQLException e) {
            throw new DaoException("User isn't found by login: ", e);
        }
        return user;
    }

    @Override
    public User findByLoginAndPassword(String login, String password) throws DaoException {
        User user = new User();
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(SqlQuery.FIND_USER_BY_LOGIN_AND_PASSWORD)) {
            statement.setString(1, login);
            String md5HexPassword = PasswordHasher.hashPassword(password);
            statement.setString(2, md5HexPassword);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                user = initItem(resultSet);
            }
        } catch (SQLException e) {
            throw new DaoException("User isn't found by login and password: ", e);
        }
        return user;
    }

    @Override
    public User delete(String login) throws DaoException {
        User user = new User();
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(SqlQuery.DELETE_USER_BY_LOGIN)) {
            statement.setString(1, login);
            user = findByLogin(login);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException("User isn't deleted by login: ", e);
        }
        return user;
    }

    @Override
    public List<User> findAll() throws DaoException {
        List<User> users = new ArrayList<>();
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(SqlQuery.FIND_ALL_USERS);
            users = initItems(resultSet);
        } catch (SQLException e) {
            throw new DaoException("Users aren't found:  ", e);
        }
        return users;
    }

    @Override
    public User findById(int id) throws DaoException {
        User user = new User();
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(SqlQuery.FIND_USER_BY_ID)) {
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                user = initItem(resultSet);
            }
        } catch (SQLException e) {
            throw new DaoException("User isn't found by id: ", e);
        }
        return user;
    }

    @Override
    public User delete(User user) throws DaoException {
        delete(user.getLogin());
        return user;
    }

    @Override
    public User delete(int id) throws DaoException {
        User user = new User();
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(SqlQuery.DELETE_USER_BY_ID)) {
            user = findById(id);
            statement.setInt(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException("User isn't deleted by id: ", e);
        }
        return user;
    }


    // add id autoincremented
    @Override
    public boolean create(User user) throws DaoException {
        boolean isCreated = false;
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(SqlQuery.CREATE_USER)) {
            statement.setString(1, user.getFirstName());
            statement.setString(2, user.getLastName());
            long birtgday = user.getBirthday().getTimeInMillis();
            statement.setLong(3, birtgday);
            String role = USER_ROLE;
            statement.setString(4, user.getLogin());
            statement.setString(5, user.getPassword());
            statement.setString(6, ACTIVE_STATUS);
            if (user.getRoleType().equals(RoleType.getRoleTypeByValue(ADMIN_ROLE))) {
                role = ADMIN_ROLE;
            }
            statement.setString(7, role);
            statement.executeUpdate();
            ResultSet resultSet = statement.getGeneratedKeys();
            if (resultSet.next()) {
                int id = resultSet.getInt(1);
                user.setId(id);
            }
            isCreated = true;
        } catch (SQLException e) {
            throw new DaoException("User isn't created: ", e);
        }
        return isCreated;
    }

    @Override
    public User update(User user) throws DaoException {
        User user1 = new User();
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(SqlQuery.UPDATE_USER)) {
            statement.setString(1, user.getFirstName());
            statement.setString(2, user.getLastName());
            long birtgday = user.getBirthday().getTimeInMillis();
            statement.setLong(3, birtgday);
            statement.setString(4, user.getLogin());
            statement.setString(5, user.getPassword());
            String role = USER_ROLE;
            if (user.getRoleType().equals(RoleType.getRoleTypeByValue(ADMIN_ROLE))) {
                role = ADMIN_ROLE;
            }
            statement.setString(6, role);
            statement.setInt(7, user.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException("User isn't updated: ", e);
        }
        return user;
    }

    private Calendar getDateFromLong(long dateMillis) {
        Calendar date = new GregorianCalendar();
        date.setTimeInMillis(dateMillis);
        return date;
    }

    protected List<User> initItems(ResultSet resultSet) throws DaoException {
        List<User> users = new ArrayList<>();
        try {
            while (resultSet.next()) {
                User user = initItem(resultSet);
                users.add(user);
            }
        } catch (SQLException e) {
            throw new DaoException("Users aren't inizialized: ", e);
        }
        return users;
    }

    protected User initItem(ResultSet resultSet) throws DaoException {
        User user = new User();
        try {
            String firstName = resultSet.getString(ColumnName.FIRST_NAME);
            user.setFirstName(firstName);
            String lastName = resultSet.getString(ColumnName.LAST_NAME);
            user.setLastName(lastName);
            long longBirthday = resultSet.getLong(ColumnName.BIRTHDAY);
            Calendar birthday = getDateFromLong(longBirthday);
            user.setBirthday(birthday);
            RoleType roleType = RoleType.getRoleTypeByValue(resultSet.getString(ColumnName.ROLE).trim());
            user.setRoleType(roleType);
            String login = resultSet.getString(ColumnName.LOGIN);
            user.setLogin(login);
            String password = resultSet.getString(ColumnName.PASSWORD);
            user.setPassword(password);
            StatusType statusType = StatusType.getStatusTypeByValue(resultSet.getString(ColumnName.STATUS).trim());
            user.setStatusType(statusType);
        } catch (SQLException e) {
            throw new DaoException("User isn't inizialized: ", e);
        }
        return user;
    }

    //TODO delete main method
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

