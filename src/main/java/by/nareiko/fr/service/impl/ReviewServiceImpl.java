package by.nareiko.fr.service.impl;

import by.nareiko.fr.dao.DaoFactory;
import by.nareiko.fr.dao.ReviewDao;
import by.nareiko.fr.entity.Review;
import by.nareiko.fr.exception.DaoException;
import by.nareiko.fr.exception.ServiceException;
import by.nareiko.fr.service.ReviewService;
import by.nareiko.fr.validator.ReviewValidator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.Optional;

public class ReviewServiceImpl implements ReviewService<Review> {
    private static final Logger LOGGER = LogManager.getLogger();
    private static final ReviewService INSTANCE = new ReviewServiceImpl();

    private ReviewServiceImpl() {
    }

    public static ReviewService getInstance() {
        return INSTANCE;
    }

    @Override
    public List<Review> findByFilmId(int filmId) throws ServiceException {
        ReviewDao reviewDao = DaoFactory.getInstance().getReviewDao();
        List<Review> reviews;
        try {
            reviews = reviewDao.findByFilmId(filmId);
        } catch (DaoException e) {
            LOGGER.error("Error while seaching reviews: ", e);
            throw new ServiceException("Error while seaching reviews: ", e);
        }
        return reviews;
    }

    @Override
    public boolean checkReviewData(String review) {
        boolean isCorrect = false;
        ReviewValidator reviewValidator = new ReviewValidator();
        if (review != null && !review.isBlank()) {
            isCorrect = reviewValidator.validateReview(review);
        }
        return isCorrect;
    }

    @Override
    public boolean deleteReview(int reviewId) throws ServiceException {
        ReviewDao reviewDao = DaoFactory.getInstance().getReviewDao();
        boolean isDeleted;
        try {
            isDeleted = reviewDao.delete(reviewId);
        } catch (DaoException e) {
            LOGGER.error("Error while deleting review: ", e);
            throw new ServiceException("Error while deleting reviews ", e);
        }
        return isDeleted;
    }

    @Override
    public Optional<Review> create(int userId, int filmId, String review) throws ServiceException {
        ReviewDao reviewDao = DaoFactory.getInstance().getReviewDao();
        Review reviewObject = new Review();
        reviewObject.setFilmId(filmId);
        reviewObject.setUserId(userId);
        reviewObject.setReview(review);
        Optional<Review> optionalReview;
        try {
            optionalReview = reviewDao.create(reviewObject);
        } catch (DaoException e) {
            LOGGER.error("Error while adding review: ", e);
            throw new ServiceException("Error while adding reviews ", e);
        }
        return optionalReview;
    }
}
