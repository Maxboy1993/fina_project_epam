package by.nareiko.fr.dao.impl;

import by.nareiko.fr.dao.*;
import by.nareiko.fr.entity.*;
import by.nareiko.fr.exception.DaoException;
import by.nareiko.fr.pool.ConnectionPool;

import java.sql.*;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

public class FullFilmInfoDaoImpl implements FilmDao<Film> {
    private static final String ACTIVE_STATUS = "active";
    private static final FilmDao INSTANCE = new FullFilmInfoDaoImpl();

    private FullFilmInfoDaoImpl() {
    }

    public static FilmDao getInstance() {
        return INSTANCE;
    }

    @Override
    public Film findByName(String name) throws DaoException {
        Film film = new Film();
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        EntityTransaction transaction = new EntityTransaction();
        try {
            connection = ConnectionPool.getInstance().getConnection();
            statement = connection.prepareStatement(SQLQuery.FIND_FILM_BY_NAME);

            transaction.beginTransaction(connection);

            statement.setString(1, name);
            resultSet = statement.executeQuery();
            if (resultSet.next()) {
                film = initFilm(resultSet);
            }

            //TODO возможно в цикл?
            statement = connection.prepareStatement(SQLQuery.FIND_ACTORS_BY_FILM_ID);

            statement.setInt(1, film.getId());
            resultSet = statement.executeQuery();
            List<Actor> actors = initActors(resultSet);
            film.setActors(actors);

            statement = connection.prepareStatement(SQLQuery.FIND_DIRECTOR_BY_FILM_ID);

            statement.setInt(1, film.getId());
            resultSet = statement.executeQuery();
            if (resultSet.next()) {
                Director director = initDirector(resultSet);
                film.setDirector(director);
            }

            transaction.commit(connection);

        } catch (SQLException e) {
            transaction.rollback(connection);
            throw new DaoException("Films aren't found by name: ", e);
        } finally {
            transaction.endTransaction(connection);
            close(resultSet);
            close(statement);
            close(connection);
        }
        return film;
    }

    @Override
    public List<Film> findByRaitingMoreThan(int raiting) throws DaoException {
        List<Film> films = findAll();
        for (int i = 0; i < films.size(); i++) {
            if (films.get(i).getRaiting() >= raiting) {
                films.add(films.get(i));
            }
        }
        return films;
    }

    @Override
    public List<Film> findAll() throws DaoException {
        List<Film> films;
        Connection connection = null;
        Statement statement = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        EntityTransaction transaction = new EntityTransaction();
        try {
            connection = ConnectionPool.getInstance().getConnection();
            statement = connection.createStatement();

            transaction.beginTransaction(connection);

            resultSet = statement.executeQuery(SQLQuery.FIND_ALL_FILMS);
            films = initFilms(resultSet);

            //TODO возможно в цикл?
            preparedStatement = connection.prepareStatement(SQLQuery.FIND_ACTORS_BY_FILM_ID);

            for (int i = 0; i < films.size(); i++) {
                Film film = films.get(i);
                preparedStatement.setInt(1, film.getId());
                resultSet = preparedStatement.executeQuery();
                List<Actor> actors = initActors(resultSet);
                film.setActors(actors);
            }

            preparedStatement = connection.prepareStatement(SQLQuery.FIND_DIRECTOR_BY_FILM_ID);

            for (int i = 0; i < films.size(); i++) {
                Film film = films.get(i);
                preparedStatement.setInt(1, film.getId());
                resultSet = preparedStatement.executeQuery();
                if (resultSet.next()) {
                    Director director = initDirector(resultSet);
                    film.setDirector(director);
                }
            }

            transaction.commit(connection);

        } catch (SQLException e) {
            transaction.rollback(connection);
            throw new DaoException("Films aren't found: ", e);
        } finally {
            transaction.endTransaction(connection);
            close(resultSet);
            close(statement);
            close(preparedStatement);
            close(connection);
        }
        return films;
    }

    @Override
    public Film findById(int id) throws DaoException {
        Film film = new Film();
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        EntityTransaction transaction = new EntityTransaction();
        try {
            connection = ConnectionPool.getInstance().getConnection();
            statement = connection.prepareStatement(SQLQuery.FIND_FILM_BY_ID);

            transaction.beginTransaction(connection);

            statement.setInt(1, id);
            resultSet = statement.executeQuery();
            if (resultSet.next()) {
                film = initFilm(resultSet);
            }

            //TODO возможно в цикл?
            statement = connection.prepareStatement(SQLQuery.FIND_ACTORS_BY_FILM_ID);

            statement.setInt(1, film.getId());
            resultSet = statement.executeQuery();
            List<Actor> actors = initActors(resultSet);
            film.setActors(actors);


            statement = connection.prepareStatement(SQLQuery.FIND_DIRECTOR_BY_FILM_ID);

            statement.setInt(1, film.getId());
            resultSet = statement.executeQuery();
            if (resultSet.next()) {
                Director director = initDirector(resultSet);
                film.setDirector(director);
            }

            transaction.commit(connection);

        } catch (SQLException e) {
            transaction.rollback(connection);
            throw new DaoException("Film isn't found by id: ", e);
        } finally {
            transaction.endTransaction(connection);
            close(resultSet);
            close(statement);
            close(connection);
        }
        return film;
    }

