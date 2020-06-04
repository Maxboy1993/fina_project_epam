package model.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.*;

public class FilmsRaitingBlob {
    private static final Logger LOGGER = LogManager.getLogger();
    private Connection connection;
    PreparedStatement preparedSt;
    ResultSet resultSet;

    public void executeBLOB(String url, String userName, String password){
        try {
            connection = new FilmsRaitingConnection().createConnection(url, userName, password);
//            statement = connection.createStatement();
//            statement.executeUpdate("CREATE TABLE FilmPoster (posterId INT AUTO_INCREMENT NOT NULL, filmId INT, " +
//                    "poster BLOB, PRIMARY KEY (posterId)), FOREIGN  KEY (filmId) REFERENCES Film (filmId)");
            BufferedImage imageReader = ImageIO.read(new File("Terminator.jpg"));
            Blob poster = connection.createBlob();
            try(OutputStream outputStream = poster.setBinaryStream(1)){
                ImageIO.write(imageReader, "jpg", outputStream);
                preparedSt = connection.prepareStatement("INSERT  INTO FilmPoster (filmId, poster) VALUES (?, ?)");
                preparedSt.setInt(1, 1);
                preparedSt.setBlob(2, poster);
                preparedSt.execute();
//                resultSet = preparedSt.executeQuery("SELECT * FROM FilmPoster");
//                while (resultSet.next()){
//                    Blob resultPoster = resultSet.getBlob(3);
//                    BufferedImage image = ImageIO.read(resultPoster.getBinaryStream());
//                    File outputFile = new File("savedPoster.jpg");
//                    ImageIO.write(image, "jpg", outputFile);
//                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            LOGGER.error("SQLException: " + e.getMessage() + ", SQLException state: " + e.getSQLState() + ", SQLException code: " + e.getErrorCode());
        }catch ( IOException e){
            e.printStackTrace();
            LOGGER.error("IOException: " + e.getMessage());
        } finally {
            if (resultSet != null){
                try {
                    resultSet.close();
                } catch (SQLException e) {
                    LOGGER.error("SQLException: " + e.getMessage() + ", SQLException state: " + e.getSQLState() + ", SQLException code: " + e.getErrorCode() );
                }
            }
            if (preparedSt != null){
                try {
                    preparedSt.close();
                } catch (SQLException e) {
                    LOGGER.error("SQLException: " + e.getMessage() + ", SQLException state: " + e.getSQLState() + ", SQLException code: " + e.getErrorCode() );
                }
            }
        }
    }

    private static final String URL = "jdbc:mysql://localhost:3306/films_raiting?useTimezone=true&serverTimezone=UTC";
    private static final String USER_NAME = "Maxboy1993";
    private static final String PASSWORD = "Dr111293";


    public static void main(String[] args) {
        FilmsRaitingBlob blob = new FilmsRaitingBlob();
        blob.executeBLOB(URL, USER_NAME, PASSWORD);
    }
}
