package by.nareiko.fr.dao.impl.entittymapper;

import by.nareiko.fr.dao.SqlColumnName;
import by.nareiko.fr.dao.DaoFactory;
import by.nareiko.fr.dao.EntityMapper;
import by.nareiko.fr.dao.FilmRaitingDao;
import by.nareiko.fr.entity.*;
import by.nareiko.fr.exception.DaoException;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

public class FullFilmInfoMapper extends EntityMapper<Film> {
    @Override
    public Film initEntity(ResultSet resultSet) throws DaoException {
        Film film = new Film();
        try {
            int id = resultSet.getInt(SqlColumnName.FILM_ID);
            film.setId(id);
            String name = resultSet.getString(SqlColumnName.FILM_NAME);
            film.setName(name);
            GenreType genreType = GenreType.getGenreTypeByValue(resultSet.getString(SqlColumnName.GENRE).trim());
            film.setGenreType(genreType);
            long longReleaseDate = resultSet.getLong(SqlColumnName.RELEASE_DATE);
            Calendar releaseDate = getDateFromLong(longReleaseDate);
            film.setReleaseDate(releaseDate);
            double raiting = countfinalFilmRaiting(film.getId());
            film.setRaiting(raiting);
        } catch (SQLException e) {
            throw new DaoException("Error while inizializing film: ", e);
        }
        return film;
    }

    @Override
    public List<Film> initEntities(ResultSet resultSet) throws SQLException, DaoException {
        List<Film> films = new ArrayList<>();
        try {
            while (resultSet.next()) {
                Film film = initEntity(resultSet);
                films.add(film);
            }
        } catch (SQLException e) {
            throw new DaoException("Error while inizializing films: ", e);
        }
        return films;
    }

    private Calendar getDateFromLong(long dateMillis) {
        Calendar date = new GregorianCalendar();
        date.setTimeInMillis(dateMillis);
        return date;
    }

    private double countfinalFilmRaiting(int filmId) throws DaoException {
        DaoFactory daoFactory = DaoFactory.getInstance();
        FilmRaitingDao raitingDao = daoFactory.getFilmRaitingDao();
        List<FilmRaiting> raitings;
        raitings = raitingDao.findByFilmId(filmId);
        int counter = 0;
        int sum = 0;
        for (int i = 0; i < raitings.size(); i++) {
            sum += raitings.get(i).getRaiting();
            counter++;
        }
        double finalRaiting = sum * 1.0 / counter;
        double result = new BigDecimal(finalRaiting).setScale(2, RoundingMode.HALF_UP).doubleValue();
        return result;
    }
}
