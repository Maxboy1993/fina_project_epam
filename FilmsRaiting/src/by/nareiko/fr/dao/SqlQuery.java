package by.nareiko.fr.dao;

public class SqlQuery {
    //actor queries
    public static final String FIND_ALL_ACTORS = "SELECT personId, profession, firstName, lastName, birthday " +
            "FROM FilmPerson WHERE profession  = 'actor'";
    public static final String FIND_ACTORS_BY_LAST_NAME = "SELECT personId, profession, firstName, lastName, birthday " +
            "FROM FilmPerson WHERE profession  = 'actor' AND lastName =?";
    public static final String FIND_ACTOR_BY_LAST_NAME_AND_FIRST_NAME = "SELECT personId, profession, firstName, lastName, birthday " +
            "FROM FilmPerson WHERE profession  = 'actor' AND lastName =? AND firstName = ?";
    public static final String FIND_ACTOR_BY_ID = "SELECT personId, profession, firstName, lastName, birthday " +
            "FROM FilmPerson WHERE profession  = 'actor' AND personId = ?";
    public static final String FIND_ACTORS_BY_FILM_ID = "SELECT filmperson.personId, filmperson.profession, filmperson.firstName, " +
            "filmperson.lastName, filmperson.birthday FROM FilmPerson " +
            "JOIN characters ON filmperson.personId = characters.characterId " +
            "WHERE profession = 'actor' AND filmId = ? ORDER BY filmperson.lastName";
    public static final String DELETE_ACTOR_BY_ID = "DELETE FROM FilmPerson WHERE personId = ?";
    public static final String DELETE_ACTOR_BY_LAST_NAME_AND_FIRST_NAME = "DELETE FROM FilmPerson WHERE profession  = 'actor' AND lastName = ? AND firstName = ?";
    public static final String CREATE_ACTOR = "INSERT INTO FilmPerson (profession, firstName, lastName, birthday) VALUES ('actor', ?, ?, ?)";
    public static final String UPDATE_ACTOR = "UPDATE FilmPerson SET firstName = ?, lastName = ? birthday = ? WHERE personId = ?";
    //director queries
    public static final String FIND_ALL_DIRECTORS = "SELECT personId, profession, firstName, lastName, birthday FROM FilmPerson WHERE profession  = 'director'";
    public static final String FIND_DIRECTOR_BY_LAST_NAME = "SELECT personId, profession, firstName, lastName, birthday " +
            "FROM FilmPerson WHERE profession  = 'director' AND lastName = ?";
    public static final String FIND_DIRECTOR_BY_LAST_NAME_AND_FIRSTNAME = "SELECT personId, profession, firstName, lastName, birthday " +
            "FROM FilmPerson WHERE profession  = 'director' AND lastName = ? AND firstName = ?";
    public static final String FIND_DIRECTOR_BY_ID = "SELECT personId, profession, firstName, lastName, birthday " +
            "FROM FilmPerson WHERE personId = ?";
    public static final String FIND_DIRECTOR_BY_FILM_ID = "SELECT filmperson.personId, filmperson.profession, filmperson.firstName, " +
            "filmperson.lastName, filmperson.birthday FROM FilmPerson " +
            "JOIN characters ON filmperson.personId = characters.characterId " +
            "WHERE profession = 'director' AND filmId = ?";
    public static final String DELETE_DIRECTOR_BY_ID = "DELETE FROM FilmPerson WHERE personId = ?";
    public static final String DELETE_DIRECTOR_BY_LAST_NAME_AND_FIRSTNAME = "DELETE FROM FilmPerson WHERE profession  = 'director' AND lastName = ? AND firstName = ?";
    public static final String CREATE_DIRECTOR = "INSERT INTO FilmPerson (profession, firstName, lastName, birthday) VALUES ('director', ?, ?, ?)";
    public static final String UPDATE_DIRECTOR = "UPDATE FilmPerson SET firstName = ?, lastName = ? birthday = ? WHERE personId = ?";
    //film raiting queries
    public static final String FIND_ALL_FILMS_RAITING = "SELECT raitingId, filmId, userId, raiting FROM FilmRaiting";
    public static final String FIND_RAITING_BY_ID_FILM = "SELECT raitingId, filmId, userId, raiting FROM FilmRaiting WHERE filmId = ?";
    public static final String FIND_RAITING_BY_ID = "SELECT raitingId, filmId, userId, raiting FROM FilmRaiting WHERE raitingId = ?";
    public static final String DELETE_RAITING_BY_ID = "DELETE FROM FilmRaiting WHERE raitingId = ?";
    public static final String CREATE_RAITING = "INSERT INTO FilmRaiting (filmId, userId, raiting) VALUES (?, ?, ?)";
    public static final String UPDATE_RAITING = "UPDATE FilmRaiting SET raiting = ? WHERE raitingId = ?";
    //review queries
    public static final String FIND_ALL_REVIEWS = "SELECT reviewId, filmId, userId, review, reviewDate FROM Review";
    public static final String FIND_REVIEW_BY_ID_FILM = "SELECT reviewId, filmId, userId, review, reviewDate FROM Review WHERE filmId = ?";
    public static final String FIND_REVIEW_BY_ID = "SELECT reviewId, filmId, userId, review, reviewDate FROM Review WHERE reviewId = ?";
    public static final String DELETE_REVIEW_BY_ID = "DELETE FROM Review WHERE reviewId = ?";
    public static final String CREATE_REVIEW = "INSERT INTO Review (filmId, userId, review, reviewDate) VALUES (?, ?, ?, ?)";
    public static final String UPDATE_REVIEW = "UPDATE Review SET review = ?, reviewDate = ? WHERE reviewId = ?";
    //user queries
    private static final String INACTIVE_STATUS = "inactive";
    public static final String FIND_ALL_USERS = "SELECT userId, firstName, lastName, birthday, " +
            "login, password, status, role, verification FROM User";
    public static final String FIND_USER_BY_LOGIN = "SELECT userId, firstName, lastName, birthday, " +
            "login, password, status, role, verification FROM User WHERE login = ?";
    public static final String FIND_USER_BY_LOGIN_AND_PASSWORD = "SELECT userId, firstName, lastName, birthday, " +
            "login, password, status, role, verification FROM User WHERE login = ? AND password = ?";
    public static final String FIND_USER_BY_ID = "SELECT userId, firstName, lastName, birthday, " +
            "login, password, status, role, verification FROM User WHERE userId = ?";
    public static final String CREATE_USER = "INSERT INTO user (firstName, lastName, birthday, " +
            "login, password, status, role) VALUES (?, ?, ?, ?, ?, ?, ?)";
    public static final String UPDATE_USER = "UPDATE user SET firstNmae = ?, lastName = ?, birthday = ? " +
            "login = ?, password = ?, role = ? WHERE userId = ?";
    public static final String VERIFY_USER = "UPDATE user SET verification = ? WHERE login = ?";
    public static final String DELETE_USER_BY_ID = "UPDATE user SET status = " + INACTIVE_STATUS + " WHERE userId = ?";
    public static final String DELETE_USER_BY_LOGIN = "UPDATE user SET status = " + INACTIVE_STATUS + " WHERE login = ?";
    public static final String DELETE_FILM_BY_ID = "UPDATE Film SET status = " + INACTIVE_STATUS + " WHERE filmId = ?";
    // film queries
    private static final String ACTIVE_STATUS = "active";
    public static final String FIND_ALL_FILMS = "SELECT filmId, filmName, releaseDate, genre, status FROM film WHERE status = 'active'";
    public static final String FIND_FILM_BY_ID = "SELECT filmId, filmName, releaseDate, genre, status FROM film" +
            "WHERE filmId = ? AND status = " + ACTIVE_STATUS;
    public static final String FIND_FILM_BY_NAME = "SELECT filmId, filmName, releaseDate, genre, status FROM film " +
            "WHERE filmName = ? ";
    public static final String CREATE_FILM = "INSERT INTO Film (filmName, releaseDate, genre, status) VALUES (?, ?, ?, ?)";
    public static final String UPDATE_FILM = "UPDATE Film SET filmName = ?, " +
            "releaseDate = ?, genre = ? WHERE filmId = ?";
    public static final String MAPPING_FILM_WITH_PERSON = "INSERT INTO Characters (filmId, characterId) VALUES (?, ?)";

    private SqlQuery() {
    }
}
