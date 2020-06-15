package by.nareiko.films_raiting.entity;

import java.util.Calendar;

public class Actor extends AbstractEntity {
    private int actorId;
    private String name;
    private Calendar birthday;

    public Actor() {
    }

    public Actor(int actorId, String name, Calendar birthday) {
        this.actorId = actorId;
        this.name = name;
        this.birthday = birthday;
    }

    public int getActorId() {
        return actorId;
    }

    public void setActorId(int actorId) {
        this.actorId = actorId;
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
        Actor actor = (Actor) o;
        return actorId == actor.getActorId() &&
                name.equals(actor.getName()) &&
                birthday.equals(actor.getBirthday());
    }

    @Override
    public int hashCode() {
        int prime = 31;
        int result = 1;
        result += prime*result + actorId;
        result += prime*result + name.hashCode();
        result += prime*result + birthday.hashCode();
        return result;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append(getClass().getName())
                .append("actorId=" + actorId)
                .append(", name='" + name)
                .append(", birthday=" + birthday);
        return builder.toString();
    }
}
