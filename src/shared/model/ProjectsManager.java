package shared.model;

import server.database.Database;
import server.database.DatabaseException;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Steven
 * Date: 2/11/14
 * Time: 3:43 PM
 * To change this template use File | Settings | File Templates.
 */
public class ProjectsManager {

    /**
     * Initialize the Database object.
     *
     * @throws ModelException the model exception
     */
    public static void initialize() throws ModelException {
        try {
            Database.initialize();
        }
        catch (DatabaseException e) {
            throw new ModelException(e.getMessage(), e);
        }
    }

    /**
     * Gets all projects.
     *
     * @return the all projects
     * @throws ModelException the model exception
     */
    public static List<Project> getAllProjects() throws ModelException {

        Database db = new Database();

        try {
            db.startTransaction();
            List<Project> projects = db.getProjectsDAO().getAll();
            db.endTransaction(true);
            return projects;
        }
        catch (DatabaseException e) {
            db.endTransaction(false);
            throw new ModelException(e.getMessage(), e);
        }
    }

    /**
     * Add project.
     *
     * @param project the project
     * @throws ModelException the model exception
     */
    public static void addProject(Project project) throws ModelException {

        Database db = new Database();

        try {
            db.startTransaction();
            db.getProjectsDAO().add(project);
            db.endTransaction(true);
        }
        catch (DatabaseException e) {
            db.endTransaction(false);
            throw new ModelException(e.getMessage(), e);
        }
    }

    /**
     * Update project.
     *
     * @param project the project
     * @throws ModelException the model exception
     */
    public static void updateProject(Project project) throws ModelException {

        Database db = new Database();

        try {
            db.startTransaction();
            db.getProjectsDAO().update(project);
            db.endTransaction(true);
        }
        catch (DatabaseException e) {
            db.endTransaction(false);
            throw new ModelException(e.getMessage(), e);
        }
    }

    /**
     * Delete project.
     *
     * @param project the project
     * @throws ModelException the model exception
     */
    public static void deleteProject(Project project) throws ModelException {

        Database db = new Database();

        try {
            db.startTransaction();
            db.getProjectsDAO().delete(project);
            db.endTransaction(true);
        }
        catch (DatabaseException e) {
            db.endTransaction(false);
            throw new ModelException(e.getMessage(), e);
        }
    }

    public static void deleteAllProjects() throws ModelException {

        Database db = new Database();

        try {
            db.startTransaction();
            db.getProjectsDAO().deleteAll();
            db.endTransaction(true);
        }
        catch (DatabaseException e) {
            db.endTransaction(false);
            throw new ModelException(e.getMessage(), e);
        }
    }

    public void addList(List<Project> projectList) throws ModelException {
        Database db = new Database();

        try {
            db.startTransaction();
            db.getProjectsDAO().addList(projectList);
            db.endTransaction(true);
        }
        catch (DatabaseException e) {
            db.endTransaction(false);
            throw new ModelException(e.getMessage(), e);
        }
    }
}
