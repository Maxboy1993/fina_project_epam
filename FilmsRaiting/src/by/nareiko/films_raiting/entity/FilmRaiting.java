package by.nareiko.films_raiting.entity;

public class FilmRaiting extends AbstractEntity {
    private double raiting;

    public FilmRaiting() {
    }

    public FilmRaiting(double raiting) {
        this.raiting = raiting;
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
        return raiting == that.getRaiting();
    }

    @Override
    public int hashCode() {
        int prime = 31;
        int result = 1;
        result += prime*result + (int) raiting;
        return result;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append(getClass().getName())
                .append(", raiting=" + raiting);
        return builder.toString();
    }
}
