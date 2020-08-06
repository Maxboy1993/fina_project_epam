package by.nareiko.fr.validator;

import by.nareiko.fr.entity.Director;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.*;

public class DirectorValidatorTest {
    private static final String FIRST_NAME = "Matew";
    private static final String LAST_NAME = "Jordan";
    private static final String BIRTHDAY = "2000-03-17";
    private static DirectorValidator directorValidator;

    @BeforeClass
    public static void setUp() {
        directorValidator = new DirectorValidator();
    }

    @Test
    public void validateDirectorData() {
        boolean expected = true;
        boolean actual = directorValidator.validateDirectorData(FIRST_NAME, LAST_NAME, BIRTHDAY);
        assertEquals(expected, actual);
    }
}