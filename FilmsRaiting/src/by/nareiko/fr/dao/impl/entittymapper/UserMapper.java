package by.nareiko.fr.dao.impl.entittymapper;

import by.nareiko.fr.dao.EntityMapper;
import by.nareiko.fr.dao.SqlColumnName;
import by.nareiko.fr.entity.RoleType;
import by.nareiko.fr.entity.StatusType;
import by.nareiko.fr.entity.User;
import by.nareiko.fr.exception.DaoException;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

public class UserMapper extends EntityMapper<User> {
    @Override
    public User initEntity(ResultSet resultSet) throws DaoException {
        User user = new User();
        try {
            int id = resultSet.getInt(SqlColumnName.USER_ID);
            user.setId(id);
            String firstName = resultSet.getString(SqlColumnName.FIRST_NAME);
            user.setFirstName(firstName);
            String lastName = resultSet.getString(SqlColumnName.LAST_NAME);
            user.setLastName(lastName);
            long longBirthday = resultSet.getLong(SqlColumnName.BIRTHDAY);
            Calendar birthday = getDateFromLong(longBirthday);
            user.setBirthday(birthday);
            RoleType roleType = RoleType.getRoleTypeByValue(resultSet.getString(SqlColumnName.ROLE).trim());
            user.setRoleType(roleType);
            String login = resultSet.getString(SqlColumnName.LOGIN);
            user.setLogin(login);
            String password = resultSet.getString(SqlColumnName.PASSWORD);
            user.setPassword(password);
            StatusType statusType = StatusType.getStatusTypeByValue(resultSet.getString(SqlColumnName.STATUS).trim());
            user.setStatusType(statusType);
            boolean isVerified = resultSet.getBoolean(SqlColumnName.VERIFICATION_NAME);
            user.setVerified(isVerified);
        } catch (SQLException e) {
            throw new DaoException("User isn't inizialized: ", e);
        }
        return user;
    }

    @Override
    public List<User> initEntities(ResultSet resultSet) throws SQLException, DaoException {
        List<User> users = new ArrayList<>();
        try {
            while (resultSet.next()) {
                User user = initEntity(resultSet);
                users.add(user);
            }
        } catch (SQLException e) {
            throw new DaoException("Users aren't inizialized: ", e);
        }
        return users;
    }

    private Calendar getDateFromLong(long dateMillis) {
        Calendar date = new GregorianCalendar();
        date.setTimeInMillis(dateMillis);
        return date;
    }
}
