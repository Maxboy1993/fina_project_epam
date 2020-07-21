package by.nareiko.fr.dao.impl;

import by.nareiko.fr.dao.FilmDao;
import by.nareiko.fr.dao.SqlQuery;
import by.nareiko.fr.dao.impl.entittymapper.ActorMapper;
import by.nareiko.fr.dao.impl.entittymapper.DirectorMapper;
import by.nareiko.fr.dao.impl.entittymapper.FullFilmInfoMapper;
import by.nareiko.fr.entity.Actor;
import by.nareiko.fr.entity.Director;
import by.nareiko.fr.entity.Film;
import by.nareiko.fr.exception.DaoException;
import by.nareiko.fr.pool.ConnectionPool;

import java.sql.*;
import java.util.List;
import java.util.Optional;

public class FullFilmInfoDaoImpl implements FilmDao<Film> {
    private static final String ACTIVE_STATUS = "active";
    private static final FilmDao INSTANCE = new FullFilmInfoDaoImpl();

    private FullFilmInfoDaoImpl() {
    }

    public static FilmDao getInstance() {
        return INSTANCE;
    }


    @Override
    public Optional<Film> findByName(String name) throws DaoException {
        Film film = new Film();
        ResultSet resultSet = null;
        FullFilmInfoMapper filmInfoMapper = new FullFilmInfoMapper();
        ActorMapper actorMapper = new ActorMapper();
        DirectorMapper directorMapper = new DirectorMapper();

        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement filmStatement = connection.prepareStatement(SqlQuery.FIND_FILM_BY_NAME);
             PreparedStatement actorStatement = connection.prepareStatement(SqlQuery.FIND_ACTORS_BY_FILM_ID);
             PreparedStatement directorStatement = connection.prepareStatement(SqlQuery.FIND_DIRECTOR_BY_FILM_ID)) {

            filmStatement.setString(1, name);
            resultSet = filmStatement.executeQuery();
            if (resultSet.next()) {
                film = filmInfoMapper.initEntity(resultSet);
            }

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
        } catch (SQLException e) {
            throw new DaoException("Error while searching film by name: ", e);
        } finally {
            close(resultSet);
        }
        return Optional.of(film);
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
             PreparedStatement directorStatement = connection.prepareStatement(SqlQuery.FIND_DIRECTOR_BY_FILM_ID)) {

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
        } catch (SQLException e) {
            throw new DaoException("Error while searching films: ", e);
        } finally {
            close(resultSet);
        }
        return films;
    }

    @Override
    public Optional<Film> findById(int id) throws DaoException {
        Film film = new Film();
        ResultSet resultSet = null;
        FullFilmInfoMapper filmInfoMapper = new FullFilmInfoMapper();
        ActorMapper actorMapper = new ActorMapper();
        DirectorMapper directorMapper = new DirectorMapper();

        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement filmStatement = connection.prepareStatement(SqlQuery.FIND_FILM_BY_ID);
             PreparedStatement actorStatement = connection.prepareStatement(SqlQuery.FIND_ACTORS_BY_FILM_ID);
             PreparedStatement directorStatement = connection.prepareStatement(SqlQuery.FIND_DIRECTOR_BY_FILM_ID)) {

            filmStatement.setInt(1, id);
            resultSet = filmStatement.executeQuery();
            if (resultSet.next()) {
                film = filmInfoMapper.initEntity(resultSet);
            }

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
        } catch (SQLException e) {
            throw new DaoException("Error while searching film by id: ", e);
        } finally {
            close(resultSet);
        }
        return Optional.of(film);
    }

    @Override
    public Optional<Film> delete(Film film) throws DaoException {
        int id = film.getId();
        Optional<Film> foundFilm = findById(id);
        delete(id);
        return foundFilm;
    }

    @Override
    public Optional<Film> delete(int id) throws DaoException {
        Optional<Film> film;
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(SqlQuery.DELETE_FILM_BY_ID)) {
            statement.setInt(1, id);
            film = findById(id);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException("Error while deleting film by id: ", e);
        }
        return film;
    }

    @Override
    public boolean create(Film film) throws DaoException {
        boolean isCreated;
        final Connection connection = ConnectionPool.getInstance().getConnection();
        ResultSet resultSet = null;
        TransactionManager transaction = new TransactionManager();

        Optional<Film> checkFilm = findByName(film.getName());
        if (checkFilm.isPresent()) {
            throw new DaoException("Film, " + film.getName() + ", already exists");
        }

        try (connection;
             PreparedStatement filmStatement = connection.prepareStatement(SqlQuery.CREATE_FILM, Statement.RETURN_GENERATED_KEYS);
             PreparedStatement actorStatement = connection.prepareStatement(SqlQuery.CREATE_ACTOR, Statement.RETURN_GENERATED_KEYS);
             PreparedStatement directorStatement = connection.prepareStatement(SqlQuery.CREATE_DIRECTOR, Statement.RETURN_GENERATED_KEYS);
             PreparedStatement mappingStatement = connection.prepareStatement(SqlQuery.MAPPING_FILM_WITH_PERSON)) {

            filmStatement.setString(1, film.getName());
            long releaseDate = film.getReleaseDate().getTimeInMillis();
            filmStatement.setLong(2, releaseDate);
            filmStatement.setString(3, film.getGenreType().getGenreType());
            filmStatement.setString(4, ACTIVE_STATUS);
            filmStatement.executeUpdate();
            resultSet = filmStatement.getGeneratedKeys();
            if (resultSet.next()) {
                int id = resultSet.getInt(1);
                film.setId(id);
            }

            List<Actor> actors = film.getActors();
            for (int i = 0; i < actors.size(); i++) {
                Actor actor = actors.get(i);
                actorStatement.setString(1, actor.getFirstName());
                actorStatement.setString(2, actor.getLastName());
                long birthday = actor.getBirthday().getTimeInMillis();
                actorStatement.setLong(3, birthday);
                actorStatement.executeUpdate();
                resultSet = actorStatement.getGeneratedKeys();
                if (resultSet.next()) {
                    int id = resultSet.getInt(1);
                    actor.setId(id);
                }
            }

            Director director = film.getDirector();
            directorStatement.setString(1, director.getFirstName());
            directorStatement.setString(2, director.getLastName());
            long birtgday = director.getBirthday().getTimeInMillis();
            directorStatement.setLong(3, birtgday);
            directorStatement.executeUpdate();
            resultSet = directorStatement.getGeneratedKeys();
            if (resultSet.next()) {
                int id = resultSet.getInt(1);
                director.setId(id);
            }

            mappingStatement.setInt(1, film.getId());
            mappingStatement.setInt(2, director.getId());
            mappingStatement.executeUpdate();

            for (int i = 0; i < actors.size(); i++) {
                mappingStatement.setInt(1, film.getId());
                mappingStatement.setInt(2, actors.get(i).getId());
                mappingStatement.executeUpdate();
            }

            transaction.commit(connection);
            isCreated = true;
        } catch (SQLException e) {
            transaction.rollback(connection);
            throw new DaoException("Error while creating film", e);
        } finally {
            transaction.endTransaction(connection);
            close(resultSet);
        }
        return isCreated;
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
            long releaseDate = film.getReleaseDate().getTimeInMillis();
            filmStatement.setLong(2, releaseDate);
            filmStatement.setString(3, film.getGenreType().getGenreType());
            filmStatement.setInt(4, film.getId());
            filmStatement.executeUpdate();

            List<Actor> actors = film.getActors();
            for (int i = 0; i < actors.size(); i++) {
                Actor actor = actors.get(i);
                actorStatement.setString(1, actor.getFirstName());
                actorStatement.setString(2, actor.getLastName());
                long birthday = actor.getBirthday().getTimeInMillis();
                actorStatement.setLong(3, birthday);
                actorStatement.setInt(4, actor.getId());
                actorStatement.executeUpdate();
            }

            Director director = film.getDirector();
            directorStatement.setString(1, director.getFirstName());
            directorStatement.setString(2, director.getLastName());
            long birtgday = director.getBirthday().getTimeInMillis();
            directorStatement.setLong(3, birtgday);
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
}
