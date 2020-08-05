package by.nareiko.fr.dao.impl;

import by.nareiko.fr.dao.FilmDao;
import by.nareiko.fr.dao.SqlColumnName;
import by.nareiko.fr.dao.SqlQuery;
import by.nareiko.fr.dao.impl.entittymapper.ActorMapper;
import by.nareiko.fr.dao.impl.entittymapper.DirectorMapper;
import by.nareiko.fr.dao.impl.entittymapper.FullFilmInfoMapper;
import by.nareiko.fr.entity.Actor;
import by.nareiko.fr.entity.Director;
import by.nareiko.fr.entity.Film;
import by.nareiko.fr.exception.DaoException;
import by.nareiko.fr.pool.ConnectionPool;
import org.apache.commons.codec.binary.Base64;

import java.sql.*;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Optional;

public class FullFilmInfoDaoImpl implements FilmDao<Film> {
    private static final String ACTIVE_STATUS = "active";
    private static final String FILM_NAME_PLACE_REGEX = "%";
    private static final String SPLIT_REGEX = "-";
    private static final int YEAR_INDEX = 0;
    private static final int MONTH_INDEX = 1;
    private static final int DAY_INDEX = 2;
    private static final FilmDao<Film> INSTANCE = new FullFilmInfoDaoImpl();


    private FullFilmInfoDaoImpl() {
    }

    public static FilmDao<Film> getInstance() {
        return INSTANCE;
    }


    @Override
    public List<Film> findByName(String name) throws DaoException {
        List<Film> films;
        ResultSet resultSet = null;
        FullFilmInfoMapper filmInfoMapper = new FullFilmInfoMapper();
        ActorMapper actorMapper = new ActorMapper();
        DirectorMapper directorMapper = new DirectorMapper();

        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement filmStatement = connection.prepareStatement(SqlQuery.FIND_FILM_BY_NAME);
             PreparedStatement actorStatement = connection.prepareStatement(SqlQuery.FIND_ACTORS_BY_FILM_ID);
             PreparedStatement posterStatement = connection.prepareStatement(SqlQuery.FIND_FILM_POSTER);
             PreparedStatement directorStatement = connection.prepareStatement(SqlQuery.FIND_DIRECTOR_BY_FILM_ID)) {

            filmStatement.setString(1, FILM_NAME_PLACE_REGEX + name + FILM_NAME_PLACE_REGEX);
            resultSet = filmStatement.executeQuery();
            films = filmInfoMapper.initEntities(resultSet);

            for (int i = 0; i < films.size(); i++) {
                Film film = films.get(i);
                actorStatement.setInt(1, film.getId());
                resultSet = actorStatement.executeQuery();
                List<Actor> actors = actorMapper.initEntities(resultSet);
                film.setActors(actors);
            }

            for (int i = 0; i < films.size(); i++) {
                Film film = films.get(i);
                directorStatement.setInt(1, film.getId());
                resultSet = directorStatement.executeQuery();
                if (resultSet.next()) {
                    Director director = directorMapper.initEntity(resultSet);
                    film.setDirector(director);
                }
            }

            for (int i = 0; i < films.size(); i++) {
                Film film = films.get(i);
                posterStatement.setInt(1, film.getId());
                resultSet = posterStatement.executeQuery();
                if (resultSet.next()) {
                    Blob blobPoster = resultSet.getBlob(SqlColumnName.PSOTER);
                    byte[] posterData = blobPoster.getBytes(1, (int) blobPoster.length());
                    film.setPoster(Base64.encodeBase64String(posterData));
                }
            }
        } catch (SQLException e) {
            throw new DaoException("Error while searching film by name: ", e);
        } finally {
            close(resultSet);
        }
        return films;
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
        ResultSet resultSet = null;
        FullFilmInfoMapper filmInfoMapper = new FullFilmInfoMapper();
        ActorMapper actorMapper = new ActorMapper();
        DirectorMapper directorMapper = new DirectorMapper();

