package shared.communication;

import shared.model.Project;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Steven
 * Date: 2/15/14
 * Time: 2:08 PM
 * To change this template use File | Settings | File Templates.
 */
public class GetAllProjects_Result {

    private List<Project> projects;

    public GetAllProjects_Result() {
        projects = null;
    }

    public List<Project> getProjects() {
        return projects;
    }

    public void setProjects(List<Project> projects) {
        this.projects = projects;
    }

}
