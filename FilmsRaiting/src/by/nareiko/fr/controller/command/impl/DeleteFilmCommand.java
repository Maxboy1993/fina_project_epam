package by.nareiko.fr.controller.command.impl;

import by.nareiko.fr.controller.RequestParameterName;
import by.nareiko.fr.controller.Router;
import by.nareiko.fr.controller.command.Command;
import by.nareiko.fr.controller.command.PagePath;
import by.nareiko.fr.entity.Film;
import by.nareiko.fr.exception.ServiceException;
import by.nareiko.fr.service.FilmService;
import by.nareiko.fr.service.ServiceFactory;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * The type Delete film command.
 */
public class DeleteFilmCommand implements Command {
    private static final String COMMAND = "controller?command=PASSING_TO_MAIN";

    @Override
    public Router execute(HttpServletRequest request) {
        int filmId = Integer.parseInt(request.getParameter(RequestParameterName.FILM_ID_PARAM));
        Router router = new Router();
        FilmService filmService = ServiceFactory.getInstance().getFilmService();
        try {
            filmService.deleteFilm(filmId);
            List<Film> films = (List<Film>) request.getServletContext().getAttribute(RequestParameterName.FILMS_LIST_PARAM);
            Film film = null;
            for (Film tempFilm:films) {
                if (tempFilm.getId() == filmId){
                    film = tempFilm;
                }
            }
            films.remove(film);
            router.setRedirect();
            router.setPage(COMMAND);
        } catch (ServiceException e) {
            router.setPage(PagePath.ERROR_500);
        }
        return router;
    }
}
