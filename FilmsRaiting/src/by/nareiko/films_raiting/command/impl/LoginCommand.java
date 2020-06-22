package by.nareiko.films_raiting.command.impl;

import by.nareiko.films_raiting.command.Command;
import by.nareiko.films_raiting.command.PagePath;
import by.nareiko.films_raiting.entity.User;
import by.nareiko.films_raiting.factory.ServiceFactory;
import by.nareiko.films_raiting.model.service.impl.UserServiceImpl;

import javax.servlet.http.HttpServletRequest;

public class LoginCommand implements Command {
    private static final String PARAM_LOGIN = "login";
    private static final String PARAM_PASSWORD = "password";

    public LoginCommand() {
    }

    @Override
    public String execute(HttpServletRequest request) {
        String page;
        String loginValue = request.getParameter(PARAM_LOGIN);
        String passwordValue = request.getParameter(PARAM_PASSWORD);
        ServiceFactory serviceFactory = ServiceFactory.getInstance();
        UserServiceImpl userService = serviceFactory.getUserService();

        // должна быть двойная валидация html5 + java - добавить в валидатор
        if (userService.checkUser(loginValue, passwordValue)) {
            User user = userService.findByLoginAndPassword(loginValue, passwordValue);
            request.setAttribute("user", user.getFirstName());
            page = PagePath.MAIN;
        } else {
            request.setAttribute("errorLoginPassMessage", "Wrong login or password");
            //error message ?????
            page = PagePath.LOGIN;
        }
        return page;
    }
}
