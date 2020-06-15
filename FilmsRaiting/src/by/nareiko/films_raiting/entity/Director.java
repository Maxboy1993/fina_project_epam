package by.nareiko.films_raiting.entity;

import java.util.Calendar;

public class Director extends AbstractEntity {
    private int directorId;
    private String name;
    private Calendar birthday;

    public Director() {
    }

    public Director(int directorId, String name, Calendar birthday) {
        this.directorId = directorId;
        this.name = name;
        this.birthday = birthday;
    }

    public int getDirectorId() {
        return directorId;
    }

    public void setDirectorId(int directorId) {
        this.directorId = directorId;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Director director = (Director) o;
        return directorId == director.getDirectorId() &&
                name.equals(director.getName()) &&
                birthday.equals(director.getBirthday());
    }

    @Override
    public int hashCode() {
        int prime = 31;
        int result = 1;
        result += prime*result + directorId;
        result += prime*result + name.hashCode();
        result += prime*result + birthday.hashCode();
        return result;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append(getClass().getName())
                .append("directorId=" + directorId)
                .append(", name='" + name)
                .append(", birthday=" + birthday);
        return builder.toString();
    }
}
