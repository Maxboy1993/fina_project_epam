package by.nareiko.fr.controller.command;

import by.nareiko.fr.controller.command.impl.DefaultCommand;
import by.nareiko.fr.controller.command.impl.FindAllFilmsCommand;
import by.nareiko.fr.controller.command.impl.SignInCommand;
import by.nareiko.fr.controller.command.impl.SignUpCommand;

public enum CommandType {
    SIGN_IN(new SignInCommand()),
    SIGN_UP(new SignUpCommand()),
    FIND_ALL_FILMS(new FindAllFilmsCommand()),
    DEFAULT_COMMAND(new DefaultCommand());

    private Command command;

    CommandType(Command command) {
        this.command = command;
    }

    public Command getCommand() {
        return command;
    }
}
