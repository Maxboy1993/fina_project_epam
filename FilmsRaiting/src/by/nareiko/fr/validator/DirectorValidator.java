package by.nareiko.fr.validator;

import by.nareiko.fr.controller.RequestParameterName;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * The type Director validator.
 */
public class DirectorValidator {
    private static final String SPLIT_REGEX = "-";
    private static final String NAME_REGEX = "[a-zA-Z ]{2,30}";
    private static final int YEAR_INDEX = 0;
    private static final int MONTH_INDEX = 1;
    private static final int DAY_INDEX = 2;
    private static final long MAX_AGE = 1009910620976L;
    private static final long MIN_AGE = -2524447659733L;
    private Set<String> errorDirectorMessages;

    /**
     * Instantiates a new Director validator.
     */
    public DirectorValidator() {
        errorDirectorMessages = new HashSet<>();
    }

    /**
     * Validate director data boolean.
     *
     * @param firstName the first name
     * @param lastName  the last name
     * @param birthday  the birthday
     * @return the boolean
     */
    public boolean validateDirectorData(String firstName, String lastName, String birthday) {
        boolean isCorrect = false;

        if (firstName != null && !firstName.isBlank() && lastName != null && !lastName.isBlank() &&
                birthday != null && !birthday.isBlank()) {
            boolean isCorrectFirstName = validateName(firstName);
            boolean isCorrectLastName = validateName(lastName);
            boolean isCorrectBirthday = validateBirthday(birthday);
            if (isCorrectFirstName && isCorrectLastName && isCorrectBirthday) {
                isCorrect = true;
            }
        }
        return isCorrect;
    }

    private boolean validateName(String name) {
        Pattern pattern = Pattern.compile(NAME_REGEX);
        Matcher matcher = pattern.matcher(name);
        boolean isCorrect = matcher.matches();
        if (!isCorrect) {
            errorDirectorMessages.add(RequestParameterName.WRONG_NAME_DATA_VALUE);
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
            errorDirectorMessages.add(RequestParameterName.WRONG_BIRTHDAY_DATA_VALUE);
        }
        return isCorrect;
    }

    /**
     * Gets error director messages.
     *
     * @return the error director messages
     */
    public Set<String> getErrorDirectorMessages() {
        Set<String> errors = new HashSet<>();
        errors.addAll(errorDirectorMessages);
        return errors;
    }
}
