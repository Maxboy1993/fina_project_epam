package by.nareiko.fr.entity;

import java.util.List;

public class Film extends AbstractEntity {
    String poster;
    private int id;
    private String name;
    private GenreType genreType;
    private String releaseDate;
    private List<Actor> actors;
    private Director director;
    private double raiting;

    public Film() {
    }

    public Film(int id, String name, GenreType genreType, String releaseDate, List<Actor> actors, Director director, double raiting, String poster) {
        this.id = id;
        this.name = name;
        this.genreType = genreType;
        this.releaseDate = releaseDate;
        this.actors = actors;
        this.director = director;
        this.raiting = raiting;
        this.poster = poster;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public List<Actor> getActors() {
        return actors;
    }

    public void setActors(List<Actor> actors) {
        this.actors = actors;
    }


    public Director getDirector() {
        return director;
    }

    public void setDirector(Director director) {
        this.director = director;
    }

    public double getRaiting() {
        return raiting;
    }

    public void setRaiting(double raiting) {
        this.raiting = raiting;
    }

    public String getPoster() {
        return poster;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Film film = (Film) o;
        return id == film.getId() &&
                name.equals(film.getName()) &&
                genreType == film.getGenreType() &&
                releaseDate.equals(film.getReleaseDate()) &&
                actors.equals(film.getActors()) &&
                director.equals(film.getDirector()) &&
                raiting == film.getRaiting();
    }

    @Override
    public int hashCode() {
        int prime = 31;
        int result = 1;
        result += prime * result + id;
        result += prime * result + genreType.hashCode();
        result += prime * result + releaseDate.hashCode();
        result += prime * result + actors.hashCode();
        result += prime * result + director.hashCode();
        result += prime * result + raiting;
        return result;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append(getClass().getName())
                .append(", id='" + id)
                .append(", name='" + name)
                .append(", genreType=" + genreType)
                .append(", releaseDate=" + releaseDate)
                .append(", actor=" + actors)
                .append(", director=" + director)
                .append(", raiting=" + raiting);
        return builder.toString();
    }
}
