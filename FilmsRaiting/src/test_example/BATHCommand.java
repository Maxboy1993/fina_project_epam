package test_example;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class BATHCommand {
    private static final Logger LOGGER = LogManager.getLogger();
    Connection connection;
    Statement statement;

    public void execute() {
        try {
            connection = new FilmsRaitingConnection().createConnection();
            statement = connection.createStatement();
            connection.setAutoCommit(false);
            statement.addBatch("INSERT  INTO FilmRaiting (filmId, userId, raiting) VALUES (3, 3, 7)");
            statement.addBatch("INSERT  INTO UserRaiting (userId, userRaiting) VALUES (2, 7)");
            statement.executeBatch();
            connection.commit();
        } catch (SQLException e) {
            try {
            if (connection != null){
                connection.rollback();
            }
                } catch (SQLException ex) {
                    LOGGER.error("SQLException: " + e.getMessage() + ", SQLException state: " + e.getSQLState() + ", SQLException code: " + e.getErrorCode());
                }
        } finally {
                try {
                    if (connection != null) {
                    connection.setAutoCommit(true);
                    }
                } catch (SQLException e) {
                    LOGGER.error("SQLException: " + e.getMessage() + ", SQLException state: " + e.getSQLState() + ", SQLException code: " + e.getErrorCode());
                }
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                LOGGER.error("SQLException: " + e.getMessage() + ", SQLException state: " + e.getSQLState() + ", SQLException code: " + e.getErrorCode());
            }
        }
    }

    public static void main(String[] args) {
        BATHCommand command = new BATHCommand();
        command.execute();
    }
}
