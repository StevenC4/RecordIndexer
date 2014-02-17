package shared.communication;

import shared.model.User;

/**
 * Created with IntelliJ IDEA.
 * User: Steven
 * Date: 2/15/14
 * Time: 3:29 PM
 * To change this template use File | Settings | File Templates.
 */
public class Search_Params {

    /**
     * The username
     */
    private String username;

    /**
     * The password
     */
    private String password;

    /**
     * A comma separated string of the integers corresponding to the fields on which to search
     */
    private String fields;

    /**
     * A comma separated string of values to search for in the corresponding fields
     */
    private String searchValues;

    /**
     * Instantiates a new Search_Params object.
     */
    public Search_Params() {
        username = null;
        password = null;
        fields = null;
        searchValues = null;
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
     * Gets fields.
     *
     * @return the fields
     */
    public String getFields() {
        return fields;
    }

    /**
     * Gets search values.
     *
     * @return the search values
     */
    public String getSearchValues() {
        return searchValues;
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
     * Sets fields.
     *
     * @param fields the fields
     */
    public void setFields(String fields) {
        this.fields = fields;
    }

    /**
     * Sets search values.
     *
     * @param searchValues the search values
     */
    public void setSearchValues(String searchValues) {
        this.searchValues = searchValues;
    }
}
