package model.dao;

import entity.AbstractEntity;

import java.util.List;

public interface RaitingDao<T extends AbstractEntity> extends GeneralFilmsRaitingDao<T> {
    List<T> findByRaiting(double raiting);
}
