package entity;

import java.util.Calendar;

public class Film extends AbstractEntity {
    private int filmId;
    private String name;
    private GenreType genreType;
    private Calendar releaseDate;
    private Actor actor;
    private Director director;
    private StatusType statusType;

    public Film() {
    }

    public Film(int filmId, String name, GenreType genreType, Calendar releaseDate, Actor actor, Director director, StatusType statusType) {
        this.filmId = filmId;
        this.name = name;
        this.genreType = genreType;
        this.releaseDate = releaseDate;
        this.actor = actor;
        this.director = director;
        this.statusType = statusType;
    }

    public int getFilmId() {
        return filmId;
    }

    public void setFilmId(int filmId) {
        this.filmId = filmId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public GenreType getGenreType() {
        return genreType;
    }

    public void setGenreType(GenreType genreType) {
        this.genreType = genreType;
    }

    public Calendar getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(Calendar releaseDate) {
        this.releaseDate = releaseDate;
    }

    public Actor getActor() {
        return actor;
    }

    public void setActor(Actor actor) {
        this.actor = actor;
    }

    public Director getDirector() {
        return director;
    }

    public void setDirector(Director director) {
        this.director = director;
    }

    public StatusType getStatusType() {
        return statusType;
    }

    public void setStatusType(StatusType statusType) {
        this.statusType = statusType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Film film = (Film) o;
        return filmId == film.getFilmId() &&
                name.equals(film.getName()) &&
                genreType == film.getGenreType() &&
                releaseDate.equals(film.getReleaseDate()) &&
                actor.equals(film.getActor()) &&
                director.equals(film.getDirector()) &&
                statusType == film.getStatusType();
    }

    @Override
    public int hashCode() {
        int prime = 31;
        int result = 1;
        result += prime*result + filmId;
        result += prime*result + genreType.hashCode();
        result += prime*result + releaseDate.hashCode();
        result += prime*result + actor.hashCode();
        result += prime*result + director.hashCode();
        result += prime*result + statusType.hashCode();
        return result;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append(getClass().getName())
                .append("filmId=" + filmId)
                .append(", name='" + name)
                .append(", genreType=" + genreType)
                .append(", releaseDate=" + releaseDate)
                .append(", actor=" + actor)
                .append(", director=" + director)
                .append(", statusType=" + statusType);
        return builder.toString();
    }
}