package test_example.servlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Enumeration;

public class AllParamServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        proccessRequest(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        proccessRequest(req, resp);
    }

    private void proccessRequest(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("text/html;charset=UTF-8");

        PrintWriter writer = resp.getWriter();
        Enumeration<String> allParam = req.getParameterNames();
        try {
            writer.println("<html>");
            writer.println("<head>");
            writer.println("<title>All parameters Servlet</title>");
            writer.println("</head>");
            writer.println("<body>");
            writer.println("<h1>All parameters Servlet:</h1>");
            writer.println("<h2>All parameters Servlet:</h2>");
            int i = 1;
            while (allParam.hasMoreElements()) {
                writer.println("<p>");
                String paramName = allParam.nextElement();
                String value = req.getParameter(paramName);
                writer.println("param " + i + " = " + paramName + ", value = " + value);
                writer.println("</p>");
                i++;

            }
            writer.println("</body>");
            writer.println("</html>");
        } finally {
            writer.close();
        }
    }
}
