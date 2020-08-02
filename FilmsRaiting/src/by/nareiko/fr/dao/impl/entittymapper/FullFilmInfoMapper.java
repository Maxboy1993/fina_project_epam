package by.nareiko.fr.dao.impl.entittymapper;

import by.nareiko.fr.dao.DaoFactory;
import by.nareiko.fr.dao.EntityMapper;
import by.nareiko.fr.dao.FilmRaitingDao;
import by.nareiko.fr.dao.SqlColumnName;
import by.nareiko.fr.entity.Film;
import by.nareiko.fr.entity.FilmRaiting;
import by.nareiko.fr.entity.GenreType;
import by.nareiko.fr.exception.DaoException;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

public class FullFilmInfoMapper extends EntityMapper<Film> {
    @Override
    public Film initEntity(ResultSet resultSet) throws SQLException, DaoException {
        Film film = new Film();
        int id = resultSet.getInt(SqlColumnName.FILM_ID);
        film.setId(id);
        String name = resultSet.getString(SqlColumnName.FILM_NAME);
        film.setName(name);
        GenreType genreType = GenreType.getGenreTypeByValue(resultSet.getString(SqlColumnName.GENRE).trim());
        film.setGenreType(genreType);
        long longReleaseDate = resultSet.getLong(SqlColumnName.RELEASE_DATE);
        Calendar releaseDate = getDateFromLong(longReleaseDate);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String formattedDate = dateFormat.format(releaseDate.getTime());
        film.setReleaseDate(formattedDate);
        double raiting = countfinalFilmRaiting(film.getId());
        film.setRaiting(raiting);
        return film;
    }

    private Calendar getDateFromLong(long dateMillis) {
        Calendar date = new GregorianCalendar();
        date.setTimeInMillis(dateMillis);
        return date;
    }

    public double countfinalFilmRaiting(int filmId) throws DaoException {
        DaoFactory daoFactory = DaoFactory.getInstance();
        FilmRaitingDao raitingDao = daoFactory.getFilmRaitingDao();
        List<FilmRaiting> raitings;
        raitings = raitingDao.findByFilmId(filmId);
        int counter = 0;
        int sum = 0;
        double result = 0;
        if (raitings.size() > 0) {
            for (int i = 0; i < raitings.size(); i++) {
                sum += raitings.get(i).getRaiting();
                counter++;
            }
            double finalRaiting = sum * 1.0 / counter;
            result = new BigDecimal(finalRaiting).setScale(1, RoundingMode.HALF_UP).doubleValue();
        }
        return result;
    }
}
