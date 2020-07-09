package by.nareiko.fr.model.dao.request;

public class FilmRaitingRequest {
    private FilmRaitingRequest() {
    }

    public static final String FIND_ALL_FILMS_RAITING = "SELECT raitingId, filmId, userId, raiting FROM FilmRaiting";
    public static final String FIND_RAITING_BY_ID_FILM = "SELECT raitingId, filmId, userId, raiting FROM FilmRaiting WHERE filmId = ?";
    public static final String FIND_RAITING_BY_ID = "SELECT raitingId, filmId, userId, raiting FROM FilmRaiting WHERE raitingId = ?";
    public static final String DELETE_RAITING_BY_ID = "DELETE FROM FilmRaiting WHERE raitingId = ?";
    public static final String CREATE_RAITING = "INSERT INTO FilmRaiting (filmId, userId, raiting) VALUES (?, ?, ?)";
    public static final String UPDATE_RAITING = "UPDATE FilmRaiting SET raiting = ? WHERE raitingId = ?";
}