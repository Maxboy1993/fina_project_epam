package by.nareiko.fr.dao;

import by.nareiko.fr.entity.AbstractEntity;
import by.nareiko.fr.exception.DaoException;

import java.util.List;

public interface PersonDao<T extends AbstractEntity> extends BaseDao<T> {
    List<T> findByLastName(String name) throws DaoException; // если у нас несколько актеров с одаковым именем, кого удалять? Может сделать

    //  предположение, что не будет одинаковых имен у актеров?
    T findByLastNameAndFirstName(String lastName, String firstName) throws DaoException;

    T delete(String lastName, String firstName) throws DaoException;
}
