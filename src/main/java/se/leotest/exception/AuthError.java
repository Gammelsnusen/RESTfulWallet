package se.leotest.exception;

public class AuthError extends RuntimeException {

    private static final long serialVersionUID = 1L;
    
    String message;
    
    public AuthError() {
        message = "Shit!";
    }
    
    public AuthError(String message) {
        this.message = message;
    }
    
    public String toString() {
        return message;
    }
}
