package by.nareiko.fr.controller.command.impl;

import by.nareiko.fr.controller.RequestParameterName;
import by.nareiko.fr.controller.Router;
import by.nareiko.fr.controller.command.Command;
import by.nareiko.fr.controller.command.PagePath;
import by.nareiko.fr.entity.Actor;
import by.nareiko.fr.entity.Director;
import by.nareiko.fr.entity.Film;
import by.nareiko.fr.exception.ServiceException;
import by.nareiko.fr.service.FilmService;
import by.nareiko.fr.service.ServiceFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Optional;
import java.util.Set;

/**
 * The type Add film command.
 */
public class AddFilmCommand implements Command {
    private static final Logger LOGGER = LogManager.getLogger();
    private static final String COMMAND = "controller?command=PASSING_TO_ADD_POSTER";

    @Override
    public Router execute(HttpServletRequest request) {
        Router router = new Router();
        String filmName = request.getParameter(RequestParameterName.FILM_NAME_PARAM);
        String releaseDate = request.getParameter(RequestParameterName.RELEASE_DATE_PARAM);
        String genre = request.getParameter(RequestParameterName.GENRE_PARAM);

        FilmService filmService = ServiceFactory.getInstance().getFilmService();
        if (filmService.checkFilmData(filmName, releaseDate, genre)) {
            Optional<Film> optionalFilm;
            try {
                List<Actor> actors = (List<Actor>) request.getSession().getAttribute(RequestParameterName.ACTORS_LIST_PARAM);
                Director director = (Director) request.getSession().getAttribute(RequestParameterName.DIRECTOR_PARAM);
                optionalFilm = filmService.addFilm(filmName, releaseDate, genre, actors, director);
                if (optionalFilm.isPresent()) {
                    request.getSession().setAttribute(RequestParameterName.FILM_ID_PARAM, optionalFilm.get().getId());
                    router.setRedirect();
                    router.setPage(COMMAND);
                } else {
                    request.setAttribute(RequestParameterName.FILM_ADDING_ERROR_PARAM, RequestParameterName.FILM_ADDING_ERROR_VALUE);
                    router.setPage(PagePath.ADD_FILM);
                }
            } catch (ServiceException e) {
                LOGGER.error("Error while film adding");
                router.setPage(PagePath.ERROR_500);
            }
        } else {
            Set<String> filmErrors = filmService.getErrorsFilmMessage();
            request.setAttribute(RequestParameterName.LIST_FILM_ADDING_ERROR_PARAM, filmErrors);
            router.setPage(PagePath.ADD_FILM);
        }
        return router;
    }
}
