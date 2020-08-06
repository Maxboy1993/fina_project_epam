package by.nareiko.fr.controller.command.impl;

import by.nareiko.fr.controller.Router;
import by.nareiko.fr.controller.command.Command;
import by.nareiko.fr.controller.command.PagePath;

import javax.servlet.http.HttpServletRequest;

/**
 * The type Passing to sign up command.
 */
public class PassingToSignUpCommand implements Command {
    @Override
    public Router execute(HttpServletRequest request) {
        Router router = new Router();
        router.setPage(PagePath.SIGN_UP);
        return router;
    }
}
