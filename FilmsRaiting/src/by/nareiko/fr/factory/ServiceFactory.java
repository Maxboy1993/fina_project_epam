package by.nareiko.fr.factory;

import by.nareiko.fr.model.service.impl.UserServiceImpl;

public class ServiceFactory {
    private static ServiceFactory serviceFactory;
    private UserServiceImpl userService;

    public static ServiceFactory getInstance(){
        if (serviceFactory ==null){
            serviceFactory = new ServiceFactory();
        }
        return serviceFactory;
    }

    public UserServiceImpl getUserService(){
        if (userService == null){
            userService = new UserServiceImpl();
        }
        return userService;
    }
}
