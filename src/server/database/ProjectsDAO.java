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

    private static Logger logger;

    static {
        logger = Logger.getLogger("recordindexer");
    }

    private Database db;

    ProjectsDAO(Database db)  {
        this.db = db;
    }

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

}