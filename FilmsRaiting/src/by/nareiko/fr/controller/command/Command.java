package by.nareiko.fr.controller.command;

import by.nareiko.fr.controller.Router;

import javax.servlet.http.HttpServletRequest;

/**
 * The interface Command.
 */
public interface Command {
    /**
     * Execute router.
     *
     * @param request the request
     * @return the router
     */
    Router execute(HttpServletRequest request);
}
