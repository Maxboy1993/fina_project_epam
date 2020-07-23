package by.nareiko.fr.controller.command.impl;

import by.nareiko.fr.controller.JspParameterName;
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

public class SignInCommand implements Command {
    private static final Logger LOGGER = LogManager.getLogger();

    public SignInCommand() {
    }

    @Override
    public Router execute(HttpServletRequest request) {
        Router router = new Router();
        String loginValue = request.getParameter(JspParameterName.LOGIN_PARAM);
        String passwordValue = request.getParameter(JspParameterName.PASSWORD_PARAM);
        ServiceFactory serviceFactory = ServiceFactory.getInstance();
        UserService userService = serviceFactory.getUserService();
        Optional<User> user;
        //TODO
        // должна быть двойная валидация html5 + java - добавить в валидатор
        try {
            HttpSession session = request.getSession();
            if (userService.checkUserByLoginAdPassword(loginValue, passwordValue)) {
                user = userService.findByLoginAndPassword(loginValue, passwordValue);
                if (user.isPresent()){
                    session.setAttribute(JspParameterName.USER_PARAM, user.get());
                    session.setAttribute(JspParameterName.USER_ROLE_PARAM, user.get().getRoleType());
                    router.setPage(PagePath.MAIN);
                }else {
                    request.setAttribute(JspParameterName.NO_LOGIN_OR_PASSWORD_PARAM, "Wrong login or password!");
                    router.setPage(PagePath.SIGN_IN);
                }
            }else {
                //TODO strings to constants in out class
                request.setAttribute(JspParameterName.VALIDATION_LOGIN_OR_PASSWORD_PARAM, "Wrong login or password format!");
                router.setPage(PagePath.SIGN_IN);
                }
        } catch (ServiceException e) {
            LOGGER.error("Error while loging in user: ", e);
            //TODO send error
//            throw new ControllerException("Error while loging in user: ", e);
        }
        return router;
    }

}
