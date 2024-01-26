package srg.exceptions;

/**
 * A custom exception subclass thrown when no flight or jump path to a specified SpacePort can be
 * found.
 */
public class NoPathException extends Exception {
    /** Constructs an NoPathException with no message. */
    public NoPathException() {
        super();
    }

    /**
     * Constructs an NoPathException that contains a message explaining why the exception occurred.
     * @param message the message that is to be passed if the error is thrown.
     */
    public NoPathException(String message) {
        super(message);
    }
}
