package by.nareiko.fr.controller.filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebFilter("*.jsp")
public class SessionLocaleFilter implements Filter {
    private static final String ENGLISH_LOCALE = "en";
    private static final String LANGUAGE_PARAM = "language";

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpSession session = req.getSession(true);
        //TODO get instead set and add else block
        String languageLocale = req.getParameter(LANGUAGE_PARAM);
        if (languageLocale != null) {
            session.setAttribute(LANGUAGE_PARAM, languageLocale);
        } else {
            session.setAttribute(LANGUAGE_PARAM, ENGLISH_LOCALE);
        }
        chain.doFilter(request, response);
    }

    public void destroy() {
    }

    public void init(FilterConfig arg0) throws ServletException {
    }
}