package by.nareiko.fr.controller.filter;

import by.nareiko.fr.controller.RequestParameterName;
import by.nareiko.fr.controller.Router;
import by.nareiko.fr.controller.command.Command;
import by.nareiko.fr.controller.command.CommandType;
import by.nareiko.fr.controller.command.PagePath;
import by.nareiko.fr.entity.RoleType;
import by.nareiko.fr.entity.StatusType;
import by.nareiko.fr.entity.User;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

/**
 * The type Security filter.
 */
@WebFilter(urlPatterns = {"/controller"})
public class SecurityFilter implements Filter {

    private Set<String> guestCommandNames;
    private Set<String> userCommandNames;
    private Set<String> adminCommandNames;

    public void destroy() {
    }

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpSession session = req.getSession();
        RoleType roleType = (RoleType) session.getAttribute(RequestParameterName.USER_ROLE_PARAM);
        User user = (User) session.getAttribute(RequestParameterName.USER_PARAM);
        if (isGuestOrInactiveUser(roleType, user)) {
            roleType = RoleType.GUEST;
            session.setAttribute(RequestParameterName.USER_ROLE_PARAM, roleType);
        }
        String commandName = req.getParameter(RequestParameterName.COMMAND_PARAM).toUpperCase();
        Router router = new Router();
        if (RoleType.GUEST.equals(roleType)) {
            if (isClientCannotExecuteCommand(guestCommandNames, commandName)) {
                setRequestAttributesAndForwardToNecessaryPage(req, router, request, response);
            }
        }
        if (RoleType.USER.equals(roleType)) {
            if (isClientCannotExecuteCommand(userCommandNames, commandName)) {
                setRequestAttributesAndForwardToNecessaryPage(req, router, request, response);
            }
        }

        if (RoleType.ADMIN.equals(roleType)) {
            if (isClientCannotExecuteCommand(adminCommandNames, commandName)) {
                setRequestAttributesAndForwardToNecessaryPage(req, router, request, response);
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

    private boolean isGuestOrInactiveUser(RoleType roleType, User user) {
        boolean result = roleType == null || user == null || user.getStatusType().equals(StatusType.INACTIVE);
        return result;
    }

    private boolean isClientCannotExecuteCommand(Set<String> availableCommands, String commandName) {
        boolean result = !availableCommands.contains(commandName);
        return result;
    }

    private void setRequestAttributesAndForwardToNecessaryPage(HttpServletRequest req, Router router, ServletRequest request, ServletResponse response) throws ServletException, IOException {
        req.setAttribute(RequestParameterName.NO_RIGHT_FOR_COMMAND_PARAM, RequestParameterName.WRONG_COMMAND_VALUE);
        router.setPage(PagePath.SIGN_IN);
        request.getRequestDispatcher(router.getPage()).forward(request, response);
    }
}
