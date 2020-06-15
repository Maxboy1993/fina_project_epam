package by.nareiko.films_raiting.command;

import by.nareiko.films_raiting.command.impl.LoginCommand;
import by.nareiko.films_raiting.command.impl.LogoutCommand;
import by.nareiko.films_raiting.command.impl.UserCommand;
import by.nareiko.films_raiting.model.service.impl.UserServiceImpl;

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
