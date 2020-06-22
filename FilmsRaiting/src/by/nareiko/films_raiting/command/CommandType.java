package by.nareiko.films_raiting.command;

import by.nareiko.films_raiting.command.impl.LoginCommand;
import by.nareiko.films_raiting.command.impl.SignUpCommand;

public enum CommandType {
    LOGIN(new LoginCommand()),
    SIGN_UP(new SignUpCommand());

    private Command command;

    CommandType(Command command) {
        this.command = command;
    }

    public Command getCommand() {
        return command;
    }
}
