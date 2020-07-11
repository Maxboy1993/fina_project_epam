package by.nareiko.fr.exception;

public class ServiceException extends Exception{
    public ServiceException() {
        super();
    }

    public ServiceException(String message) {
        super(message);
    }

    public ServiceException(Exception e) {
        super(e);
    }

    public ServiceException(String messagr, Exception e) {
        super(messagr, e);
    }
}
