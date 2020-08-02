package by.nareiko.fr.service;

import by.nareiko.fr.entity.AbstractEntity;
import by.nareiko.fr.exception.ServiceException;

import java.util.Optional;
import java.util.Set;

public interface FilmPersonService<T extends AbstractEntity> {
    boolean checkPersonData(String firstName, String lastName, String birthday);

    Optional<T> createPerson(String firstName, String lastName, String birthday) throws ServiceException;

    Optional<T> findByLastNameAndFirstName(String lastName, String firstName) throws ServiceException;

    Set<String> getFilmPersonErrors();
}
