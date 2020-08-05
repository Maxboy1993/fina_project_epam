package by.nareiko.fr.service;

import by.nareiko.fr.exception.ServiceException;

import javax.servlet.http.Part;
import java.io.InputStream;
import java.util.List;
import java.util.Optional;

public interface PosterService<T> {
    boolean checkPosterExtension(Part filePart);

    Optional<T> addPoster(int filmId, InputStream inputStream) throws ServiceException;

    InputStream findPosterById(int filmId) throws ServiceException;

    List<InputStream> findAllPosters(List<Integer> filmsId) throws ServiceException;

}
