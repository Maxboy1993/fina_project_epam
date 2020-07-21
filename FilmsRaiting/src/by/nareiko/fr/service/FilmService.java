package by.nareiko.fr.service;

import by.nareiko.fr.entity.AbstractEntity;
import by.nareiko.fr.exception.ServiceException;

import java.util.List;

public interface FilmService<T extends AbstractEntity> {
    List<T> findAllFilms() throws ServiceException;
}
