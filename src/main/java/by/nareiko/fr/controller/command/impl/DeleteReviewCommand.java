package by.nareiko.fr.controller.command.impl;

import by.nareiko.fr.controller.RequestParameterName;
import by.nareiko.fr.controller.Router;
import by.nareiko.fr.controller.command.Command;
import by.nareiko.fr.controller.command.PagePath;
import by.nareiko.fr.entity.Review;
import by.nareiko.fr.exception.ServiceException;
import by.nareiko.fr.service.ReviewService;
import by.nareiko.fr.service.ServiceFactory;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

public class DeleteReviewCommand implements Command {
    private static final String COMMAND = "controller?command=PASSING_TO_FILM_INFO";

    @Override
    public Router execute(HttpServletRequest request) {
        int reviewId = Integer.parseInt(request.getParameter(RequestParameterName.REVIEW_ID_PARAM));
        Router router = new Router();
        ReviewService reviewService = ServiceFactory.getInstance().getReviewService();
        try {
            reviewService.deleteReview(reviewId);
            List<Review> reviews = (List<Review>) request.getSession().getAttribute(RequestParameterName.REVIEW_LIST_PARAM);
            Review review = null;
            for (Review rev:reviews) {
                if (rev.getId() == reviewId){
                    review = rev;
                }
            }
            reviews.remove(review);
            router.setRedirect();
            router.setPage(COMMAND);
        } catch (ServiceException e) {
            router.setPage(PagePath.ERROR_500);
        }
        return router;
    }
}
