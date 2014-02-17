package server.database;

/**
 * Created with IntelliJ IDEA.
 * User: Steven
 * Date: 2/11/14
 * Time: 6:12 PM
 * To change this template use File | Settings | File Templates.
 */
@SuppressWarnings("serial")
public class DatabaseException extends Exception {

    /**
     * Instantiates a new Database exception.
     */
    public DatabaseException() {
        return;
    }

    /**
     * Instantiates a new Database exception.
     *
     * @param message the message
     */
    public DatabaseException(String message) {
        super(message);
    }

    /**
     * Instantiates a new Database exception.
     *
     * @param cause the cause
     */
    public DatabaseException(Throwable cause) {
        super(cause);

    }

    /**
     * Instantiates a new Database exception.
     *
     * @param message the message
     * @param cause the cause
     */
    public DatabaseException(String message, Throwable cause) {
        super(message, cause);
    }
}