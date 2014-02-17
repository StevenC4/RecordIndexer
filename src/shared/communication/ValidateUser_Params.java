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
    private String username;

    /**
     * The password
     */
    private String password;

    /**
     * Instantiates a new ValidateUser_Params object.
     */
    public ValidateUser_Params() {
        username = null;
        password = null;
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
     * Sets user.
     *
     * @param user the user
     */
    public void setUser(User user) {
        username = user.getUsername();
        password = user.getPassword();
    }
}
