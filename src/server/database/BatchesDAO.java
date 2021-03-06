package server.database;

import shared.model.Batch;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
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
public class BatchesDAO {

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
     * Instantiates a new BatchesDAO.
     *
     * @param db the database object
     */
    BatchesDAO(Database db) {
        this.db = db;
    }

    /**
     * For testing only
     * @param batch
     * @throws DatabaseException
     */

    public void add(Batch batch) throws DatabaseException {

        logger.entering("server.database.BatchesDAO", "add");

        String query = "INSERT INTO batches" +
                "(batch_id, project_id, path, status) VALUES" +
                "(?,?,?,?)";

        try {
            PreparedStatement statement = db.getConnection().prepareStatement(query);

            statement.setInt(1, batch.getBatchId());
            statement.setInt(2, batch.getProjectId());
            statement.setString(3, batch.getPath());
            statement.setString(4, batch.getStatus());

            statement.executeUpdate();
        } catch (Exception e) {
            throw new DatabaseException(e.getMessage(), e);
        }

        logger.exiting("server.database.BatchesDAO", "add");
    }

    public List<Batch> getAll() throws DatabaseException {

        logger.entering("server.database.BatchesDAO", "getAll");

        List<Batch> batches = new ArrayList<Batch>();
        String query = "SELECT * FROM batches";

        try {
            PreparedStatement statement = db.getConnection().prepareStatement(query);

            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                batches.add(new Batch(rs.getInt("batch_id"), rs.getInt("project_id"), rs.getString("path"), rs.getString("status")));
            }
        } catch (Exception e) {
            throw new DatabaseException(e.getMessage(), e);
        }

        logger.exiting("server.database.BatchesDAO", "getAll");

        return batches;
    }

    /**
     * End testing methods
     */

    /**
     * Update the batch in the database.
     *
     * @param batch the batch
     * @throws DatabaseException the database exception
     */
    public void update(Batch batch) throws DatabaseException {

        logger.entering("server.database.BatchesDAO", "update");

        String query = "UPDATE batches SET " +
                "project_id = ?, " +
                "path = ?, " +
                "status = ? " +
                "WHERE batch_id = ?";

        try {
            PreparedStatement statement = db.getConnection().prepareStatement(query);

            statement.setInt(1, batch.getProjectId());
            statement.setString(2, batch.getPath());
            statement.setString(3, batch.getStatus());
            statement.setInt(4, batch.getBatchId());

            statement.executeUpdate();
        } catch (Exception e) {
            throw new DatabaseException(e.getMessage(), e);
        }

        logger.exiting("server.database.BatchesDAO", "update");
    }

    public void deleteAll() throws DatabaseException {

        logger.entering("server.database.BatchesDAO", "deleteAll");

        String query = "DELETE FROM batches";
        String resetIncrement = "UPDATE sqlite_sequence SET seq=? WHERE name=?";

        try {
            PreparedStatement statement = db.getConnection().prepareStatement(query);
            statement.executeUpdate();
            statement = db.getConnection().prepareStatement(resetIncrement);
            statement.setInt(1, 0);
            statement.setString(2, "batches");
            statement.executeUpdate();
        } catch (Exception e) {
            throw new DatabaseException(e.getMessage(), e);
        }

        logger.exiting("server.database.BatchesDAO", "deleteAll");
    }

    /**
     * Gets sample image for the project.
     *
     * @param projectId the project id
     * @return the sample image
     * @throws DatabaseException the database exception
     */
    public Batch getSampleImage(int projectId) throws DatabaseException {

        logger.entering("server.database.BatchesDAO", "getSampleImage");

        Batch batch;
        String query = "SELECT * FROM batches WHERE project_id = ?";

        try {
            PreparedStatement statement = db.getConnection().prepareStatement(query);

            statement.setInt(1, projectId);

            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                batch = new Batch(rs.getInt("batch_id"), rs.getInt("project_id"), rs.getString("path"), rs.getString("status"));
            } else {
                throw new Exception("Project ID is invalid or has no corresponding batches");
            }
        } catch (Exception e) {
            throw new DatabaseException(e.getMessage(), e);
        }

        return batch;
    }

    public void addList(List<Batch> batchList) throws DatabaseException {

        logger.entering("server.database.BatchesDAO", "addList");

        try {
            for (int i = 0; i < batchList.size(); i++) {
                String query = "INSERT INTO batches" +
                        "(project_id, path, status) VALUES" +
                        "(?,?,?)";
                PreparedStatement statement = db.getConnection().prepareStatement(query);

                statement.setInt(1, batchList.get(i).getProjectId());
                statement.setString(2, batchList.get(i).getPath());
                statement.setString(3, batchList.get(i).getStatus());

                statement.executeUpdate();
            }
        } catch (Exception e) {
            throw new DatabaseException(e.getMessage(), e);
        }

        logger.exiting("server.database.BatchesDAO", "addList");
    }

    public Batch getNextAvailableBatch(int projectId) throws DatabaseException {
        logger.entering("server.database.BatchesDAO", "getNextAvailableBatch");

        Batch batch;

        try {
            String query = "SELECT * FROM batches WHERE project_id=? AND status=?";
                    PreparedStatement statement = db.getConnection().prepareStatement(query);

            statement.setInt(1, projectId);
            statement.setString(2, "new");

            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                batch = new Batch(rs.getInt("batch_id"), rs.getInt("project_id"), rs.getString("path"), rs.getString("status"));
            } else {
                throw new Exception("There are no new batches associated with this project");
            }

        } catch (Exception e) {
            throw new DatabaseException(e.getMessage(), e);
        }

        logger.exiting("server.database.BatchesDAO", "getNextAvailableBatch");

        return batch;
    }

    public Batch getBatchByBatchId(int batchId) throws DatabaseException {
        logger.entering("server.database.BatchesDAO", "getBatchByBatchId");

        Batch batch;

        try {
            String query = "SELECT * FROM batches WHERE batch_id=?";
            PreparedStatement statement = db.getConnection().prepareStatement(query);
            statement.setInt(1, batchId);

            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                batch = new Batch(rs.getInt("batch_id"), rs.getInt("project_id"), rs.getString("path"), rs.getString("status"));
            } else {
                throw new Exception("There is no batch associated with this batch ID");
            }
        } catch (Exception e) {
            throw new DatabaseException(e.getMessage(), e);
        }

        logger.exiting("server.database.BatchesDAO", "getBatchByBatchId");

        return batch;
    }
}