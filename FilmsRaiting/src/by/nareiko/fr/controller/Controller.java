package by.nareiko.fr.controller;

import by.nareiko.fr.controller.command.Command;
import by.nareiko.fr.controller.command.CommandProvider;
import by.nareiko.fr.pool.ConnectionPool;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/controller")
public class Controller extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processServlet(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processServlet(request, response);
    }

    @Override
    public void init() throws ServletException {
        ConnectionPool.getInstance();
    }

    private void processServlet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Command command = CommandProvider.defineCommand(request.getParameter(RequestParameterName.COMMAND_PARAM));
        Router router = command.execute(request);

        if (Router.DisPathType.FORWARD.equals(router.getDisPathType())) {
            request.getRequestDispatcher(router.getPage()).forward(request, response);
        } else {
            response.sendRedirect(router.getPage());
        }
    }

    @Override
    public void destroy() {
        ConnectionPool.getInstance().destroyPool();
    }
}
