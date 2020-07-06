package by.nareiko.fr.model.dao;

import by.nareiko.fr.entity.AbstractEntity;

import java.util.List;

public interface PersonDao<T extends AbstractEntity> extends BaseDao<T> {
    List<T> findByLastName(String name); // если у нас несколько актеров с одаковым именем, кого удалять? Может сделать
    //  предположение, что не будет одинаковых имен у актеров?
    T findByLastNameAndFirstName(String lastName, String firstName);
    T delete(String lastName, String firstName);
}
