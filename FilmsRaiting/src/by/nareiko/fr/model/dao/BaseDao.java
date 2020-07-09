package by.nareiko.fr.model.dao;

import by.nareiko.fr.entity.AbstractEntity;
import by.nareiko.fr.exception.DaoException;
import by.nareiko.fr.pool.ConnectionPool;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

public interface BaseDao<T extends AbstractEntity> {
    static final Logger LOGGER = LogManager.getLogger();

    List<T> findAll();
    T findById(int id);
    T delete(T t) throws DaoException;
    T delete(int id);
    boolean create(T t);
    T update(T t);

    default void close(Statement statement){
        try {
            if (statement != null) {
                statement.close();
            }
        } catch (SQLException e) {
            LOGGER.error("SQLException: " + e.getMessage() + ", SQLException state: " + e.getSQLState() + ", SQLException code: " + e.getErrorCode() );
        }
    }

    default void close(Connection connection) throws DaoException {
        if (connection != null) {
            ConnectionPool pool =  ConnectionPool.getInstance();
            pool.releaseConnection(connection);
        }
    }
}
