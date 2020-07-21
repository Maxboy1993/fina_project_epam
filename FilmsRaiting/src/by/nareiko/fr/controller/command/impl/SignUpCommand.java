package by.nareiko.fr.controller.command.impl;

import by.nareiko.fr.controller.command.Command;
import by.nareiko.fr.controller.command.PagePath;
import by.nareiko.fr.entity.User;
import by.nareiko.fr.exception.ServiceException;
import by.nareiko.fr.service.ServiceFactory;
import by.nareiko.fr.service.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

public class SignUpCommand implements Command {
    private static final String PARAM_FIRST_NAME = "firstName";
    private static final String PARAM_LAST_NAME = "lastName";
    private static final String PARAM_LOGIN = "login";
    private static final String PARAM_PASSWORD = "password";
    private static final String PARAM_DAY = "day";
    private static final String PARAM_MONTH = "month";
    private static final String PARAM_YEAR = "year";
    private static final Logger LOGGER = LogManager.getLogger();

    public SignUpCommand() {
    }


    @Override
    public String execute(HttpServletRequest request) {
        String page = PagePath.SIGN_UP;
        String firstNameValue = request.getParameter(PARAM_FIRST_NAME);
        String lastNameValue = request.getParameter(PARAM_LAST_NAME);
        String loginValue = request.getParameter(PARAM_LOGIN);
        String passwordValue = request.getParameter(PARAM_PASSWORD);
        //TODO FIX
        String dayValue = request.getParameter(PARAM_DAY);
        String monthValue = request.getParameter(PARAM_MONTH);
        String yearValue = request.getParameter(PARAM_YEAR);
        String[] birthday = new String[3];
        birthday[0] = yearValue;
        birthday[1] = monthValue;
        birthday[2] = dayValue;

        // должна быть двойная валидация html5 + java - добавить в валидатор

        UserService userService = ServiceFactory.getInstance().getUserService();

        try {
            if (userService.checkUserRegistrationData(firstNameValue, lastNameValue, loginValue, passwordValue, birthday)) {
                Optional<User> user = userService.registrateUser(firstNameValue, lastNameValue, loginValue, passwordValue, birthday);
                request.setAttribute("user", user.get().getFirstName() + "successful registered");
                page = PagePath.MAIN;
            } else {
                request.setAttribute("errorRegistrationPassMessage", "Incorrect registration data");
            }
        } catch (ServiceException e) {
            LOGGER.error("Registration error", e);
        }
        return page;
    }
}
