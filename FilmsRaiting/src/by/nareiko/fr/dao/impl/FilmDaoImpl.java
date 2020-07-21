//package by.nareiko.fr.dao.impl;
//
//import by.nareiko.fr.dao.*;
//import by.nareiko.fr.entity.Film;
//import by.nareiko.fr.entity.FilmRaiting;
//import by.nareiko.fr.entity.GenreType;
//import by.nareiko.fr.exception.DaoException;
//import by.nareiko.fr.pool.ConnectionPool;
//
//import java.math.BigDecimal;
//import java.math.RoundingMode;
//import java.sql.*;
//import java.util.*;
//
//public class FilmDaoImpl extends EntityInitializer<Film> implements FilmDao<Film> {
//    private static final String ACTIVE_STATUS = "active";
//    private static final FilmDao INSTANCE = new FilmDaoImpl();
//
//    private FilmDaoImpl() {
//    }
//
//    public static FilmDao getInstance() {
//        return INSTANCE;
//    }
//
//    @Override
//    public Optional<Film> findByName(String name) throws DaoException {
//        Optional<Film> film = Optional.empty();
//        try (Connection connection = ConnectionPool.getInstance().getConnection();
//             PreparedStatement statement = connection.prepareStatement(SqlQuery.FIND_FILM_BY_NAME)) {
//            statement.setString(1, name);
//            ResultSet resultSet = statement.executeQuery();
//            if (resultSet.next()) {
//                film = Optional.of(initItem(resultSet));
//            }
//        } catch (SQLException e) {
//            throw new DaoException("Error while searching film by name: ", e);
//        }
//        return film;
//    }
//
//    @Override
//    public List<Film> findByRaitingMoreThan(int raiting) throws DaoException {
//        List<Film> films = findAll();
//        for (int i = 0; i < films.size(); i++) {
//            if (films.get(i).getRaiting() >= raiting) {
//                films.add(films.get(i));
//            }
//        }
//        return films;
//    }
//
//    @Override
//    public List<Film> findAll() throws DaoException {
//        List<Film> films = new ArrayList<>();
//        try (Connection connection = ConnectionPool.getInstance().getConnection();
//             Statement statement = connection.createStatement()) {
//            ResultSet resultSet = statement.executeQuery(SqlQuery.FIND_ALL_FILMS);
//            films = initItems(resultSet);
//        } catch (SQLException e) {
//            throw new DaoException("Error while searching films: ", e);
//        }
//        return films;
//    }
//
//
//    @Override
//    public Optional<Film> findById(int id) throws DaoException {
//        Optional<Film> film = Optional.empty();
//        try (Connection connection = ConnectionPool.getInstance().getConnection();
//             PreparedStatement statement = connection.prepareStatement(SqlQuery.FIND_FILM_BY_ID)) {
//            statement.setInt(1, id);
//            ResultSet resultSet = statement.executeQuery();
//            if (resultSet.next()) {
//                film = Optional.of(initItem(resultSet));
//            }
//        } catch (SQLException e) {
//            throw new DaoException("Error while searching film id: ", e);
//        }
//        return film;
//    }
//
//    @Override
//    public Optional<Film> delete(Film film) throws DaoException {
//        int id = film.getId();
//        Optional<Film> foundFilm = Optional.empty();
//        foundFilm = findById(id);
//        delete(id);
//        return foundFilm;
//    }
//
//    @Override
//    public Optional<Film> delete(int id) throws DaoException {
//        Optional<Film> film;
//        try (Connection connection = ConnectionPool.getInstance().getConnection();
//             PreparedStatement statement = connection.prepareStatement(SqlQuery.DELETE_FILM_BY_ID)) {
//            statement.setInt(1, id);
//            film = findById(id);
//            statement.executeUpdate();
//        } catch (SQLException e) {
//            throw new DaoException("Error while deleting film by id: ", e);
//        }
//        return film;
//    }
//
//    @Override
//    public boolean create(Film film) throws DaoException {
//        boolean isCreated;
//
//        Optional<Film> checkFilm = findByName(film.getName());
//        if (checkFilm.isPresent()) {
//            throw new DaoException("Film, " + film.getName() + ", already exists");
//        }
//
//        try (Connection connection = ConnectionPool.getInstance().getConnection();
//             PreparedStatement statement = connection.prepareStatement(SqlQuery.CREATE_FILM, Statement.RETURN_GENERATED_KEYS)) {
//            statement.setString(1, film.getName());
//            long releaseDate = film.getReleaseDate().getTimeInMillis();
//            statement.setLong(2, releaseDate);
//            statement.setString(3, film.getGenreType().getGenreType());
//            statement.setString(4, ACTIVE_STATUS);
//            statement.executeUpdate();
//            ResultSet resultSet = statement.getGeneratedKeys();
//            if (resultSet.next()) {
//                int id = resultSet.getInt(1);
//                film.setId(id);
//            }
//            isCreated = true;
//        } catch (SQLException e) {
//            throw new DaoException("Error while creating film", e);
//        }
//        return isCreated;
//    }
//
//    @Override
//    public Optional<Film> update(Film film) throws DaoException {
//        try (Connection connection = ConnectionPool.getInstance().getConnection();
//             PreparedStatement statement = connection.prepareStatement(SqlQuery.UPDATE_FILM)) {
//            statement.setString(1, film.getName());
//            long releaseDate = film.getReleaseDate().getTimeInMillis();
//            statement.setLong(2, releaseDate);
//            statement.setString(3, film.getGenreType().getGenreType());
//            statement.setInt(4, film.getId());
//            statement.executeUpdate();
//        } catch (SQLException e) {
//            throw new DaoException("Error while updating film", e);
//        }
//        return Optional.of(film);
//    }
//
//    private Calendar getDateFromLong(long dateMillis) {
//        Calendar date = new GregorianCalendar();
//        date.setTimeInMillis(dateMillis);
//        return date;
//    }
//
//    protected List<Film> initItems(ResultSet resultSet) throws DaoException {
//        List<Film> films = new ArrayList<>();
//        try {
//            while (resultSet.next()) {
//                Film film = initItem(resultSet);
//                films.add(film);
//            }
//        } catch (SQLException e) {
//            throw new DaoException("Error while inizializing films: ", e);
//        }
//        return films;
//    }
//
//    protected Film initItem(ResultSet resultSet) throws DaoException {
//        Film film = new Film();
//        try {
//            int id = resultSet.getInt(ColumnName.FILM_ID);
//            film.setId(id);
//            String name = resultSet.getString(ColumnName.FILM_NAME);
//            film.setName(name);
//            GenreType genreType = GenreType.getGenreTypeByValue(resultSet.getString(ColumnName.GENRE));
//            film.setGenreType(genreType);
//            long longReleaseDate = resultSet.getLong(ColumnName.RELEASE_DATE);
//            Calendar releaseDate = getDateFromLong(longReleaseDate);
//            film.setReleaseDate(releaseDate);
//            double raiting = countfinalFilmRaiting(film.getId());
//            film.setRaiting(raiting);
//        } catch (SQLException e) {
//            throw new DaoException("Error while inizializing film: ", e);
//        }
//        return film;
//    }
//
//    double countfinalFilmRaiting(int filmId) throws DaoException {
//        FilmRaitingDao raitingDao = DaoFactory.getInstance().getFilmRaitingDao();
//        List<FilmRaiting> raitings = new ArrayList<>();
//        raitings = raitingDao.findByFilmId(filmId);
//        int counter = 0;
//        int sum = 0;
//        for (int i = 0; i < raitings.size(); i++) {
//            sum += raitings.get(i).getRaiting();
//            counter++;
//        }
//        double finalRaiting = sum * 1.0 / counter;
//        double result = new BigDecimal(finalRaiting).setScale(2, RoundingMode.HALF_UP).doubleValue();
//        return result;
//    }
//}
