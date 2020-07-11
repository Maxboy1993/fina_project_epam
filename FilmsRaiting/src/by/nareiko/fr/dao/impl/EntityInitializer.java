package by.nareiko.fr.dao.impl;

import by.nareiko.fr.entity.AbstractEntity;
import by.nareiko.fr.exception.DaoException;

import java.sql.ResultSet;
import java.util.List;

public abstract class EntityInitializer<T extends AbstractEntity> {
    abstract T initItem(ResultSet resultSet) throws DaoException;

    abstract List<T> initItems(ResultSet resultSet) throws DaoException;
}
