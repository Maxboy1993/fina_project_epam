package by.nareiko.fr.exception;

/**
 * The type Dao exception.
 */
public class DaoException extends Exception {
    /**
     * Instantiates a new Dao exception.
     */
    public DaoException() {
        super();
    }

    /**
     * Instantiates a new Dao exception.
     *
     * @param message the message
     */
    public DaoException(String message) {
        super(message);
    }

    /**
     * Instantiates a new Dao exception.
     *
     * @param e the e
     */
    public DaoException(Exception e) {
        super(e);
    }

    /**
     * Instantiates a new Dao exception.
     *
     * @param messagr the messagr
     * @param e       the e
     */
    public DaoException(String messagr, Exception e) {
        super(messagr, e);
    }
}
