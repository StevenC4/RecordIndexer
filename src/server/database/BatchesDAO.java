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
     * Gets all batches from the database.
     *
     * @return all the batches
     * @throws DatabaseException the database exception
     */
    public List<Batch> getAll() throws DatabaseException {

        logger.entering("server.database.BatchesDAO", "getAll");

        String query = "SELECT * FROM batches";
        List<Batch> batches;

        try {
            PreparedStatement statement = db.getConnection().prepareStatement(query);
            ResultSet rs = statement.executeQuery();

            batches = new ArrayList<Batch>();
            while (rs.next()) {
                Batch batch = new Batch();
                batch.setBatchId(rs.getInt("batch_id"));
                batch.setProjectId(rs.getInt("project_id"));
                batch.setPath(rs.getString("path"));
                batch.setStatus(rs.getString("status"));
                batches.add(batch);
            }
        } catch (Exception e) {
            throw new DatabaseException(e.getMessage(), e);
        }

        logger.exiting("server.database.BatchesDAO", "getAll");

        return batches;
    }

    /**
     * Add a batch to the database.
     *
     * @param batch the batch
     * @throws DatabaseException the database exception
     */
    public void add(Batch batch) throws DatabaseException {

        logger.entering("server.database.BatchesDAO", "add");

        String query = "INSERT INTO batches" +
                "(project_id, path, status) VALUES" +
                "(?,?,?)";

        try {
            PreparedStatement statement = db.getConnection().prepareStatement(query);

            statement.setInt(1, batch.getProjectId());
            statement.setString(2, batch.getPath());
            statement.setString(3, batch.getStatus());

            statement.executeUpdate();
        } catch (Exception e) {
            throw new DatabaseException(e.getMessage(), e);
        }

        logger.exiting("server.database.BatchesDAO", "add");
    }

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
                "status = ?, " +
                "WHERE batch_id = ?" +
                "(?,?,?,?)";

        try {
            PreparedStatement statement = db.getConnection().prepareStatement(query);

            statement.setInt(1, batch.getProjectId());
            statement.setString(2, batch.getPath());
            statement.setString(3, batch.getStatus());

            statement.executeUpdate();
        } catch (Exception e) {
            throw new DatabaseException(e.getMessage(), e);
        }

        logger.exiting("server.database.BatchesDAO", "update");
    }

    /**
     * Delete the batch from the database.
     *
     * @param batch the batch
     * @throws DatabaseException the database exception
     */
    public void delete(Batch batch) throws DatabaseException {

        logger.entering("server.database.BatchesDAO", "delete");

        String query = "DELETE batches WHERE batch_id = ?";

        try {
            PreparedStatement statement = db.getConnection().prepareStatement(query);

            statement.setInt(1, batch.getBatchId());

            statement.executeUpdate();
        } catch (Exception e) {
            throw new DatabaseException(e.getMessage(), e);
        }

        logger.exiting("server.database.BatchesDAO", "delete");
    }

}