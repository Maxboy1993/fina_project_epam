package model.dao.exception;

public class ReaderExceprion extends Exception {
    public ReaderExceprion(){
        super();
    }
    public ReaderExceprion(String message){
        super(message);
    }
    public ReaderExceprion(Exception e){
        super(e);
    }
    public ReaderExceprion(String messagr, Exception e){
        super(messagr, e);
    }
}
