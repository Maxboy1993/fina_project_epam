package by.nareiko.fr.dao;

/**
 * The type Sql query.
 */
public class SqlQuery {
    /**
     * The constant FIND_ALL_ACTORS.
     */
//actor queries
    public static final String FIND_ALL_ACTORS = "SELECT personId, profession, firstName, lastName, birthday " +
            "FROM FilmPerson WHERE profession  = 'actor'";
    /**
     * The constant FIND_ACTORS_BY_LAST_NAME.
     */
    public static final String FIND_ACTORS_BY_LAST_NAME = "SELECT personId, profession, firstName, lastName, birthday " +
            "FROM FilmPerson WHERE profession  = 'actor' AND lastName =?";
    /**
     * The constant FIND_ACTOR_BY_LAST_NAME_AND_FIRST_NAME.
     */
    public static final String FIND_ACTOR_BY_LAST_NAME_AND_FIRST_NAME = "SELECT personId, profession, firstName, lastName, birthday " +
            "FROM FilmPerson WHERE profession  = 'actor' AND lastName = ? AND firstName = ?";
    /**
     * The constant FIND_ACTOR_BY_ID.
     */
    public static final String FIND_ACTOR_BY_ID = "SELECT personId, profession, firstName, lastName, birthday " +
            "FROM FilmPerson WHERE profession  = 'actor' AND personId = ?";
    /**
     * The constant FIND_ACTORS_BY_FILM_ID.
     */
    public static final String FIND_ACTORS_BY_FILM_ID = "SELECT filmperson.personId, filmperson.profession, filmperson.firstName, " +
            "filmperson.lastName, filmperson.birthday FROM FilmPerson " +
            "JOIN characters ON filmperson.personId = characters.characterId " +
            "WHERE profession = 'actor' AND filmId = ? ORDER BY filmperson.lastName";
    /**
     * The constant DELETE_ACTOR_BY_ID.
     */
    public static final String DELETE_ACTOR_BY_ID = "DELETE FROM FilmPerson WHERE personId = ?";
    /**
     * The constant DELETE_ACTOR_BY_LAST_NAME_AND_FIRST_NAME.
     */
    public static final String DELETE_ACTOR_BY_LAST_NAME_AND_FIRST_NAME = "DELETE FROM FilmPerson WHERE profession  = 'actor' AND lastName = ? AND firstName = ?";
    /**
     * The constant CREATE_ACTOR.
     */
    public static final String CREATE_ACTOR = "INSERT INTO FilmPerson (profession, firstName, lastName, birthday) VALUES ('actor', ?, ?, ?)";
    /**
     * The constant UPDATE_ACTOR.
     */
    public static final String UPDATE_ACTOR = "UPDATE FilmPerson SET firstName = ?, lastName = ? birthday = ? WHERE personId = ?";
    /**
     * The constant FIND_ALL_DIRECTORS.
     */
//director queries
    public static final String FIND_ALL_DIRECTORS = "SELECT personId, profession, firstName, lastName, birthday FROM FilmPerson WHERE profession  = 'director'";
    /**
     * The constant FIND_DIRECTOR_BY_LAST_NAME.
     */
    public static final String FIND_DIRECTOR_BY_LAST_NAME = "SELECT personId, profession, firstName, lastName, birthday " +
            "FROM FilmPerson WHERE profession  = 'director' AND lastName = ?";
    /**
     * The constant FIND_DIRECTOR_BY_LAST_NAME_AND_FIRSTNAME.
     */
    public static final String FIND_DIRECTOR_BY_LAST_NAME_AND_FIRSTNAME = "SELECT personId, profession, firstName, lastName, birthday " +
            "FROM FilmPerson WHERE profession  = 'director' AND lastName = ? AND firstName = ?";
    /**
     * The constant FIND_DIRECTOR_BY_ID.
     */
    public static final String FIND_DIRECTOR_BY_ID = "SELECT personId, profession, firstName, lastName, birthday " +
            "FROM FilmPerson WHERE personId = ?";
    /**
     * The constant FIND_DIRECTOR_BY_FILM_ID.
     */
    public static final String FIND_DIRECTOR_BY_FILM_ID = "SELECT filmperson.personId, filmperson.profession, filmperson.firstName, " +
            "filmperson.lastName, filmperson.birthday FROM FilmPerson " +
            "JOIN characters ON filmperson.personId = characters.characterId " +
            "WHERE profession = 'director' AND filmId = ?";
    /**
     * The constant DELETE_DIRECTOR_BY_ID.
     */
    public static final String DELETE_DIRECTOR_BY_ID = "DELETE FROM FilmPerson WHERE personId = ?";
    /**
     * The constant DELETE_DIRECTOR_BY_LAST_NAME_AND_FIRSTNAME.
     */
    public static final String DELETE_DIRECTOR_BY_LAST_NAME_AND_FIRSTNAME = "DELETE FROM FilmPerson WHERE profession  = 'director' AND lastName = ? AND firstName = ?";
    /**
     * The constant CREATE_DIRECTOR.
     */
    public static final String CREATE_DIRECTOR = "INSERT INTO FilmPerson (profession, firstName, lastName, birthday) VALUES ('director', ?, ?, ?)";
    /**
     * The constant UPDATE_DIRECTOR.
     */
    public static final String UPDATE_DIRECTOR = "UPDATE FilmPerson SET firstName = ?, lastName = ? birthday = ? WHERE personId = ?";
    /**
     * The constant FIND_ALL_FILMS_RAITING.
     */
//film raiting queries
    public static final String FIND_ALL_FILMS_RAITING = "SELECT raitingId, filmId, userId, raiting FROM FilmRaiting";
    /**
     * The constant FIND_RAITING_BY_ID_FILM.
     */
    public static final String FIND_RAITING_BY_ID_FILM = "SELECT raitingId, filmId, userId, raiting FROM FilmRaiting WHERE filmId = ?";
    /**
     * The constant FIND_RAITING_BY_ID.
     */
    public static final String FIND_RAITING_BY_ID = "SELECT raitingId, filmId, userId, raiting FROM FilmRaiting WHERE raitingId = ?";
    /**
     * The constant DELETE_RAITING_BY_ID.
     */
    public static final String DELETE_RAITING_BY_ID = "DELETE FROM FilmRaiting WHERE raitingId = ?";
    /**
     * The constant CREATE_RAITING.
     */
    public static final String CREATE_RAITING = "INSERT INTO FilmRaiting (filmId, userId, raiting) VALUES (?, ?, ?)";
    /**
     * The constant UPDATE_RAITING.
     */
    public static final String UPDATE_RAITING = "UPDATE FilmRaiting SET raiting = ? WHERE raitingId = ?";
    /**
     * The constant FIND_ALL_REVIEWS.
     */
//review queries
    public static final String FIND_ALL_REVIEWS = "SELECT reviewId, filmId, userId, review, reviewDate FROM Review";
    /**
     * The constant FIND_REVIEW_BY_ID_FILM.
     */
    public static final String FIND_REVIEW_BY_ID_FILM = "SELECT reviewId, filmId, userId, review, reviewDate FROM Review WHERE filmId = ?";
    /**
     * The constant FIND_REVIEW_BY_ID.
     */
    public static final String FIND_REVIEW_BY_ID = "SELECT reviewId, filmId, userId, review, reviewDate FROM Review WHERE reviewId = ?";
    /**
     * The constant DELETE_REVIEW_BY_ID.
     */
    public static final String DELETE_REVIEW_BY_ID = "DELETE FROM Review WHERE reviewId = ?";
    /**
     * The constant CREATE_REVIEW.
     */
    public static final String CREATE_REVIEW = "INSERT INTO Review (filmId, userId, review, reviewDate) VALUES (?, ?, ?, ?)";
    /**
     * The constant UPDATE_REVIEW.
     */
    public static final String UPDATE_REVIEW = "UPDATE Review SET review = ? WHERE reviewId = ?";
    /**
     * The constant FIND_ALL_USERS.
     */
//user queries
    public static final String FIND_ALL_USERS = "SELECT userId, firstName, lastName, birthday, " +
            "login, password, status, role, verification FROM User";
    /**
     * The constant FIND_USER_BY_LOGIN.
     */
    public static final String FIND_USER_BY_LOGIN = "SELECT userId, firstName, lastName, birthday, " +
            "login, password, status, role, verification FROM User WHERE login = ?";
    /**
     * The constant FIND_USER_BY_LOGIN_AND_PASSWORD.
     */
    public static final String FIND_USER_BY_LOGIN_AND_PASSWORD = "SELECT userId, firstName, lastName, birthday, " +
            "login, password, status, role, verification FROM User WHERE login = ? AND password = ?";
    /**
     * The constant FIND_USER_BY_ID.
     */
    public static final String FIND_USER_BY_ID = "SELECT userId, firstName, lastName, birthday, " +
            "login, password, status, role, verification FROM User WHERE userId = ?";
    /**
     * The constant CREATE_USER.
     */
    public static final String CREATE_USER = "INSERT INTO user (firstName, lastName, birthday, " +
            "login, password, status, role) VALUES (?, ?, ?, ?, ?, ?, ?)";
    /**
     * The constant UPDATE_USER.
     */
    public static final String UPDATE_USER = "UPDATE user SET firstName = ?, lastName = ?, birthday = ?, " +
            "login = ?, password = ?, role = ? WHERE userId = ?";
    /**
     * The constant VERIFY_USER.
     */
    public static final String VERIFY_USER = "UPDATE user SET verification = ? WHERE login = ?";
    /**
     * The constant DELETE_USER_BY_ID.
     */
    public static final String DELETE_USER_BY_ID = "UPDATE user SET status = 'inactive' WHERE userId = ?";
    /**
     * The constant DELETE_USER_BY_LOGIN.
     */
    public static final String DELETE_USER_BY_LOGIN = "UPDATE user SET status = 'inactive' WHERE login = ?";
    /**
     * The constant DELETE_FILM_BY_ID.
     */
    public static final String DELETE_FILM_BY_ID = "UPDATE Film SET status = 'inactive' WHERE filmId = ?";
    /**
     * The constant FIND_ALL_FILMS.
     */
// film queries
    public static final String FIND_ALL_FILMS = "SELECT filmId, filmName, releaseDate, genre, status FROM film WHERE status = 'active'";
    /**
     * The constant FIND_FILM_BY_ID.
     */
    public static final String FIND_FILM_BY_ID = "SELECT filmId, filmName, releaseDate, genre, status FROM film " +
            "WHERE filmId = ? AND status = 'active'";
    /**
     * The constant FIND_FILM_BY_NAME.
     */
    public static final String FIND_FILM_BY_NAME = "SELECT filmId, filmName, releaseDate, genre, status FROM film " +
            "WHERE filmName LIKE ? ";
    /**
     * The constant CREATE_FILM.
     */
    public static final String CREATE_FILM = "INSERT INTO Film (filmName, releaseDate, genre, status) VALUES (?, ?, ?, ?)";
    /**
     * The constant UPDATE_FILM.
     */
    public static final String UPDATE_FILM = "UPDATE Film SET filmName = ?, " +
            "releaseDate = ?, genre = ? WHERE filmId = ?";
    /**
     * The constant MAPPING_FILM_WITH_PERSON.
     */
    public static final String MAPPING_FILM_WITH_PERSON = "INSERT INTO Characters (filmId, characterId) VALUES (?, ?)";
    /**
     * The constant FIND_FILM_POSTER.
     */
//poster queries
    public static final String FIND_FILM_POSTER = "SELECT poster FROM FilmPoster WHERE filmId = ?";
    /**
     * The constant ADD_FILM_POSTER.
     */
    public static final String ADD_FILM_POSTER = "INSERT INTO FilmPoster (filmId, poster) VALUES (?, ?)";
    /**
     * The constant UPDATE_FILM_POSTER.
     */
    public static final String UPDATE_FILM_POSTER = "UPDATE FilmPoster SET poster = ? WHERE filmId = ?";
    /**
     * The constant DELETE_POSTER_BY_FILM_ID.
     */
    public static final String DELETE_POSTER_BY_FILM_ID = "DELETE FROM FilmPoster WHERE filmId = ?";

    private SqlQuery() {
    }
}
