package command.impl;

import command.Command;
import command.PagePath;

import javax.servlet.http.HttpServletRequest;

import static command.PagePath.LOGIN;
import static command.PagePath.MAIN;

public class LoginCommand implements Command {
    private static final String PARAM_LOGIN = "login";
    private static final String PARAM_PASSWORD = "password";

    //private UserServiceIml service;

    public LoginCommand(/*UserServiceIml service*/){
        // this.service = service;
    }

    @Override
    public String execute(HttpServletRequest request) {
        String page;
        String loginValue = request.getParameter(PARAM_LOGIN);
        String passwordValue = request.getParameter(PARAM_PASSWORD);
        // валидатор вынести в отдельный класс и вызывать из сервиса
        // должна быть двойная валидация html5 + java
        if (loginValue != null && !loginValue.isBlank() && passwordValue != null && !passwordValue.isBlank()) {
            if(true /*service.checkUser(loginValue, passwordValue*/){
            request.setAttribute("user", loginValue);
            page = MAIN;
        }else {
                //error message
                page = LOGIN;
        }
        }else {
            //error message
            page = LOGIN;
        }
        return page;
    }
}
