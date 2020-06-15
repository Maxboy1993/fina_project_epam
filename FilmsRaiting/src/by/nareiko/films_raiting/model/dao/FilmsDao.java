package by.nareiko.films_raiting.model.dao;

import by.nareiko.films_raiting.entity.AbstractEntity;
import by.nareiko.films_raiting.entity.Film;

import java.util.List;

public interface FilmsDao<T extends AbstractEntity> extends BaseDao<T> {
    List<Film> findByName(String name);
    List<Film> findByRaitingMoreThan(int raiting);
    T delete(String name);
}
