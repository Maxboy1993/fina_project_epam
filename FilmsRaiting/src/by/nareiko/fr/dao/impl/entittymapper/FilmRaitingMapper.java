package by.nareiko.fr.dao.impl.entittymapper;

import by.nareiko.fr.dao.EntityMapper;
import by.nareiko.fr.dao.SqlColumnName;
import by.nareiko.fr.entity.FilmRaiting;

import java.sql.ResultSet;
import java.sql.SQLException;

public class FilmRaitingMapper extends EntityMapper<FilmRaiting> {
    @Override
    public FilmRaiting initEntity(ResultSet resultSet) throws SQLException {
        FilmRaiting filmRaiting = new FilmRaiting();
        int raitingId = resultSet.getInt(SqlColumnName.RAITING_ID);
        filmRaiting.setId(raitingId);
        int filmId = resultSet.getInt(SqlColumnName.FILM_ID);
        filmRaiting.setFilmId(filmId);
        int userId = resultSet.getInt(SqlColumnName.USER_ID);
        filmRaiting.setUserId(userId);
        int raiting = resultSet.getInt(SqlColumnName.FILM_RAITING);
        filmRaiting.setRaiting(raiting);
        return filmRaiting;
    }
}
