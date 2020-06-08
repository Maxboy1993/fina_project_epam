package entity;

import java.util.Calendar;

public class User  extends AbstractEntity {
    private int userId;
    private String name;
    private Calendar birthday;
    private RoleType role;
    private String login;
    private String password;
    private StatusType status;

    public User() {
    }

    public User(int userId, String name, Calendar birthday, RoleType role, String login, String password, StatusType status) {
        this.userId = userId;
        this.name = name;
        this.birthday = birthday;
        this.role = role;
        this.login = login;
        this.password = password;
        this.status = status;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return userId == user.getUserId() &&
                name.equals(user.getName()) &&
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
        result += prime*result + userId;
        result += prime*result + name.hashCode();
        result += prime*result + birthday.hashCode();
        result += prime*result + role.hashCode();
        result += prime*result + login.hashCode();
        result += prime*result + password.hashCode();
        result += prime*result + status.hashCode();
        return result;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append(getClass().getName())
                .append("userId=" + userId)
                .append(", name='" + name)
                .append(", birthday=" + birthday)
                .append(", role=" + role)
                .append(", login='" + login)
                .append(", password='" + password)
                .append(", status=" + status);
        return builder.toString();
    }
}
