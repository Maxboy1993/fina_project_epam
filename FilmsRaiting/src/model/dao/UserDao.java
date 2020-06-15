package model.dao;

import entity.AbstractEntity;
import model.dao.exception.DaoException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import pool.ConnectionPool;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

public interface UserDao<T extends AbstractEntity> extends GeneralFilmsRaitingDao<T>{
    static final Logger LOGGER = LogManager.getLogger();

    T findByLogin(String login);
    T findByLoginAndPassword(String login, String password);
    T delete(String login) throws DaoException;
}
