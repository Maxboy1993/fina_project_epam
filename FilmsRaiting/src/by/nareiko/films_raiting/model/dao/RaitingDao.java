package by.nareiko.films_raiting.model.dao;

import by.nareiko.films_raiting.entity.AbstractEntity;

import java.util.List;

public interface RaitingDao<T extends AbstractEntity> extends BaseDao<T> {
    List<T> findByRaiting(double raiting);
}
