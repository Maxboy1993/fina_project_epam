package by.nareiko.fr.service.impl;

import by.nareiko.fr.dao.DaoFactory;
import by.nareiko.fr.dao.FilmDao;
import by.nareiko.fr.dao.impl.FullFilmInfoDaoImpl;
import by.nareiko.fr.entity.Film;
import by.nareiko.fr.exception.DaoException;
import by.nareiko.fr.exception.ServiceException;
import by.nareiko.fr.service.FilmService;
import by.nareiko.fr.service.ServiceFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

public class FilmServiceImpl implements FilmService<Film> {
    private static final Logger LOGGER = LogManager.getLogger();
    private static final FilmService INSTANCE = new FilmServiceImpl();

    public static FilmService getInstance(){
        return INSTANCE;
    }

    private FilmServiceImpl(){}

    @Override
    public List<Film> findAllFilms() throws ServiceException {
        FilmDao fullFilmInfoDao = DaoFactory.getInstance().getFullFilmInfoDao();
        List<Film> films;
        try {
            films = fullFilmInfoDao.findAll();
        } catch (DaoException e) {
            LOGGER.error("Error while seaching all films: ", e);
            throw new ServiceException("Error while seaching all films: ", e);
        }

        return films;
    }

    //TODO DELETE
    public static void main(String[] args) throws ServiceException {
        FilmService filmService = ServiceFactory.getInstance().getFilmService();
        List<Film> films = filmService.findAllFilms();
        for (Film f:films) {
            System.out.println(f.getName() + " " + f.getGenreType() + " " + f.getRaiting() + " " + f.getDirector().getLastName());
        }
    }
}
