package by.nareiko.fr.dao;

import by.nareiko.fr.entity.AbstractEntity;
import by.nareiko.fr.exception.DaoException;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * The type Entity mapper.
 *
 * @param <T> the type parameter
 */
public abstract class EntityMapper<T extends AbstractEntity> {

    /**
     * Init entity t.
     *
     * @param resultSet the result set
     * @return the t
     * @throws SQLException the sql exception
     * @throws DaoException the dao exception
     */
    public abstract T initEntity(ResultSet resultSet) throws SQLException, DaoException;

    /**
     * Init entities list.
     *
     * @param resultSet the result set
     * @return the list
     * @throws SQLException the sql exception
     * @throws DaoException the dao exception
     */
    public List<T> initEntities(ResultSet resultSet) throws SQLException, DaoException {
        List<T> actors = new ArrayList<>();
        while (resultSet.next()) {
            T t = initEntity(resultSet);
            actors.add(t);
        }
        return actors;
    }
}
