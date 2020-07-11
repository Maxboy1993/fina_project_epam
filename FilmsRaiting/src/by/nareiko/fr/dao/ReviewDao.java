package by.nareiko.fr.dao;

import by.nareiko.fr.entity.AbstractEntity;
import by.nareiko.fr.exception.DaoException;

import java.util.List;

public interface ReviewDao<T extends AbstractEntity> extends BaseDao<T> {
    List<T> findByFilmId(int filmId) throws DaoException;
}
