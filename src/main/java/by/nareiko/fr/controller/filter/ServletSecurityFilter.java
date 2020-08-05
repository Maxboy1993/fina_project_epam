package by.nareiko.fr.controller.filter;

import by.nareiko.fr.controller.RequestParameterName;
import by.nareiko.fr.controller.Router;
import by.nareiko.fr.controller.command.CommandType;
import by.nareiko.fr.controller.command.PagePath;
import by.nareiko.fr.entity.RoleType;
import by.nareiko.fr.entity.StatusType;
import by.nareiko.fr.entity.User;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

@WebFilter(urlPatterns = {"/controller"})
public class ServletSecurityFilter implements Filter {
    Set<String> guestCommandNames;
    Set<String> userCommandNames;
    private Set<String> adminCommandNames;

    public void destroy() {
    }

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpSession session = req.getSession();
        RoleType roleType = (RoleType) session.getAttribute(RequestParameterName.USER_ROLE_PARAM);
        User user = (User) session.getAttribute(RequestParameterName.USER_PARAM);
        if (roleType == null || user == null || user.getStatusType().equals(StatusType.INACTIVE)) {
            roleType = RoleType.GUEST;
            session.setAttribute(RequestParameterName.USER_ROLE_PARAM, roleType);
        }
        String commandName = req.getParameter(RequestParameterName.COMMAND_PARAM).toUpperCase();
        Router router = new Router();
        if (RoleType.GUEST.equals(roleType)) {
            //TODO checking - move to method
            if (!guestCommandNames.contains(commandName)) {
                req.setAttribute(RequestParameterName.NO_RIGHT_FOR_COMMAND_PARAM, RequestParameterName.WRONG_COMMAND_VALUE);
                router.setPage(PagePath.SIGN_IN);
                request.getRequestDispatcher(router.getPage()).forward(request, response);
            }
        }
        if (RoleType.USER.equals(roleType)) {
            if (!userCommandNames.contains(commandName)) {
                req.setAttribute(RequestParameterName.NO_RIGHT_FOR_COMMAND_PARAM, RequestParameterName.WRONG_COMMAND_VALUE);
                router.setPage(PagePath.SIGN_IN);
                request.getRequestDispatcher(router.getPage()).forward(request, response);
            }
        }

        if (RoleType.ADMIN.equals(roleType)) {
            if (!adminCommandNames.contains(commandName)) {
                req.setAttribute(RequestParameterName.NO_RIGHT_FOR_COMMAND_PARAM, RequestParameterName.WRONG_COMMAND_VALUE);
                router.setPage(PagePath.SIGN_IN);
                request.getRequestDispatcher(router.getPage()).forward(request, response);
            }
        }
        chain.doFilter(request, response);
    }

    public void init(FilterConfig fConfig) throws ServletException {
        CommandType[] commandTypes = CommandType.values();
        adminCommandNames = new HashSet<>();
        for (int i = 0; i < commandTypes.length; i++) {
            adminCommandNames.add(commandTypes[i].getCommandNameValue());
        }

        guestCommandNames = new HashSet<>();
        guestCommandNames.add("SIGN_IN");
        guestCommandNames.add("SIGN_UP");
        guestCommandNames.add("CHANGE_LANGUAGE");
        guestCommandNames.add("PASSING_TO_SIGN_UP");
        guestCommandNames.add("PASSING_TO_SIGN_IN");
        guestCommandNames.add("DEFAULT_COMMAND");
        guestCommandNames.add("FIND_ALL_FILMS");

        userCommandNames = new HashSet<>();
        userCommandNames.add("SIGN_IN");
        userCommandNames.add("SIGN_UP");
        userCommandNames.add("CHANGE_LANGUAGE");
        userCommandNames.add("PASSING_TO_SIGN_UP");
        userCommandNames.add("PASSING_TO_SIGN_IN");
        userCommandNames.add("DEFAULT_COMMAND");
        userCommandNames.add("FIND_ALL_FILMS");
        userCommandNames.add("LOG_OUT");
        userCommandNames.add("CONFIRM_EMAIL");
        userCommandNames.add("PASSING_TO_MAIN");
        userCommandNames.add("FIND_FILM_BY_NAME");
        userCommandNames.add("SHOW_FILM_INFO");
        userCommandNames.add("COUNT_FILM_RAITING");
        userCommandNames.add("ADD_REVIEW");
        userCommandNames.add("PASSING_TO_FILM_INFO");
        userCommandNames.add("PASSING_TO_USER_PROFILE");
    }
}
