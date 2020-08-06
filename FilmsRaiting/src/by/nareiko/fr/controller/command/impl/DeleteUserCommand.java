package by.nareiko.fr.controller.command.impl;

import by.nareiko.fr.controller.RequestParameterName;
import by.nareiko.fr.controller.Router;
import by.nareiko.fr.controller.command.Command;
import by.nareiko.fr.controller.command.PagePath;
import by.nareiko.fr.entity.User;
import by.nareiko.fr.exception.ServiceException;
import by.nareiko.fr.service.ServiceFactory;
import by.nareiko.fr.service.UserService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * The type Delete user command.
 */
public class DeleteUserCommand implements Command {
    private static final String COMMAND = "controller?command=PASSING_TO_SHOW_USERS";
    @Override
    public Router execute(HttpServletRequest request) {
        int userId = Integer.parseInt(request.getParameter(RequestParameterName.USER_ID_PARAM));
        Router router = new Router();
        UserService userService = ServiceFactory.getInstance().getUserService();
        try {
            userService.deleteUser(userId);
            List<User> users = (List<User>) request.getSession().getAttribute(RequestParameterName.USER_LIST_PARAM);
            User user = null;
            for (User person:users) {
                if (person.getId() == userId){
                    user = person;
                }
            }
            users.remove(user);
            router.setRedirect();
            router.setPage(COMMAND);
        } catch (ServiceException e) {
            router.setPage(PagePath.ERROR_500);
        }
        return router;
    }
}
