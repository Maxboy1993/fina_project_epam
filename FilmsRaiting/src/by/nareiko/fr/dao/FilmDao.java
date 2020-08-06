package by.nareiko.fr.dao;

import by.nareiko.fr.entity.AbstractEntity;
import by.nareiko.fr.exception.DaoException;

import java.util.List;

/**
 * The interface Film dao.
 *
 * @param <T> the type parameter
 */
public interface FilmDao<T extends AbstractEntity> extends BaseDao<T> {
    /**
     * Find by name list.
     *
     * @param name the name
     * @return the list
     * @throws DaoException the dao exception
     */
    List<T> findByName(String name) throws DaoException;

    /**
     * Find by raiting more than list.
     *
     * @param raiting the raiting
     * @return the list
     * @throws DaoException the dao exception
     */
    List<T> findByRaitingMoreThan(int raiting) throws DaoException;
}
