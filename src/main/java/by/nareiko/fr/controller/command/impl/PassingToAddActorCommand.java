package by.nareiko.fr.controller.command.impl;

import by.nareiko.fr.controller.RequestParameterName;
import by.nareiko.fr.controller.Router;
import by.nareiko.fr.controller.command.Command;
import by.nareiko.fr.controller.command.PagePath;
import by.nareiko.fr.entity.Actor;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

public class PassingToAddActorCommand implements Command {

    @Override
    public Router execute(HttpServletRequest request) {
        Router router = new Router();
        router.setPage(PagePath.ADD_ACTOR);
        List<Actor> actors = (List<Actor>) (request.getSession().getAttribute(RequestParameterName.ACTORS_LIST_PARAM));
        if (actors == null) {
            actors = new ArrayList<>();
            request.getSession().setAttribute(RequestParameterName.ACTORS_LIST_PARAM, actors);
        }
        return router;
    }
}
