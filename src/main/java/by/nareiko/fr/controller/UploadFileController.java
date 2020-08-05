package by.nareiko.fr.controller;

import by.nareiko.fr.controller.command.PagePath;
import by.nareiko.fr.entity.Film;
import by.nareiko.fr.exception.ServiceException;
import by.nareiko.fr.service.FilmService;
import by.nareiko.fr.service.PosterService;
import by.nareiko.fr.service.ServiceFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Optional;

@WebServlet("/uploadController")
@MultipartConfig(fileSizeThreshold = 1024 * 1024,
        maxFileSize = 1024 * 1024 * 10, maxRequestSize = 1024 * 1024 * 5 * 5)
public class UploadFileController extends HttpServlet {
    private static final Logger LOGGER = LogManager.getLogger();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int filmId = (int) request.getSession().getAttribute(RequestParameterName.FILM_ID_PARAM);
        Router router = new Router();
        PosterService filmPosterService = ServiceFactory.getInstance().getFilmPosterService();
        Part filePart = request.getPart(RequestParameterName.POSTER_PARAM);
        final InputStream inputStream = filePart.getInputStream();
        FilmService filmService = ServiceFactory.getInstance().getFilmService();
        try (inputStream) {
            if (filmPosterService.checkPosterExtension(filePart)) {
                if (inputStream != null) {
                    filmPosterService.addPoster(filmId, inputStream);
                    Optional<Film> optionalFilm = filmService.findById(filmId);
                    if (optionalFilm.isPresent()) {
                        List<Film> films = (List<Film>) request.getServletContext().getAttribute(RequestParameterName.FILMS_LIST_PARAM);
                        films.add(0, optionalFilm.get());
                    } else {
                        request.setAttribute(RequestParameterName.FILM_ADDING_ERROR_PARAM, RequestParameterName.FILM_ADDING_ERROR_VALUE);
                    }
                    router.setPage(PagePath.MAIN);
                } else {
                    router.setPage(PagePath.ADD_POSTER);
                    request.setAttribute(RequestParameterName.POSTER_ADDING_ERROR, RequestParameterName.POSTER_ADDING_ERROR_VALUE);
                }
            } else {
                request.setAttribute(RequestParameterName.POSTER_FORMAT_ERROR_PARAM, RequestParameterName.POSTER_FORMAT_ERROR_VALUE);
                router.setPage(PagePath.ADD_POSTER);
            }
        } catch (IOException | ServiceException e) {
            LOGGER.error("Error while poster adding", e);
            router.setPage(PagePath.ERROR_500);
        }
        if (Router.DisPathType.FORWARD.equals(router.getDisPathType())) {
            request.getRequestDispatcher(router.getPage()).forward(request, response);
        } else {
            response.sendRedirect(router.getPage());
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        super.doGet(request, response);
    }
}
