package by.nareiko.fr.service.impl;

import by.nareiko.fr.dao.DaoFactory;
import by.nareiko.fr.dao.FilmPersonDao;
import by.nareiko.fr.entity.Director;
import by.nareiko.fr.exception.DaoException;
import by.nareiko.fr.exception.ServiceException;
import by.nareiko.fr.service.FilmPersonService;
import by.nareiko.fr.validator.DirectorValidator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

public class DirectorServiceImpl implements FilmPersonService<Director> {
    private static final FilmPersonService INSTANCE = new DirectorServiceImpl();
    private static final Logger LOGGER = LogManager.getLogger();
    private Set<String> directorErrors = new HashSet<>();

    private DirectorServiceImpl() {
    }

    public static FilmPersonService getInstance() {
        return INSTANCE;
    }

    @Override
    public boolean checkPersonData(String firstName, String lastName, String birthday) {
        boolean isCorrect = false;
        DirectorValidator directorValidator = new DirectorValidator();
        if (directorValidator.validateDirectorData(firstName, lastName, birthday)) {
            isCorrect = true;
        }
        if (!isCorrect) {
            directorErrors = directorValidator.getErrorDirectorMessages();
        }
        return isCorrect;
    }

    @Override
    public Optional<Director> createPerson(String firstName, String lastName, String birthday) throws ServiceException {
        Director director = new Director();
        director.setFirstName(firstName);
        director.setLastName(lastName);
        director.setBirthday(birthday);
        FilmPersonDao personDao = DaoFactory.getInstance().getDirectorDao();
        Optional<Director> optionalDirector;
        try {
            optionalDirector = personDao.create(director);
        } catch (DaoException e) {
            LOGGER.error("Error while actor adding", e);
            throw new ServiceException("Error while actor adding", e);
        }
        return optionalDirector;
    }

    @Override
    public Optional<Director> findByLastNameAndFirstName(String lastName, String firstName) throws ServiceException {
        FilmPersonDao directorDao = DaoFactory.getInstance().getDirectorDao();
        Optional<Director> optionalDirector;
        try {
            optionalDirector = directorDao.findByLastNameAndFirstName(lastName, firstName);
        } catch (DaoException e) {
            LOGGER.error("Error while actor searching", e);
            throw new ServiceException("Error while actor searching", e);
        }
        return optionalDirector;
    }

    @Override
    public Set<String> getFilmPersonErrors() {
        Set<String> errors = new HashSet<>();
        errors.addAll(directorErrors);
        directorErrors.removeAll(directorErrors);
        return errors;
    }
}
