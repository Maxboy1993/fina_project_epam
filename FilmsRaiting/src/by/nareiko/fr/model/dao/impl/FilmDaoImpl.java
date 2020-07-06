//package by.nareiko.fr.model.dao.impl;
//
//import by.nareiko.fr.entity.*;
//import by.nareiko.fr.exception.EntityException;
//import by.nareiko.fr.model.dao.FilmsDao;
//import by.nareiko.fr.pool.ConnectionPool;
//import org.apache.logging.log4j.LogManager;
//import org.apache.logging.log4j.Logger;
//
//import java.sql.*;
//import java.util.ArrayList;
//import java.util.Calendar;
//import java.util.GregorianCalendar;
//import java.util.List;
//// реализовать методы
//public class FilmDaoImpl implements FilmsDao<Film> {
//    private List<Film> films;
//    private Film film;
//    private static final String FIND_ALL_FILMS = "SELECT filminfo.filmName, filminfo.releaseDate, Genre.genre, status.status FROM film " +
//            "JOIN filminfo ON film.infoId = filminfo.infoId " +
//            "JOIN genre ON film.genreId = genre.genreId " +
//            "JOIN status ON film.statusId = status.statusId " +
//            "ORDER BY film.filmId";
//    private static final String FIND_FILM_BY_NAME = "SELECT filminfo.filmName, filminfo.releaseDate, Genre.genre, status.status FROM film " +
//            "JOIN filminfo ON film.infoId = filminfo.infoId " +
//            "JOIN genre ON film.genreId = genre.genreId " +
//            "JOIN status ON film.statusId = status.statusId " +
//            "WHERE Film.name = ? ORDER BY film.filmId";
//    private static final String FIND_FILM_BY_RAITING = "SELECT Film.filmId, Film.name, Film.genreId, Genre.genre, Film.releaseDate, Film.actorId, " +
//            "Actor.name, Actor.birthday, Film.directorId, Director.name, Director.birthday, Film.statusId, Status.status FROM Film JOIN Status ON Film.statusId = Status.statusId " +
//            "JOIN Actor ON Film.actorId = Actor.actorId JOIN Director ON Film.directorId = Director.directorId JOIN FilmRaiting ON Film.filmId = FilmRaiting.filmId WHERE FilmRaiting.raiting = ?";
//    private static final String FIND_FILM_BY_ID = "SELECT Film.filmId, Film.name, Film.genreId, Genre.genre, Film.releaseDate, Film.actorId, " +
//            "Actor.name, Actor.birthday, Film.directorId, Director.name, Director.birthday, Film.statusId, Status.status FROM Film JOIN Status ON Film.statusId = Status.statusId " +
//            "JOIN Actor ON Film.actorId = Actor.actorId JOIN Director ON Film.directorId = Director.directorId WHERE Film.filmId = ?";
//    private static final String DELETE_FILM_BY_ID = "UPDATE Film SET statusId = 2 WHERE filmId = ?";
//    private static final String DELETE_FILM_BY_NAME = "UPDATE Film SET statusId = 2 WHERE name = ?";
//    private static final String CREATE_FILM = "INSERT INTO Film (name, genreId, releaseDate, " +
//            "actorId, directorId, statusId) VALUES (?, ?, ?, ?, ?, ?)";
//    private static final String UPDATE_FILM = "UPDATE Film SET name = ?, genreId = ?" +
//            "releaseDate = ?, actorId = ?, directorId = ?, statusId = ? WHERE filmId = ?";
//    private static final Logger LOGGER = LogManager.getLogger();
//    boolean isCreated;
//    private static final String ACTIVE_STATUS = "active";
//    private static final int ACTIVE_STATUS_ID = 1;
//    private static final String INACTIVE_STATUS = "inactive";
//    private static final int INACTIVE_STATUS_ID = 2;
//
//    public FilmDaoImpl() {
//        films = new ArrayList<>();
//    }
//
//    @Override
//    public List<Film> findByName(String name) {
//        try (Connection connection = ConnectionPool.getInstance().getConnection();
//             PreparedStatement statement = connection.prepareStatement(FIND_FILM_BY_NAME)) {
//            statement.setString(1, name);
//            ResultSet resultSet = statement.executeQuery();
//            if (resultSet.next()) {
//                films = initFilms(resultSet);
//            }
//        } catch (SQLException | EntityException e) {
//            LOGGER.error(e);
//        }
//        return films;
//    }
//
//    @Override
//    public List<Film> findByRaitingMoreThan(int raiting) {
//        try (Connection connection = ConnectionPool.getInstance().getConnection();
//             PreparedStatement statement = connection.prepareStatement(FIND_FILM_BY_RAITING)) {
//            statement.setInt(1, raiting);
//            ResultSet resultSet = statement.executeQuery();
//            if (resultSet.next()) {
//                films = initFilms(resultSet);
//            }
//        } catch (SQLException | EntityException e) {
//            LOGGER.error(e);
//        }
//        return films;
//    }
//
//    @Override
//    public Film delete(String name) {
//        try (Connection connection = ConnectionPool.getInstance().getConnection();
//             PreparedStatement statement = connection.prepareStatement(DELETE_FILM_BY_NAME)) {
//            // if (film.getStatusType().equals("active")){                вынести проверку в сервис
//            statement.setString(1, name);
//            statement.executeUpdate();
////            }else {
////                throw new DaoException("User with login: " + login + ", is alredy deleted");
////            }
//        } catch (SQLException e) {
//            LOGGER.error("SQLException: ", e);
//        }
//        return film;
//    }
//
//    @Override
//    public List<Film> findAll() {
//        try (Connection connection = ConnectionPool.getInstance().getConnection();
//             Statement statement = connection.createStatement()) {
//            ResultSet resultSet = statement.executeQuery(FIND_ALL_FILMS);
//            films = initFilms(resultSet);
//        } catch (SQLException | EntityException e) {
//            LOGGER.error(e);
//        }
//        return films;
//    }
//
//    @Override
//    public Film findById(int id) {
//        return null;
//    }
//
////    @Override
//    public Film findById(int id) {
////        try (Connection connection = ConnectionPool.getInstance().getConnection();
////             PreparedStatement statement = connection.prepareStatement(FIND_FILM_BY_ID)) {
////            statement.setInt(1, id);
////            ResultSet resultSet = statement.executeQuery();
////            if (resultSet.next()) {
////                film = initFilm(resultSet);
////            }
////        } catch (SQLException | EntityException e) {
//////            LOGGER.error(e);
////        }
////        return film;
////    }
//
//    @Override
//    public Film delete(Film film) {
//       delete(film.getName());
//        return film;
//    }
//
//    @Override
//    public Film delete(int id) {
//        return null;
//    }
//
////    @Override
////    public Film delete(int id) {
////        try (Connection connection = ConnectionPool.getInstance().getConnection();
////             PreparedStatement statement = connection.prepareStatement(DELETE_FILM_BY_ID)) {
////            statement.setInt(1, id);
////            statement.executeUpdate();
////            film = findById(id);  //?????????????????????????????????????
////            //протеститть
////        } catch (SQLException e) {
//////            LOGGER.error("SQLException: ", e);
////        }
////        return film;
////    }
//
//    @Override
//    public boolean create(Film film) {
//        return false;
//    }
//
//    @Override
//    public Film update(Film film) {
//        return null;
//    }
//
//    private Calendar getDateFromLong(long dateMillis) {
//        Calendar date = new GregorianCalendar();
//        date.setTimeInMillis(dateMillis);
//        return date;
//    }
//
//    private List<Film> initFilms(ResultSet resultSet) throws SQLException, EntityException {
//        while (resultSet.next()) {
//            film = initFilm(resultSet);
//            films.add(film);
//        }
//        return films;
//    }
//
//    private Film initFilm(ResultSet resultSet) throws SQLException, EntityException {
//        film = new Film();
//        int id = resultSet.getInt("filmId");
//        film.setId(id);
//        String name = resultSet.getString(2);
//        film.setName(name);
//        GenreType genreType = GenreType.getGenreTypeByValue(resultSet.getString(4));
//        film.setGenreType(genreType);
//        long longReleaseDate = resultSet.getLong(5);
//        Calendar releaseDate = getDateFromLong(longReleaseDate);
//        film.setReleaseDate(releaseDate);
//        Actor actor = new Actor();
//        actor.setFirstName(resultSet.getString(7)); //!!!!!!!!!!!!!!!!!!! add LastName
//        long longActorBirthday = resultSet.getLong(8);
//        Calendar actorBirthday = getDateFromLong(longReleaseDate);
//        actor.setBirthday(actorBirthday);
//        film.setActor(actor);
//        Director director = new Director();
//        director.setFirstName(resultSet.getString(10)); // add lastName
//        long longDirectorBirthday = resultSet.getLong(11);
//        Calendar directorBirthday = getDateFromLong(longDirectorBirthday);
//        director.setBirthday(directorBirthday);
//        film.setDirector(director);
//        StatusType statusType = StatusType.getStatusTypeByValue(resultSet.getString(13));
//        film.setStatusType(statusType);
//        return film;
//    }
//
//    public static void main(String[] args) {
//        FilmsDao dao = new FilmDaoImpl();
////        dao.delete(1);
//        List<Film> films = dao.findAll();
//        for (Film f : films) {
//            System.out.println(f.toString());
//        }
//    }
//}
