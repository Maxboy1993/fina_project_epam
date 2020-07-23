package by.nareiko.fr.controller.command.impl;

import by.nareiko.fr.controller.Router;
import by.nareiko.fr.controller.command.Command;
import by.nareiko.fr.controller.command.PagePath;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Enumeration;

public class LogOutCommand implements Command {
    @Override
    public Router execute(HttpServletRequest request) {
        Router router = new Router();
        router.setPage(PagePath.SIGN_IN);
        HttpSession session = request.getSession(false);
        if (session != null) {
            Enumeration<String> parameterNames = request.getParameterNames();
            while (parameterNames.hasMoreElements()){
                String parameterName = parameterNames.nextElement();
                session.removeAttribute(parameterName);
            }
            session.invalidate();
        }
        return router;
    }
}

