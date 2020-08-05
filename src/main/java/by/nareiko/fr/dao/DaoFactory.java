package by.nareiko.fr.dao;

import by.nareiko.fr.dao.impl.*;
import by.nareiko.fr.entity.Actor;
import by.nareiko.fr.entity.Director;
import by.nareiko.fr.entity.Film;
import by.nareiko.fr.entity.FilmRaiting;
import by.nareiko.fr.entity.Review;
import by.nareiko.fr.entity.User;

public final class DaoFactory {
    private static final DaoFactory INSTANCE = new DaoFactory();

    private FilmPersonDao<Actor> actorDao;
    private FilmPersonDao<Director> directorDao;
    //    private FilmDao filmDao;
    private FilmDao<Film> fullFilmInfoDao;
    private FilmRatingDao<FilmRaiting> filmRatingDao;
    private ReviewDao<Review> reviewDao;
    private UserDao<User> userDao;
    private PosterDao filmPosterDao;

    private DaoFactory() {
    }

    public static DaoFactory getInstance() {
        return INSTANCE;
    }

    public FilmPersonDao<Actor> getActorDao() {
        actorDao = ActorDaoImpl.getInstance();
        return actorDao;
    }

    public FilmPersonDao<Director> getDirectorDao() {
        directorDao = DirectorDaoImpl.getInstance();
        return directorDao;
    }

//    public FilmDao getFilmDao() {
//        filmDao = FilmDaoImpl.getInstance();
//        return filmDao;
//    }

    public FilmDao<Film> getFullFilmInfoDao() {
        fullFilmInfoDao = FullFilmInfoDaoImpl.getInstance();
        return fullFilmInfoDao;
    }


    public FilmRatingDao<FilmRaiting> getFilmRatingDao() {
        filmRatingDao = FilmRatingDaoImpl.getInstance();
        return filmRatingDao;
    }

    public ReviewDao<Review> getReviewDao() {
        reviewDao = ReviewDaoImpl.getInstance();
        return reviewDao;
    }

    public UserDao<User> getUserDao() {
        userDao = UserDaoImpl.getInstance();
        return userDao;
    }

    public PosterDao getFilmPosterDao() {
        filmPosterDao = FilmPosterDaoImpl.getInstance();
        return filmPosterDao;
    }
}

