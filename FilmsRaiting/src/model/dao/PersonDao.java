package model.dao;

import entity.AbstractEntity;
import entity.Film;

import java.util.List;

public interface PersonDao <T extends AbstractEntity> extends UnitedFilmsRaitingDao {
    List<T> findAllByName(String patternName);
    List<T> findAllByAgeYounger(int maxAge);
    List<T> findAllByAgeOlder(int minAge);
}
