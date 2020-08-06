package by.nareiko.fr.controller.command.impl;

import by.nareiko.fr.controller.RequestParameterName;
import by.nareiko.fr.controller.Router;
import by.nareiko.fr.controller.command.Command;
import by.nareiko.fr.controller.command.PagePath;
import by.nareiko.fr.entity.Film;
import by.nareiko.fr.entity.Review;
import by.nareiko.fr.entity.User;
import by.nareiko.fr.exception.ServiceException;
import by.nareiko.fr.service.FilmService;
import by.nareiko.fr.service.ReviewService;
import by.nareiko.fr.service.ServiceFactory;
import by.nareiko.fr.service.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * The type Show film info command.
 */
public class ShowFilmInfoCommand implements Command {
    private static final Logger LOGGER = LogManager.getLogger();

    @Override
    public Router execute(HttpServletRequest request) {
        Router router = new Router();
        int filmId = Integer.parseInt(request.getParameter(RequestParameterName.FILM_ID_PARAM));
        FilmService filmService = ServiceFactory.getInstance().getFilmService();
        ReviewService reviewService = ServiceFactory.getInstance().getReviewService();
        UserService userService = ServiceFactory.getInstance().getUserService();
        try {
            Optional<Film> optionalFilm = filmService.findById(filmId);
            if (optionalFilm.isPresent()) {
                request.getSession().setAttribute(RequestParameterName.FILM_PARAM, optionalFilm.get());
                request.setAttribute(RequestParameterName.DIRECTOR_PARAM, optionalFilm.get().getDirector());
                request.setAttribute(RequestParameterName.ACTORS_LIST_PARAM, optionalFilm.get().getActors());
                List<Review> reviews = reviewService.findByFilmId(filmId);
                request.getSession().setAttribute(RequestParameterName.REVIEW_LIST_PARAM, reviews);
                List<User> users = new ArrayList<>();
                for (int i = 0; i < reviews.size(); i++) {
                    int userId = reviews.get(i).getUserId();
                    Optional<User> optionalUser = userService.findById(userId);
                    if (optionalUser.isPresent()) {
                        users.add(optionalUser.get());
                    }
                }
                request.getSession().setAttribute(RequestParameterName.USER_LIST_PARAM, users);
                request.setAttribute(RequestParameterName.FILM_ID_PARAM, filmId);
                router.setPage(PagePath.FILM_INFO);
            } else {
                request.setAttribute(RequestParameterName.FILM_SEARCHING_ERROR_PARAM, RequestParameterName.FILM_SEARCHING_ERROR_VALUE);
                router.setPage(PagePath.MAIN);
            }
        } catch (ServiceException e) {
            LOGGER.error("Error while searching film by id: ", e);
            router.setPage(PagePath.ERROR_500);
        }
        return router;
    }
}
