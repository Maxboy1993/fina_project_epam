package by.nareiko.fr.controller.command;

import by.nareiko.fr.controller.command.impl.SignInCommand;
import by.nareiko.fr.controller.command.impl.SignUpCommand;

public enum CommandType {
    SIGN_IN(new SignInCommand()),
    SIGN_UP(new SignUpCommand());

    private Command command;

    CommandType(Command command) {
        this.command = command;
    }

    public Command getCommand() {
        return command;
    }
}
