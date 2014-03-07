package shared.communication;

/**
 * Created with IntelliJ IDEA.
 * User: Steven
 * Date: 2/15/14
 * Time: 3:26 PM
 * To change this template use File | Settings | File Templates.
 */
public class Operation_Result {

    boolean failed;

    public Operation_Result() {
        failed = false;
    }

    public void setFailed(boolean failed) {
        this.failed = failed;
    }

    public boolean getFailed() {
        return failed;
    }

    public String toString() {
        if (failed) {
            return "FAILED\n";
        } else {
            return "TRUE\n";
        }
    }
}
