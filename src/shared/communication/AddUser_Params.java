package shared.communication;

import shared.model.User;

/**
 * Created with IntelliJ IDEA.
 * User: Steven
 * Date: 2/15/14
 * Time: 2:08 PM
 * To change this template use File | Settings | File Templates.
 */
public class AddUser_Params {

    private User user;

    public AddUser_Params() {
        user = null;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
    
}
