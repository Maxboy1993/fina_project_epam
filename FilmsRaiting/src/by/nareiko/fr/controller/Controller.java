package by.nareiko.fr.controller;

import by.nareiko.fr.controller.command.Command;
import by.nareiko.fr.controller.command.CommandProvider;
import by.nareiko.fr.controller.command.CommandType;
import by.nareiko.fr.controller.command.PagePath;
import by.nareiko.fr.exception.ControllerException;
import by.nareiko.fr.pool.ConnectionPool;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@WebServlet("/controller")
public class Controller extends HttpServlet {
    private static final Logger LOGGER = LogManager.getLogger();
    private static final String DEFAULT_COMMAND_NAME = "DEFAULT_COMMAND";

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

    @Override
    public void destroy() {
        ConnectionPool.getInstance().destroyPool();
    }

    private void processServlet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Optional<Command> optionalCommand = CommandProvider.defineCommand(request.getParameter("command")); // сделать как константу
        //TODO dedault command
        Command command;
        String page;
        try {
            command = optionalCommand.orElseThrow(ControllerException::new);
            page = command.execute(request);
        } catch (ControllerException e) {
            //TODO replace on error pagr?
            page = PagePath.SIGN_IN;
            request.setAttribute("wrongCommandNameMessege", "Wrong command name was requested");
            LOGGER.error("Error while command executing: ", e);
        }



        RequestDispatcher dispatcher = request.getRequestDispatcher(page);
        dispatcher.forward(request, response);
    }
}
