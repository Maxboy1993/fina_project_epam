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
import javax.servlet.http.HttpSession;
import java.util.Optional;
import java.util.Set;

/**
 * The type Sign in command.
 */
public class SignInCommand implements Command {
    private static final Logger LOGGER = LogManager.getLogger();

    @Override
    public Router execute(HttpServletRequest request) {
        Router router = new Router();
        String loginValue = request.getParameter(RequestParameterName.LOGIN_PARAM);
        String passwordValue = request.getParameter(RequestParameterName.PASSWORD_PARAM);
        ServiceFactory serviceFactory = ServiceFactory.getInstance();
        UserService userService = serviceFactory.getUserService();
        Optional<User> user;
        try {
            HttpSession session = request.getSession();
            if (userService.checkUserByLoginAdPassword(loginValue, passwordValue)) {
                user = userService.findByLoginAndPassword(loginValue, passwordValue);
                if (user.isPresent()) {
                    if (user.get().isVerified()) {
                        session.setAttribute(RequestParameterName.USER_PARAM, user.get());
                        session.setAttribute(RequestParameterName.USER_ROLE_PARAM, user.get().getRoleType());
                        request.getSession().removeAttribute(RequestParameterName.VERIFICATION_PARAM);
                        router.setPage(PagePath.MAIN);
                    } else {
                        request.setAttribute(RequestParameterName.VERIFICATION_PARAM, RequestParameterName.VERIFICATION_VALUE);
                        router.setPage(PagePath.SIGN_IN);
                    }
                } else {
                    request.setAttribute(RequestParameterName.NO_LOGIN_OR_PASSWORD_PARAM, RequestParameterName.NO_LOGIN_OR_PASSWORD_VALUE);
                    router.setPage(PagePath.SIGN_IN);
                }
            } else {
                Set<String> userValidationErrors = userService.getUserErrorMessages();
                request.setAttribute(RequestParameterName.USER_VALIDATION_ERRORS_PARAM, userValidationErrors);
                router.setPage(PagePath.SIGN_IN);
            }
        } catch (ServiceException e) {
            LOGGER.error("Error while loging in user: ", e);
            router.setPage(PagePath.ERROR_500);
        }
        return router;
    }
}
