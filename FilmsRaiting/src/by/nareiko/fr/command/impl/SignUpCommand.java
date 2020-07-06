package by.nareiko.fr.command.impl;

import by.nareiko.fr.command.Command;
import by.nareiko.fr.command.PagePath;
import by.nareiko.fr.entity.User;
import by.nareiko.fr.factory.ServiceFactory;
import by.nareiko.fr.model.service.impl.UserServiceImpl;

import javax.servlet.http.HttpServletRequest;

public class SignUpCommand implements Command {
    private static final String PARAM_FIRST_NAME = "firstName";
    private static final String PARAM_LAST_NAME = "lastName";
    private static final String PARAM_LOGIN = "login";
    private static final String PARAM_PASSWORD = "password";
    private static final String PARAM_DAY = "day";
    private static final String PARAM_MONTH = "month";
    private static final String PARAM_YEAR = "year";

    public SignUpCommand() {
    }


    @Override
    public String execute(HttpServletRequest request) {
        String page;
        String firstNameValue = request.getParameter(PARAM_FIRST_NAME);
        String lastNameValue = request.getParameter(PARAM_LAST_NAME);
        String loginValue = request.getParameter(PARAM_LOGIN);
        String passwordValue = request.getParameter(PARAM_PASSWORD);
        String dayValue = request.getParameter(PARAM_DAY);
        String monthValue = request.getParameter(PARAM_MONTH);
        String yearValue = request.getParameter(PARAM_YEAR);
        String[] birthday = new String[3];
        birthday[0]  = yearValue;
        birthday[1]  = monthValue;
        birthday[2]  = dayValue;

        // должна быть двойная валидация html5 + java - добавить в валидатор

        ServiceFactory serviceFactory = ServiceFactory.getInstance();
        UserServiceImpl userService = serviceFactory.getUserService();

        if (userService.checkUserRegistrationData(firstNameValue, lastNameValue, loginValue, passwordValue, birthday)) {
            User user = userService.registrateUser(firstNameValue, lastNameValue, loginValue, passwordValue, birthday);
            request.setAttribute("user", user.getFirstName() +  "seccussful registrated");
            page = PagePath.MAIN;
        } else {
// request.setAttribute("errorLoginPassMessage", ); передаем объекты по ключу в jsp
            //error message ?????
            page = PagePath.SIGN_UP;
        }
        return page;
    }
}
