package by.nareiko.fr.service;

import by.nareiko.fr.entity.AbstractEntity;
import by.nareiko.fr.exception.ServiceException;

import java.util.List;
import java.util.Optional;

public interface FilmRaitingService<T extends AbstractEntity> {
    List<T> findByFilmId(int filmId) throws ServiceException;

    Optional<T> create(int filmId, int userId, int userRaiting) throws ServiceException;

    double countfinalFilmRaiting(int filmId) throws ServiceException;
}
