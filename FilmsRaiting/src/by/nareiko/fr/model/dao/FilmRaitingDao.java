package by.nareiko.fr.model.dao;

import by.nareiko.fr.entity.AbstractEntity;

import java.util.List;
import java.util.Map;

public interface FilmRaitingDao<T extends AbstractEntity> extends BaseDao<T>  {
    List<T> findByFilmId(int filmId);
}
