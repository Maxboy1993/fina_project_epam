package by.nareiko.fr.exception;

public class ControllerException extends Exception {
    public ControllerException() {
        super();
    }

    public ControllerException(String message) {
        super(message);
    }

    public ControllerException(Exception e) {
        super(e);
    }

    public ControllerException(String messagr, Exception e) {
        super(messagr, e);
    }
}
