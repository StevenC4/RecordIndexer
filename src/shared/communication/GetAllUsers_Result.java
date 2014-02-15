package shared.communication;

import shared.model.User;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Steven
 * Date: 2/15/14
 * Time: 2:08 PM
 * To change this template use File | Settings | File Templates.
 */
public class GetAllUsers_Result {

    private List<User> users;

    public GetAllUsers_Result() {
        users = null;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

}
