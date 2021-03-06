package by.nareiko.fr.pool;

import by.nareiko.fr.exception.DaoException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayDeque;
import java.util.Enumeration;
import java.util.Queue;
import java.util.ResourceBundle;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * The type Connection pool.
 */
public class ConnectionPool {
    private static final String PROPERTIES_PATH = "database";
    private static final String DRIVER_CLASS = "MYSQLJDBC.driver";
    private static final String URL = "MYSQLJDBC.url";
    private static final String USER_NAME = "MYSQLJDBC.username";
    private static final String PASSWORD = "MYSQLJDBC.password";
    private static final int DEFAULT_POOL_SIZE = 32;
    private static final Logger LOGGER = LogManager.getLogger();
    private static ConnectionPool pool;
    private static Lock lock = new ReentrantLock(true);
    private BlockingQueue<ProxyConnection> freeConnections;
    private Queue<ProxyConnection> givenAwayConnections;

    private ConnectionPool() {
        freeConnections = new LinkedBlockingDeque<>();
        givenAwayConnections = new ArrayDeque<>();
        initConnection();
    }

    /**
     * Gets instance.
     *
     * @return the instance
     */
    public static ConnectionPool getInstance() {
        if (pool == null) {
            try {
                lock.lock();
                if (pool == null) {
                    pool = new ConnectionPool();
                }
            } finally {
                lock.unlock();
            }
        }
        return pool;
    }

    private void initConnection() {
        try {
            ResourceBundle bundle = ResourceBundle.getBundle(PROPERTIES_PATH);
            String driverClass = bundle.getString(DRIVER_CLASS);
            String url = bundle.getString(URL);
            String userName = bundle.getString(USER_NAME);
            String password = bundle.getString(PASSWORD);

            Class.forName(driverClass);
            for (int i = 0; i < DEFAULT_POOL_SIZE; i++) {
                Connection connection = DriverManager.getConnection(url, userName, password);
                freeConnections.add(new ProxyConnection(connection));
            }
        } catch (ClassNotFoundException | SQLException e) {
            LOGGER.error("Connection pool wasn't initialized: ", e);
        }
    }

    /**
     * Gets connection.
     *
     * @return the connection
     */
    public Connection getConnection() {
        ProxyConnection connection = null;
        if (!freeConnections.isEmpty()) {
            try {
                connection = freeConnections.take();
            } catch (InterruptedException e) {
                LOGGER.error("Connection isn't available: ", e);
                Thread.currentThread().interrupt();
            }
            givenAwayConnections.add(connection);
        }
        return connection;
    }

    /**
     * Release connection.
     *
     * @param connection the connection
     * @throws DaoException the dao exception
     * @throws SQLException the sql exception
     */
    public void releaseConnection(Connection connection) throws DaoException, SQLException {
        if (connection.getClass() != ProxyConnection.class) {
            throw new DaoException("Invalid connection");
        }
        connection.setAutoCommit(true);
        if (givenAwayConnections.contains(connection)) {
            givenAwayConnections.remove(connection);
            freeConnections.add((ProxyConnection) connection);
        } else {
            throw new DaoException("Connection is off. It cannot be released!");
        }
    }

    /**
     * Destroy pool.
     */
    public void destroyPool() {
        for (int i = 0; i < DEFAULT_POOL_SIZE; i++) {
            try {
                freeConnections.take().finishConnection();
            } catch (InterruptedException e) {
                LOGGER.error("InterruptedException: ", e.getMessage());
            }
        }
        deregisterDrivers();
    }

    private void deregisterDrivers() {
        Enumeration<Driver> drivers = DriverManager.getDrivers();
        while (drivers.hasMoreElements()) {
            Driver driver = drivers.nextElement();
            try {
                DriverManager.deregisterDriver(driver);
            } catch (SQLException e) {
                LOGGER.error("Deregister driver error", e);
            }
        }
    }

    protected int getFreeConnactionsCount(){
        return freeConnections.size();
    }

    protected int getGivenConnactionsCount(){
        return givenAwayConnections.size();
    }
}
