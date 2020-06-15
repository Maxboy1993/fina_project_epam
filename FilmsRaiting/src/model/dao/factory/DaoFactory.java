package model.dao.factory;

import model.dao.impl.*;

public class DaoFactory {
    private static DaoFactory daoFactory;
    private ActorDaoImpl actorDao;
    private DirectorDaoImpl directorDao;
    private FilmDaoImpl filmDao;
    private FilmRaitingDaoImpl filmRaitingDao;
    private ReviewDaoImpl reviewDao;
    private UserDaoImpl userDao;
    UserRaitingDaoImpl userRaitingDao;

    public static DaoFactory getInstance(){
        if (daoFactory ==null){
            daoFactory = new DaoFactory();
        }
        return daoFactory;
    }

    public ActorDaoImpl getActorDao(){
        if (actorDao == null){
            actorDao = new ActorDaoImpl();
        }
        return actorDao;
    }

    public DirectorDaoImpl getDirectorDao(){
        if (directorDao == null){
            directorDao = new DirectorDaoImpl();
        }
        return directorDao;
    }

    public FilmDaoImpl getFilmDao(){
        if (filmDao == null){
            filmDao = new FilmDaoImpl();
        }
        return filmDao;
    }


    public FilmRaitingDaoImpl getFilmRaitingDao(){
        if (filmRaitingDao == null){
            filmRaitingDao = new FilmRaitingDaoImpl();
        }
        return filmRaitingDao;
    }

    public ReviewDaoImpl getReviewDao(){
        if (reviewDao == null){
            reviewDao = new ReviewDaoImpl();
        }
        return reviewDao;
    }

    public UserDaoImpl getUserDao(){
        if (userDao == null){
            userDao = new UserDaoImpl();
        }
        return userDao;
    }
    UserRaitingDaoImpl getUserRaitingDao(){
        if (userRaitingDao == null){
            userRaitingDao = new UserRaitingDaoImpl();
        }
        return userRaitingDao;
    }
}
