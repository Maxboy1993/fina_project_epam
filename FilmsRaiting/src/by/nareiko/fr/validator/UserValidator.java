package by.nareiko.fr.validator;

import by.nareiko.fr.controller.RequestParameterName;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * The type User validator.
 */
public class UserValidator {
    private static final String SPLIT_REGEX = "-";
    private static final String LOGIN_REGEX = "[a-zA-Z0-9_.-]{1,40}@[a-zA-Z0-9_-]{2,40}.[a-z]{2,4}";
    private static final int LOGIN_MAX_LENGTH = 84;
    private static final String PASSWORD_REGEX = "[a-zA-Z0-9_.-@]{6,50}";
    private static final int PASSWORD_MAX_LENGTH = 50;
    private static final String NAME_REGEX = "[a-zA-Z ]{2,50}";
    private static final int NAME_MAX_LENGTH = 50;
    private static final int YEAR_INDEX = 0;
    private static final int MONTH_INDEX = 1;
    private static final int DAY_INDEX = 2;
    private static final long MAX_AGE = 1451680420578L;
    private static final long MIN_AGE = -2524447659733L;
    private Set<String> errorMessages;

    /**
     * Instantiates a new User validator.
     */
    public UserValidator() {
        errorMessages = new HashSet<>();
    }

    /**
     * Validate login and password boolean.
     *
     * @param login    the login
     * @param password the password
     * @return the boolean
     */
    public boolean validateLoginAndPassword(String login, String password) {
        boolean isCorrect = false;
        if (login != null && !login.isBlank() && password != null && !password.isBlank()) {
            boolean isLoginMatched = validateLogin(login);
            boolean isPassMatched = validatePassword(password);
            if (isLoginMatched && isPassMatched) {
                isCorrect = true;
            }
        }
        return isCorrect;
    }

    /**
     * Validate registration data boolean.
     *
     * @param firstName the first name
     * @param lastName  the last name
     * @param login     the login
     * @param password  the password
     * @param birthday  the birthday
     * @return the boolean
     */
    public boolean validateRegistrationData(String firstName, String lastName, String login, String password, String birthday) {
        boolean isCorrect = false;

        if (firstName != null && !firstName.isBlank() && lastName != null && !lastName.isBlank() &&
                login != null && !login.isBlank() && password != null && !password.isBlank() && birthday != null && !birthday.isBlank()) {
            boolean isCorrectFirstName = validateName(firstName);
            boolean isCorrectLastName = validateName(lastName);
            boolean isCorrectLogin = validateLogin(login);
            boolean isCorrectPassword = validatePassword(password);
            boolean isCorrectBirthday = validateBirthday(birthday);
            if (isCorrectFirstName && isCorrectLastName && isCorrectLogin && isCorrectPassword && isCorrectBirthday) {
                isCorrect = true;
            }
        }

        return isCorrect;
    }

    private boolean validateLogin(String login) {
        boolean isLengthMatched = login.length() <= LOGIN_MAX_LENGTH;
        Pattern pattern = Pattern.compile(LOGIN_REGEX);
        Matcher matcher = pattern.matcher(login);
        boolean isLoginRegexMatched = matcher.matches();
        boolean isCorrect = false;
        if (isLengthMatched && isLoginRegexMatched) {
            isCorrect = true;
        }
        if (!isCorrect) {
            errorMessages.add(RequestParameterName.WRONG_LOGIN_DATA_VALUE);
        }
        return isCorrect;
    }

    private boolean validatePassword(String password) {
        boolean isLengthMatched = password.length() <= PASSWORD_MAX_LENGTH;
        Pattern pattern = Pattern.compile(PASSWORD_REGEX);
        Matcher matcher = pattern.matcher(password);
        boolean isPassRegexMatched = matcher.matches();
        boolean isCorrect = false;
        if (isLengthMatched && isPassRegexMatched) {
            isCorrect = true;
        }
        if (!isCorrect) {
            errorMessages.add(RequestParameterName.WRONG_PASSWORD_DATA_VALUE);
        }
        return isCorrect;
    }

    private boolean validateName(String name) {
        boolean isLengthMatched = name.length() <= NAME_MAX_LENGTH;
        Pattern pattern = Pattern.compile(NAME_REGEX);
        Matcher matcher = pattern.matcher(name);
        boolean isNameRegexMatched = matcher.matches();
        boolean isCorrect = false;
        if (isLengthMatched && isNameRegexMatched) {
            isCorrect = true;
        }
        if (!isCorrect) {
            errorMessages.add(RequestParameterName.WRONG_NAME_DATA_VALUE);
        }
        return isCorrect;
    }

    private boolean validateBirthday(String birthday) {
        String[] date = birthday.split(SPLIT_REGEX);
        int year = Integer.parseInt(date[YEAR_INDEX]);
        int month = Integer.parseInt(date[MONTH_INDEX]) - 1;
        int day = Integer.parseInt(date[DAY_INDEX]);

        Calendar calendarBirthday = new GregorianCalendar();
        calendarBirthday.set(year, month, day);
        long birthdayMillis = calendarBirthday.getTimeInMillis();
        boolean isCorrect = false;
        if (birthdayMillis >= MIN_AGE && birthdayMillis <= MAX_AGE) {
            isCorrect = true;
        }
        if (!isCorrect) {
            errorMessages.add(RequestParameterName.WRONG_BIRTHDAY_DATA_VALUE);
        }
        return isCorrect;
    }

    /**
     * Gets user error messages.
     *
     * @return the user error messages
     */
    public Set<String> getUserErrorMessages() {
        Set<String> errors = new HashSet<>();
        errors.addAll(errorMessages);
        return errors;
    }
}
