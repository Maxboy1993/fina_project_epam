package by.nareiko.fr.dao;

import by.nareiko.fr.entity.AbstractEntity;
import by.nareiko.fr.exception.DaoException;

public interface UserDao<T extends AbstractEntity> extends BaseDao<T> {

    T findByLogin(String login) throws DaoException;

    T findByLoginAndPassword(String login, String password) throws DaoException;

    T delete(String login) throws DaoException;
}
