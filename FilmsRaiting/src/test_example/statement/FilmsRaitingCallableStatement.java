package test_example.statement;

import test_example.FilmsRaitingConnection;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class FilmsRaitingCallableStatement {
    private static final Logger LOGGER = LogManager.getLogger();
    private static final String SQL_REQUEST = "{call userBirthday (?, ?)}";
    public void excecuteCallableStatement() {
        try (Connection connection = new FilmsRaitingConnection().createConnection();
            CallableStatement callSt = connection.prepareCall(SQL_REQUEST)) {
            callSt.setString(1, "Jhon");
            callSt.registerOutParameter(2, Types.VARCHAR);
            callSt.execute();
            Calendar birthday = new GregorianCalendar();
            birthday.setTimeInMillis(Long.parseLong(callSt.getString(2)));
//            DateFormat dateFormat = new SimpleDateFormat("d MMMM yyyy");
//            System.out.println(dateFormat.format(birthday.getTime()));
        } catch (SQLException e) {
            LOGGER.error("SQLException: " + e.getMessage() + ", SQLException state: " + e.getSQLState() + ", SQLException code: " + e.getErrorCode() );
        }
    }

    public static void main(String[] args) {
        FilmsRaitingCallableStatement callableStatement = new FilmsRaitingCallableStatement();
        callableStatement.excecuteCallableStatement();
    }
}
