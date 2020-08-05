package by.nareiko.fr.service;

import by.nareiko.fr.entity.AbstractEntity;
import by.nareiko.fr.entity.Review;
import by.nareiko.fr.exception.ServiceException;

import java.util.List;
import java.util.Optional;

public interface ReviewService<T extends AbstractEntity> {
    List<T> findByFilmId(int filmId) throws ServiceException;

    boolean checkReviewData(String review);

    boolean deleteReview(int reviewId) throws ServiceException;

    Optional<Review> create(int userId, int filmId, String review) throws ServiceException;
}
