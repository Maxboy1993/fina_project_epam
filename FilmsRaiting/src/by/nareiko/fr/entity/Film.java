package by.nareiko.fr.entity;

import java.util.List;

/**
 * The type Film.
 */
public class Film extends AbstractEntity {
    /**
     * The Poster.
     */
    String poster;
    private int id;
    private String name;
    private GenreType genreType;
    private String releaseDate;
    private List<Actor> actors;
    private Director director;
    private double raiting;

    /**
     * Instantiates a new Film.
     */
    public Film() {
    }

    /**
     * Instantiates a new Film.
     *
     * @param id          the id
     * @param name        the name
     * @param genreType   the genre type
     * @param releaseDate the release date
     * @param actors      the actors
     * @param director    the director
     * @param raiting     the raiting
     * @param poster      the poster
     */
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
     * Gets name.
     *
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets name.
     *
     * @param name the name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets genre type.
     *
     * @return the genre type
     */
    public GenreType getGenreType() {
        return genreType;
    }

    /**
     * Sets genre type.
     *
     * @param genreType the genre type
     */
    public void setGenreType(GenreType genreType) {
        this.genreType = genreType;
    }

    /**
     * Gets release date.
     *
     * @return the release date
     */
    public String getReleaseDate() {
        return releaseDate;
    }

    /**
     * Sets release date.
     *
     * @param releaseDate the release date
     */
    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    /**
     * Gets actors.
     *
     * @return the actors
     */
    public List<Actor> getActors() {
        return actors;
    }

    /**
     * Sets actors.
     *
     * @param actors the actors
     */
    public void setActors(List<Actor> actors) {
        this.actors = actors;
    }


    /**
     * Gets director.
     *
     * @return the director
     */
    public Director getDirector() {
        return director;
    }

    /**
     * Sets director.
     *
     * @param director the director
     */
    public void setDirector(Director director) {
        this.director = director;
    }

    /**
     * Gets raiting.
     *
     * @return the raiting
     */
    public double getRaiting() {
        return raiting;
    }

    /**
     * Sets raiting.
     *
     * @param raiting the raiting
     */
    public void setRaiting(double raiting) {
        this.raiting = raiting;
    }

    /**
     * Gets poster.
     *
     * @return the poster
     */
    public String getPoster() {
        return poster;
    }

    /**
     * Sets poster.
     *
     * @param poster the poster
     */
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
