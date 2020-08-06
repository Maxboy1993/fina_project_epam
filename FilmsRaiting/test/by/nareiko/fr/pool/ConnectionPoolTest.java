package by.nareiko.fr.pool;

import by.nareiko.fr.exception.DaoException;
import org.junit.Test;

import java.sql.Connection;
import java.sql.SQLException;

import static org.junit.Assert.assertEquals;

public class ConnectionPoolTest {
    private Connection connection;

    @Test
    public void getInstanceTest() {
        testGetInstance();
    }

    @Test
    public void getConnectionTest() throws SQLException, DaoException {
        connection = testGetConnection();
        ConnectionPool.getInstance().releaseConnection(connection);
    }

    @Test
    public void releaseConnectionTest() throws SQLException, DaoException {
        connection = testGetConnection();
        ConnectionPool.getInstance().releaseConnection(connection);
        testGetInstance();
    }

    @Test(expected = DaoException.class)
    public void releaseConnectionWithExceptionTest() throws SQLException, DaoException {
        connection = testGetConnection();
        try {
            ConnectionPool.getInstance().releaseConnection(new WrongTypeConnection());
        } finally {
            ConnectionPool.getInstance().releaseConnection(connection);
        }
    }

    @Test
    public void destroyPool() {
        int expectedFreeConnectionsCount = 0;
        int expectedGivenConnectionsCount = 0;

        ConnectionPool connectionPool = ConnectionPool.getInstance();
        connectionPool.destroyPool();

        int actualFreeConnectionsCount = connectionPool.getFreeConnactionsCount();
        assertEquals(expectedFreeConnectionsCount, actualFreeConnectionsCount);

        int actualGivenConnectionsCount = connectionPool.getGivenConnactionsCount();
        assertEquals(expectedGivenConnectionsCount, actualGivenConnectionsCount);

    }

    private void testGetInstance() {
        int expectedFreeConnectionsCount = 32;
        int expectedGivenConnectionsCount = 0;

        ConnectionPool connectionPool = ConnectionPool.getInstance();

        int actualFreeConnectionsCount = connectionPool.getFreeConnactionsCount();
        assertEquals(expectedFreeConnectionsCount, actualFreeConnectionsCount);

        int actualGivenConnectionsCount = connectionPool.getGivenConnactionsCount();
        assertEquals(expectedGivenConnectionsCount, actualGivenConnectionsCount);
    }

    private Connection testGetConnection() {
        int expectedFreeConnectionsCount = 31;
        int expectedGivenConnectionsCount = 1;

        ConnectionPool connectionPool = ConnectionPool.getInstance();
        connection = connectionPool.getConnection();
        int actualFreeConnectionsCount = connectionPool.getFreeConnactionsCount();
        assertEquals(expectedFreeConnectionsCount, actualFreeConnectionsCount);

        int actualGivenConnectionsCount = connectionPool.getGivenConnactionsCount();
        assertEquals(expectedGivenConnectionsCount, actualGivenConnectionsCount);

        return connection;
    }

    static class WrongTypeConnection extends ProxyConnection {
        WrongTypeConnection() {
            super(null);
        }
    }
}

