package by.nareiko.fr.validator;

import by.nareiko.fr.controller.RequestParameterName;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FilmValidator {
    private static final String SPLIT_REGEX = "-";
    private static final long MIN_DATE = -2366725226371L;
    private static final long RELEASE_PERIOD = 777600000L;
    private static final int YEAR_INDEX = 0;
    private static final int MONTH_INDEX = 1;
    private static final int DAY_INDEX = 2;
    private static final String FILM_NAME_REGEX = "[a-zA-Z0-9 ]{2,100}";
    private static final String GENRE_REGEX = "[a-zA-Z ]{5,50}";
    private Set<String> errorMessage;

    public FilmValidator() {
        errorMessage = new HashSet<>();
    }


    public boolean validateFilmData(String filmName, String releaseDate, String genre) {
        boolean isCorrectFilm = false;
        if (filmName != null && !filmName.isBlank() && releaseDate != null && !releaseDate.isBlank() && genre != null && !genre.isBlank()) {
            boolean isCorrectName = validateFilmName(filmName);
            boolean isCorrectReleaseDate = validateReleaseDate(releaseDate);
            boolean isCorrectGenre = validateGenre(genre);
            if (isCorrectName && isCorrectReleaseDate && isCorrectGenre) {
                isCorrectFilm = true;
            }
        }
        return isCorrectFilm;
    }

    public boolean validateFilmName(String filmName) {
        Pattern pattern = Pattern.compile(FILM_NAME_REGEX);
        Matcher matcher = pattern.matcher(filmName);
        boolean isCorrect = matcher.matches();
        if (!isCorrect) {
            errorMessage.add(RequestParameterName.WRONG_FILM_NAME_VALUE);
        }
        return isCorrect;
    }

    private boolean validateGenre(String genre) {
        Pattern pattern = Pattern.compile(GENRE_REGEX);
        Matcher matcher = pattern.matcher(genre);
        boolean isCorrect = matcher.matches();
        if (!isCorrect) {
            errorMessage.add(RequestParameterName.WRONG_FILM_GENRE_VALUE);
        }
        return isCorrect;
    }

    private boolean validateReleaseDate(String releaseDate) {
        String[] date = releaseDate.split(SPLIT_REGEX);
        int year = Integer.parseInt(date[YEAR_INDEX]);
        int month = Integer.parseInt(date[MONTH_INDEX]) - 1;
        int day = Integer.parseInt(date[DAY_INDEX]);

        Calendar calendarBirthday = new GregorianCalendar();
        long currentDate = calendarBirthday.getTimeInMillis();
        calendarBirthday.set(year, month, day);
        long birthdayMillis = calendarBirthday.getTimeInMillis();
        boolean isCorrect = false;
        if (birthdayMillis >= MIN_DATE && birthdayMillis <= (currentDate - RELEASE_PERIOD)) {
            isCorrect = true;
        }
        if (!isCorrect) {
            errorMessage.add(RequestParameterName.WRONG_RELEASE_DATE_VALUE);
        }
        return isCorrect;
    }

    public Set<String> getFilmErrorMessage() {
        Set<String> errors = new HashSet<>();
        errors.addAll(errorMessage);
        return errors;
    }
}
