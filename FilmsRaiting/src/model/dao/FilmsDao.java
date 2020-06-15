package model.dao;

import entity.AbstractEntity;
import entity.Film;

import java.util.List;

public interface FilmsDao<T extends AbstractEntity> extends GeneralFilmsRaitingDao<T> {
    List<Film> findByName(String name);
    List<Film> findByRaitingMoreThan(int raiting);
    T delete(String name);
}
