package by.nareiko.fr.controller.filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebFilter("/*")
public class SessionLocaleFilter implements Filter {
    private static final String ENGLISH_LOCALE = "en";
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpSession session = req.getSession(true);
        //TODO get instead set and add else block
        String languageLocale = req.getParameter("lang");
        if (languageLocale != null) {
            session.setAttribute("lang", request.getParameter("lang"));
        }else{
            session.setAttribute("lang", ENGLISH_LOCALE);
        }
        chain.doFilter(request, response);
    }

    public void destroy() {
    }

    public void init(FilterConfig arg0) throws ServletException {
    }
}