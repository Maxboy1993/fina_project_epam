package by.nareiko.fr.validator;

import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.*;

public class FilmValidatorTest {
    private static final String FILM_NAME = "The lost";
    private static final String RELEASE_DATE = "2004-08-22";
    private static final String GENRE = "Thriller";
    private static FilmValidator filmValidator;

    @BeforeClass
    public static void setUp() {
        filmValidator = new FilmValidator();
    }

    @Test
    public void validateFilmData() {
        boolean expected = true;
        boolean actual = filmValidator.validateFilmData(FILM_NAME, RELEASE_DATE, GENRE);
        assertEquals(expected, actual);
    }

    @Test
    public void validateFilmName() {
        boolean expected = true;
        boolean actual = filmValidator.validateFilmName(FILM_NAME);
        assertEquals(expected, actual);
    }
}