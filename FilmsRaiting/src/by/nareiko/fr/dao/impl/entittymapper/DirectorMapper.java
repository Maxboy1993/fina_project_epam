package by.nareiko.fr.dao.impl.entittymapper;

import by.nareiko.fr.dao.SqlColumnName;
import by.nareiko.fr.dao.EntityMapper;
import by.nareiko.fr.entity.Director;
import by.nareiko.fr.exception.DaoException;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

public class DirectorMapper extends EntityMapper<Director> {

    @Override
    public Director initEntity(ResultSet resultSet) throws DaoException {
        Director director = new Director();
        try {
            int id = resultSet.getInt(SqlColumnName.PERSON_ID);
            director.setId(id);
            String firstName = resultSet.getString(SqlColumnName.FIRST_NAME);
            director.setFirstName(firstName);
            String lastName = resultSet.getString(SqlColumnName.LAST_NAME);
            director.setLastName(lastName);
            long longBirthday = resultSet.getLong(SqlColumnName.BIRTHDAY);
            Calendar birthday = getDateFromLong(longBirthday);
            director.setBirthday(birthday);
        } catch (SQLException e) {
            throw new DaoException("Error while inizializing director: ", e);
        }
        return director;
    }


    @Override
    public List<Director> initEntities(ResultSet resultSet) throws SQLException, DaoException {
        List<Director> directors = new ArrayList<>();
        try {
            while (resultSet.next()) {
                Director director = initEntity(resultSet);
                directors.add(director);
            }
        } catch (SQLException e) {
            throw new DaoException("Error while inizializing directors: ", e);
        }
        return directors;
    }

    public Calendar getDateFromLong(long dateMillis) {
        Calendar date = new GregorianCalendar();
        date.setTimeInMillis(dateMillis);
        return date;
    }
}
