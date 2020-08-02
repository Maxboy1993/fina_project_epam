package by.nareiko.fr.service;

import by.nareiko.fr.entity.AbstractEntity;
import by.nareiko.fr.entity.Actor;
import by.nareiko.fr.entity.Director;
import by.nareiko.fr.exception.ServiceException;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface FilmService<T extends AbstractEntity> {
    List<T> findAllFilms() throws ServiceException;

    boolean checkFilmData(String filmName, String releaseDate, String genre);

    boolean checkFilmName(String filmName);

    boolean deleteFilm(int filmId) throws ServiceException;

    Optional<T> addFilm(String filmName, String releaseDate, String genre, List<Actor> actors, Director director) throws ServiceException;

    Optional<T> findById(int filmId) throws ServiceException;

    List<T> findFilmsByName(String filmName) throws ServiceException;

    Set<String> getErrorsFilmMessage();
}
