package by.nareiko.fr.exception;

public class DaoException extends Exception {
    public DaoException() {
        super();
    }

    public DaoException(String message) {
        super(message);
    }

    public DaoException(Exception e) {
        super(e);
    }

    public DaoException(String messagr, Exception e) {
        super(messagr, e);
    }
}
