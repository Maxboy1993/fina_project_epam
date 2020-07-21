package by.nareiko.fr.dao.impl.entittymapper;

import by.nareiko.fr.dao.SqlColumnName;
import by.nareiko.fr.dao.EntityMapper;
import by.nareiko.fr.entity.FilmRaiting;
import by.nareiko.fr.exception.DaoException;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class FilmRaitingMapper extends EntityMapper<FilmRaiting> {
    @Override
    public FilmRaiting initEntity(ResultSet resultSet) throws DaoException {
        FilmRaiting filmRaiting = new FilmRaiting();
        try {
            int raitingId = resultSet.getInt(SqlColumnName.RAITING_ID);
            filmRaiting.setId(raitingId);
            int filmId = resultSet.getInt(SqlColumnName.FILM_ID);
            filmRaiting.setFilmId(filmId);
            int userId = resultSet.getInt(SqlColumnName.USER_ID);
            filmRaiting.setUserId(userId);
            int raiting = resultSet.getInt(SqlColumnName.FILM_RAITING);
            filmRaiting.setRaiting(raiting);
        } catch (SQLException e) {
            throw new DaoException("Error while inizializing Film's raiting: ", e);
        }
        return filmRaiting;
    }

    @Override
    public List<FilmRaiting> initEntities(ResultSet resultSet) throws SQLException, DaoException {
        List<FilmRaiting> filmRaitings = new ArrayList<>();
        try {
            while (resultSet.next()) {
                FilmRaiting filmRaiting = initEntity(resultSet);
                filmRaitings.add(filmRaiting);
            }
        } catch (SQLException e) {
            throw new DaoException("Error while inizializing Film's raitings: ", e);
        }
        return filmRaitings;
    }
}
