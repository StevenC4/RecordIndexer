package shared.model;

/**
 * Created with IntelliJ IDEA.
 * User: Steven
 * Date: 2/15/14
 * Time: 2:26 PM
 * To change this template use File | Settings | File Templates.
 */
public class ModelException extends Exception {

    public ModelException() {
        return;
    }

    public ModelException(String message) {
        super(message);
    }

    public ModelException(Throwable cause) {
        super(cause);
    }

    public ModelException(String message, Throwable cause) {
        super(message, cause);
    }

}
