package by.nareiko.films_raiting.entity;

import java.util.Calendar;

public class Director extends AbstractEntity {
    private String firstName;
    private String lastName;
    private Calendar birthday;

    public Director() {
    }

    public Director(String firstName, String lastName, Calendar birthday) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthday = birthday;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Calendar getBirthday() {
        return birthday;
    }

    public void setBirthday(Calendar birthday) {
        this.birthday = birthday;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Director director = (Director) o;
        return firstName.equals(director.getFirstName()) &&
                lastName.equals(director.getLastName()) &&
                birthday.equals(director.getBirthday());
    }

    @Override
    public int hashCode() {
        int prime = 31;
        int result = 1;
        result += prime*result + firstName.hashCode();
        result += prime*result + lastName.hashCode();
        result += prime*result + birthday.hashCode();
        return result;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append(getClass().getName())
                .append(", firstName = " + firstName)
                .append(", lastName = " + lastName)
                .append(", birthday = " + birthday);
        return builder.toString();
    }
}
