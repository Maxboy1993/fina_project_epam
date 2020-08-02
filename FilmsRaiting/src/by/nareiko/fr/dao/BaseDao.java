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
import java.util.Optional;

public interface BaseDao<T extends AbstractEntity> {
    static final Logger LOGGER = LogManager.getLogger();

    List<T> findAll() throws DaoException;

    Optional<T> findById(int id) throws DaoException;

    boolean delete(T t) throws DaoException;

    boolean delete(int id) throws DaoException;

    Optional<T> create(T t) throws DaoException;

    Optional<T> update(T t) throws DaoException;

    default void close(Connection connection) throws DaoException {
        try {
            if (connection != null) {
                ConnectionPool pool = ConnectionPool.getInstance();
                pool.releaseConnection(connection);
            }
        } catch (SQLException e) {
            LOGGER.error("SQLException: Error while closing statement", e);
        }
    }


    default void close(Statement statement) {
        try {
            if (statement != null) {
                statement.close();
            }
        } catch (SQLException e) {
            LOGGER.error("SQLException: Error while closing statement", e);
        }
    }


    default void close(ResultSet resultSet) {
        try {
            if (resultSet != null) {
                resultSet.close();
            }
        } catch (SQLException e) {
            LOGGER.error("SQLException: Error while closing result set", e);
        }
    }
}
