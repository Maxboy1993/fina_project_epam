package by.nareiko.fr.entity;

/**
 * The type Film raiting.
 */
public class FilmRaiting extends AbstractEntity {
    private int id;
    private int filmId;
    private int userId;
    private int raiting;

    /**
     * Instantiates a new Film raiting.
     */
    public FilmRaiting() {
    }

    /**
     * Instantiates a new Film raiting.
     *
     * @param id      the id
     * @param filmId  the film id
     * @param userId  the user id
     * @param raiting the raiting
     */
    public FilmRaiting(int id, int filmId, int userId, int raiting) {
        this.id = id;
        this.filmId = filmId;
        this.userId = userId;
        this.raiting = raiting;
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
     * Gets film id.
     *
     * @return the film id
     */
    public int getFilmId() {
        return filmId;
    }

    /**
     * Sets film id.
     *
     * @param filmId the film id
     */
    public void setFilmId(int filmId) {
        this.filmId = filmId;
    }

    /**
     * Gets user id.
     *
     * @return the user id
     */
    public int getUserId() {
        return userId;
    }

    /**
     * Sets user id.
     *
     * @param userId the user id
     */
    public void setUserId(int userId) {
        this.userId = userId;
    }

    /**
     * Gets raiting.
     *
     * @return the raiting
     */
    public int getRaiting() {
        return raiting;
    }

    /**
     * Sets raiting.
     *
     * @param raiting the raiting
     */
    public void setRaiting(int raiting) {
        this.raiting = raiting;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FilmRaiting that = (FilmRaiting) o;
        return id == that.getId() &&
                filmId == that.getFilmId() &&
                raiting == that.getRaiting() &&
                userId == that.getUserId();
    }

    @Override
    public int hashCode() {
        int prime = 31;
        int result = 1;
        result += prime * result + id;
        result += prime * result + filmId;
        result += prime * result + userId;
        result += prime * result + raiting;
        return result;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append(getClass().getName())
                .append(", id=" + id)
                .append(", filmId=" + filmId)
                .append(", userId=" + userId)
                .append(", raiting=" + raiting);
        return builder.toString();
    }
}
