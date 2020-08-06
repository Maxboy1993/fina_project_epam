package by.nareiko.fr.controller.command.impl;

import by.nareiko.fr.controller.RequestParameterName;
import by.nareiko.fr.controller.Router;
import by.nareiko.fr.controller.command.Command;
import by.nareiko.fr.controller.command.PagePath;

import javax.servlet.http.HttpServletRequest;

/**
 * The type Change language command.
 */
public class ChangeLanguageCommand implements Command {

    @Override
    public Router execute(HttpServletRequest request) {
        String targetLanguage = request.getParameter(RequestParameterName.LANGUAGE_PARAM);
        if (targetLanguage != null) {
            request.getSession().setAttribute(RequestParameterName.LANGUAGE_PARAM, targetLanguage);
        }
        Router router = new Router();
        router.setPage(PagePath.SIGN_IN);
        return router;
    }
}
