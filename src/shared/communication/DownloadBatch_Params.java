package shared.communication;

import shared.model.User;

/**
 * Created with IntelliJ IDEA.
 * User: Steven
 * Date: 2/15/14
 * Time: 3:28 PM
 * To change this template use File | Settings | File Templates.
 */
public class DownloadBatch_Params {

    private User user;

    /**
     * The project ID
     */
    private int projectId;

    /**
     * Instantiates a new DownloadBatch_Params object.
     */
    public DownloadBatch_Params() {
        user = null;
        projectId = -1;
    }

    /**
     * Gets username.
     *
     * @return the username
     */
    public User getUser() {
        return user;
    }
    /**
     * Gets project id.
     *
     * @return the project id
     */
    public int getProjectId() {
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
    public void setProjectId(int projectId) {
        this.projectId = projectId;
    }

}
