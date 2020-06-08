package test_example.statement;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import test_example.FilmsRaitingConnection;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Savepoint;
import java.sql.Statement;

public class Transaction {
    private static final Logger LOGGER = LogManager.getLogger();
    Connection connection;
    Statement statement;
    Savepoint savepoint;

    public void execute() {
        try {
            connection = new FilmsRaitingConnection().createConnection();
            statement = connection.createStatement();
            connection.setAutoCommit(false);
            statement.addBatch("INSERT  INTO FilmRaiting (filmId, userId, raiting) VALUES (3, 3, 7)");
            savepoint = connection.setSavepoint("savepoint 1");
            statement.addBatch("INSERT  INTO UserRaiting (userId, userRaiting) VALUES (2, 7)");
            statement.executeBatch();
            connection.commit();
        } catch (SQLException e) {
            try {
                if (connection != null) {
                    connection.rollback(savepoint);
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException ex) {
                LOGGER.error("SQLException: " + e.getMessage() + ", SQLException state: "
                        + e.getSQLState() + ", SQLException code: " + e.getErrorCode());
            }
        }
    }
}
