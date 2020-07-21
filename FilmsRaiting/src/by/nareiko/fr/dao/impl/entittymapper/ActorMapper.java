package by.nareiko.fr.dao.impl.entittymapper;

import by.nareiko.fr.dao.SqlColumnName;
import by.nareiko.fr.dao.EntityMapper;
import by.nareiko.fr.entity.Actor;
import by.nareiko.fr.exception.DaoException;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

public class ActorMapper extends EntityMapper<Actor> {
    @Override
    public Actor initEntity(ResultSet resultSet) throws DaoException {
        Actor actor = new Actor();
        try {
            int id = resultSet.getInt(SqlColumnName.PERSON_ID);
            actor.setId(id);
            String firstName = resultSet.getString(SqlColumnName.FIRST_NAME);
            actor.setFirstName(firstName);
            String lastName = resultSet.getString(SqlColumnName.LAST_NAME);
            actor.setLastName(lastName);
            long longBirthday = resultSet.getLong(SqlColumnName.BIRTHDAY);
            Calendar birthday = getDateFromLong(longBirthday);
            actor.setBirthday(birthday);
        } catch (SQLException e) {
            throw new DaoException("Error while inizializing actor: ", e);
        }
        return actor;
    }

    @Override
    public List<Actor> initEntities(ResultSet resultSet) throws SQLException, DaoException {
        List<Actor> actors = new ArrayList<>();
        try {
            while (resultSet.next()) {
                Actor actor = initEntity(resultSet);
                actors.add(actor);
            }
        } catch (SQLException e) {
            throw new DaoException("Error while inizializing actors: ", e);
        }
        return actors;
    }

    public Calendar getDateFromLong(long dateMillis) {
        Calendar date = new GregorianCalendar();
        date.setTimeInMillis(dateMillis);
        return date;
    }
}
