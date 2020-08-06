package by.nareiko.fr.controller.command;

import by.nareiko.fr.controller.command.impl.*;

/**
 * The enum Command type.
 */
public enum CommandType {
    /**
     * The Sign in.
     */
    SIGN_IN(new SignInCommand(), "SIGN_IN"),
    /**
     * The Sign up.
     */
    SIGN_UP(new SignUpCommand(), "SIGN_UP"),
    /**
     * The Find all films.
     */
    FIND_ALL_FILMS(new FindAllFilmsCommand(), "FIND_ALL_FILMS"),
    /**
     * The Change language.
     */
    CHANGE_LANGUAGE(new ChangeLanguageCommand(), "CHANGE_LANGUAGE"),
    /**
     * The Change role.
     */
    CHANGE_ROLE(new ChangeRoleCommand(), "CHANGE_ROLE"),
    /**
     * The Log out.
     */
    LOG_OUT(new LogOutCommand(), "LOG_OUT"),
    /**
     * The Confirm email.
     */
    CONFIRM_EMAIL(new ConfirmEmailCommand(), "CONFIRM_EMAIL"),
    /**
     * The Passing to sign up.
     */
    PASSING_TO_SIGN_UP(new PassingToSignUpCommand(), "PASSING_TO_SIGN_UP"),
    /**
     * The Passing to sign in.
     */
    PASSING_TO_SIGN_IN(new PassingToSignInCommand(), "PASSING_TO_SIGN_IN"),
    /**
     * The Passing to main.
     */
    PASSING_TO_MAIN(new PassingToMainCommand(), "PASSING_TO_MAIN"),
    /**
     * The Passing to add actor.
     */
    PASSING_TO_ADD_ACTOR(new PassingToAddActorCommand(), "PASSING_TO_ADD_ACTOR"),
    /**
     * The Passing to add poster.
     */
    PASSING_TO_ADD_POSTER(new PassingToAddPosterCommand(), "PASSING_TO_ADD_POSTER"),
    /**
     * The Passing to show users.
     */
    PASSING_TO_SHOW_USERS(new PassingToShowUsersCommand(), "PASSING_TO_SHOW_USERS"),
    /**
     * The Add film.
     */
    ADD_FILM(new AddFilmCommand(), "ADD_FILM"),
    /**
     * The Delete user.
     */
    DELETE_USER(new DeleteUserCommand(), "DELETE_USER"),
    /**
     * The Delete review.
     */
    DELETE_REVIEW(new DeleteReviewCommand(), "DELETE_REVIEW"),
    /**
     * The Delete film.
     */
    DELETE_FILM(new DeleteFilmCommand(), "DELETE_FILM"),
    /**
     * The Add director.
     */
    ADD_DIRECTOR(new AddDirectorCommand(), "ADD_DIRECTOR"),
    /**
     * The Passing to add film.
     */
    PASSING_TO_ADD_FILM(new PassingToAddFilmCommand(), "PASSING_TO_ADD_FILM"),
    /**
     * The Passing to add director.
     */
    PASSING_TO_ADD_DIRECTOR(new PassingToAddDirectorCommand(), "PASSING_TO_ADD_DIRECTOR"),
    /**
     * The Passing to film info.
     */
    PASSING_TO_FILM_INFO(new PassingToFilmInfoCommand(), "PASSING_TO_FILM_INFO"),
    /**
     * The Passing to user profile.
     */
    PASSING_TO_USER_PROFILE(new PassingToUserProfileCommand(), "PASSING_TO_USER_PROFILE"),
    /**
     * The Add actor.
     */
    ADD_ACTOR(new AddActorCommand(), "ADD_ACTOR"),
    /**
     * The Find all users.
     */
    FIND_ALL_USERS(new FindAllUsersCommand(), "FIND_ALL_USERS"),
    /**
     * The Find film by name.
     */
    FIND_FILM_BY_NAME(new FindFilmByNameCommand(), "FIND_FILM_BY_NAME"),
    /**
     * The Show film info.
     */
    SHOW_FILM_INFO(new ShowFilmInfoCommand(), "SHOW_FILM_INFO"),
    /**
     * The Count film raiting.
     */
    COUNT_FILM_RAITING(new CountFilmRaiting(), "COUNT_FILM_RAITING"),
    /**
     * The Add review.
     */
    ADD_REVIEW(new AddReviewCommand(), "ADD_REVIEW"),
    /**
     * The Default command.
     */
    DEFAULT_COMMAND(new DefaultCommand(), "DEFAULT_COMMAND");

    private Command command;
    private String commandNameValue;

    CommandType(Command command, String commandNameValue) {
        this.command = command;
        this.commandNameValue = commandNameValue;
    }

    /**
     * Gets command.
     *
     * @return the command
     */
    public Command getCommand() {
        return command;
    }

    /**
     * Gets command name value.
     *
     * @return the command name value
     */
    public String getCommandNameValue() {
        return commandNameValue;
    }
}
