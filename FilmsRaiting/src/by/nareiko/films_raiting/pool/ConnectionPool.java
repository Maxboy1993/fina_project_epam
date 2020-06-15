package by.nareiko.films_raiting.pool;

import by.nareiko.films_raiting.exception.DaoException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayDeque;
import java.util.Enumeration;
import java.util.Properties;
import java.util.Queue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ConnectionPool {
    private static ConnectionPool pool;
    private static final String DRIVER_CLASS = "MYSQLJDBC.driver";
    private static final String URL = "MYSQLJDBC.url";
    private static final String USER_NAME = "MYSQLJDBC.username";
    private static final String PASSWORD = "MYSQLJDBC.password";
    private static final int DEFAULT_POOL_SIZE = 32;
    private BlockingQueue<ProxyConnection> freeConnections;
    private Queue<ProxyConnection> givenAwayConnections;
    private static Lock lock = new ReentrantLock(true);
//    private static final Logger LOGGER = LogManager.getLogger();

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

    private ConnectionPool() {
        freeConnections = new LinkedBlockingDeque<>();
        givenAwayConnections = new ArrayDeque<>();
        initConnection();
    }

    private void initConnection() {
        try {
            Properties properties = new Properties();
            InputStream inputStream = Files.newInputStream(Paths.get("resourses/data/database.properties"));
            properties.load(inputStream);
            String driverClass = properties.getProperty(DRIVER_CLASS);
            String url = properties.getProperty(URL);
            String userName = properties.getProperty(USER_NAME);
            String password = properties.getProperty(PASSWORD);
            Class.forName(driverClass);
            Connection connection = DriverManager.getConnection(url, userName, password);
            for (int i = 0; i < DEFAULT_POOL_SIZE; i++) {
                //replace to ProxyConnection
                freeConnections.add(new ProxyConnection(connection));
            }
        } catch (ClassNotFoundException | SQLException | IOException e) {
//            LOGGER.error("Connection pool wasn't initialized: ", e);
        }
    }

    public Connection getConnection() {
        ProxyConnection connection = null;
        if (!freeConnections.isEmpty()) {
            try {
                connection = freeConnections.take();
            } catch (InterruptedException e) {
//                LOGGER.error("Connection isn't available: ", e);
                Thread.currentThread().interrupt();
            }
            givenAwayConnections.add(connection);
        }
        return connection;
    }

    public void releaseConnection(Connection connection) throws DaoException {
        if (connection.getClass() != ProxyConnection.class){
            throw  new DaoException("Invalid connection");
        }
        givenAwayConnections.remove(connection);
        freeConnections.add((ProxyConnection) connection);
    }

    public void destroyPool() throws DaoException {
        for (int i = 0; i < DEFAULT_POOL_SIZE; i++) {
            try {
                freeConnections.take().finishConnection();
            } catch (InterruptedException e) {
//                LOGGER.error("InterruptedException: ", e.getMessage());
            }
        }
        deregosterDrivers();
    }

    private void deregosterDrivers() {
        Enumeration<Driver> drivers = DriverManager.getDrivers();
        while (drivers.hasMoreElements()) {
            Driver driver = (Driver) drivers.nextElement();
            try {
                DriverManager.deregisterDriver(driver);
            } catch (SQLException e) {
//                LOGGER.error("Deregister driver error", e);
            }
        }
    }
}
