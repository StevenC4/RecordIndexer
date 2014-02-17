package shared.communication;

import shared.model.User;

/**
 * Created with IntelliJ IDEA.
 * User: Steven
 * Date: 2/17/14
 * Time: 11:00 AM
 * To change this template use File | Settings | File Templates.
 */
public class GetSampleImage_Params {

    /**
     * The username
     */
    private String username;

    /**
     * The password
     */
    private String password;

    /**
     * The project ID
     */
    private int projectId;

    /**
     * Instantiates a new GetSampleImage_Params object.
     */
    public GetSampleImage_Params() {
        username = null;
        password = null;
        projectId = -1;
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
        username = user.getUsername();
        password = user.getPassword();
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
