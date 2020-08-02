package by.nareiko.fr.service;

import by.nareiko.fr.service.impl.*;

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

    public static ServiceFactory getInstance() {
        return INSTANCE;
    }

    public UserService getUserService() {
        userService = UserServiceImpl.getInstance();
        return userService;
    }

    public FilmPersonService getActorService() {
        actorService = ActorServiceImpl.getInstance();
        return actorService;
    }

    public FilmPersonService getDirectorService() {
        directorService = DirectorServiceImpl.getInstance();
        return directorService;
    }

    public FilmService getFilmService() {
        filmService = FilmServiceImpl.getInstance();
        return filmService;
    }

    public PosterService getFilmPosterService() {
        filmPosterService = FilmPosterServiceImpl.getInstance();
        return filmPosterService;
    }

    public FilmRaitingService getFilmRaitingService() {
        filmRaitingService = FilmRaitingServiceImpl.getInstance();
        return filmRaitingService;
    }

    public ReviewService getReviewService() {
        reviewService = ReviewServiceImpl.getInstance();
        return reviewService;
    }
}
