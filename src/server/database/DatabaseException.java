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
    public DatabaseException() {
        return;
    }

    public DatabaseException(String message) {
        super(message);
    }

    public DatabaseException(Throwable cause) {
        super(cause);

    }

    public DatabaseException(String message, Throwable cause) {
        super(message, cause);
    }
}