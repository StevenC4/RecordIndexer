package shared.model;

import static org.junit.Assert.*;
import org.junit.Test;
import org.junit.Before;
import org.junit.After;
import server.database.Database;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Steven
 * Date: 3/6/14
 * Time: 5:44 PM
 * To change this template use File | Settings | File Templates.
 */
public class ProjectsManagerTest {

    ProjectsManager projectsManager;

    @Before
    public void before() throws Exception {
        projectsManager = new ProjectsManager();
        FieldsManager.initialize();
        projectsManager.deleteAllProjects();
    }

    @After
    public void after() throws Exception {
    }

    /**
     *
     * Method: getAllProjects()
     *
     */
    @Test
    public void testGetAllProjects() throws Exception {
        List<Project> projects = new ArrayList<Project>();
        projects.add(new Project(1, "title1", 5, 5, 9));
        projects.add(new Project(2, "title3", 2, 8, 2));
        projects.add(new Project(3, "title2", 5, 7, 4));
        projects.add(new Project(4, "title4", 4, 2, 8));
        projectsManager.addList(projects);
        List<Project> returnedList = projectsManager.getAllProjects();
        assertEquals(projects.size(), returnedList.size());
        assertEquals(projects, returnedList);
    }

    /**
     *
     * Method: deleteAllProjects()
     *
     */
    @Test
    public void testDeleteAllProjects() throws Exception {
        projectsManager.addProject(new Project(1, "title1", 5, 5, 9));
        projectsManager.deleteAllProjects();
        assertEquals(0, projectsManager.getAllProjects().size());
    }

    /**
     *
     * Method: addList(List<Project> projectList)
     *
     */
    @Test
    public void testAddList() throws Exception {
        projectsManager.deleteAllProjects();
        List<Project> projects = new ArrayList<Project>();
        projects.add(new Project(1, "title1", 5, 5, 9));
        projects.add(new Project(2, "title3", 2, 8, 2));
        projects.add(new Project(3, "title2", 5, 7, 4));
        projects.add(new Project(4, "title4", 4, 2, 8));
        projectsManager.addList(projects);
        List<Project> returnedList = projectsManager.getAllProjects();
        assertEquals(projects.size(), returnedList.size());
        assertEquals(projects, returnedList);
    }

    /**
     *
     * Method: getProjectById(int projectId)
     *
     */
    @Test
    public void testGetProjectById() throws Exception {
        projectsManager.deleteAllProjects();
        Project project1 = new Project(1, "title1", 5, 5, 9);
        Project project2 = new Project(2, "title3", 2, 8, 2);
        Project project3 = new Project(3, "title2", 5, 7, 4);
        Project project4 = new Project(4, "title4", 4, 2, 8);
        projectsManager.addProject(project1);
        projectsManager.addProject(project2);
        projectsManager.addProject(project3);
        projectsManager.addProject(project4);
        Project returnedProject = projectsManager.getProjectById(project3.getProjectId());
        assertEquals(project3, returnedProject);
    }
}
