package by.nareiko.fr.controller.filter;

import by.nareiko.fr.controller.RequestParameterName;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebFilter("*.jsp")
public class SessionLocaleFilter implements Filter {

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpSession session = req.getSession(true);
        String languageLocale = req.getParameter(RequestParameterName.LANGUAGE_PARAM);
        if (languageLocale != null) {
            session.setAttribute(RequestParameterName.LANGUAGE_PARAM, languageLocale);
        } else {
            session.setAttribute(RequestParameterName.LANGUAGE_PARAM, RequestParameterName.ENGLISH_PARAM);
        }
        chain.doFilter(request, response);
    }

    public void destroy() {
    }

    public void init(FilterConfig arg0) throws ServletException {
    }
}