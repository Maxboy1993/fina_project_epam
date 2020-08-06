package by.nareiko.fr.service.impl;

import by.nareiko.fr.dao.DaoFactory;
import by.nareiko.fr.dao.FilmDao;
import by.nareiko.fr.entity.Actor;
import by.nareiko.fr.entity.Director;
import by.nareiko.fr.entity.Film;
import by.nareiko.fr.entity.GenreType;
import by.nareiko.fr.exception.DaoException;
import by.nareiko.fr.exception.ServiceException;
import by.nareiko.fr.service.FilmService;
import by.nareiko.fr.validator.FilmValidator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

/**
 * The type Film service.
 */
public class FilmServiceImpl implements FilmService<Film> {
    private static final Logger LOGGER = LogManager.getLogger();
    private static final FilmService INSTANCE = new FilmServiceImpl();
    private Set<String> errorsFilmMessage = new HashSet<>();

    private FilmServiceImpl() {
    }

    /**
     * Gets instance.
     *
     * @return the instance
     */
    public static FilmService getInstance() {
        return INSTANCE;
    }

    @Override
    public List<Film> findAllFilms() throws ServiceException {
        FilmDao fullFilmInfoDao = DaoFactory.getInstance().getFullFilmInfoDao();
        List<Film> films;
        try {
            films = fullFilmInfoDao.findAll();
        } catch (DaoException e) {
            LOGGER.error("Error while searching all films: ", e);
            throw new ServiceException("Error while searching all films: ", e);
        }

        return films;
    }

    @Override
    public boolean checkFilmData(String filmName, String releaseDate, String genre) {
        boolean isCorrectFilm = false;
        FilmValidator filmValidator = new FilmValidator();
        if (filmValidator.validateFilmData(filmName, releaseDate, genre)) {
            isCorrectFilm = true;
        }
        if (!isCorrectFilm) {
            errorsFilmMessage = filmValidator.getFilmErrorMessage();
        }
        return isCorrectFilm;
    }

    @Override
    public boolean checkFilmName(String filmName) {
        boolean isCorrect = false;
        FilmValidator filmValidator = new FilmValidator();
        if (filmName != null && !filmName.isBlank()) {
            isCorrect = filmValidator.validateFilmName(filmName);
        }
        if (!isCorrect) {
            errorsFilmMessage = filmValidator.getFilmErrorMessage();
        }
        return isCorrect;
    }

    @Override
    public boolean deleteFilm(int filmId) throws ServiceException {
        FilmDao filmDao = DaoFactory.getInstance().getFullFilmInfoDao();
        boolean isDeleted;
        try {
            isDeleted = filmDao.delete(filmId);
        } catch (DaoException e) {
            LOGGER.error("Error while deleting film: ", e);
            throw new ServiceException("Error while deleting film: : ", e);
        }
        return isDeleted;
    }

    @Override
    public Optional<Film> addFilm(String filmName, String releaseDate, String genre, List<Actor> actors, Director director) throws ServiceException {
        FilmDao filmDao = DaoFactory.getInstance().getFullFilmInfoDao();
        Film film = new Film();
        film.setName(filmName);
        film.setReleaseDate(releaseDate);
        GenreType genreType = GenreType.getGenreTypeByValue(genre);
        film.setGenreType(genreType);
        film.setActors(actors);
        film.setDirector(director);
        Optional<Film> optionalFilm;
        try {
            optionalFilm = filmDao.create(film);
        } catch (DaoException e) {
            LOGGER.error("Error while adding film: ", e);
            throw new ServiceException("Error while adding film: : ", e);
        }
        return optionalFilm;
    }

    @Override
    public Optional<Film> findById(int filmId) throws ServiceException {
        FilmDao filmDao = DaoFactory.getInstance().getFullFilmInfoDao();
        Optional<Film> film;
        try {
            film = filmDao.findById(filmId);
        } catch (DaoException e) {
            LOGGER.error("Error while searching film by id: ", e);
            throw new ServiceException("Error while searching film by id: ", e);
        }
        return film;
    }

    @Override
    public List<Film> findFilmsByName(String filmName) throws ServiceException {
        FilmDao filmDao = DaoFactory.getInstance().getFullFilmInfoDao();
        List<Film> films;
        try {
            films = filmDao.findByName(filmName);
        } catch (DaoException e) {
            LOGGER.error("Error while film searching by name", e);
            throw new ServiceException("Error while film searching by name", e);
        }
        return films;
    }

    public Set<String> getErrorsFilmMessage() {
        Set<String> errors = new HashSet<>();
        errors.addAll(errorsFilmMessage);
        errorsFilmMessage.removeAll(errorsFilmMessage);
        return errors;
    }
}
