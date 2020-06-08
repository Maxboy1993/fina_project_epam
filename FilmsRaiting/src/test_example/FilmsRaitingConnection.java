package test_example;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.*;
import java.util.Properties;

public class FilmsRaitingConnection {
private Connection connection;
private Properties properties;
private InputStream inputStream;
private static final Logger LOGGER = LogManager.getLogger();

public Connection createConnection() {
    try {
        properties = new Properties();
        inputStream = Files.newInputStream(Paths.get("resourses/data/database.properties"));
        properties.load(inputStream);

        String driverClass = properties.getProperty("MYSQLJDBC.driver");
        String url = properties.getProperty("MYSQLJDBC.url");
        String userName = properties.getProperty("MYSQLJDBC.username");
        String password = properties.getProperty("MYSQLJDBC.password");

        Class.forName(driverClass);
            connection = DriverManager.getConnection(url, userName, password);
            LOGGER.info("Connection successful");
    } catch (ClassNotFoundException e) {
        LOGGER.error("JDBC driver doesn't connect: ", e);
    } catch (SQLException e) {
        LOGGER.error("Connection exception: " + e.getMessage());
    } catch (FileNotFoundException e) {
        LOGGER.error("FileNotFoundException exception: ", e.getMessage());
    } catch (IOException e) {
        LOGGER.error("IO exception: ", e.getMessage());
    }
    return connection;
}

    public static void main(String[] args) {
        FilmsRaitingConnection connection = new FilmsRaitingConnection();
        connection.createConnection();
    }
}
