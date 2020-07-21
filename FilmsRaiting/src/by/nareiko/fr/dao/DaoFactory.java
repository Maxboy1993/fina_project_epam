package by.nareiko.fr.dao;

import by.nareiko.fr.dao.impl.*;

public final class DaoFactory {
    private static final DaoFactory INSTANCE = new DaoFactory();

    private PersonDao actorDao;
    private PersonDao directorDao;
//    private FilmDao filmDao;
    private FilmDao fullFilmInfoDao;
    private FilmRaitingDao filmRaitingDao;
    private ReviewDao reviewDao;
    private UserDao userDao;

    private DaoFactory() {
    }

    public static DaoFactory getInstance() {
        return INSTANCE;
    }

    public PersonDao getActorDao() {
        actorDao = ActorDaoImpl.getInstance();
        return actorDao;
    }

    public PersonDao getDirectorDao() {
        directorDao = DirectorDaoImpl.getInstance();
        return directorDao;
    }

//    public FilmDao getFilmDao() {
//        filmDao = FilmDaoImpl.getInstance();
//        return filmDao;
//    }

    public FilmDao getFullFilmInfoDao() {
        fullFilmInfoDao = FullFilmInfoDaoImpl.getInstance();
        return fullFilmInfoDao;
    }


    public FilmRaitingDao getFilmRaitingDao() {
        filmRaitingDao = FilmRaitingDaoImpl.getInstance();
        return filmRaitingDao;
    }

    public ReviewDao getReviewDao() {
        reviewDao = ReviewDaoImpl.getInstance();
        return reviewDao;
    }

    public UserDao getUserDao() {
        userDao = UserDaoImpl.getInstance();
        return userDao;
    }
}

