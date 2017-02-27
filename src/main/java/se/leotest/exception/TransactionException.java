package se.leotest.exception;

/**
 * Exception som kastas vid fel vid transaktioner
 * 
 * @author Andreas
 */
public class TransactionException extends Exception {
    
    private static final long serialVersionUID = 1L;
    
    String message;
    
    public TransactionException() {
        message = "Unknown TransactionException";
    }
    
    public TransactionException(String message) {
        this.message = message;
    }
    
    public String toString() {
        return message;
    }
}