    @Override
    public Film delete(Film film) throws DaoException {
        delete(film.getId());
        return film;
    }

    @Override
    public Film delete(int id) throws DaoException {
        Film film;
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(SQLQuery.DELETE_FILM_BY_ID)) {
            statement.setInt(1, id);
            film = findById(id);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException("Film isn't deleted by id: ", e);
        }
        return film;
    }

    @Override
    public boolean create(Film film) throws DaoException {
        boolean isCreated;
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        EntityTransaction transaction = new EntityTransaction();

        Film checkFilm = findByName(film.getName());
        if (!checkFilm.getName().equalsIgnoreCase(film.getName())) {
            throw new DaoException("Film, " + film.getName() + ", already exists");
        }

        try {
            connection = ConnectionPool.getInstance().getConnection();
            statement = connection.prepareStatement(SQLQuery.CREATE_FILM, Statement.RETURN_GENERATED_KEYS);

            transaction.beginTransaction(connection);

            statement.setString(1, film.getName());
            long releaseDate = film.getReleaseDate().getTimeInMillis();
            statement.setLong(2, releaseDate);
            statement.setString(3, film.getGenreType().getGenreType());
            statement.setString(4, ACTIVE_STATUS);
            statement.executeUpdate();
            resultSet = statement.getGeneratedKeys();
            if (resultSet.next()) {
                int id = resultSet.getInt(1);
                film.setId(id);
            }

            statement = connection.prepareStatement(SQLQuery.CREATE_ACTOR, Statement.RETURN_GENERATED_KEYS);

            List<Actor> actors = film.getActors();
            for (int i = 0; i < actors.size(); i++) {
                Actor actor = actors.get(i);
                statement.setString(1, actor.getFirstName());
                statement.setString(2, actor.getLastName());
                long birthday = actor.getBirthday().getTimeInMillis();
                statement.setLong(3, birthday);
                statement.executeUpdate();
                resultSet = statement.getGeneratedKeys();
                if (resultSet.next()) {
                    int id = resultSet.getInt(1);
                    actor.setId(id);
                }
            }

            statement = connection.prepareStatement(SQLQuery.CREATE_DIRECTOR, Statement.RETURN_GENERATED_KEYS);

            Director director = film.getDirector();
            statement.setString(1, director.getFirstName());
            statement.setString(2, director.getLastName());
            long birtgday = director.getBirthday().getTimeInMillis();
            statement.setLong(3, birtgday);
            statement.executeUpdate();
            resultSet = statement.getGeneratedKeys();
            if (resultSet.next()) {
                int id = resultSet.getInt(1);
                director.setId(id);
            }

            statement = connection.prepareStatement(SQLQuery.MAPPING_FILM_WITH_PERSON);

            statement.setInt(1, film.getId());
            statement.setInt(2, director.getId());
            statement.executeUpdate();

            //TODO is necessary duplicate?
            statement = connection.prepareStatement(SQLQuery.MAPPING_FILM_WITH_PERSON);

            for (int i = 0; i < actors.size(); i++) {
                statement.setInt(1, film.getId());
                statement.setInt(2, actors.get(i).getId());
                statement.executeUpdate();
            }

            transaction.commit(connection);
            isCreated = true;
        } catch (SQLException e) {
            transaction.rollback(connection);
            throw new DaoException("Film isn't created", e);
        } finally {
            transaction.endTransaction(connection);
            close(resultSet);
            close(statement);
            close(connection);
        }
        return isCreated;
    }

    @Override
    public Film update(Film film) throws DaoException {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        EntityTransaction transaction = new EntityTransaction();

        try {
            connection = ConnectionPool.getInstance().getConnection();
            statement = connection.prepareStatement(SQLQuery.UPDATE_FILM);

            transaction.beginTransaction(connection);

            statement.setString(1, film.getName());
            long releaseDate = film.getReleaseDate().getTimeInMillis();
            statement.setLong(2, releaseDate);
            statement.setString(3, film.getGenreType().getGenreType());
            statement.setInt(4, film.getId());
            statement.executeUpdate();

            statement = connection.prepareStatement(SQLQuery.UPDATE_ACTOR);

            List<Actor> actors = film.getActors();
            for (int i = 0; i < actors.size(); i++) {
                Actor actor = actors.get(i);
                statement.setString(1, actor.getFirstName());
                statement.setString(2, actor.getLastName());
                long birthday = actor.getBirthday().getTimeInMillis();
                statement.setLong(3, birthday);
                statement.setInt(4, actor.getId());
                statement.executeUpdate();
            }

            statement = connection.prepareStatement(SQLQuery.UPDATE_DIRECTOR);

            Director director = film.getDirector();
            statement.setString(1, director.getFirstName());
            statement.setString(2, director.getLastName());
            long birtgday = director.getBirthday().getTimeInMillis();
            statement.setLong(3, birtgday);
            statement.setInt(4, director.getId());
            statement.executeUpdate();

            transaction.commit(connection);
        } catch (SQLException e) {
            transaction.rollback(connection);
            throw new DaoException("Film isn't created", e);
        } finally {
            transaction.endTransaction(connection);
            close(resultSet);
            close(statement);
            close(connection);
        }
        return null;
    }

    private Calendar getDateFromLong(long dateMillis) {
        Calendar date = new GregorianCalendar();
        date.setTimeInMillis(dateMillis);
        return date;
    }

    List<Film> initFilms(ResultSet resultSet) throws DaoException {
        List<Film> films = new ArrayList<>();
        try {
            while (resultSet.next()) {
                Film film = initFilm(resultSet);
                films.add(film);
            }
        } catch (SQLException e) {
            throw new DaoException("Films aren't inizialized: ", e);
        }
        return films;
    }

    private Film initFilm(ResultSet resultSet) throws DaoException {
        Film film = new Film();
        try {
            int id = resultSet.getInt(ColumnName.FILM_ID);
            film.setId(id);
            String name = resultSet.getString(ColumnName.FILM_NAME);
            film.setName(name);
            GenreType genreType = GenreType.getGenreTypeByValue(resultSet.getString(ColumnName.GENRE));
            film.setGenreType(genreType);
            long longReleaseDate = resultSet.getLong(ColumnName.RELEASE_DATE);
            Calendar releaseDate = getDateFromLong(longReleaseDate);
            film.setReleaseDate(releaseDate);
            double raiting = countfinalFilmRaiting(film.getId());
            film.setRaiting(raiting);
        } catch (SQLException e) {
            throw new DaoException("Film isn't inizialized: ", e);
        }
        return film;
    }

    private List<Actor> initActors(ResultSet resultSet) throws DaoException {
        List<Actor> actors = new ArrayList<>();
        try {
            while (resultSet.next()) {
                Actor actor = initActor(resultSet);
                actors.add(actor);
            }
        } catch (SQLException e) {
            throw new DaoException("Actors aren't inizialized: ", e);
        }
        return actors;
    }

    private Actor initActor(ResultSet resultSet) throws DaoException {
        Actor actor = new Actor();
        try {
            int id = resultSet.getInt(ColumnName.PERSON_ID);
            actor.setId(id);
            String firstName = resultSet.getString(ColumnName.FIRST_NAME);
            actor.setFirstName(firstName);
            String lastName = resultSet.getString(ColumnName.LAST_NAME);
            actor.setLastName(lastName);
            long longBirthday = resultSet.getLong(ColumnName.BIRTHDAY);
            Calendar birthday = getDateFromLong(longBirthday);
            actor.setBirthday(birthday);
        } catch (SQLException e) {
            throw new DaoException("Actor isn't inizialized: ", e);
        }
        return actor;
    }

    private List<Director> initDirectors(ResultSet resultSet) throws DaoException {
        List<Director> directors = new ArrayList<>();
        try {
            while (resultSet.next()) {
                Director director = initDirector(resultSet);
                directors.add(director);
            }
        } catch (SQLException e) {
            throw new DaoException("Directors aren't inizialized: ", e);
        }
        return directors;
    }

    private Director initDirector(ResultSet resultSet) throws DaoException {
        Director director = new Director();
        try {
            int id = resultSet.getInt(ColumnName.PERSON_ID);
            director.setId(id);
            String firstName = resultSet.getString(ColumnName.FIRST_NAME);
            director.setFirstName(firstName);
            String lastName = resultSet.getString(ColumnName.LAST_NAME);
            director.setLastName(lastName);
            long longBirthday = resultSet.getLong(ColumnName.BIRTHDAY);
            Calendar birthday = getDateFromLong(longBirthday);
            director.setBirthday(birthday);
        } catch (SQLException e) {
            throw new DaoException("Director isn't inizialized: ", e);
        }
        return director;
    }

    double countfinalFilmRaiting(int filmId) throws DaoException {
        DaoFactory daoFactory = DaoFactory.getInstance();
        FilmRaitingDao raitingDao = daoFactory.getFilmRaitingDao();
        List<FilmRaiting> raitings = new ArrayList<>();
        raitings = raitingDao.findByFilmId(filmId);
        int counter = 0;
        int sum = 0;
        for (int i = 0; i < raitings.size(); i++) {
            sum += raitings.get(i).getRaiting();
            counter++;
        }
        double finalRaiting = sum / counter;
        return finalRaiting;
    }
}
