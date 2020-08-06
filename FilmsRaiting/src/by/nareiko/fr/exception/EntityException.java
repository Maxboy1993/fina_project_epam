package by.nareiko.fr.exception;

/**
 * The type Entity exception.
 */
public class EntityException extends Exception {
    /**
     * Instantiates a new Entity exception.
     */
    public EntityException() {
        super();
    }

    /**
     * Instantiates a new Entity exception.
     *
     * @param message the message
     */
    public EntityException(String message) {
        super(message);
    }

    /**
     * Instantiates a new Entity exception.
     *
     * @param e the e
     */
    public EntityException(Exception e) {
        super(e);
    }

    /**
     * Instantiates a new Entity exception.
     *
     * @param messagr the messagr
     * @param e       the e
     */
    public EntityException(String messagr, Exception e) {
        super(messagr, e);
    }
}
