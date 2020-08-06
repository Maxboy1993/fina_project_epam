package by.nareiko.fr.dao.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * The type Transaction manager.
 */
public class TransactionManager {
    private static final Logger LOGGER = LogManager.getLogger();

    /**
     * Begin transaction.
     *
     * @param connection the connection
     */
    public void beginTransaction(Connection connection) {
        try {
            connection.setAutoCommit(false);
        } catch (SQLException e) {
            LOGGER.error("Auto commit isn't setted as false: ", e);
        }
    }

    /**
     * End transaction.
     *
     * @param connection the connection
     */
    public void endTransaction(Connection connection) {
        try {
            connection.setAutoCommit(true);
        } catch (SQLException e) {
            LOGGER.error("Auto commit isn't setted as true: ", e);
        }
    }

    /**
     * Rollback.
     *
     * @param connection the connection
     */
    public void rollback(Connection connection) {
        try {
            connection.rollback();
        } catch (SQLException e) {
            LOGGER.error("Transaction isn't rollbacked: ", e);
        }
    }

    /**
     * Commit.
     *
     * @param connection the connection
     */
    public void commit(Connection connection) {
        try {
            connection.commit();
        } catch (SQLException e) {
            LOGGER.error("Transaction isn't committed: ", e);
        }
    }
}
