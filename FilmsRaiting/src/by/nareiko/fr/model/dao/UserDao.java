package by.nareiko.fr.model.dao;

import by.nareiko.fr.entity.AbstractEntity;
import by.nareiko.fr.exception.DaoException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public interface UserDao<T extends AbstractEntity> extends BaseDao<T> {
    static final Logger LOGGER = LogManager.getLogger();

    T findByLogin(String login);
    T findByLoginAndPassword(String login, String password);
    T delete(String login) throws DaoException;
}
