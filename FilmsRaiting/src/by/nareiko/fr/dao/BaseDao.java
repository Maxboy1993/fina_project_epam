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

/**
 * The interface Base dao.
 *
 * @param <T> the type parameter
 */
public interface BaseDao<T extends AbstractEntity> {
    /**
     * The constant LOGGER.
     */
    static final Logger LOGGER = LogManager.getLogger();

    /**
     * Find all list.
     *
     * @return the list
     * @throws DaoException the dao exception
     */
    List<T> findAll() throws DaoException;

    /**
     * Find by id optional.
     *
     * @param id the id
     * @return the optional
     * @throws DaoException the dao exception
     */
    Optional<T> findById(int id) throws DaoException;

    /**
     * Delete boolean.
     *
     * @param t the t
     * @return the boolean
     * @throws DaoException the dao exception
     */
    boolean delete(T t) throws DaoException;

    /**
     * Delete boolean.
     *
     * @param id the id
     * @return the boolean
     * @throws DaoException the dao exception
     */
    boolean delete(int id) throws DaoException;

    /**
     * Create optional.
     *
     * @param t the t
     * @return the optional
     * @throws DaoException the dao exception
     */
    Optional<T> create(T t) throws DaoException;

    /**
     * Update optional.
     *
     * @param t the t
     * @return the optional
     * @throws DaoException the dao exception
     */
    Optional<T> update(T t) throws DaoException;

    /**
     * Close.
     *
     * @param connection the connection
     * @throws DaoException the dao exception
     */
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


    /**
     * Close.
     *
     * @param statement the statement
     */
    default void close(Statement statement) {
        try {
            if (statement != null) {
                statement.close();
            }
        } catch (SQLException e) {
            LOGGER.error("SQLException: Error while closing statement", e);
        }
    }


    /**
     * Close.
     *
     * @param resultSet the result set
     */
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
