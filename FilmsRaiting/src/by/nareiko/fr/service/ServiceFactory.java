package by.nareiko.fr.service;

import by.nareiko.fr.service.impl.*;

/**
 * The type Service factory.
 */
public final class ServiceFactory {
    private static final ServiceFactory INSTANCE = new ServiceFactory();
    private UserService userService;
    private FilmService filmService;
    private FilmPersonService actorService;
    private FilmPersonService directorService;
    private PosterService filmPosterService;
    private FilmRaitingService filmRaitingService;
    private ReviewService reviewService;

    private ServiceFactory() {
    }

    /**
     * Gets instance.
     *
     * @return the instance
     */
    public static ServiceFactory getInstance() {
        return INSTANCE;
    }

    /**
     * Gets user service.
     *
     * @return the user service
     */
    public UserService getUserService() {
        userService = UserServiceImpl.getInstance();
        return userService;
    }

    /**
     * Gets actor service.
     *
     * @return the actor service
     */
    public FilmPersonService getActorService() {
        actorService = ActorServiceImpl.getInstance();
        return actorService;
    }

    /**
     * Gets director service.
     *
     * @return the director service
     */
    public FilmPersonService getDirectorService() {
        directorService = DirectorServiceImpl.getInstance();
        return directorService;
    }

    /**
     * Gets film service.
     *
     * @return the film service
     */
    public FilmService getFilmService() {
        filmService = FilmServiceImpl.getInstance();
        return filmService;
    }

    /**
     * Gets film poster service.
     *
     * @return the film poster service
     */
    public PosterService getFilmPosterService() {
        filmPosterService = FilmPosterServiceImpl.getInstance();
        return filmPosterService;
    }

    /**
     * Gets film raiting service.
     *
     * @return the film raiting service
     */
    public FilmRaitingService getFilmRaitingService() {
        filmRaitingService = FilmRaitingServiceImpl.getInstance();
        return filmRaitingService;
    }

    /**
     * Gets review service.
     *
     * @return the review service
     */
    public ReviewService getReviewService() {
        reviewService = ReviewServiceImpl.getInstance();
        return reviewService;
    }
}
