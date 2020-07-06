package by.nareiko.fr.model.dao;

import by.nareiko.fr.entity.AbstractEntity;

import java.util.List;

public interface RaitingDao<T extends AbstractEntity> extends BaseDao<T> {
    List<T> findByRaiting(double raiting);
}
