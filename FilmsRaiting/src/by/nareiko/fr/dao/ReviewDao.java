package by.nareiko.fr.dao;

import by.nareiko.fr.entity.AbstractEntity;
import by.nareiko.fr.exception.DaoException;

import java.util.List;

/**
 * The interface Review dao.
 *
 * @param <T> the type parameter
 */
public interface ReviewDao<T extends AbstractEntity> extends BaseDao<T> {
    /**
     * Find by film id list.
     *
     * @param filmId the film id
     * @return the list
     * @throws DaoException the dao exception
     */
    List<T> findByFilmId(int filmId) throws DaoException;
}
