package by.nareiko.fr.exception;

/**
 * The type Service exception.
 */
public class ServiceException extends Exception {
    /**
     * Instantiates a new Service exception.
     */
    public ServiceException() {
        super();
    }

    /**
     * Instantiates a new Service exception.
     *
     * @param message the message
     */
    public ServiceException(String message) {
        super(message);
    }

    /**
     * Instantiates a new Service exception.
     *
     * @param e the e
     */
    public ServiceException(Exception e) {
        super(e);
    }

    /**
     * Instantiates a new Service exception.
     *
     * @param messagr the messagr
     * @param e       the e
     */
    public ServiceException(String messagr, Exception e) {
        super(messagr, e);
    }
}
