package by.nareiko.fr.dao.impl;

import by.nareiko.fr.exception.DaoException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.SQLException;

public class EntityTransaction {
    private static final Logger LOGGER = LogManager.getLogger();

    public void beginTransaction(Connection connection) {
        try {
            connection.setAutoCommit(true);
        } catch (SQLException e) {
            LOGGER.error("Auto commit isn't seted as true: ", e);
        }
    }

    public void endTransaction(Connection connection) {
        try {
            connection.setAutoCommit(false);
        } catch (SQLException e) {
            LOGGER.error("Auto commit isn't seted as false: ", e);
        }
    }

    public void rollback(Connection connection) {
        try {
            connection.rollback();
        } catch (SQLException e) {
            LOGGER.error("Transaction isn't rollbacked: ", e);
        }
    }

    public void commit(Connection connection) {
        try {
            connection.commit();
        } catch (SQLException e) {
            LOGGER.error("Transaction isn't commited: ", e);
        }
    }
}
