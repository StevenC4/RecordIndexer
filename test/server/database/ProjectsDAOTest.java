package server.database;

import org.junit.Test; 
import org.junit.Before; 
import org.junit.After;
import shared.model.Project;

/** 
* ProjectsDAO Tester. 
* 
* @author <Authors name> 
* @since <pre>Feb 12, 2014</pre> 
* @version 1.0 
*/ 
public class ProjectsDAOTest {

    private Database db;
    private ProjectsDAO projectsDAO;

@Before
public void before() throws Exception {
    db = new Database();
    Database.initialize();
    projectsDAO = new ProjectsDAO(db);
} 

@After
public void after() throws Exception {
    db = null;
    projectsDAO = null;
} 

/** 
* 
* Method: add(Project project)
* 
*/ 
@Test
public void testAddProject() throws Exception {
    Project project = new Project(0, "Test Project", 20, 10, 80);
    Database db = new Database();
    ProjectsDAO projectDAO = new ProjectsDAO(db);

    projectDAO.add(project);
} 


} 
