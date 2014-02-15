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

    private static Logger logger;

    static {
        logger = Logger.getLogger("recordindexer");
    }

    private Database db;

    BatchesDAO(Database db) {
        this.db = db;
    }

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