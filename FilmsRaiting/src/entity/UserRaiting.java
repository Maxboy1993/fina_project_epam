package entity;

public class UserRaiting  extends AbstractEntity {
    private int userId;
    private double raiting;

    public UserRaiting() {
    }

    public UserRaiting(int userId, double raiting) {
        this.userId = userId;
        this.raiting = raiting;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int filmId) {
        this.userId = userId;
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
        UserRaiting that = (UserRaiting) o;
        return userId == that.getUserId() &&
                raiting == that.getRaiting();
    }

    @Override
    public int hashCode() {
        int prime = 31;
        int result = 1;
        result += prime*result + userId;
        result += prime*result + (int) raiting;
        return result;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append(getClass().getName())
                .append("userId=" + userId)
                .append(", raiting=" + raiting);
        return builder.toString();
    }
}
