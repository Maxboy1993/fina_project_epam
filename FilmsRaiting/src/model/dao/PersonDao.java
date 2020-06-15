package model.dao;

import entity.AbstractEntity;

import java.util.List;

public interface PersonDao <T extends AbstractEntity> extends GeneralFilmsRaitingDao<T> {
    List<T> findByName(String name); // усли у нас несколько актеров с одаковым именем, кого удалять? Может сделать
  //  предположение, что не будет одинаковых имен у актеров?
    List<T> delete(String name);
}
