package by.nareiko.fr.dao;

import by.nareiko.fr.entity.AbstractEntity;
import by.nareiko.fr.exception.DaoException;

import java.util.List;
import java.util.Optional;

public interface PersonDao<T extends AbstractEntity> extends BaseDao<T> {
    List<T> findByLastName(String name) throws DaoException;

    Optional<T> findByLastNameAndFirstName(String lastName, String firstName) throws DaoException;

    Optional<T> delete(String lastName, String firstName) throws DaoException;
}
