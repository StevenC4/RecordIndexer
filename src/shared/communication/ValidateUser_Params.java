package shared.communication;

import shared.model.User;

/**
 * Created with IntelliJ IDEA.
 * User: Steven
 * Date: 2/15/14
 * Time: 3:27 PM
 * To change this template use File | Settings | File Templates.
 */
public class ValidateUser_Params {

    /**
     * The username
     */
    private User user;

    /**
     * Instantiates a new ValidateUser_Params object.
     */
    public ValidateUser_Params() {
        user = null;
    }

    public ValidateUser_Params(User user) {
        this.user = user;
    }

    /**
     * Sets user.
     *
     * @param user the user
     */
    public void setUser(User user) {
        this.user = user;
    }

    public User getUser() {
        return user;
    }

}
