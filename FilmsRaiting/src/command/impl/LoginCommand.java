package command.impl;

import command.Command;
import command.PagePath;
import entity.User;
import model.service.impl.UserServiceImpl;

import javax.servlet.http.HttpServletRequest;

import static command.PagePath.LOGIN;
import static command.PagePath.MAIN;

public class LoginCommand implements Command {
    private static final String PARAM_LOGIN = "login";
    private static final String PARAM_PASSWORD = "password";

    private UserServiceImpl service;

    public LoginCommand(UserServiceImpl service) {
        this.service = service;
    }

    @Override
    public String execute(HttpServletRequest request) {
        String page;
        String loginValue = request.getParameter(PARAM_LOGIN);
        String passwordValue = request.getParameter(PARAM_PASSWORD);

        // должна быть двойная валидация html5 + java - добавить в валидатор

        if (service.checkUser(loginValue, passwordValue)) {

            User user = service.findByLoginAndPassword(loginValue, passwordValue);
            request.setAttribute("user", user.getName());
            page = PagePath.MAIN;
        } else {
            //error message ?????
            page = PagePath.LOGIN;
        }
        return page;
    }
}
