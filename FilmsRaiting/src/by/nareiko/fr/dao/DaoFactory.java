package by.nareiko.fr.dao;

import by.nareiko.fr.dao.impl.*;

public final class DaoFactory {
    private static final DaoFactory DAO_FACTORY = new DaoFactory();

    private PersonDao actorDao;
    private PersonDao directorDao;
    private FilmsDao filmDao;
    private FilmRaitingDao filmRaitingDao;
    private ReviewDao reviewDao;
    private UserDao userDao;

    private DaoFactory() {
    }

    public static DaoFactory getInstance() {
        return DAO_FACTORY;
    }

    public PersonDao getActorDao() {
        if (actorDao == null) {
            actorDao = new ActorDaoImpl();
        }
        return actorDao;
    }

    public PersonDao getDirectorDao() {
        if (directorDao == null) {
            directorDao = new DirectorDaoImpl();
        }
        return directorDao;
    }

    public FilmsDao getFilmDao() {
        if (filmDao == null) {
            filmDao = new FilmDaoImpl();
        }
        return filmDao;
    }


    public FilmRaitingDao getFilmRaitingDao() {
        if (filmRaitingDao == null) {
            filmRaitingDao = new FilmRaitingDaoImpl();
        }
        return filmRaitingDao;
    }

    public ReviewDao getReviewDao() {
        if (reviewDao == null) {
            reviewDao = new ReviewDaoImpl();
        }
        return reviewDao;
    }

    public UserDao getUserDao() {
        if (userDao == null) {
            userDao = new UserDaoImpl();
        }
        return userDao;
    }
}

