package model.service;

import model.service.FilmsRaitingConnection;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;

public class FilmsRaitingCallableStatement {
    private static final Logger LOGGER = LogManager.getLogger();
    private Connection connection;
    private CallableStatement callSt;

    public void excecuteCallableStatement( String url, String userName, String password) {
        try {
            connection = new FilmsRaitingConnection().createConnection(url, userName, password);
            callSt = connection.prepareCall("{call userCount (?)}");
            callSt.registerOutParameter(1, Types.INTEGER);
            callSt.execute();
        } catch (SQLException e) {
            LOGGER.error("SQLException: " + e.getMessage() + ", SQLException state: " + e.getSQLState() + ", SQLException code: " + e.getErrorCode() );
        } finally {
            if (callSt != null){
                try {
                    callSt.close();
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
//        FilmsRaitingCallableStatement callableStatement = new FilmsRaitingCallableStatement();
//        callableStatement.excecuteCallableStatement(URL, USER_NAME, PASSWORD);
//    }
}
