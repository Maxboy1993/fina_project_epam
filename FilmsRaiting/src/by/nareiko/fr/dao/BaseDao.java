package by.nareiko.fr.dao;

import by.nareiko.fr.entity.AbstractEntity;
import by.nareiko.fr.exception.DaoException;
import by.nareiko.fr.pool.ConnectionPool;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

public interface BaseDao<T extends AbstractEntity> {
    static final Logger LOGGER = LogManager.getLogger();

    List<T> findAll() throws DaoException;

    T findById(int id) throws DaoException;

    T delete(T t) throws DaoException;

    T delete(int id) throws DaoException;

    boolean create(T t) throws DaoException;

    T update(T t) throws DaoException;

    default void close(Statement statement) {
        try {
            if (statement != null) {
                statement.close();
            }
        } catch (SQLException e) {
            LOGGER.error("SQLException: ", e);
        }
    }

    default void close(Connection connection) throws DaoException {
        if (connection != null) {
            ConnectionPool pool = ConnectionPool.getInstance();
            pool.releaseConnection(connection);
        }
    }
}
