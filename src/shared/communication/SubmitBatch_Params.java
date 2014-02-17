package shared.communication;

import shared.model.User;

/**
 * Created with IntelliJ IDEA.
 * User: Steven
 * Date: 2/15/14
 * Time: 3:28 PM
 * To change this template use File | Settings | File Templates.
 */
public class SubmitBatch_Params {

    /**
     * The username
     */
    private String username;

    /**
     * The password
     */
    private String password;

    /**
     * The ID of the batch being submitted
     */
    private int batchId;

    /**
     * A set of record data separated by semicolons.  Record data is divided into field data, separated by commas.
     */
    private String fieldValues;

    /**
     * Instantiates a new SubmitBatch_Params object.
     */
    public SubmitBatch_Params() {
        username = null;
        password = null;
        batchId = -1;
        fieldValues = null;
    }

    /**
     * Gets username.
     *
     * @return the username
     */
    public String getUsername() {
        return username;
    }

    /**
     * Gets password.
     *
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * Gets batch id.
     *
     * @return the batch id
     */
    public int getBatchId() {
        return batchId;
    }

    /**
     * Gets field values.
     *
     * @return the field values
     */
    public String getFieldValues() {
        return fieldValues;
    }

    /**
     * Sets user.
     *
     * @param user the user
     */
    public void setUser(User user) {
        username = user.getUsername();
        password = user.getPassword();
    }

    /**
     * Sets batch id.
     *
     * @param batchId the batch id
     */
    public void setBatchId(int batchId) {
        this.batchId = batchId;
    }

    /**
     * Sets field values.
     *
     * @param fieldValues the field values
     */
    public void setFieldValues(String fieldValues) {
        this.fieldValues = fieldValues;
    }

}
