package by.nareiko.fr.controller.command.impl;

import by.nareiko.fr.controller.JspParameterName;
import by.nareiko.fr.controller.Router;
import by.nareiko.fr.controller.command.Command;
import by.nareiko.fr.controller.command.PagePath;

import javax.servlet.http.HttpServletRequest;

public class ChangeLanguageCommand implements Command {

    @Override
    public Router execute(HttpServletRequest request) {
        String targetLanguage = request.getParameter(JspParameterName.LANGUAGE_PARAM);
        if (targetLanguage != null) {
            request.getSession().setAttribute(JspParameterName.LANGUAGE_PARAM, targetLanguage);
        }
        Router router = new Router();
        router.setPage(PagePath.SIGN_IN);
        return router;
    }
}
