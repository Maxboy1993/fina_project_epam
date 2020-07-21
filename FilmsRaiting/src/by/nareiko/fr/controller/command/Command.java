package by.nareiko.fr.controller.command;

import by.nareiko.fr.exception.ControllerException;

import javax.servlet.http.HttpServletRequest;

public interface Command {
    String execute(HttpServletRequest request) throws ControllerException;
}
