package by.nareiko.fr.model.dao.impl;

import by.nareiko.fr.entity.*;
import by.nareiko.fr.exception.EntityException;
import by.nareiko.fr.factory.DaoFactory;
import by.nareiko.fr.model.dao.FilmsDao;
import by.nareiko.fr.model.dao.request.FilmRequest;
import by.nareiko.fr.pool.ConnectionPool;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
// реализовать методы
public class FilmDaoImpl implements FilmsDao<Film> {
    private DaoFactory daoFactory;
    private FilmRaitingDaoImpl raitingDao;
    private List<Film> films;
    private List<Film> filmsWitnRequiredRaiting;
    private Film film;
    private static final Logger LOGGER = LogManager.getLogger();
    boolean isCreated;
    private static final String ACTIVE_STATUS = "active";
    private static final int ACTIVE_STATUS_ID = 1;

    public FilmDaoImpl() {
        films = new ArrayList<>();
    }

    @Override
    public List<Film> findByName(String name) {
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(FilmRequest.FIND_FILM_BY_NAME)) {
            statement.setString(1, name);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                films = initFilms(resultSet);
            }
        } catch (SQLException | EntityException e) {
            LOGGER.error(e);
        }
        return films;
    }

    @Override
    public List<Film> findByRaitingMoreThan(int raiting) {
        films = findAll();
        for (int i = 0; i < films.size(); i++) {
            if (films.get(i).getRaiting() >= raiting ){
                filmsWitnRequiredRaiting.add(films.get(i));
            }
        }
        return filmsWitnRequiredRaiting;
    }

    @Override
    public List<Film> findAll() {
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(FilmRequest.FIND_ALL_FILMS);
            films = initFilms(resultSet);
        } catch (SQLException | EntityException e) {
            LOGGER.error("SQLException: ", e);
        }
        return films;
    }



    @Override
    public Film findById(int id) {
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(FilmRequest.FIND_FILM_BY_ID)) {
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                film = initFilm(resultSet);
            }
        } catch (SQLException | EntityException e) {
            LOGGER.error("SQLException: ", e);
        }
        return film;
    }

    @Override
    public Film delete(Film film) {
       delete(film.getId());
        return film;
    }

    @Override
    public Film delete(int id) {
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(FilmRequest.DELETE_FILM_BY_ID)) {
            statement.setInt(1, id);
            film = findById(id);
            statement.executeUpdate();
        } catch (SQLException e) {
            LOGGER.error("SQLException: ", e);
        }
        return film;
    }

    @Override
    public boolean create(Film film) {
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(FilmRequest.CREATE_FILM)) {
            statement.setString(1, film.getName());
            int roleId = USER_ROLE_ID;
            if (user.getRoleType().equals(RoleType.getRoleTypeByValue(ADMIN_ROLE))) {
                roleId = ADMIN_ROLE_ID;
            }
            statement.setInt(film.);
            public static final String CREATE_FILM = "INSERT INTO Film (name, genreId, releaseDate, " +
                    "statusId) VALUES (?, ?, ?, ?)";

        } catch (SQLException e) {
            LOGGER.error("SQLException: ", e);
        }
        return ;
    }

    @Override
    public Film update(Film film) {
        return null;
    }

    private Calendar getDateFromLong(long dateMillis) {
        Calendar date = new GregorianCalendar();
        date.setTimeInMillis(dateMillis);
        return date;
    }

    private List<Film> initFilms(ResultSet resultSet) throws SQLException, EntityException {
        while (resultSet.next()) {
            film = initFilm(resultSet);
            films.add(film);
        }
        return films;
    }

    private Film initFilm(ResultSet resultSet) throws SQLException, EntityException {
        film = new Film();
        int id = resultSet.getInt("filmId");
        film.setId(id);
        String name = resultSet.getString(2);
        film.setName(name);
        GenreType genreType = GenreType.getGenreTypeByValue(resultSet.getString(4));
        film.setGenreType(genreType);
        long longReleaseDate = resultSet.getLong(5);
        Calendar releaseDate = getDateFromLong(longReleaseDate);
        film.setReleaseDate(releaseDate);
        Actor actor = new Actor();
        actor.setFirstName(resultSet.getString(7)); //!!!!!!!!!!!!!!!!!!! add LastName
        long longActorBirthday = resultSet.getLong(8);
        Calendar actorBirthday = getDateFromLong(longReleaseDate);
        actor.setBirthday(actorBirthday);
        film.setActor(actor);
        Director director = new Director();
        director.setFirstName(resultSet.getString(10)); // add lastName
        long longDirectorBirthday = resultSet.getLong(11);
        Calendar directorBirthday = getDateFromLong(longDirectorBirthday);
        director.setBirthday(directorBirthday);
        film.setDirector(director);
        StatusType statusType = StatusType.getStatusTypeByValue(resultSet.getString(13));
        film.setStatusType(statusType);
        double raiting = countfinalFilmRaiting(id);
        film.setRaiting(raiting);
        return film;
    }

    public static void main(String[] args) {
        FilmsDao dao = new FilmDaoImpl();
//        dao.delete(1);
        List<Film> films = dao.findAll();
        for (Film f : films) {
            System.out.println(f.toString());
        }
    }

    private double countfinalFilmRaiting(int filmId){
        daoFactory = DaoFactory.getInstance();
        raitingDao = daoFactory.getFilmRaitingDao();
        List<FilmRaiting> raitings = new ArrayList<>();
        raitings = raitingDao.findByFilmId(filmId);
        int counter = 0;
        int sum = 0;
        for (int i = 0; i < raitings.size(); i++) {
            sum += raitings.get(i).getRaiting();
            counter++;
        }
        double finalRaiting = sum/counter;
        return finalRaiting;
    }
}
