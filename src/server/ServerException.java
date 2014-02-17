package server;

/**
 * The type Server exception.
 */
@SuppressWarnings("serial")
public class ServerException extends Exception {

    /**
     * Instantiates a new Server exception.
     */
    public ServerException() {
		return;
	}

    /**
     * Instantiates a new Server exception.
     *
     * @param message the message
     */
    public ServerException(String message) {
		super(message);
	}

    /**
     * Instantiates a new Server exception.
     *
     * @param throwable the throwable
     */
    public ServerException(Throwable throwable) {
		super(throwable);
	}

    /**
     * Instantiates a new Server exception.
     *
     * @param message the message
     * @param throwable the throwable
     */
    public ServerException(String message, Throwable throwable) {
		super(message, throwable);
	}

}
