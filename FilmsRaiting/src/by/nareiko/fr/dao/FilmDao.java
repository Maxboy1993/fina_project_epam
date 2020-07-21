package by.nareiko.fr.dao;

import by.nareiko.fr.entity.AbstractEntity;
import by.nareiko.fr.exception.DaoException;

import java.util.List;
import java.util.Optional;

public interface FilmDao<T extends AbstractEntity> extends BaseDao<T> {
    Optional<T> findByName(String name) throws DaoException;

    List<T> findByRaitingMoreThan(int raiting) throws DaoException;
}
