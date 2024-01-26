package srg.exceptions;

/**
 * A custom exception subclass thrown when a necessary resource is unavailable or a room is
 * broken.
 */
public class InsufficientResourcesException extends Exception {
    /** Constructs an InsufficientResourcesException with no message. */
    public InsufficientResourcesException() {
        super();
    }

    /**
     * Constructs an InsufficientResourcesException that contains a message explaining
     * why the exception occurred.
     * @param message the message that is to be passed if the error is thrown.
     */
    public InsufficientResourcesException(String message) {
        super(message);
    }
}
