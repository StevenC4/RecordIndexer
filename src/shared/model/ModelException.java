package shared.model;

/**
 * Created with IntelliJ IDEA.
 * User: Steven
 * Date: 2/15/14
 * Time: 2:26 PM
 * To change this template use File | Settings | File Templates.
 */
public class ModelException extends Exception {

    /**
     * Instantiates a new Model exception.
     */
    public ModelException() {
        return;
    }

    /**
     * Instantiates a new Model exception.
     *
     * @param message the message
     */
    public ModelException(String message) {
        super(message);
    }

    /**
     * Instantiates a new Model exception.
     *
     * @param cause the cause
     */
    public ModelException(Throwable cause) {
        super(cause);
    }

    /**
     * Instantiates a new Model exception.
     *
     * @param message the message
     * @param cause the cause
     */
    public ModelException(String message, Throwable cause) {
        super(message, cause);
    }

}
