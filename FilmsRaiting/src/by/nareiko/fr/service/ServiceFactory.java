package by.nareiko.fr.service;

import by.nareiko.fr.service.impl.UserServiceImpl;

public final class ServiceFactory {
    private static ServiceFactory serviceFactory;
    private UserService userService;

    private ServiceFactory(){}

    public static ServiceFactory getInstance() {
        if (serviceFactory == null) {
            serviceFactory = new ServiceFactory();
        }
        return serviceFactory;
    }

    public UserService getUserService() {
        if (userService == null) {
            userService = new UserServiceImpl();
        }
        return userService;
    }
}
