package by.nareiko.fr.service.impl;

import by.nareiko.fr.dao.DaoFactory;
import by.nareiko.fr.dao.FilmRatingDao;
import by.nareiko.fr.entity.FilmRaiting;
import by.nareiko.fr.exception.DaoException;
import by.nareiko.fr.exception.ServiceException;
import by.nareiko.fr.service.FilmRaitingService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Optional;

public class FilmRaitingServiceImpl implements FilmRaitingService<FilmRaiting> {
    private static final Logger LOGGER = LogManager.getLogger();

    private static final FilmRaitingService INSTANCE = new FilmRaitingServiceImpl();

    private FilmRaitingServiceImpl() {
    }

    public static FilmRaitingService getInstance() {
        return INSTANCE;
    }

    @Override
    public List<FilmRaiting> findByFilmId(int filmId) throws ServiceException {
        FilmRatingDao filmRatingDao = DaoFactory.getInstance().getFilmRatingDao();
        List<FilmRaiting> filmRaitings = null;
        try {
            filmRaitings = filmRatingDao.findByFilmId(filmId);
        } catch (DaoException e) {
            LOGGER.error("Error while raiting searching", e);
            throw new ServiceException("Error while raiting searching", e);
        }
        return filmRaitings;
    }

    @Override
    public Optional<FilmRaiting> create(int filmId, int userId, int userRaiting) throws ServiceException {
        FilmRaiting filmRaiting = new FilmRaiting();
        filmRaiting.setFilmId(filmId);
        filmRaiting.setUserId(userId);
        filmRaiting.setRaiting(userRaiting);
        FilmRatingDao filmRatingDao = DaoFactory.getInstance().getFilmRatingDao();
        Optional<FilmRaiting> optionalFilmRaiting;
        try {
            optionalFilmRaiting = filmRatingDao.create(filmRaiting);
        } catch (DaoException e) {
            LOGGER.error("Error while raiting adding", e);
            throw new ServiceException("Error while raiting adding", e);
        }
        return optionalFilmRaiting;
    }

    public double countfinalFilmRaiting(int filmId) throws ServiceException {
        DaoFactory daoFactory = DaoFactory.getInstance();
        FilmRatingDao raitingDao = daoFactory.getFilmRatingDao();
        double result = 0;
        List<FilmRaiting> raitings;
        try {
            raitings = raitingDao.findByFilmId(filmId);
            int counter = 0;
            int sum = 0;
            if (raitings.size() > 0) {
                for (int i = 0; i < raitings.size(); i++) {
                    sum += raitings.get(i).getRaiting();
                    counter++;
                }
                double finalRaiting = sum * 1.0 / counter;
                result = new BigDecimal(finalRaiting).setScale(1, RoundingMode.HALF_UP).doubleValue();
            }
        } catch (DaoException e) {
            LOGGER.error("Error while raiting counting", e);
            throw new ServiceException("Error while raiting counting", e);
        }
        return result;
    }
}
