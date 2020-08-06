package by.nareiko.fr.entity;

/**
 * The type Director.
 */
public class Director extends AbstractEntity {
    private int id;
    private String firstName;
    private String lastName;
    private String birthday;

    /**
     * Instantiates a new Director.
     */
    public Director() {
    }

    /**
     * Instantiates a new Director.
     *
     * @param id        the id
     * @param firstName the first name
     * @param lastName  the last name
     * @param birthday  the birthday
     */
    public Director(int id, String firstName, String lastName, String birthday) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthday = birthday;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Director director = (Director) o;
        return id == director.getId() &&
                firstName.equals(director.getFirstName()) &&
                lastName.equals(director.getLastName()) &&
                birthday.equals(director.getBirthday());
    }

    @Override
    public int hashCode() {
        int prime = 31;
        int result = 1;
        result += prime * result + id;
        result += prime * result + firstName.hashCode();
        result += prime * result + lastName.hashCode();
        result += prime * result + birthday.hashCode();
        return result;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append(getClass().getName())
                .append(", id= " + id)
                .append(", firstName = " + firstName)
                .append(", lastName = " + lastName)
                .append(", birthday = " + birthday);
        return builder.toString();
    }
}
