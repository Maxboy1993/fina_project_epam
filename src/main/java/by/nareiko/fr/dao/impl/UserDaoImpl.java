package by.nareiko.fr.dao.impl;

import by.nareiko.fr.dao.SqlQuery;
import by.nareiko.fr.dao.UserDao;
import by.nareiko.fr.dao.impl.entittymapper.UserMapper;
import by.nareiko.fr.entity.RoleType;
import by.nareiko.fr.entity.User;
import by.nareiko.fr.exception.DaoException;
import by.nareiko.fr.pool.ConnectionPool;
import by.nareiko.fr.util.PasswordHasher;

import java.sql.*;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Optional;

public class UserDaoImpl implements UserDao<User> {
    private static final String ADMIN_ROLE = "admin";
    private static final String USER_ROLE = "user";
    private static final String ACTIVE_STATUS = "active";
    private static final int VERIFIED_MARKER = 1;
    private static final String SPLIT_REGEX = "-";
    private static final int YEAR_INDEX = 0;
    private static final int MONTH_INDEX = 1;
    private static final int DAY_INDEX = 2;
    private static final UserDao<User> INSTANCE = new UserDaoImpl();

    private UserDaoImpl() {
    }

    public static UserDao<User> getInstance() {
        return INSTANCE;
    }

    @Override
    public Optional<User> findByLogin(String login) throws DaoException {
        User user = null;
        UserMapper userMapper = new UserMapper();
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(SqlQuery.FIND_USER_BY_LOGIN)) {
            statement.setString(1, login);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                user = userMapper.initEntity(resultSet);
            }
        } catch (SQLException e) {
            throw new DaoException("User isn't found by login: ", e);
        }
        return Optional.ofNullable(user);
    }

    @Override
    public Optional<User> findByLoginAndPassword(String login, String password) throws DaoException {
        User user = null;
        UserMapper userMapper = new UserMapper();
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(SqlQuery.FIND_USER_BY_LOGIN_AND_PASSWORD)) {
            statement.setString(1, login);
            String md5HexPassword = PasswordHasher.hashPassword(password);
            statement.setString(2, md5HexPassword);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                user = userMapper.initEntity(resultSet);
            }
        } catch (SQLException e) {
            throw new DaoException("User isn't found by login and password: ", e);
        }
        return Optional.ofNullable(user);
    }

    @Override
    public boolean delete(String login) throws DaoException {
        boolean isDeleted;
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(SqlQuery.DELETE_USER_BY_LOGIN)) {
            statement.setString(1, login);
            statement.executeUpdate();
            isDeleted = true;
        } catch (SQLException e) {
            throw new DaoException("User isn't deleted by login: ", e);
        }
        return isDeleted;
    }

    @Override
    public List<User> findAll() throws DaoException {
        List<User> users;
        UserMapper userMapper = new UserMapper();
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(SqlQuery.FIND_ALL_USERS);
            users = userMapper.initEntities(resultSet);
        } catch (SQLException e) {
            throw new DaoException("Users aren't found:  ", e);
        }
        return users;
    }

    @Override
    public Optional<User> findById(int id) throws DaoException {
        User user = null;
        UserMapper userMapper = new UserMapper();
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(SqlQuery.FIND_USER_BY_ID)) {
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                user = userMapper.initEntity(resultSet);
            }
        } catch (SQLException e) {
            throw new DaoException("User isn't found by id: ", e);
        }
        return Optional.ofNullable(user);
    }

    @Override
    public boolean delete(User user) throws DaoException {
        int id = user.getId();
        boolean isDeleted = delete(id);
        return isDeleted;
    }

    @Override
    public boolean delete(int id) throws DaoException {
        boolean isDeleted;
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(SqlQuery.DELETE_USER_BY_ID)) {
            statement.setInt(1, id);
            statement.executeUpdate();
            isDeleted = true;
        } catch (SQLException e) {
            throw new DaoException("User isn't deleted by id: ", e);
        }
        return isDeleted;
    }


    @Override
    public Optional<User> create(User user) throws DaoException {
        Optional<User> optionalUser;
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(SqlQuery.CREATE_USER, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, user.getFirstName());
            statement.setString(2, user.getLastName());
            String birthday = user.getBirthday();
            long date = modifyDate(birthday);
            statement.setLong(3, date);
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
            optionalUser = findById(user.getId());
        } catch (SQLException e) {
            throw new DaoException("User isn't created: ", e);
        }
        return optionalUser;
    }

    @Override
    public Optional<User> update(User user) throws DaoException {
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(SqlQuery.UPDATE_USER)) {
            statement.setString(1, user.getFirstName());
            statement.setString(2, user.getLastName());
            String birthday = user.getBirthday();
            long date = modifyDate(birthday);
            statement.setLong(3, date);
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
        return Optional.ofNullable(user);
    }

    @Override
    public void verifyUser(String login) throws DaoException {
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(SqlQuery.VERIFY_USER)) {
            statement.setInt(1, VERIFIED_MARKER);
            statement.setString(2, login);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException("User isn't verified: ", e);
        }
    }

    private long modifyDate(String birthday) {
        String[] date = birthday.split(SPLIT_REGEX);
        int year = Integer.parseInt(date[YEAR_INDEX]);
        int month = Integer.parseInt(date[MONTH_INDEX]) - 1;
        int day = Integer.parseInt(date[DAY_INDEX]);

        Calendar calendarBirthday = new GregorianCalendar();
        calendarBirthday.set(year, month, day);
        long birthdayMillis = calendarBirthday.getTimeInMillis();
        return birthdayMillis;
    }
}

