package by.nareiko.fr.controller.filter;

import by.nareiko.fr.controller.JspParameterName;
import by.nareiko.fr.controller.command.PagePath;
import by.nareiko.fr.entity.RoleType;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.*;

@WebFilter(urlPatterns = {"/Controller"})
public class ServletSecurityFilter implements Filter {
    private Map<RoleType, Set<String>> roleAndCommandMapping;

    public void destroy() {
    }

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;
        HttpSession session = req.getSession();
        RoleType roleType = (RoleType) session.getAttribute(JspParameterName.USER_ROLE_PARAM);
//        String commandName = req.getParameter(JspParameterName.COMMAND_PARAM);
        String page = PagePath.SIGN_IN;
        if (roleType == null) {
            roleType = RoleType.GUEST;
        }
        if (roleType == RoleType.GUEST){
            session.setAttribute(JspParameterName.USER_ROLE_PARAM, roleType);
            RequestDispatcher dispatcher = request.getServletContext().getRequestDispatcher(page);
            dispatcher.forward(req, resp);
        }

//        else if (!roleAndCommandMapping.get(roleType).contains(req.getParameter(commandName))) {
//            TODO edd error page
//            page = errorPage;
//            RequestDispatcher dispatcher = request.getServletContext().getRequestDispatcher(page);
//            dispatcher.forward(req, resp);
//            return;
//        }
        chain.doFilter(request, response);
    }

    public void init(FilterConfig fConfig) throws ServletException {
//        roleAndCommandMapping = new HashMap<>();
//        Set<String> adminCommandNames = new HashSet<>(Arrays.asList(new String[]{"SIGN_IN", "SIGN_UP", "DEFAULT_COMMAND", "FIND_ALL_FILMS"}));
//        Set<String> userCommandNames = new HashSet<>(Arrays.asList(new String[]{"SIGN_IN", "SIGN_UP", "DEFAULT_COMMAND", "FIND_ALL_FILMS"}));
//        Set<String> guestCommandNames = new HashSet<>(Arrays.asList(new String[]{"SIGN_IN", "SIGN_UP", "DEFAULT_COMMAND", "FIND_ALL_FILMS"}));
//        roleAndCommandMapping.put(RoleType.ADMIN, adminCommandNames);
//        roleAndCommandMapping.put(RoleType.USER, userCommandNames);
//        roleAndCommandMapping.put(RoleType.GUEST, guestCommandNames);
    }
}
