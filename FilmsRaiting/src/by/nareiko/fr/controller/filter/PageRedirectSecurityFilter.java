package by.nareiko.fr.controller.filter;

import by.nareiko.fr.controller.JspParameterName;
import by.nareiko.fr.controller.command.PagePath;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter(urlPatterns = {"/jsp/*"},
        initParams = {@WebInitParam(name = JspParameterName.INDEX_PATH, value = "/" + PagePath.INDEX)})
public class PageRedirectSecurityFilter implements Filter {
    private String indexPath;

    public void init(FilterConfig fConfig) throws ServletException {
        indexPath = fConfig.getInitParameter(JspParameterName.INDEX_PATH);
    }

    public void doFilter(ServletRequest request, ServletResponse response,
                         FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        httpResponse.sendRedirect(httpRequest.getContextPath() + indexPath);
        chain.doFilter(request, response);
    }

    public void destroy() {
    }
}
