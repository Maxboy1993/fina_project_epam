package by.nareiko.fr.controller.command.impl;

import by.nareiko.fr.controller.RequestParameterName;
import by.nareiko.fr.controller.Router;
import by.nareiko.fr.controller.command.Command;
import by.nareiko.fr.controller.command.PagePath;

import javax.servlet.http.HttpServletRequest;

/**
 * The type Default command.
 */
public class DefaultCommand implements Command {
    @Override
    public Router execute(HttpServletRequest request) {
        Router router = new Router();
        request.setAttribute(RequestParameterName.WRONG_COMMAND_PARAM, RequestParameterName.NOT_EXIST_COMMAND_VALUE);
        router.setPage(PagePath.SIGN_IN);
        return router;
    }
}
