package shared.communication;

import shared.model.User;

/**
 * Created with IntelliJ IDEA.
 * User: Steven
 * Date: 3/6/14
 * Time: 3:21 PM
 * To change this template use File | Settings | File Templates.
 */
public class ValidateUser_Result extends Operation_Result {

    private User user;
    private boolean validated;

    public ValidateUser_Result() {
        super();
        validated = false;
    }

    public ValidateUser_Result(User user) {
        super();
        this.user = user;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public boolean isValidated() {
        return validated;
    }

    public void setValidated(boolean validated) {
        this.validated = validated;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        if (failed) {
            sb.append("FAILED\n");
        } else if (validated) {
            sb.append("TRUE\n");
            sb.append(user.getFirstName()).append("\n");
            sb.append(user.getLastName()).append("\n");
            sb.append(user.getIndexedRecords()).append("\n");
        } else {
            sb.append("FALSE\n");
        }
        return sb.toString();
    }
}
