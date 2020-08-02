package by.nareiko.fr.entity;

public class Director extends AbstractEntity {
    private int id;
    private String firstName;
    private String lastName;
    private String birthday;

    public Director() {
    }

    public Director(int id, String firstName, String lastName, String birthday) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthday = birthday;
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

    public String getBirthday() {
        return birthday;
    }

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
