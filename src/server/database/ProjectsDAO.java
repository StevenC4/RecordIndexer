package server.database;

import shared.model.Project;

import java.sql.Connection;
import java.sql.PreparedStatement;
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
        logger = Logger.getLogger("projectmanager");
    }

    private Database db;

    ProjectsDAO(Database db)  {
        this.db = db;
    }

    public boolean addProject(Project project) {
        PreparedStatement statement = null;
        Connection connection = null;
        boolean hasError = false;

        String query = "INSERT INTO projects" +
                "(project_id, title, records_per_image, first_y_coord, record_height) VALUES" +
                "(?,?,?,?,?)";

        try {
            db.startTransaction();
            connection = db.getConnection();
            statement = connection.prepareStatement(query);

            statement.setInt(1, project.getProjectId());
            statement.setString(2, project.getTitle());
            statement.setInt(3, project.getRecordsPerImage());
            statement.setInt(4, project.getFirstYCoord());
            statement.setInt(5, project.getRecordHeight());

            statement.executeUpdate();
        } catch (Exception e) {
            hasError = true;
        } finally {
            if (hasError) {
                db.endTransaction(false);
            } else {
                db.endTransaction(true);
            }
        }

        return false;
    }
}
