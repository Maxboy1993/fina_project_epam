package by.nareiko.fr.controller.command.impl;

import by.nareiko.fr.controller.RequestParameterName;
import by.nareiko.fr.controller.Router;
import by.nareiko.fr.controller.command.Command;
import by.nareiko.fr.controller.command.PagePath;
import by.nareiko.fr.entity.User;
import by.nareiko.fr.exception.ServiceException;
import by.nareiko.fr.service.ServiceFactory;
import by.nareiko.fr.service.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * The type Find all users command.
 */
public class FindAllUsersCommand implements Command {
    private static final Logger LOGGER = LogManager.getLogger();

    @Override
    public Router execute(HttpServletRequest request) {
        Router router = new Router();
        UserService userService = ServiceFactory.getInstance().getUserService();
        try {
            List<User> users = userService.findAllUsers();
            request.getSession().setAttribute(RequestParameterName.USER_LIST_PARAM, users);
            router.setPage(PagePath.SHOW_USERS);
        } catch (ServiceException e) {
            LOGGER.error("Error while users searching: ", e);
            router.setPage(PagePath.ERROR_500);
        }
        return router;
    }
}
