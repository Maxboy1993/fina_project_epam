package by.nareiko.fr.controller.command;

import by.nareiko.fr.controller.command.impl.*;

public enum CommandType {
    SIGN_IN(new SignInCommand()),
    SIGN_UP(new SignUpCommand()),
    FIND_ALL_FILMS(new FindAllFilmsCommand()),
    CHANGE_LANGUAGE(new ChangeLanguageCommand()),
    LOG_OUT(new LogOutCommand()),
    CONFIRM_EMAIL(new ConfirmEmailCommand()),
    PASSING_TO_SIGN_UP(new PassingToSignUp()),
    DEFAULT_COMMAND(new DefaultCommand());

    private Command command;

    CommandType(Command command) {
        this.command = command;
    }

    public Command getCommand() {
        return command;
    }
}
