package by.nareiko.fr.controller.command.impl;

import by.nareiko.fr.controller.JspParameterName;
import by.nareiko.fr.controller.Router;
import by.nareiko.fr.controller.command.Command;
import by.nareiko.fr.controller.command.PagePath;
import by.nareiko.fr.exception.ServiceException;
import by.nareiko.fr.service.ServiceFactory;
import by.nareiko.fr.service.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;

public class ConfirmEmailCommand implements Command {
    private static final Logger LOGGER = LogManager.getLogger();
    private UserService userService = ServiceFactory.getInstance().getUserService();

    @Override
    public Router execute(HttpServletRequest request) {
        Router router = new Router();
        String login = request.getParameter(JspParameterName.LOGIN_PARAM);
        try {
            userService.activateUser(login);
            router.setPage(PagePath.SIGN_IN);
        }catch (ServiceException e){
            LOGGER.error("Error while user verification", e);
            //TODO change on error page
            router.setPage(PagePath.SIGN_IN);
        }
        return router;
    }
}
