package by.nareiko.films_raiting.validator;

public class UserValidator {
    public boolean checkLoginData(String login, String password) {
        boolean flag = false;

        //изменить валидацию на соответствие регулярному выражению EMAIL

        if (login != null && !login.isBlank() && password != null && !password.isBlank()) {
            flag = true;
        }
        return flag;
    }

    public boolean checkRegistrationData(String firstName, String lastName, String login, String password) {
        boolean flag = false;

        //изменить валидацию на соответствие регулярному выражению EMAIL

        if (firstName != null && !firstName.isBlank() && lastName != null && !lastName.isBlank() &&
        login != null && !login.isBlank() && password != null && !password.isBlank()){
            flag = true;
        }
        return flag;
    }
}
