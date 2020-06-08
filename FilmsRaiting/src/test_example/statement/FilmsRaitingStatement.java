package test_example.statement;

import entity.Actor;
import test_example.FilmsRaitingConnection;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

public class FilmsRaitingStatement {
    private List<Actor> actors;
    private ResultSet resultSet;
    private static final Logger LOGGER = LogManager.getLogger();
    private static final String ALL_ATORS_REQUEST = "SELECT actorId, name, birthday From actor";

    public List<Actor> executeResultSet() {
        try(Connection connection = new FilmsRaitingConnection().createConnection();
            Statement statement = connection.createStatement()) {
            resultSet = statement.executeQuery(ALL_ATORS_REQUEST);
            actors = new ArrayList<>();
            while(resultSet.next()){
                int actorId = resultSet.getInt(1);
                String name = resultSet.getString("name");
                Calendar birthday = new GregorianCalendar();
                birthday.setTimeInMillis(resultSet.getLong("birthday"));
                actors.add(new Actor(actorId, name, birthday));
            }
            ResultSetMetaData resultSetMetaData = resultSet.getMetaData();
            System.out.println(resultSetMetaData.getColumnCount());
            System.out.println(resultSetMetaData.getColumnName(1));

            } catch (SQLException e) {
            LOGGER.error("SQLException: " + e.getMessage() + ", SQLException state: " + e.getSQLState() + ", SQLException code: " + e.getErrorCode() );
            }
        return actors;
    }

    public static void main(String[] args) {
        FilmsRaitingStatement statement = new FilmsRaitingStatement();
        List<Actor> actorsList = new ArrayList<>();
        actorsList = statement.executeResultSet();
        DateFormat df = new SimpleDateFormat("d MMMM yyyy");
        for (Actor actor : actorsList) {
            System.out.println(actor.getActorId() + " " + actor.getName() + " " +
                   df.format(actor.getBirthday().getTime()));
        }
    }

    }

