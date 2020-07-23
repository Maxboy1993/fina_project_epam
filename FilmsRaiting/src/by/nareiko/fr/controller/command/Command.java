package by.nareiko.fr.controller.command;

import by.nareiko.fr.controller.Router;
import by.nareiko.fr.exception.ControllerException;

import javax.servlet.http.HttpServletRequest;

public interface Command {
    Router execute(HttpServletRequest request);
}
