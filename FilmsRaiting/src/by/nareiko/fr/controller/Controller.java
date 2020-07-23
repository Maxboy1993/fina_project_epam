package by.nareiko.fr.controller;

import by.nareiko.fr.controller.command.Command;
import by.nareiko.fr.controller.command.CommandProvider;
import by.nareiko.fr.controller.command.PagePath;
import by.nareiko.fr.pool.ConnectionPool;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/controller")
public class Controller extends HttpServlet {
//    private static final Logger LOGGER = LogManager.getLogger();
//    private static final String DEFAULT_COMMAND_NAME = "DEFAULT_COMMAND";

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
        Command command = CommandProvider.defineCommand(request.getParameter("command"));
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
