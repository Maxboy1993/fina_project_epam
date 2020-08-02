package by.nareiko.fr.controller.command;

import by.nareiko.fr.controller.command.impl.*;

public enum CommandType {
    SIGN_IN(new SignInCommand(), "SIGN_IN"),
    SIGN_UP(new SignUpCommand(), "SIGN_UP"),
    FIND_ALL_FILMS(new FindAllFilmsCommand(), "FIND_ALL_FILMS"),
    CHANGE_LANGUAGE(new ChangeLanguageCommand(), "CHANGE_LANGUAGE"),
    CHANGE_ROLE(new ChangeRoleCommand(), "CHANGE_ROLE"),
    LOG_OUT(new LogOutCommand(), "LOG_OUT"),
    CONFIRM_EMAIL(new ConfirmEmailCommand(), "CONFIRM_EMAIL"),
    PASSING_TO_SIGN_UP(new PassingToSignUpCommand(), "PASSING_TO_SIGN_UP"),
    PASSING_TO_SIGN_IN(new PassingToSignInCommand(), "PASSING_TO_SIGN_IN"),
    PASSING_TO_MAIN(new PassingToMainCommand(), "PASSING_TO_MAIN"),
    PASSING_TO_ADD_ACTOR(new PassingToAddActorCommand(), "PASSING_TO_ADD_ACTOR"),
    PASSING_TO_ADD_POSTER(new PassingToAddPosterCommand(), "PASSING_TO_ADD_POSTER"),
    PASSING_TO_SHOW_USERS(new PassingToShowUsersCommand(), "PASSING_TO_SHOW_USERS"),
    ADD_FILM(new AddFilmCommand(), "ADD_FILM"),
    DELETE_USER(new DeleteUserCommand(), "DELETE_USER"),
    DELETE_REVIEW(new DeleteReviewCommand(), "DELETE_REVIEW"),
    DELETE_FILM(new DeleteFilmCommand(), "DELETE_FILM"),
    ADD_DIRECTOR(new AddDirectorCommand(), "ADD_DIRECTOR"),
    PASSING_TO_ADD_FILM(new PassingToAddFilmCommand(), "PASSING_TO_ADD_FILM"),
    PASSING_TO_ADD_DIRECTOR(new PassingToAddDirectorCommand(), "PASSING_TO_ADD_DIRECTOR"),
    PASSING_TO_FILM_INFO(new PassingToFilmInfoCommand(), "PASSING_TO_FILM_INFO"),
    PASSING_TO_USER_PROFILE(new PassingToUserProfileCommand(), "PASSING_TO_USER_PROFILE"),
    ADD_ACTOR(new AddActorCommand(), "ADD_ACTOR"),
    FIND_ALL_USERS(new FindAllUsersCommand(), "FIND_ALL_USERS"),
    FIND_FILM_BY_NAME(new FindFilmByNameCommand(), "FIND_FILM_BY_NAME"),
    SHOW_FILM_INFO(new ShowFilmInfoCommand(), "SHOW_FILM_INFO"),
    COUNT_FILM_RAITING(new CountFilmRaiting(), "COUNT_FILM_RAITING"),
    ADD_REVIEW(new AddReviewCommand(), "ADD_REVIEW"),
    DEFAULT_COMMAND(new DefaultCommand(), "DEFAULT_COMMAND");

    private Command command;
    private String commandNameValue;

    CommandType(Command command, String commandNameValue) {
        this.command = command;
        this.commandNameValue = commandNameValue;
    }

    public Command getCommand() {
        return command;
    }

    public String getCommandNameValue() {
        return commandNameValue;
    }
}
