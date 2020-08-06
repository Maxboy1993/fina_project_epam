package by.nareiko.fr.dao;

import by.nareiko.fr.dao.impl.*;

/**
 * The type Dao factory.
 */
public final class DaoFactory {
    private static final DaoFactory INSTANCE = new DaoFactory();

    private FilmPersonDao actorDao;
    private FilmPersonDao directorDao;
    //    private FilmDao filmDao;
    private FilmDao fullFilmInfoDao;
    private FilmRaitingDao filmRaitingDao;
    private ReviewDao reviewDao;
    private UserDao userDao;
    private PosterDao filmPosterDao;

    private DaoFactory() {
    }

    /**
     * Gets instance.
     *
     * @return the instance
     */
    public static DaoFactory getInstance() {
        return INSTANCE;
    }

    /**
     * Gets actor dao.
     *
     * @return the actor dao
     */
    public FilmPersonDao getActorDao() {
        actorDao = ActorDaoImpl.getInstance();
        return actorDao;
    }

    /**
     * Gets director dao.
     *
     * @return the director dao
     */
    public FilmPersonDao getDirectorDao() {
        directorDao = DirectorDaoImpl.getInstance();
        return directorDao;
    }

//    public FilmDao getFilmDao() {
//        filmDao = FilmDaoImpl.getInstance();
//        return filmDao;
//    }

    /**
     * Gets full film info dao.
     *
     * @return the full film info dao
     */
    public FilmDao getFullFilmInfoDao() {
        fullFilmInfoDao = FullFilmInfoDaoImpl.getInstance();
        return fullFilmInfoDao;
    }


    /**
     * Gets film raiting dao.
     *
     * @return the film raiting dao
     */
    public FilmRaitingDao getFilmRaitingDao() {
        filmRaitingDao = FilmRaitingDaoImpl.getInstance();
        return filmRaitingDao;
    }

    /**
     * Gets review dao.
     *
     * @return the review dao
     */
    public ReviewDao getReviewDao() {
        reviewDao = ReviewDaoImpl.getInstance();
        return reviewDao;
    }

    /**
     * Gets user dao.
     *
     * @return the user dao
     */
    public UserDao getUserDao() {
        userDao = UserDaoImpl.getInstance();
        return userDao;
    }

    /**
     * Gets film poster dao.
     *
     * @return the film poster dao
     */
    public PosterDao getFilmPosterDao() {
        filmPosterDao = FilmPosterDaoImpl.getInstance();
        return filmPosterDao;
    }
}

