package by.nareiko.fr.controller.command.impl;

import by.nareiko.fr.controller.command.Command;
import by.nareiko.fr.controller.command.PagePath;
import by.nareiko.fr.entity.User;
import by.nareiko.fr.service.ServiceFactory;
import by.nareiko.fr.service.UserService;

import javax.servlet.http.HttpServletRequest;

public class LoginCommand implements Command {
    private static final String PARAM_LOGIN = "login";
    private static final String PARAM_PASSWORD = "password";

    public LoginCommand() {
    }

    @Override
    public String execute(HttpServletRequest request) {
        String page = PagePath.LOGIN;
        String loginValue = request.getParameter(PARAM_LOGIN);
        String passwordValue = request.getParameter(PARAM_PASSWORD);
        ServiceFactory serviceFactory = ServiceFactory.getInstance();
        UserService userService = serviceFactory.getUserService();
        User user = null;
        // должна быть двойная валидация html5 + java - добавить в валидатор
        if (userService.checkUser(loginValue, passwordValue)) {
            user = userService.findByLoginAndPassword(loginValue, passwordValue);
            request.setAttribute("user", user.getFirstName());
            page = PagePath.MAIN;
        } else if (user == null) {
            request.setAttribute("errorLoginPassMessage", "Wrong login or password");
        }
        return page;
    }
}
