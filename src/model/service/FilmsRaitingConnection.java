package model.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;

public class FilmsRaitingConnection {
private Connection connection;
private static final Logger LOGGER = LogManager.getLogger();

public Connection createConnection( String url, String userName, String password) {
    try {
        Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(url, userName, password);
            LOGGER.info("Connection successful");
    } catch (ClassNotFoundException e) {
        LOGGER.error("JDBC driver doesn't connect: " + e);
    }catch (SQLException e) {
        LOGGER.error("Connection mistake: " + e.getMessage());
    }
        return connection;
}

    private static final String URL = "jdbc:mysql://localhost:3306/films_raiting?useTimezone=true&serverTimezone=UTC";
    private static final String USER_NAME = "Maxboy1993";
    private static final String PASSWORD = "Dr111293";

    public static void main(String[] args) {
       FilmsRaitingConnection connection = new FilmsRaitingConnection();
               connection.createConnection(URL, USER_NAME, PASSWORD);
    }
}
