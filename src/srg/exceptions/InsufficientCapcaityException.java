package srg.exceptions;

/**
 * A custom exception subclass that is thrown when there is not enough capacity to store a resource.
 */
public class InsufficientCapcaityException extends Exception {
    /** Constructs an InsufficientCapcaityException with no message. */
    public InsufficientCapcaityException() {
        super();
    }

    /**
     * Constructs an InsufficientCapcaityException that contains a message explaining
     * why the exception occurred.
     * @param message the message that is to be passed if the error is thrown.
     */
    public InsufficientCapcaityException(String message) {
        super(message);
    }
}
