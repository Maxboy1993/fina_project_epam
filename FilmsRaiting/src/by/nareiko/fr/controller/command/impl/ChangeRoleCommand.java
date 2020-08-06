package by.nareiko.fr.controller.command.impl;

import by.nareiko.fr.controller.RequestParameterName;
import by.nareiko.fr.controller.Router;
import by.nareiko.fr.controller.command.Command;
import by.nareiko.fr.controller.command.PagePath;
import by.nareiko.fr.entity.RoleType;
import by.nareiko.fr.entity.User;
import by.nareiko.fr.exception.ServiceException;
import by.nareiko.fr.service.ServiceFactory;
import by.nareiko.fr.service.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Optional;

/**
 * The type Change role command.
 */
public class ChangeRoleCommand implements Command {
    private static final String COMMAND = "controller?command=PASSING_TO_SHOW_USERS";
    private static final Logger LOGGER = LogManager.getLogger();

    @Override
    public Router execute(HttpServletRequest request) {
        Router router = new Router();
        int userId = Integer.parseInt(request.getParameter(RequestParameterName.USER_ID_PARAM));
        String newRole = request.getParameter(RequestParameterName.NEW_ROLE_PARAM);
        UserService userService = ServiceFactory.getInstance().getUserService();
        try {
            Optional<User> optionalUser = userService.findById(userId);
            if (optionalUser.isPresent()) {
                User user = optionalUser.get();
                user.setRoleType(RoleType.getRoleTypeByValue(newRole));
                userService.changeRole(user);
                List<User> users = (List<User>) request.getSession().getAttribute(RequestParameterName.USER_LIST_PARAM);
                for (User tempUser: users) {
                    if (tempUser.getId() == userId){
                        tempUser.setRoleType(user.getRoleType());
                    }
                }
                router.setPage(COMMAND);
            } else {
                request.setAttribute(RequestParameterName.ERRO_CHANGING_ROLE_PARAM, RequestParameterName.ERRO_CHANGING_ROLE_VALUE);
                router.setPage(PagePath.SHOW_USERS);
            }
        } catch (ServiceException e) {
            LOGGER.error("Error while role changing: ", e);
            router.setPage(PagePath.ERROR_500);
        }
        return router;
    }
}
