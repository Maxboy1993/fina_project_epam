package by.nareiko.fr.controller;

import by.nareiko.fr.controller.command.Command;
import by.nareiko.fr.controller.command.CommandProvider;
import by.nareiko.fr.pool.ConnectionPool;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

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

    @Override
    public void destroy() {
        ConnectionPool.getInstance().destroyPool();
    }

    private void processServlet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Optional<Command> optionalCommand = CommandProvider.defineCommand(request.getParameter("command")); // сделать как константу
        Command command = optionalCommand.orElseThrow(IllegalArgumentException::new); // создать свое исключение CommandNotFoundException

        String page = command.execute(request);

        RequestDispatcher dispatcher = request.getRequestDispatcher(page);
        dispatcher.forward(request, response);
    }
}