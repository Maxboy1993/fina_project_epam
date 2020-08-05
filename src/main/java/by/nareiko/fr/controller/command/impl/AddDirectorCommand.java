package by.nareiko.fr.controller.command.impl;

import by.nareiko.fr.controller.RequestParameterName;
import by.nareiko.fr.controller.Router;
import by.nareiko.fr.controller.command.Command;
import by.nareiko.fr.controller.command.PagePath;
import by.nareiko.fr.entity.Director;
import by.nareiko.fr.exception.ServiceException;
import by.nareiko.fr.service.FilmPersonService;
import by.nareiko.fr.service.ServiceFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;
import java.util.Set;

public class AddDirectorCommand implements Command {
    private static final Logger LOGGER = LogManager.getLogger();
    private static final String COMMAND = "controller?command=PASSING_TO_ADD_FILM";

    @Override
    public Router execute(HttpServletRequest request) {
        Router router = new Router();
        String firstName = request.getParameter(RequestParameterName.FIRST_NAME_PARAM);
        String lastName = request.getParameter(RequestParameterName.LAST_NAME_PARAM);
        String birthday = request.getParameter(RequestParameterName.BIRTHDAY_PARAM);

        FilmPersonService directorService = ServiceFactory.getInstance().getDirectorService();
        Optional<Director> optionalDirector;
        if (directorService.checkPersonData(firstName, lastName, birthday)) {
            try {
                optionalDirector = directorService.findByLastNameAndFirstName(lastName, firstName);
                if (optionalDirector.isPresent()) {
                    request.getSession().setAttribute(RequestParameterName.DIRECTOR_PARAM, optionalDirector.get());
                    router.setPage(PagePath.ADD_FILM);
                } else {
                    optionalDirector = directorService.createPerson(firstName, lastName, birthday);
                    if (optionalDirector.isPresent()) {
                        request.getSession().setAttribute(RequestParameterName.DIRECTOR_PARAM, optionalDirector.get());
                        router.setRedirect();
                        router.setPage(COMMAND);
                    } else {
                        request.setAttribute(RequestParameterName.DIRECTOR_ADDING_ERROR_PARAM, RequestParameterName.DIRECTOR_ADDING_ERROR_VALUE);
                        router.setPage(PagePath.ADD_DIRECTOR);
                    }
                }
            } catch (ServiceException e) {
                LOGGER.error("Error while director adding: ", e);
                router.setPage(PagePath.ERROR_500);
            }
        } else {
            Set<String> directorErrors = directorService.getFilmPersonErrors();
            request.setAttribute(RequestParameterName.LIST_DIRECTOR_ADDING_ERROR_PARAM, directorErrors);
            router.setPage(PagePath.ADD_DIRECTOR);
        }
        return router;
    }
}
