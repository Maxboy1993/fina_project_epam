package by.nareiko.fr.validator;

import by.nareiko.fr.controller.RequestParameterName;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ActorValidator {
    private static final String SPLIT_REGEX = "-";
    private static final String NAME_REGEX = "[a-zA-Z ]{2,30}";
    private static final int YEAR_INDEX = 0;
    private static final int MONTH_INDEX = 1;
    private static final int DAY_INDEX = 2;
    private static final long MAX_AGE = 1451680420578L;
    private static final long MIN_AGE = -2524447659733L;
    private Set<String> errorActorMessages;

    public ActorValidator() {
        errorActorMessages = new HashSet<>();
    }


    public boolean validateActorData(String firstName, String lastName, String birthday) {
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
            errorActorMessages.add(RequestParameterName.WRONG_NAME_DATA_VALUE);
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
            errorActorMessages.add(RequestParameterName.WRONG_BIRTHDAY_DATA_VALUE);
        }
        return isCorrect;
    }

    public Set<String> getErrorActorMessages() {
        Set<String> errors = new HashSet<>();
        errors.addAll(errorActorMessages);
        return errors;
    }
}
