package by.nareiko.fr.dao.impl.entittymapper;

import by.nareiko.fr.dao.EntityMapper;
import by.nareiko.fr.dao.SqlColumnName;
import by.nareiko.fr.entity.Director;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 * The type Director mapper.
 */
public class DirectorMapper extends EntityMapper<Director> {

    @Override
    public Director initEntity(ResultSet resultSet) throws SQLException {
        Director director = new Director();
        int id = resultSet.getInt(SqlColumnName.PERSON_ID);
        director.setId(id);
        String firstName = resultSet.getString(SqlColumnName.FIRST_NAME);
        director.setFirstName(firstName);
        String lastName = resultSet.getString(SqlColumnName.LAST_NAME);
        director.setLastName(lastName);
        long longBirthday = resultSet.getLong(SqlColumnName.BIRTHDAY);
        Calendar birthday = getDateFromLong(longBirthday);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String formattedDate = dateFormat.format(birthday.getTime());
        director.setBirthday(formattedDate);
        return director;
    }

    /**
     * Gets date from long.
     *
     * @param dateMillis the date millis
     * @return the date from long
     */
    public Calendar getDateFromLong(long dateMillis) {
        Calendar date = new GregorianCalendar();
        date.setTimeInMillis(dateMillis);
        return date;
    }
}
