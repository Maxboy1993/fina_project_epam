package by.nareiko.fr.model.dao;

import by.nareiko.fr.entity.AbstractEntity;
import by.nareiko.fr.entity.Film;

import java.util.List;

public interface FilmsDao<T extends AbstractEntity> extends BaseDao<T> {
    List<Film> findByName(String name);
    List<Film> findByRaitingMoreThan(int raiting);
}
