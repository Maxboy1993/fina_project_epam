package model.dao;

import entity.AbstractEntity;
import entity.Film;

import java.util.List;

public interface FilmsDao extends UnitedFilmsRaitingDao <Integer, Film> {
    List<Film> findAllByName(String patternName);
    List<Film> findAllByRaitingMoreThan(int minRaiting);
}
