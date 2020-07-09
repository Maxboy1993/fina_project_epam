package by.nareiko.fr.model.dao.request;

public class ActorRequest {
    private ActorRequest(){}

    public static final String FIND_ALL_ACTORS = "SELECT personId, profession, firstName, lastName, birthday " +
            "FROM FilmPerson WHERE profession  = 'actor'";
    public static final String FIND_ACTORS_BY_LAST_NAME = "SELECT personId, profession, firstName, lastName, birthday " +
            "FROM FilmPerson WHERE profession  = 'actor' AND lastName =?";
    public static final String FIND_ACTORS_BY_LAST_NAME_AND_FIRST_NAME = "SELECT personId, profession, firstName, lastName, birthday " +
            "FROM FilmPerson WHERE profession  = 'actor' AND lastName =? AND firstName = ?";
    public static final String FIND_ACTOR_BY_ID = "SELECT personId, profession, firstName, lastName, birthday " +
            "FROM FilmPerson WHERE profession  = 'actor' AND personId = ?";
    public static final String DELETE_ACTOR_BY_ID = "DELETE FROM FilmPerson WHERE personId = ?";
    public static final String DELETE_ACTOR_BY_LAST_NAME_AND_FIRST_NAME = "DELETE FROM FilmPerson WHERE profession  = 'actor' AND lastName = ? AND firstName = ?";
    public static final String CREATE_ACTOR = "INSERT INTO FilmPerson (profession, firstName, lastName, birthday) VALUES ('actor', ?, ?, ?)";
    public static final String UPDATE_ACTOR = "UPDATE FilmPerson SET firstName = ?, lastName = ? birthday = ? WHERE personId = ?";
}
