package by.nareiko.fr.controller.command.impl;

import by.nareiko.fr.controller.RequestParameterName;
import by.nareiko.fr.controller.Router;
import by.nareiko.fr.controller.command.Command;
import by.nareiko.fr.controller.command.PagePath;
import by.nareiko.fr.entity.Actor;
import by.nareiko.fr.entity.Director;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public class PassingToAddPosterCommand implements Command {

    @Override
    public Router execute(HttpServletRequest request) {
        Router router = new Router();
        List<Actor> actorList = (List<Actor>) request.getSession().getAttribute(RequestParameterName.ACTORS_LIST_PARAM);
        Director director = (Director) request.getSession().getAttribute(RequestParameterName.DIRECTOR_PARAM);
        int filmId = (int) request.getSession().getAttribute(RequestParameterName.FILM_ID_PARAM);
        if (actorList == null) {
            request.setAttribute(RequestParameterName.ACTOR_ADDING_ERROR_PARAM, RequestParameterName.ACTOR_ADDING_ERROR_VALUE);
            router.setPage(PagePath.ADD_ACTOR);
        }
        if (director == null) {
            request.setAttribute(RequestParameterName.DIRECTOR_ADDING_ERROR_PARAM, RequestParameterName.DIRECTOR_ADDING_ERROR_VALUE);
            router.setPage(PagePath.ADD_DIRECTOR);
        }
        if (filmId == 0) {
            request.setAttribute(RequestParameterName.FILM_ADDING_ERROR_PARAM, RequestParameterName.FILM_NOT_ADDED_VALUE);
            router.setPage(PagePath.ADD_FILM);

        } else {
            router.setPage(PagePath.ADD_POSTER);
        }
        return router;
    }
}
