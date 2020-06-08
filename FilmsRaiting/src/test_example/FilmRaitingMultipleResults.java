package test_example;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;

public class FilmRaitingMultipleResults {
    private static final Logger LOGGER = LogManager.getLogger();
    private Connection connection;
    private CallableStatement calSt;
    private ResultSet resultSet;

public void execute(String url, String userName, String password){
    try {
        connection = new FilmsRaitingConnection().createConnection();
        calSt = connection.prepareCall("{CALL counter}");
        boolean hasResult = calSt.execute();
        while (hasResult){
            resultSet = calSt.getResultSet();
            while (resultSet.next()){
                System.out.println(resultSet.getInt(1));
            }
            hasResult = calSt.getMoreResults();
        }
    }catch (SQLException e){
        LOGGER.error("SQLException: " + e.getMessage() + ", SQLException state: " + e.getSQLState() + ", SQLException code: " + e.getErrorCode() );
    }
}

        private static final String URL = "jdbc:mysql://localhost:3306/films_raiting?useTimezone=true&serverTimezone=UTC";
    private static final String USER_NAME = "Maxboy1993";
    private static final String PASSWORD = "Dr111293";

//    public static void main(String[] args) {
//        FilmRaitingMultipleResults filmsRaitingTest = new FilmRaitingMultipleResults();
//        filmsRaitingTest.execute(URL, USER_NAME, PASSWORD);
//    }
}
