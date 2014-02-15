package shared.communication;

import shared.model.Project;

/**
 * Created with IntelliJ IDEA.
 * User: Steven
 * Date: 2/15/14
 * Time: 2:08 PM
 * To change this template use File | Settings | File Templates.
 */
public class AddProject_Params {

    private Project project;

    public AddProject_Params() {
        project = null;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }
    
}
