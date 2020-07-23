package by.nareiko.fr.controller.command.impl;

import by.nareiko.fr.controller.JspParameterName;
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

public class SignUpCommand implements Command {
    private static final Logger LOGGER = LogManager.getLogger();

    public SignUpCommand() {
    }

    @Override
    public Router execute(HttpServletRequest request) {
        String firstNameValue = request.getParameter(JspParameterName.FIRST_NAME_PARAM);
        String lastNameValue = request.getParameter(JspParameterName.LAST_NAME_PARAM);
        String loginValue = request.getParameter(JspParameterName.LOGIN_PARAM);
        String passwordValue = request.getParameter(JspParameterName.PASSWORD_PARAM);
        String birthday = request.getParameter(JspParameterName.BIRTHDAY_PARAM);

        Router router = new Router();
        router.setRedirect();
        try {
            UserService userService = ServiceFactory.getInstance().getUserService();
            if (userService.checkUserRegistrationData(firstNameValue, lastNameValue, loginValue, passwordValue, birthday)) {
                Optional<User> user = userService.registrateUser(firstNameValue, lastNameValue, loginValue, passwordValue, birthday);
                request.getSession().setAttribute(JspParameterName.USER_PARAM, user.get());
                request.getSession().setAttribute(JspParameterName.USER_ROLE_PARAM, user.get().getRoleType());
                router.setPage(PagePath.SIGN_IN);
                MailSender sender = new MailSender(loginValue);
                sender.send();
                request.setAttribute(JspParameterName.VERIFICATION_PARAM, "Dear " + user.get().getFirstName() + " ! " + "Letter was sent on your email adress. You can confirm your email by link.");
            } else {
                //TODO add sort of error message
                request.setAttribute(JspParameterName.ERROR_REGISTRATION_PARAM, "Incorrect registration data");
                router.setPage(PagePath.SIGN_UP);
            }
        } catch (ServiceException e) {
            LOGGER.error("Registration error", e);
            //TODO error page
        }
        return router;
    }
}
