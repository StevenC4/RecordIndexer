package server.database;

import shared.model.Project;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

/**
 * Created with IntelliJ IDEA.
 * User: Steven
 * Date: 2/12/14
 * Time: 10:27 AM
 * To change this template use File | Settings | File Templates.
 */
public class ProjectsDAO {

    /**
     * The logger
     */
    private static Logger logger;

    static {
        logger = Logger.getLogger("recordindexer");
    }

    /**
     * The database object
     */
    private Database db;

    /**
     * Instantiates a new ProjectsDAO.
     * @param db the database object
     */
    ProjectsDAO(Database db)  {
        this.db = db;
    }

    /**
     * Gets all the projects from the database.
     *
     * @return all the projects from the database
     * @throws DatabaseException the database exception
     */
    public List<Project> getAll() throws DatabaseException {

        logger.entering("server.database.ProjectsDAO", "getAll");

        String query = "SELECT * FROM projects";
        List<Project> projects;

        try {
            PreparedStatement statement = db.getConnection().prepareStatement(query);
            ResultSet rs = statement.executeQuery();

            projects = new ArrayList<Project>();
            while(rs.next()) {
                Project project = new Project();
                project.setProjectId(rs.getInt("project_id"));
                project.setTitle(rs.getString("title"));
                project.setRecordsPerImage(rs.getInt("records_per_image"));
                project.setFirstYCoord(rs.getInt("first_y_coord"));
                project.setRecordHeight(rs.getInt("record_height"));
                projects.add(project);
            }
        } catch (Exception e) {
            throw new DatabaseException(e.getMessage(), e);
        }

        logger.exiting("server.database.ProjectsDAO", "getAll");

        return projects;
    }

    /**
     * Add the project to the databases.
     *
     * @param project the project
     * @throws DatabaseException the database exception
     */
    public void add(Project project) throws DatabaseException {

        logger.entering("server.database.ProjectsDAO", "add");

        String query = "INSERT INTO projects" +
                "(title, records_per_image, first_y_coord, record_height) VALUES" +
                "(?,?,?,?)";

        try {
            PreparedStatement statement = db.getConnection().prepareStatement(query);

            statement.setString(1, project.getTitle());
            statement.setInt(2, project.getRecordsPerImage());
            statement.setInt(3, project.getFirstYCoord());
            statement.setInt(4, project.getRecordHeight());

            statement.executeUpdate();
        } catch (Exception e) {
            throw new DatabaseException(e.getMessage(), e);
        }

        logger.exiting("server.database.ProjectsDAO", "add");
    }

    /**
     * Update the project in the database.
     *
     * @param project the project
     * @throws DatabaseException the database exception
     */
    public void update(Project project) throws DatabaseException {

        logger.entering("server.database.ProjectsDAO", "update");

        String query = "UPDATE projects SET " +
                "title = ?, " +
                "records_per_image = ?, " +
                "first_y_coord = ?, " +
                "record_height = ? " +
                "WHERE project_id = ?" +
                "(?,?,?,?,?)";

        try {
            PreparedStatement statement = db.getConnection().prepareStatement(query);

            statement.setString(1, project.getTitle());
            statement.setInt(2, project.getRecordsPerImage());
            statement.setInt(3, project.getFirstYCoord());
            statement.setInt(4, project.getRecordHeight());
            statement.setInt(5, project.getProjectId());

            statement.executeUpdate();
        } catch (Exception e) {
            throw new DatabaseException(e.getMessage(), e);
        }

        logger.exiting("server.database.ProjectsDAO", "update");
    }

    /**
     * Delete the project from the database.
     *
     * @param project the project
     * @throws DatabaseException the database exception
     */
    public void delete(Project project) throws DatabaseException {

        logger.entering("server.database.ProjectsDAO", "delete");

        String query = "DELETE projects WHERE project_id = ?";

        try {
            PreparedStatement statement = db.getConnection().prepareStatement(query);

            statement.setInt(1, project.getProjectId());

            statement.executeUpdate();
        } catch (Exception e) {
            throw new DatabaseException(e.getMessage(), e);
        }

        logger.exiting("server.database.ProjectsDAO", "delete");
    }

    public void deleteAll() throws DatabaseException {

        logger.entering("server.database.ProjectsDAO", "deleteAll");

        String query = "DELETE FROM projects";
        String resetIncrement = "UPDATE sqlite_sequence SET seq=? WHERE name=?";

        try {
            PreparedStatement statement = db.getConnection().prepareStatement(query);
            statement.executeUpdate();
            statement = db.getConnection().prepareStatement(resetIncrement);
            statement.setInt(1, 0);
            statement.setString(2, "projects");
            statement.executeUpdate();
        } catch (Exception e) {
            throw new DatabaseException(e.getMessage(), e);
        }

        logger.exiting("server.database.ProjectsDAO", "deleteAll");
    }

    public void addList(List<Project> projectList) throws DatabaseException {
        logger.entering("server.database.ProjectsDAO", "addList");

        try {
            for (int i = 0; i < projectList.size(); i++) {
                String query = "INSERT INTO projects" +
                        "(title, records_per_image, first_y_coord, record_height) VALUES" +
                        "(?,?,?,?)";
                PreparedStatement statement = db.getConnection().prepareStatement(query);

                statement.setString(1, projectList.get(i).getTitle());
                statement.setInt(2, projectList.get(i).getRecordsPerImage());
                statement.setInt(3, projectList.get(i).getFirstYCoord());
                statement.setInt(4, projectList.get(i).getRecordHeight());

                statement.executeUpdate();
            }
        } catch (Exception e) {
            throw new DatabaseException(e.getMessage(), e);
        }

        logger.exiting("server.database.ProjectsDAO", "addList");
    }

    public Project getProjectById(int projectId) throws DatabaseException {
        logger.entering("server.database.ProjectsDAO", "getProjectById");

        Project project;

        try {
            String query = "SELECT * FROM projects WHERE project_id=?";
            PreparedStatement statement = db.getConnection().prepareStatement(query);
            statement.setInt(1, projectId);

            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                project = new Project(rs.getInt("project_id"), rs.getString("title"), rs.getInt("records_per_image"), rs.getInt("first_y_coord"), rs.getInt("record_height"));
            } else {
                throw new Exception("No such project found in the database");
            }
        } catch (Exception e) {
            throw new DatabaseException(e.getMessage(), e);
        }

        logger.exiting("server.database.ProjectsDAO", "getProjectById");

        return project;
    }
}