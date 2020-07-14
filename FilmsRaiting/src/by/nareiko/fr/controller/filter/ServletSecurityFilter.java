package by.nareiko.fr.controller.filter;

import by.nareiko.fr.controller.command.PagePath;
import by.nareiko.fr.entity.RoleType;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebFilter(urlPatterns = { "/controller" }, servletNames = { "Controller" })
public class ServletSecurityFilter implements Filter {
    public void destroy() {
    }

    public void doFilter(ServletRequest request, ServletResponse response,
                         FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;
        HttpSession session = req.getSession();
        RoleType roleType = (RoleType) session.getAttribute("userType");
        if (roleType == null) {
            roleType = RoleType.GUEST;
            session.setAttribute("userType", roleType);
            RequestDispatcher dispatcher = request.getServletContext()
                    .getRequestDispatcher(PagePath.SIGN_IN);
            dispatcher.forward(req, resp);
            return;
        }
        chain.doFilter(request, response);
    }

    public void init(FilterConfig fConfig) throws ServletException {
    }
}
