package by.nareiko.fr.controller.command.impl;

import by.nareiko.fr.controller.RequestParameterName;
import by.nareiko.fr.controller.Router;
import by.nareiko.fr.controller.command.Command;
import by.nareiko.fr.controller.command.PagePath;
import by.nareiko.fr.entity.Film;
import by.nareiko.fr.entity.FilmRaiting;
import by.nareiko.fr.entity.User;
import by.nareiko.fr.exception.ServiceException;
import by.nareiko.fr.service.FilmRaitingService;
import by.nareiko.fr.service.ServiceFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Optional;

public class CountFilmRaiting implements Command {
    private static final Logger LOGGER = LogManager.getLogger();

    @Override
    public Router execute(HttpServletRequest request) {
        int filmId = Integer.parseInt(request.getParameter(RequestParameterName.FILM_ID_PARAM));
        User user = (User) request.getSession().getAttribute(RequestParameterName.USER_PARAM);
        int userId = user.getId();
        int userRaiting = Integer.parseInt(request.getParameter(RequestParameterName.FILM_RAITING_PARAM));
        Router router = new Router();
        FilmRaitingService filmRaitingService = ServiceFactory.getInstance().getFilmRaitingService();
        try {
            List<FilmRaiting> raitingsFromDB = filmRaitingService.findByFilmId(filmId);
            boolean isUserAlreadiAddRaiting = false;
            for (FilmRaiting rait:raitingsFromDB) {
                if (rait.getUserId() == userId){
                    isUserAlreadiAddRaiting = true;
                }
            }
            if (!isUserAlreadiAddRaiting) {
                Optional<FilmRaiting> optionalFilmRaiting = filmRaitingService.create(filmId, userId, userRaiting);
                if (optionalFilmRaiting.isPresent()) {
                    double newRaiting = filmRaitingService.countfinalFilmRaiting(filmId);
                    List<Film> filmList = (List<Film>) request.getServletContext().getAttribute(RequestParameterName.FILMS_LIST_PARAM);
                    for (int i = 0; i < filmList.size(); i++) {
                        Film film = filmList.get(i);
                        if (film.getId() == filmId) {
                            film.setRaiting(newRaiting);
                            break;
                        }
                    }
                    router.setPage(PagePath.MAIN);
                } else {
                    request.setAttribute(RequestParameterName.ERROR_RAITING_COUNTING_PARAM, RequestParameterName.ERROR_RAITING_COUNTING_VALUE);
                    router.setPage(PagePath.MAIN);
                }
            }else {
                request.setAttribute(RequestParameterName.ERROR_RAITING_COUNTING_PARAM, RequestParameterName.DOUBLE_RAITING_VALUE);
                router.setPage(PagePath.MAIN);
            }
        } catch (ServiceException e) {
            LOGGER.error("Error while raiting counting", e);
            router.setPage(PagePath.ERROR_500);
        }
        return router;
    }
}
