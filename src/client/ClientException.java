package client;

/**
 * The type Client exception.
 */
@SuppressWarnings("serial")
public class ClientException extends Exception {

    /**
     * Instantiates a new Client exception.
     */
    public ClientException() {
		return;
	}

    /**
     * Instantiates a new Client exception.
     *
     * @param message the message
     */
    public ClientException(String message) {
		super(message);
	}

    /**
     * Instantiates a new Client exception.
     *
     * @param throwable the throwable
     */
    public ClientException(Throwable throwable) {
		super(throwable);
	}

    /**
     * Instantiates a new Client exception.
     *
     * @param message the message
     * @param throwable the throwable
     */
    public ClientException(String message, Throwable throwable) {
		super(message, throwable);
	}

}
