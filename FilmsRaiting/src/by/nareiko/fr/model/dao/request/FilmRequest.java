package by.nareiko.fr.model.dao.request;

public class FilmRequest {
    private FilmRequest() {
    }

    private static final int INACTIVE_STATUS_ID = 2;
    // REQUESTS
    public static final String FIND_ALL_FILMS = "SELECT film.filmId, filminfo.filmName, filminfo.releaseDate, Genre.genre, status.status " +
            "FROM film " +
            "JOIN filminfo ON film.filmId = filminfo.filmId " +
            "JOIN genre ON film.genreId = genre.genreId " +
            "JOIN status ON film.statusId = status.statusId " +
            "ORDER BY film.filmId ";
    public static final String FIND_FILM_BY_NAME = "SELECT film.filmId, filminfo.filmName, filminfo.releaseDate, Genre.genre, status.status" +
            "FROM film " +
            "JOIN filminfo ON film.filmId = filminfo.filmId " +
            "JOIN genre ON film.genreId = genre.genreId " +
            "JOIN status ON film.statusId = status.statusId " +
            "WHERE Film.name = ? " +
            "ORDER BY film.filmId";
    public static final String FIND_FILM_BY_ID = "SELECT film.filmId, filminfo.filmName, filminfo.releaseDate, Genre.genre, status.status" +
            "FROM film " +
            "JOIN filminfo ON film.filmId = filminfo.filmId " +
            "JOIN genre ON film.genreId = genre.genreId " +
            "JOIN status ON film.statusId = status.statusId " +
            "WHERE Film.filmId = ? " +
            "ORDER BY film.filmId";
    public static final String DELETE_FILM_BY_ID = "UPDATE Film SET statusId = " + INACTIVE_STATUS_ID + " WHERE filmId = ?";
    public static final String CREATE_FILM = "INSERT INTO Film (name, genreId, releaseDate, " +
            "statusId) VALUES (?, ?, ?, ?)";
    public static final String UPDATE_FILM = "UPDATE Film SET name = ?, genreId = ?" +
            "releaseDate = ? WHERE filmId = ?";


}
