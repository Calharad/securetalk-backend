package pl.calharad.securetalk.exception;

public class IncorrectPasswordException extends RuntimeException {

    public IncorrectPasswordException() {
    }

    public IncorrectPasswordException(String message) {
        super(message);
    }

    public IncorrectPasswordException(Throwable cause) {
        super(cause);
    }

    public IncorrectPasswordException(String message, Throwable cause) {
        super(message, cause);
    }
}
