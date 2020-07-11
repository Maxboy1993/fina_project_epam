package by.nareiko.fr.dao;

import by.nareiko.fr.entity.AbstractEntity;
import by.nareiko.fr.entity.Film;
import by.nareiko.fr.exception.DaoException;

import java.util.List;

public interface FilmsDao<T extends AbstractEntity> extends BaseDao<T> {
    List<Film> findByName(String name) throws DaoException;

    List<Film> findByRaitingMoreThan(int raiting) throws DaoException;
}
