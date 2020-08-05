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

public class ConfirmEmailCommand implements Command {
    private static final Logger LOGGER = LogManager.getLogger();

    @Override
    public Router execute(HttpServletRequest request) {
        Router router = new Router();
        String login = request.getParameter(RequestParameterName.LOGIN_PARAM);
        UserService userService = ServiceFactory.getInstance().getUserService();
        try {
            userService.activateUser(login);
            User user = (User) request.getSession().getAttribute(RequestParameterName.USER_PARAM);
            user.setVerified(true);
            router.setPage(PagePath.SIGN_IN);
        } catch (ServiceException e) {
            LOGGER.error("Error while user verification", e);
            router.setPage(PagePath.ERROR_500);
        }
        return router;
    }
}
