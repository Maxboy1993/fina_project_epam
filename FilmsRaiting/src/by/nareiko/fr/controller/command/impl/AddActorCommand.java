package by.nareiko.fr.controller.command.impl;

import by.nareiko.fr.controller.RequestParameterName;
import by.nareiko.fr.controller.Router;
import by.nareiko.fr.controller.command.Command;
import by.nareiko.fr.controller.command.PagePath;
import by.nareiko.fr.entity.Actor;
import by.nareiko.fr.exception.ServiceException;
import by.nareiko.fr.service.FilmPersonService;
import by.nareiko.fr.service.ServiceFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Optional;
import java.util.Set;

/**
 * The type Add actor command.
 */
public class AddActorCommand implements Command {
    private static final Logger LOGGER = LogManager.getLogger();
    private static final String COMMAND = "controller?command=PASSING_TO_ADD_ACTOR";

    @Override
    public Router execute(HttpServletRequest request) {
        Router router = new Router();
        String firstName = request.getParameter(RequestParameterName.FIRST_NAME_PARAM);
        String lastName = request.getParameter(RequestParameterName.LAST_NAME_PARAM);
        String birthday = request.getParameter(RequestParameterName.BIRTHDAY_PARAM);

        FilmPersonService actorService = ServiceFactory.getInstance().getActorService();
        Optional<Actor> optionalActor;
        if (actorService.checkPersonData(firstName, lastName, birthday)) {
            try {
                optionalActor = actorService.findByLastNameAndFirstName(lastName, firstName);
                List<Actor> actors = (List<Actor>) request.getSession().getAttribute(RequestParameterName.ACTORS_LIST_PARAM);
                if (optionalActor.isPresent()) {
                    actors.add(optionalActor.get());
                } else {
                    optionalActor = actorService.createPerson(firstName, lastName, birthday);
                    if (optionalActor.isPresent()) {
                        request.getSession().setAttribute(RequestParameterName.ACTOR_PARAM, optionalActor.get());
                        actors.add(optionalActor.get());
                        router.setRedirect();
                        router.setPage(COMMAND);
                    } else {
                        request.setAttribute(RequestParameterName.ACTOR_ADDING_ERROR_PARAM, RequestParameterName.ACTOR_ADDING_ERROR_VALUE);
                        router.setPage(PagePath.ADD_ACTOR);
                    }
                }
            } catch (ServiceException e) {
                LOGGER.error("Error while actor adding: ", e);
                router.setPage(PagePath.ERROR_500);
            }
        } else {
            Set<String> actorrErrors = actorService.getFilmPersonErrors();
            request.setAttribute(RequestParameterName.LIST_ACTOR_ADDING_ERROR_PARAM, actorrErrors);
        }
        return router;
    }
}
