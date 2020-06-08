package test_example.statement;

import test_example.FilmsRaitingConnection;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;

public class FilmsRaitingPrepareStatement {
    private static final Logger LOGGER = LogManager.getLogger();
    private static final String SQL_REQUEST = "INSERT  INTO FilmRaiting (filmId, userId, raiting) VALUES (?, ?, ?)";
    ResultSet resultSet;

    public void excecuteResultSet() {
        try (Connection connection = new FilmsRaitingConnection().createConnection();
             PreparedStatement prepareSt = connection.prepareStatement(SQL_REQUEST, Statement.RETURN_GENERATED_KEYS)) {
            prepareSt.setInt(1, 2);
            prepareSt.setInt(2, 3);
            prepareSt.setInt(3, 8);
            prepareSt.executeUpdate();
            resultSet = prepareSt.getGeneratedKeys();
            if (resultSet.next()){
                int key = resultSet.getInt(1);
                System.out.println(key);
            }
        } catch (SQLException e) {
            LOGGER.error("SQLException: " + e.getMessage() + ", SQLException state: " + e.getSQLState() + ", SQLException code: " + e.getErrorCode());
        }
    }

    public static void main(String[] args) {
        FilmsRaitingPrepareStatement filmsRaitingTest = new FilmsRaitingPrepareStatement();
        filmsRaitingTest.excecuteResultSet();
    }
}
