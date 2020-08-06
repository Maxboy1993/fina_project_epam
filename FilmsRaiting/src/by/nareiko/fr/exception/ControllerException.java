package by.nareiko.fr.exception;

/**
 * The type Controller exception.
 */
public class ControllerException extends Exception {
    /**
     * Instantiates a new Controller exception.
     */
    public ControllerException() {
        super();
    }

    /**
     * Instantiates a new Controller exception.
     *
     * @param message the message
     */
    public ControllerException(String message) {
        super(message);
    }

    /**
     * Instantiates a new Controller exception.
     *
     * @param e the e
     */
    public ControllerException(Exception e) {
        super(e);
    }

    /**
     * Instantiates a new Controller exception.
     *
     * @param messagr the messagr
     * @param e       the e
     */
    public ControllerException(String messagr, Exception e) {
        super(messagr, e);
    }
}
