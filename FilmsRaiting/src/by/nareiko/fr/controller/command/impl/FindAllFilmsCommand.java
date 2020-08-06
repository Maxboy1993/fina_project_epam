package by.nareiko.fr.controller.command.impl;

import by.nareiko.fr.controller.RequestParameterName;
import by.nareiko.fr.controller.Router;
import by.nareiko.fr.controller.command.Command;
import by.nareiko.fr.controller.command.PagePath;
import by.nareiko.fr.entity.Film;
import by.nareiko.fr.exception.ServiceException;
import by.nareiko.fr.service.FilmService;
import by.nareiko.fr.service.ServiceFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * The type Find all films command.
 */
public class FindAllFilmsCommand implements Command {
    private static final Logger LOGGER = LogManager.getLogger();

    @Override
    public Router execute(HttpServletRequest request) {
        Router router = new Router();
        FilmService filmService = ServiceFactory.getInstance().getFilmService();
        List<Film> films;
        try {
            films = filmService.findAllFilms();
            request.getServletContext().setAttribute(RequestParameterName.FILMS_LIST_PARAM, films);
            router.setPage(PagePath.SIGN_IN);
        } catch (ServiceException e) {
            LOGGER.error("Error while searching films: ", e);
            router.setPage(PagePath.ERROR_500);
        }
        return router;
    }
}

