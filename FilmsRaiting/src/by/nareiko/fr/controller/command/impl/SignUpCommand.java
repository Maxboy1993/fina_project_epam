package by.nareiko.fr.controller.command.impl;

import by.nareiko.fr.controller.RequestParameterName;
import by.nareiko.fr.controller.Router;
import by.nareiko.fr.controller.command.Command;
import by.nareiko.fr.controller.command.PagePath;
import by.nareiko.fr.entity.User;
import by.nareiko.fr.exception.ServiceException;
import by.nareiko.fr.service.ServiceFactory;
import by.nareiko.fr.service.UserService;
import by.nareiko.fr.util.MailSender;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;
import java.util.Set;

/**
 * The type Sign up command.
 */
public class SignUpCommand implements Command {
    private static final String COMMAND = "controller?command=PASSING_TO_SIGN_IN";
    private static final Logger LOGGER = LogManager.getLogger();

    /**
     * Instantiates a new Sign up command.
     */
    public SignUpCommand() {
    }

    @Override
    public Router execute(HttpServletRequest request) {
        String firstNameValue = request.getParameter(RequestParameterName.FIRST_NAME_PARAM);
        String lastNameValue = request.getParameter(RequestParameterName.LAST_NAME_PARAM);
        String loginValue = request.getParameter(RequestParameterName.LOGIN_PARAM);
        String passwordValue = request.getParameter(RequestParameterName.PASSWORD_PARAM);
        String birthday = request.getParameter(RequestParameterName.BIRTHDAY_PARAM);

        Router router = new Router();
        try {
            UserService userService = ServiceFactory.getInstance().getUserService();
            if (userService.checkUserRegistrationData(firstNameValue, lastNameValue, loginValue, passwordValue, birthday)) {
                Optional<User> user = userService.registrateUser(firstNameValue, lastNameValue, loginValue, passwordValue, birthday);
                request.getSession().setAttribute(RequestParameterName.USER_PARAM, user.get());
                request.getSession().setAttribute(RequestParameterName.USER_ROLE_PARAM, user.get().getRoleType());
                MailSender sender = new MailSender(loginValue);
                sender.send();
                request.setAttribute(RequestParameterName.VERIFICATION_PARAM, RequestParameterName.VERIFICATION_CONFIRMING_VALUE);
                router.setRedirect();
                router.setPage(COMMAND);
            } else {
                Set<String> userValidationErrors = userService.getUserErrorMessages();
                request.setAttribute(RequestParameterName.USER_VALIDATION_ERRORS_PARAM, userValidationErrors);
                router.setPage(PagePath.SIGN_UP);
            }
        } catch (ServiceException e) {
            LOGGER.error("Registration error", e);
            router.setPage(PagePath.ERROR_500);
        }
        return router;
    }
}
