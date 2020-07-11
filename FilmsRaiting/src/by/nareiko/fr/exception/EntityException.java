package by.nareiko.fr.exception;

public class EntityException extends Exception {
    public EntityException() {
        super();
    }

    public EntityException(String message) {
        super(message);
    }

    public EntityException(Exception e) {
        super(e);
    }

    public EntityException(String messagr, Exception e) {
        super(messagr, e);
    }
}
