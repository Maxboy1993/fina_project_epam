package by.nareiko.fr.command;

import by.nareiko.fr.command.impl.LoginCommand;
import by.nareiko.fr.command.impl.SignUpCommand;

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
