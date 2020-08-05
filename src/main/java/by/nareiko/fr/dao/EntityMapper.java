package by.nareiko.fr.dao;

import by.nareiko.fr.entity.AbstractEntity;
import by.nareiko.fr.exception.DaoException;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public abstract class EntityMapper<T extends AbstractEntity> {

    public abstract T initEntity(ResultSet resultSet) throws SQLException, DaoException;

    public List<T> initEntities(ResultSet resultSet) throws SQLException, DaoException {
        List<T> actors = new ArrayList<>();
        while (resultSet.next()) {
            T t = initEntity(resultSet);
            actors.add(t);
        }
        return actors;
    }
}
