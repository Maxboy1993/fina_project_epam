package by.nareiko.fr.validator;

public class UserValidator {
    public boolean checkLoginData(String login, String password) {
        boolean flag = false;

        //изменить валидацию на соответствие регулярному выражению EMAIL

        if (login != null && !login.isBlank() && password != null && !password.isBlank()) {
            flag = true;
        }
        return flag;
    }

    public boolean checkRegistrationData(String firstName, String lastName, String login, String password, String[] bithday) {
        boolean flag = false;

        //изменить валидацию на соответствие регулярному выражению EMAIL

        if (firstName != null && !firstName.isBlank() && lastName != null && !lastName.isBlank() &&
        login != null && !login.isBlank() && password != null && !password.isBlank()){
            flag = true;
        }
        for (int i = 0; i < bithday.length; i++) {
            if (Integer.parseInt(bithday[i]) < 0){
                return  false;
            }
        }
        return flag;
    }
}
