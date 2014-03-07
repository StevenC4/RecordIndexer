package shared.communication;

import shared.model.Project;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Steven
 * Date: 3/6/14
 * Time: 3:29 PM
 * To change this template use File | Settings | File Templates.
 */
public class GetProjects_Result extends Operation_Result {

    private List<Project> projects;

    public GetProjects_Result() {
        super();
        projects = null;
    }

    public GetProjects_Result(List<Project> projects) {
        super();
        this.projects = projects;
    }

    public List<Project> getProjects() {
        return projects;
    }

    public void setProjects(List<Project> projects) {
        this.projects = projects;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        if (failed) {
            sb.append("FAILED\n");
        } else {
            for (int i = 0; i < projects.size(); i++) {
                sb.append(projects.get(i).getProjectId()).append("\n");
                sb.append(projects.get(i).getTitle()).append("\n");
            }
        }
        return sb.toString();
    }
}
