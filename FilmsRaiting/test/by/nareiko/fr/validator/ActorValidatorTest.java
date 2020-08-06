package by.nareiko.fr.validator;

import org.junit.BeforeClass;
import org.junit.Test;



import static org.junit.Assert.*;

public class ActorValidatorTest {
    private static final String FIRST_NAME = "Matew";
    private static final String LAST_NAME = "Jordan";
    private static final String BIRTHDAY = "2000-03-17";
    private static ActorValidator actorValidator;

    @BeforeClass
    public static void setUp() {
        actorValidator = new ActorValidator();
    }

    @Test
    public void validateActorData() {
        String firstName = FIRST_NAME;
        String lastName = LAST_NAME;
        String birthday = BIRTHDAY;
        boolean expected = true;
        boolean actual = actorValidator.validateActorData(firstName, lastName, birthday);
        assertEquals(expected, actual);
    }
}