package by.nareiko.fr.service.impl;

import by.nareiko.fr.dao.DaoFactory;
import by.nareiko.fr.dao.PosterDao;
import by.nareiko.fr.exception.DaoException;
import by.nareiko.fr.exception.ServiceException;
import by.nareiko.fr.service.PosterService;
import by.nareiko.fr.validator.PosterValidator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.Part;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class FilmPosterServiceImpl implements PosterService<Blob> {
    private static final Logger LOGGER = LogManager.getLogger();
    private static final PosterService INSTANCE = new FilmPosterServiceImpl();

    private FilmPosterServiceImpl() {
    }

    public static PosterService getInstance() {
        return INSTANCE;
    }

    @Override
    public boolean checkPosterExtension(Part filePart) {
        PosterValidator posterValidator = new PosterValidator();
        boolean isCorrect = posterValidator.validatePosterExtension(filePart);
        return isCorrect;
    }

    @Override
    public Optional<Blob> addPoster(int filmId, InputStream inputStream) throws ServiceException {
        Optional<Blob> blob = Optional.empty();
        PosterDao posterDao = DaoFactory.getInstance().getFilmPosterDao();
        try {
            blob = posterDao.addPoster(filmId, inputStream);
        } catch (DaoException e) {
            LOGGER.error("Error while poster adding", e);
            throw new ServiceException("Error while poster adding", e);
        }
        return blob;
    }

    @Override
    public InputStream findPosterById(int filmId) throws ServiceException {
        PosterDao posterDao = DaoFactory.getInstance().getFilmPosterDao();
        InputStream inputStream = null;
        try {
            Optional<Blob> poster = posterDao.findPoster(filmId);
            if (poster.isPresent()) {
                inputStream = poster.get().getBinaryStream();
            }
        } catch (DaoException | SQLException e) {
            LOGGER.error("Error while poster searchong", e);
            throw new ServiceException("Error while poster searching", e);
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    LOGGER.error("Error while inputStream closing: ", e);
                }
            }
        }
        return inputStream;
    }

    @Override
    public List<InputStream> findAllPosters(List<Integer> filmsId) throws ServiceException {
        List<InputStream> streams = new ArrayList<>();
        InputStream inputStream;

        try {
            for (int i = 0; i < filmsId.size(); i++) {
                inputStream = findPosterById(filmsId.get(i));
                streams.add(inputStream);
            }
        } catch (ServiceException e) {
            LOGGER.error("Error while posters searchong", e);
            throw new ServiceException("Error while posters searching", e);
        }
        return streams;
    }
}
