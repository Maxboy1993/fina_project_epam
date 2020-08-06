package by.nareiko.fr.validator;

import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * The type User validator test.
 */
public class UserValidatorTest {
    private static final String FIRST_NAME = "Matew";
    private static final String LAST_NAME = "Jordan";
    private static final String BIRTHDAY = "2000-03-17";
    private static final String LOGIN = "mmm@gmail.com";
    private static final String PASSWORD = "d1@_dsam";
    private static UserValidator userValidator;

    @BeforeClass
    public static void setUp() {
        userValidator = new UserValidator();
    }

    @Test
    public void validateLoginAndPassword() {
        boolean expected = true;
        boolean actual = userValidator.validateLoginAndPassword(LOGIN, PASSWORD);
        assertEquals(expected, actual);
    }

    @Test
    public void validateRegistrationData() {
        boolean expected = true;
        boolean actual = userValidator.validateRegistrationData(FIRST_NAME, LAST_NAME, LOGIN, PASSWORD, BIRTHDAY);
        assertEquals(expected, actual);
    }
}