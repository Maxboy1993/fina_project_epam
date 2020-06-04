package model.service.statement;

import model.service.FilmsRaitingConnection;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;

public class FilmsRaitingPrepareStatement {
    private static final Logger LOGGER = LogManager.getLogger();
    private  Connection connection;
    private PreparedStatement prepareSt;
    private ResultSet resultSet;

    public void excecuteResultSet( String url, String userName, String password) {
        try {
                connection = new FilmsRaitingConnection().createConnection(url, userName, password);
                prepareSt = connection.prepareStatement("INSERT  INTO FilmRaiting (filmId, userId, raiting) VALUES (?, ?, ?)");
                prepareSt.setInt(1, 1);
                prepareSt.setInt(2, 3);
                prepareSt.setInt(3, 9);
                prepareSt.execute();
                resultSet = prepareSt.executeQuery("SELECT * FROM FilmRaiting");
                while (resultSet.next()){
                    int filmId = resultSet.getInt(2);
                    int userId = resultSet.getInt(3);
                    int raiting = resultSet.getInt(4);
                }
                }catch (SQLException e){
                    LOGGER.error("SQLException: " + e.getMessage() + ", SQLException state: " + e.getSQLState() + ", SQLException code: " + e.getErrorCode() );
        } finally {
            if (resultSet != null){
                try {
                    resultSet.close();
                } catch (SQLException e) {
                    LOGGER.error("SQLException: " + e.getMessage() + ", SQLException state: " + e.getSQLState() + ", SQLException code: " + e.getErrorCode() );
                }
            }
            if (prepareSt != null){
                try {
                    prepareSt.close();
                } catch (SQLException e) {
                    LOGGER.error("SQLException: " + e.getMessage() + ", SQLException state: " + e.getSQLState() + ", SQLException code: " + e.getErrorCode() );
                }
            }
        }
    }

//    private static final String URL = "jdbc:mysql://localhost:3306/films_raiting?useTimezone=true&serverTimezone=UTC";
//    private static final String USER_NAME = "Maxboy1993";
//    private static final String PASSWORD = "Dr111293";
//
//    public static void main(String[] args) {
//        FilmsRaitingPrepareStatement filmsRaitingTest = new FilmsRaitingPrepareStatement();
//        filmsRaitingTest.excecuteResultSet(URL, USER_NAME, PASSWORD);
//    }
}
