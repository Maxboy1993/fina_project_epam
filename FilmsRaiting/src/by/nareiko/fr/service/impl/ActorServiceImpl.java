package by.nareiko.fr.service.impl;

import by.nareiko.fr.dao.DaoFactory;
import by.nareiko.fr.dao.FilmPersonDao;
import by.nareiko.fr.entity.Actor;
import by.nareiko.fr.exception.DaoException;
import by.nareiko.fr.exception.ServiceException;
import by.nareiko.fr.service.FilmPersonService;
import by.nareiko.fr.validator.ActorValidator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

public class ActorServiceImpl implements FilmPersonService<Actor> {
    private static final FilmPersonService INSTANCE = new ActorServiceImpl();
    private static final Logger LOGGER = LogManager.getLogger();
    private Set<String> actorErrors = new HashSet<>();


    private ActorServiceImpl() {
    }

    public static FilmPersonService getInstance() {
        return INSTANCE;
    }

    @Override
    public boolean checkPersonData(String firstName, String lastName, String birthday) {
        boolean isCorrect = false;
        ActorValidator actorValidator = new ActorValidator();
        if (actorValidator.validateActorData(firstName, lastName, birthday)) {
            isCorrect = true;
        }
        if (!isCorrect) {
            actorErrors = actorValidator.getErrorActorMessages();
        }
        return isCorrect;
    }

    @Override
    public Optional<Actor> createPerson(String firstName, String lastName, String birthday) throws ServiceException {
        Actor actor = new Actor();
        actor.setFirstName(firstName);
        actor.setLastName(lastName);
        actor.setBirthday(birthday);
        FilmPersonDao personDao = DaoFactory.getInstance().getActorDao();
        Optional<Actor> optionalActor;
        try {
            optionalActor = personDao.create(actor);
        } catch (DaoException e) {
            LOGGER.error("Error while actor adding", e);
            throw new ServiceException("Error while actor adding", e);
        }
        return optionalActor;
    }

    @Override
    public Optional<Actor> findByLastNameAndFirstName(String lastName, String firstName) throws ServiceException {
        FilmPersonDao actorDao = DaoFactory.getInstance().getActorDao();
        Optional<Actor> optionalActor;
        try {
            optionalActor = actorDao.findByLastNameAndFirstName(lastName, firstName);
        } catch (DaoException e) {
            LOGGER.error("Error while actor searching", e);
            throw new ServiceException("Error while actor searching", e);
        }

        return optionalActor;
    }

    @Override
    public Set<String> getFilmPersonErrors() {
        Set<String> errors = new HashSet<>();
        errors.addAll(actorErrors);
        actorErrors.removeAll(actorErrors);
        return errors;
    }
}
