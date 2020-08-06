package by.nareiko.fr.service;

import by.nareiko.fr.entity.AbstractEntity;
import by.nareiko.fr.entity.Review;
import by.nareiko.fr.exception.ServiceException;

import java.util.List;
import java.util.Optional;

/**
 * The interface Review service.
 *
 * @param <T> the type parameter
 */
public interface ReviewService<T extends AbstractEntity> {
    /**
     * Find by film id list.
     *
     * @param filmId the film id
     * @return the list
     * @throws ServiceException the service exception
     */
    List<T> findByFilmId(int filmId) throws ServiceException;

    /**
     * Check review data boolean.
     *
     * @param review the review
     * @return the boolean
     */
    boolean checkReviewData(String review);

    /**
     * Delete review boolean.
     *
     * @param reviewId the review id
     * @return the boolean
     * @throws ServiceException the service exception
     */
    boolean deleteReview(int reviewId) throws ServiceException;

    /**
     * Create optional.
     *
     * @param userId the user id
     * @param filmId the film id
     * @param review the review
     * @return the optional
     * @throws ServiceException the service exception
     */
    Optional<Review> create(int userId, int filmId, String review) throws ServiceException;
}
