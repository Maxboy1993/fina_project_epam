package by.nareiko.fr.entity;

import java.sql.Blob;
import java.util.Calendar;

public class User extends AbstractEntity {
    private int id;
    private String firstName;
    private String lastName;
    private Calendar birthday;
    private RoleType role;
    private String login;
    private String password;
    private StatusType status;
    private Blob poster;

    public User() {
    }

    public User(int id, String firstName, String lastName, Calendar birthday, RoleType role, String login, String password, StatusType status, Blob poster) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthday = birthday;
        this.role = role;
        this.login = login;
        this.password = password;
        this.status = status;
        this.poster = poster;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public RoleType getRoleType() {
        return role;
    }

    public void setRoleType(RoleType role) {
        this.role = role;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public StatusType getStatusType() {
        return status;
    }

    public void setStatusType(StatusType status) {
        this.status = status;
    }

    public Blob getPoster() {
        return poster;
    }

    public void setPoster(Blob poster) {
        this.poster = poster;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return id == user.getId() &&
                firstName.equals(user.getFirstName()) &&
                lastName.equals(user.getLastName()) &&
                birthday.equals(user.getBirthday()) &&
                role == user.getRoleType() &&
                login.equals(user.getLogin()) &&
                password.equals(user.getPassword()) &&
                status == user.getStatusType();
    }

    @Override
    public int hashCode() {
        int prime = 31;
        int result = 1;
        result += prime * result + id;
        result += prime * result + firstName.hashCode();
        result += prime * result + lastName.hashCode();
        result += prime * result + birthday.hashCode();
        result += prime * result + role.hashCode();
        result += prime * result + login.hashCode();
        result += prime * result + password.hashCode();
        result += prime * result + status.hashCode();
        return result;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append(getClass().getName())
                .append(", id = " + id)
                .append(", firstName = " + firstName)
                .append(", lastName = " + lastName)
                .append(", birthday = " + birthday)
                .append(", role = " + role)
                .append(", login = " + login)
                .append(", password = " + password)
                .append(", status = " + status);
        return builder.toString();
    }
}
