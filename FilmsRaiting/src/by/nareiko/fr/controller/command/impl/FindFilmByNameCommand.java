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

public class FindFilmByNameCommand implements Command {
    private static final Logger LOGGER = LogManager.getLogger();

    @Override
    public Router execute(HttpServletRequest request) {
        String filmNmae = request.getParameter(RequestParameterName.FILM_NAME_PARAM);

        Router router = new Router();
        FilmService filmService = ServiceFactory.getInstance().getFilmService();
        if (filmService.checkFilmName(filmNmae)) {
            try {
                List<Film> filmList = filmService.findFilmsByName(filmNmae);
                request.setAttribute(RequestParameterName.FILMS_LIST_BY_NAME_PARAM, filmList);
                router.setPage(PagePath.MAIN);
            } catch (ServiceException e) {
                LOGGER.error("Error while film searching by name", e);
                router.setPage(PagePath.ERROR_500);
            }
        } else {
            request.setAttribute(RequestParameterName.FILM_SEARCHING_ERROR_PARAM, RequestParameterName.FILM_SEARCHING_BY_NAME_ERROR_VALUE);
            router.setPage(PagePath.MAIN);
        }
        return router;
    }
}
