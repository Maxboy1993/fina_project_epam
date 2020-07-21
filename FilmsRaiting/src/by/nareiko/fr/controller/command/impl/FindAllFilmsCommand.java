package by.nareiko.fr.controller.command.impl;

import by.nareiko.fr.controller.command.Command;
import by.nareiko.fr.controller.command.PagePath;
import by.nareiko.fr.entity.Film;
import by.nareiko.fr.exception.ControllerException;
import by.nareiko.fr.exception.ServiceException;
import by.nareiko.fr.service.FilmService;
import by.nareiko.fr.service.ServiceFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

public class FindAllFilmsCommand implements Command {
    private static final String FILMS_LIST_PARAM = "filmsList";
    private static final Logger LOGGER = LogManager.getLogger();

    @Override
    public String execute(HttpServletRequest request) throws ControllerException {
        String page = PagePath.SIGN_IN;
        FilmService filmService = ServiceFactory.getInstance().getFilmService();
        List<Film> films;
        try {
            films = filmService.findAllFilms();
            request.getServletContext().setAttribute(FILMS_LIST_PARAM, films);
        } catch (ServiceException e) {
            LOGGER.error("Error while searching films: ", e);
            throw new ControllerException("Error while searching films: ", e);
        }
        return page;
    }
}

