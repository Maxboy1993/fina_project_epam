package by.nareiko.fr.controller.command.impl;

import by.nareiko.fr.controller.command.Command;
import by.nareiko.fr.controller.command.PagePath;
import by.nareiko.fr.entity.User;
import by.nareiko.fr.exception.ControllerException;
import by.nareiko.fr.exception.ServiceException;
import by.nareiko.fr.service.ServiceFactory;
import by.nareiko.fr.service.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Optional;

public class SignInCommand implements Command {
    private static final String PARAM_LOGIN = "login";
    private static final String PARAM_PASSWORD = "password";
    private static final Logger LOGGER = LogManager.getLogger();

    public SignInCommand() {
    }

    @Override
    public String execute(HttpServletRequest request) throws ControllerException {
        String page = "";
        String loginValue = request.getParameter(PARAM_LOGIN);
        String passwordValue = request.getParameter(PARAM_PASSWORD);
        ServiceFactory serviceFactory = ServiceFactory.getInstance();
        UserService userService = serviceFactory.getUserService();
        Optional<User> user;
        //TODO
        // должна быть двойная валидация html5 + java - добавить в валидатор
        try {
            HttpSession session = request.getSession();
            if (userService.checkUser(loginValue, passwordValue)) {
                user = userService.findByLoginAndPassword(loginValue, passwordValue);
                if (user.isPresent()){
                    session.setAttribute("user", user.get());
                    session.setAttribute("userRole", user.get().getRoleType());
                    page = PagePath.MAIN;
                }else {
                    request.setAttribute("errorLoginOrPasswordSearchingMessage", "Wrong login or password!");
                    page = PagePath.SIGN_IN;
                }
            }else {
                //TODO strings to constants in out class
                request.setAttribute("errorLoginOrPasswordValidationMessage", "Wrong login or password format!");
                page = PagePath.SIGN_IN;
                }
        } catch (ServiceException e) {
            LOGGER.error("Error while loging in user: ", e);
            throw new ControllerException("Error while loging in user: ", e);
        }
        return page;
    }
}
