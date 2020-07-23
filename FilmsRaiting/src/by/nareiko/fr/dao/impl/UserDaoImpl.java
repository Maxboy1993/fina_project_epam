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
import java.util.List;
import java.util.Optional;

public class UserDaoImpl implements UserDao<User> {
    private static final String ADMIN_ROLE = "admin";
    private static final String USER_ROLE = "user";
    private static final String ACTIVE_STATUS = "active";
    private static final int VERIFIED_MARKER = 1;
    private static final UserDao INSTANCE = new UserDaoImpl();

    private UserDaoImpl() {
    }

    public static UserDao getInstance() {
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
    public Optional<User> delete(String login) throws DaoException {
        Optional<User> user;
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
    public Optional<User> delete(User user) throws DaoException {
        int id = user.getId();
        Optional<User> foundUser = findById(id);
        delete(id);
        return foundUser;
    }

    @Override
    public Optional<User> delete(int id) throws DaoException {
        Optional<User> user;
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


    @Override
    public boolean create(User user) throws DaoException {
        boolean isCreated = false;
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(SqlQuery.CREATE_USER, Statement.RETURN_GENERATED_KEYS)) {
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
    public Optional<User> update(User user) throws DaoException {
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
}

