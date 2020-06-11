package test_example.servlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class FirstPageServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PrintWriter writer = response.getWriter();

        writer.println("<html>");
        writer.println("<head>");
        writer.println("<title>First Servlet</title>");
        writer.println("</head>");
        writer.println("<body>");
        writer.println("<h1>Hello world First page!</h1>");
        writer.println("</body>");
        writer.println("</html>");








        writer.println();
    }
}
