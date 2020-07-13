package by.nareiko.fr.dao;

import by.nareiko.fr.entity.AbstractEntity;
import by.nareiko.fr.exception.DaoException;

import java.sql.ResultSet;
import java.util.List;

public abstract class EntityInitializer<T extends AbstractEntity> {
    protected abstract T initItem(ResultSet resultSet) throws DaoException;

    protected abstract List<T> initItems(ResultSet resultSet) throws DaoException;
}
