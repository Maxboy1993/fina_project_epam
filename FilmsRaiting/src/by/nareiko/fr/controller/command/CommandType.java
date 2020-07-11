package by.nareiko.fr.controller.command;

import by.nareiko.fr.controller.command.impl.LoginCommand;
import by.nareiko.fr.controller.command.impl.SignUpCommand;

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
