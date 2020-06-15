package command;

import command.impl.LoginCommand;
import command.impl.LogoutCommand;
import command.impl.UserCommand;
import model.service.impl.UserServiceImpl;

public enum CommandType {
    LOGIN(new LoginCommand(new UserServiceImpl())),
    LOGOUT(new LogoutCommand()),
    ALL_USERS(new UserCommand());

    private Command command;

    CommandType(Command command) {
        this.command = command;
    }

    public Command getCommand() {
        return command;
    }
}
