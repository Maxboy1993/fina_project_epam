package by.nareiko.fr.entity;

public class FilmRaiting extends AbstractEntity {
    private int id;
    private int filmId;
    private double raiting;

    public FilmRaiting() {
    }

    public FilmRaiting(int id, int filmId, double raiting) {
        this.id = id;
        this.filmId = filmId;
        this.raiting = raiting;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getFilmId() {
        return filmId;
    }

    public void setFilmId(int filmId) {
        this.filmId = filmId;
    }

    public double getRaiting() {
        return raiting;
    }

    public void setRaiting(double raiting) {
        this.raiting = raiting;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FilmRaiting that = (FilmRaiting) o;
        return id == that.getId() &&
                filmId == that.getFilmId() &&
                raiting == that.getRaiting();
    }

    @Override
    public int hashCode() {
        int prime = 31;
        int result = 1;
        result += prime * result + id;
        result += prime * result + filmId;
        result += prime * result + (int) raiting;
        return result;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append(getClass().getName())
                .append(", id=" + id)
                .append(", filmId=" + filmId)
                .append(", raiting=" + raiting);
        return builder.toString();
    }
}
