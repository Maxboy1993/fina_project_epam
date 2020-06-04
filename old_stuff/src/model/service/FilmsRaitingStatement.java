package model.service;

import model.dao.exception.ReaderExceprion;
import model.dao.reader.factory.SQLReaderFactory;
import model.dao.reader.impl.SQLReaderImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.List;

public class FilmsRaitingStatement {
    private List<String> data;
    private Connection connection;
    private Statement statement;
    private ResultSet resultSet;
    private static final Logger LOGGER = LogManager.getLogger();

    public void executeeResultSet( String url, String userName, String password, String filePath ) {
        try {
                connection = new FilmsRaitingConnection().createConnection(url, userName, password);
                statement = connection.createStatement();
                SQLReaderFactory factory = SQLReaderFactory.getInstance();
                SQLReaderImpl reader = factory.getSQLReaderImpl();
                data = reader.read(filePath);
                String line = "";
                for (String element : data) {
                    line = element;
                    if (element.endsWith(";")){
                        line = line.substring(0, line.length()-1);
                    }
                    statement.executeUpdate(line);
                }
                    resultSet = statement.executeQuery("SELECT * FROM Film");
                    while (resultSet.next()){
                        int filmId = resultSet.getInt(1);
                        String name = resultSet.getString(2);
                        Date releaseDate = resultSet.getDate(4);
                    }
            } catch (SQLException e) {
            LOGGER.error("SQLException: " + e.getMessage() + ", SQLException state: " + e.getSQLState() + ", SQLException code: " + e.getErrorCode() );
            }catch (ReaderExceprion e){
                LOGGER.error("Exception during reading file: " + e);
            } finally {
            if (resultSet != null){
                try {
                    resultSet.close();
                } catch (SQLException e) {
                    LOGGER.error("SQLException: " + e.getMessage() + ", SQLException state: " + e.getSQLState() + ", SQLException code: " + e.getErrorCode() );
                }
            }
            if (statement != null){
                try {
                    statement.close();
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
//
//    public static void main(String[] args) {
//        FilmsRaitingStatement resultSet = new FilmsRaitingStatement();
//        resultSet.executeeResultSet(URL, USER_NAME, PASSWORD, "resourses/data/filmsRaiting");
//    }

    }

