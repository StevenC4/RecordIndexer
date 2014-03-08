package shared.communication;

import shared.model.User;

/**
 * Created with IntelliJ IDEA.
 * User: Steven
 * Date: 2/15/14
 * Time: 3:29 PM
 * To change this template use File | Settings | File Templates.
 */
public class GetFields_Params {

    private User user;

    /**
     * The project ID
     */
    private Object projectId;

    /**
     * Instantiates a new GetFields_Params object.
     */
    public GetFields_Params() {
        user = null;
        projectId = "";
    }

    public User getUser() {
        return user;
    }

    /**
     * Gets project id.
     *
     * @return the project id
     */
    public Object getProjectId() {
        return projectId;
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
     * Sets project id.
     *
     * @param projectId the project id
     */
    public void setProjectId(Object projectId) {
        this.projectId = projectId;
    }

}
