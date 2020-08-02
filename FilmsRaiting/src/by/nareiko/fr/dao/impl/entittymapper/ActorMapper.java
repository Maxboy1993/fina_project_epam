package by.nareiko.fr.dao.impl.entittymapper;

import by.nareiko.fr.dao.EntityMapper;
import by.nareiko.fr.dao.SqlColumnName;
import by.nareiko.fr.entity.Actor;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class ActorMapper extends EntityMapper<Actor> {
    @Override
    public Actor initEntity(ResultSet resultSet) throws SQLException {
        Actor actor = new Actor();
        int id = resultSet.getInt(SqlColumnName.PERSON_ID);
        actor.setId(id);
        String firstName = resultSet.getString(SqlColumnName.FIRST_NAME);
        actor.setFirstName(firstName);
        String lastName = resultSet.getString(SqlColumnName.LAST_NAME);
        actor.setLastName(lastName);
        long longBirthday = resultSet.getLong(SqlColumnName.BIRTHDAY);
        Calendar birthday = getDateFromLong(longBirthday);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String formattedDate = dateFormat.format(birthday.getTime());
        actor.setBirthday(formattedDate);
        return actor;
    }

    public Calendar getDateFromLong(long dateMillis) {
        Calendar date = new GregorianCalendar();
        date.setTimeInMillis(dateMillis);
        return date;
    }
}
