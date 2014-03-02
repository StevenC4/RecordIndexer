package shared.communication;

/**
 * Created with IntelliJ IDEA.
 * User: Steven
 * Date: 2/15/14
 * Time: 3:26 PM
 * To change this template use File | Settings | File Templates.
 */
public class Operation_Result {

    /**
     * The result string
     */
    private String result;

    /**
     * Instantiates a new Operation_Result object.
     */
    public Operation_Result() {
        result = null;
    }

    public Operation_Result(String result) {
        this.result = result;
    }

    /**
     * Gets result.
     *
     * @return the result
     */
    public String getResult() {
        return result;
    }

    /**
     * Sets result.
     *
     * @param result the result
     */
    public void setResult(String result) {
        this.result = result;
    }

}
