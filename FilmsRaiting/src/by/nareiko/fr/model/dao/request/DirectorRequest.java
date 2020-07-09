package by.nareiko.fr.model.dao.request;

public class DirectorRequest {
    private DirectorRequest(){}

    public static final String FIND_ALL_DIRECTORS = "SELECT personId, profession, firstName, lastName, birthday FROM FilmPerson WHERE profession  = 'director'";
    public static final String FIND_DIRECTOR_BY_LAST_NAME = "SELECT personId, profession, firstName, lastName, birthday " +
            "FROM FilmPerson WHERE profession  = 'director' AND lastName = ?";
    public static final String FIND_DIRECTOR_BY_LAST_NAME_AND_FIRSTNAME = "SELECT personId, profession, firstName, lastName, birthday " +
            "FROM FilmPerson WHERE profession  = 'director' AND lastName = ? AND firstName = ?";
    public static final String FIND_DIRECTOR_BY_ID = "SELECT personId, profession, firstName, lastName, birthday " +
            "FROM FilmPerson WHERE personId = ?";
    public static final String DELETE_DIRECTOR_BY_ID = "DELETE FROM FilmPerson WHERE personId = ?";
    public static final String DELETE_DIRECTOR_BY_LAST_NAME_AND_FIRSTNAME = "DELETE FROM FilmPerson WHERE profession  = 'director' AND lastName = ? AND firstName = ?";
    public static final String CREATE_DIRECTOR = "INSERT INTO FilmPerson (profession, firstName, lastName, birthday) VALUES ('director', ?, ?, ?)";
    public static final String UPDATE_DIRECTOR = "UPDATE FilmPerson SET firstName = ?, lastName = ? birthday = ? WHERE personId = ?";
}
