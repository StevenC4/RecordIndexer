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

    private User user;

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
        user = null;
        fields = null;
        searchValues = null;
    }

    public Search_Params(User user, String fields, String searchValues) {
        this.user = user;
        this.fields = fields;
        this.searchValues = searchValues;
    }

    public User getUser() {
        return user;
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
        this.user = user;
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
