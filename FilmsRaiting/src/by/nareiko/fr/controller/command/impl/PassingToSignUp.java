package by.nareiko.fr.controller.command.impl;

import by.nareiko.fr.controller.Router;
import by.nareiko.fr.controller.command.Command;
import by.nareiko.fr.controller.command.PagePath;
import by.nareiko.fr.exception.ControllerException;

import javax.servlet.http.HttpServletRequest;

public class PassingToSignUp implements Command {
    @Override
    public Router execute(HttpServletRequest request){
        Router router = new Router();
        router.setPage(PagePath.SIGN_UP);
        return router;
    }
}