        try (Connection connection = ConnectionPool.getInstance().getConnection();
             Statement filmStatement = connection.createStatement();
             PreparedStatement actorStatement = connection.prepareStatement(SqlQuery.FIND_ACTORS_BY_FILM_ID);
             PreparedStatement directorStatement = connection.prepareStatement(SqlQuery.FIND_DIRECTOR_BY_FILM_ID);
             PreparedStatement posterStatement = connection.prepareStatement(SqlQuery.FIND_FILM_POSTER)) {

            resultSet = filmStatement.executeQuery(SqlQuery.FIND_ALL_FILMS);
            films = filmInfoMapper.initEntities(resultSet);

            for (int i = 0; i < films.size(); i++) {
                Film film = films.get(i);
                actorStatement.setInt(1, film.getId());
                resultSet = actorStatement.executeQuery();
                List<Actor> actors = actorMapper.initEntities(resultSet);
                film.setActors(actors);
            }

            for (int i = 0; i < films.size(); i++) {
                Film film = films.get(i);
                directorStatement.setInt(1, film.getId());
                resultSet = directorStatement.executeQuery();
                if (resultSet.next()) {
                    Director director = directorMapper.initEntity(resultSet);
                    film.setDirector(director);
                }
            }

            for (int i = 0; i < films.size(); i++) {
                Film film = films.get(i);
                posterStatement.setInt(1, film.getId());
                resultSet = posterStatement.executeQuery();
                if (resultSet.next()) {
                    Blob blobPoster = resultSet.getBlob(SqlColumnName.PSOTER);
                    byte[] posterData = blobPoster.getBytes(1, (int) blobPoster.length());
                    film.setPoster(Base64.encodeBase64String(posterData));
                }
            }
        } catch (SQLException e) {
            throw new DaoException("Error while searching films: ", e);
        } finally {
            close(resultSet);
        }
        return films;
    }

    @Override
    public Optional<Film> findById(int id) throws DaoException {
        Film film = null;
        ResultSet resultSet = null;
        FullFilmInfoMapper filmInfoMapper = new FullFilmInfoMapper();
        ActorMapper actorMapper = new ActorMapper();
        DirectorMapper directorMapper = new DirectorMapper();

        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement filmStatement = connection.prepareStatement(SqlQuery.FIND_FILM_BY_ID);
             PreparedStatement actorStatement = connection.prepareStatement(SqlQuery.FIND_ACTORS_BY_FILM_ID);
             PreparedStatement posterStatement = connection.prepareStatement(SqlQuery.FIND_FILM_POSTER);
             PreparedStatement directorStatement = connection.prepareStatement(SqlQuery.FIND_DIRECTOR_BY_FILM_ID)) {

            filmStatement.setInt(1, id);
            resultSet = filmStatement.executeQuery();
            if (resultSet.next()) {
                film = filmInfoMapper.initEntity(resultSet);

                actorStatement.setInt(1, film.getId());
                resultSet = actorStatement.executeQuery();
                List<Actor> actors = actorMapper.initEntities(resultSet);
                film.setActors(actors);

                directorStatement.setInt(1, film.getId());
                resultSet = directorStatement.executeQuery();
                if (resultSet.next()) {
                    Director director = directorMapper.initEntity(resultSet);
                    film.setDirector(director);
                }

                posterStatement.setInt(1, film.getId());
                resultSet = posterStatement.executeQuery();
                if (resultSet.next()) {
                    Blob blobPoster = resultSet.getBlob(SqlColumnName.PSOTER);
                    byte[] posterData = blobPoster.getBytes(1, (int) blobPoster.length());
                    film.setPoster(Base64.encodeBase64String(posterData));
                }
            }
        } catch (SQLException e) {
            throw new DaoException("Error while searching film by id: ", e);
        } finally {
            close(resultSet);
        }
        return Optional.ofNullable(film);
    }

    @Override
    public boolean delete(Film film) throws DaoException {
        int id = film.getId();
        boolean isDeleted = delete(id);
        return isDeleted;
    }

    @Override
    public boolean delete(int id) throws DaoException {
        boolean isDeleted;
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(SqlQuery.DELETE_FILM_BY_ID)) {
            statement.setInt(1, id);
            statement.executeUpdate();
            isDeleted = true;
        } catch (SQLException e) {
            throw new DaoException("Error while deleting film by id: ", e);
        }
        return isDeleted;
    }

    @Override
    public Optional<Film> create(Film film) throws DaoException {
        final Connection connection = ConnectionPool.getInstance().getConnection();
        ResultSet resultSet = null;
        TransactionManager transaction = new TransactionManager();

        Optional<Film> optionalFilm;
        try (connection;
             PreparedStatement filmStatement = connection.prepareStatement(SqlQuery.CREATE_FILM, Statement.RETURN_GENERATED_KEYS);
             PreparedStatement mappingStatement = connection.prepareStatement(SqlQuery.MAPPING_FILM_WITH_PERSON)) {

            transaction.beginTransaction(connection);

            filmStatement.setString(1, film.getName());
            String releaseDate = film.getReleaseDate();
            long date = modifyDate(releaseDate);
            filmStatement.setLong(2, date);
            filmStatement.setString(3, film.getGenreType().getGenreType());
            filmStatement.setString(4, ACTIVE_STATUS);
            filmStatement.executeUpdate();
            resultSet = filmStatement.getGeneratedKeys();
            if (resultSet.next()) {
                int id = resultSet.getInt(1);
                film.setId(id);

                mappingStatement.setInt(1, film.getId());
                mappingStatement.setInt(2, film.getDirector().getId());
                mappingStatement.executeUpdate();

                for (int i = 0; i < film.getActors().size(); i++) {
                    mappingStatement.setInt(1, film.getId());
                    mappingStatement.setInt(2, film.getActors().get(i).getId());
                    mappingStatement.executeUpdate();
                }
            }
            transaction.commit(connection);
            optionalFilm = findById(film.getId());
        } catch (SQLException e) {
            transaction.rollback(connection);
            throw new DaoException("Error while creating film", e);
        } finally {
            transaction.endTransaction(connection);
            close(resultSet);
        }
        return optionalFilm;
    }

    @Override
    public Optional<Film> update(Film film) throws DaoException {
        final Connection connection = ConnectionPool.getInstance().getConnection();
        ResultSet resultSet = null;
        TransactionManager transaction = new TransactionManager();

        try (connection;
             PreparedStatement filmStatement = connection.prepareStatement(SqlQuery.UPDATE_FILM);
             PreparedStatement actorStatement = connection.prepareStatement(SqlQuery.UPDATE_ACTOR);
             PreparedStatement directorStatement = connection.prepareStatement(SqlQuery.UPDATE_DIRECTOR)) {


            transaction.beginTransaction(connection);

            filmStatement.setString(1, film.getName());
            String releaseDate = film.getReleaseDate();
            long date = modifyDate(releaseDate);
            filmStatement.setLong(2, date);
            filmStatement.setString(3, film.getGenreType().getGenreType());
            filmStatement.setInt(4, film.getId());
            filmStatement.executeUpdate();

            List<Actor> actors = film.getActors();
            for (int i = 0; i < actors.size(); i++) {
                Actor actor = actors.get(i);
                actorStatement.setString(1, actor.getFirstName());
                actorStatement.setString(2, actor.getLastName());
                String birthday = actor.getBirthday();
                date = modifyDate(birthday);
                actorStatement.setLong(3, date);
                actorStatement.setInt(4, actor.getId());
                actorStatement.executeUpdate();
            }

            Director director = film.getDirector();
            directorStatement.setString(1, director.getFirstName());
            directorStatement.setString(2, director.getLastName());
            String birthday = director.getBirthday();
            date = modifyDate(birthday);
            directorStatement.setLong(3, date);
            directorStatement.setInt(4, director.getId());
            directorStatement.executeUpdate();

            transaction.commit(connection);
        } catch (SQLException e) {
            transaction.rollback(connection);
            throw new DaoException("Error while updating film", e);
        } finally {
            transaction.endTransaction(connection);
            close(resultSet);
        }
        return Optional.ofNullable(film);
    }

    private long modifyDate(String birthday) {
        String[] date = birthday.split(SPLIT_REGEX);
        int year = Integer.parseInt(date[YEAR_INDEX]);
        int month = Integer.parseInt(date[MONTH_INDEX]) - 1;
        int day = Integer.parseInt(date[DAY_INDEX]);

        Calendar calendarBirthday = new GregorianCalendar();
        calendarBirthday.set(year, month, day);
        long birthdayMillis = calendarBirthday.getTimeInMillis();
        return birthdayMillis;
    }
}
