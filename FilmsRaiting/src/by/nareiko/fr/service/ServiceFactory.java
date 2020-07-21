package by.nareiko.fr.service;

import by.nareiko.fr.service.impl.FilmServiceImpl;
import by.nareiko.fr.service.impl.UserServiceImpl;

public final class ServiceFactory {
    private static final ServiceFactory INSTANCE = new ServiceFactory();
    private UserService userService;
    private FilmService filmService;

    private ServiceFactory() {
    }

    public static ServiceFactory getInstance() {
        return INSTANCE;
    }

    public UserService getUserService() {
        userService = UserServiceImpl.getInstance();
        return userService;
    }

    public FilmService getFilmService() {
        filmService = FilmServiceImpl.getInstance();
        return filmService;
    }
}
