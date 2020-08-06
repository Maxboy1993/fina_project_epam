package by.nareiko.fr.controller.command.impl;

import by.nareiko.fr.controller.RequestParameterName;
import by.nareiko.fr.controller.Router;
import by.nareiko.fr.controller.command.Command;
import by.nareiko.fr.controller.command.PagePath;
import by.nareiko.fr.entity.Review;
import by.nareiko.fr.entity.User;
import by.nareiko.fr.exception.ServiceException;
import by.nareiko.fr.service.ReviewService;
import by.nareiko.fr.service.ServiceFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Optional;

/**
 * The type Add review command.
 */
public class AddReviewCommand implements Command {
    private static final Logger LOGGER = LogManager.getLogger();
    private static final String COMMAND = "controller?command=PASSING_TO_FILM_INFO";

    @Override
    public Router execute(HttpServletRequest request) {
        String review = request.getParameter(RequestParameterName.REVUEW_PARAM);
        int filmId = Integer.parseInt(request.getParameter(RequestParameterName.FILM_ID_PARAM));
        User user = (User) request.getSession().getAttribute(RequestParameterName.USER_PARAM);
        int userId = user.getId();
        Router router = new Router();
        ReviewService reviewService = ServiceFactory.getInstance().getReviewService();
        if (reviewService.checkReviewData(review)) {
            try {
                List<Review> reviewsFromDB = reviewService.findByFilmId(filmId);
                boolean isUserAlreadyAddReview = false;
                for (Review rev:reviewsFromDB) {
                    if (rev.getUserId() == userId){
                        isUserAlreadyAddReview = true;
                    }
                }
                if (!isUserAlreadyAddReview) {
                    Optional<Review> optionalReview = reviewService.create(userId, filmId, review);
                    if (optionalReview.isPresent()) {
                        List<Review> reviews = (List<Review>) request.getSession().getAttribute(RequestParameterName.REVIEW_LIST_PARAM);
                        reviews.add(optionalReview.get());
                        List<User> users = (List<User>) request.getSession().getAttribute(RequestParameterName.USER_LIST_PARAM);
                        User currentUser = (User) request.getSession().getAttribute(RequestParameterName.USER_PARAM);
                        users.add(currentUser);
                        router.setRedirect();
                        router.setPage(COMMAND);
                    } else {
                        request.setAttribute(RequestParameterName.ERROR_REVIEW_ADDIG_PARAM, RequestParameterName.ERROR_REVIEW_ADDIG_VALUE);
                        router.setPage(PagePath.FILM_INFO);
                    }
                }else {
                    request.setAttribute(RequestParameterName.ERROR_REVIEW_ADDIG_PARAM, RequestParameterName.DOUBLE_REVIEW_ADDIG_VALUE);
                    router.setPage(PagePath.FILM_INFO);
                }
            } catch (ServiceException e) {
                LOGGER.error("Error while adding review: ", e);
                router.setPage(PagePath.ERROR_500);
            }
        } else {
            request.setAttribute(RequestParameterName.ERROR_REVIEW_ADDIG_PARAM, "Incorrect review");
            router.setPage(PagePath.FILM_INFO);
        }
        return router;
    }
}
