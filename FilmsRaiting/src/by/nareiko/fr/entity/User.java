package by.nareiko.fr.entity;

/**
 * The type User.
 */
public class User extends AbstractEntity {
    private int id;
    private String firstName;
    private String lastName;
    private String birthday;
    private RoleType role;
    private String login;
    private String password;
    private StatusType status;
    private boolean isVerified;

    /**
     * Instantiates a new User.
     */
    public User() {
    }

    /**
     * Instantiates a new User.
     *
     * @param id         the id
     * @param firstName  the first name
     * @param lastName   the last name
     * @param birthday   the birthday
     * @param role       the role
     * @param login      the login
     * @param password   the password
     * @param status     the status
     * @param isVerified the is verified
     */
    public User(int id, String firstName, String lastName, String birthday, RoleType role, String login, String password, StatusType status, boolean isVerified) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthday = birthday;
        this.role = role;
        this.login = login;
        this.password = password;
        this.status = status;
        this.isVerified = isVerified;
    }

    /**
     * Gets id.
     *
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * Sets id.
     *
     * @param id the id
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Gets first name.
     *
     * @return the first name
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Sets first name.
     *
     * @param firstName the first name
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * Gets last name.
     *
     * @return the last name
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * Sets last name.
     *
     * @param lastName the last name
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * Gets birthday.
     *
     * @return the birthday
     */
    public String getBirthday() {
        return birthday;
    }

    /**
     * Sets birthday.
     *
     * @param birthday the birthday
     */
    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    /**
     * Gets role type.
     *
     * @return the role type
     */
    public RoleType getRoleType() {
        return role;
    }

    /**
     * Sets role type.
     *
     * @param role the role
     */
    public void setRoleType(RoleType role) {
        this.role = role;
    }

    /**
     * Gets login.
     *
     * @return the login
     */
    public String getLogin() {
        return login;
    }

    /**
     * Sets login.
     *
     * @param login the login
     */
    public void setLogin(String login) {
        this.login = login;
    }

    /**
     * Gets password.
     *
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * Sets password.
     *
     * @param password the password
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Gets status type.
     *
     * @return the status type
     */
    public StatusType getStatusType() {
        return status;
    }

    /**
     * Sets status type.
     *
     * @param status the status
     */
    public void setStatusType(StatusType status) {
        this.status = status;
    }

    /**
     * Is verified boolean.
     *
     * @return the boolean
     */
    public boolean isVerified() {
        return isVerified;
    }

    /**
     * Sets verified.
     *
     * @param verified the verified
     */
    public void setVerified(boolean verified) {
        isVerified = verified;
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
                status == user.getStatusType() &&
                isVerified == user.isVerified();
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
        result += prime * result + (isVerified == true ? 1 : 0);
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
                .append(", status = " + status)
                .append(", isVerified" + isVerified);
        return builder.toString();
    }
}
