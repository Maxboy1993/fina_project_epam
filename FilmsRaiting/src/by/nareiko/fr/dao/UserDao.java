package by.nareiko.fr.dao;

import by.nareiko.fr.entity.AbstractEntity;
import by.nareiko.fr.exception.DaoException;

import java.util.Optional;

public interface UserDao<T extends AbstractEntity> extends BaseDao<T> {

    Optional<T> findByLogin(String login) throws DaoException;

    Optional<T> findByLoginAndPassword(String login, String password) throws DaoException;

    Optional<T> delete(String login) throws DaoException;
}
