package shared.model;

import server.database.Database;
import server.database.DatabaseException;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Steven
 * Date: 2/11/14
 * Time: 3:52 PM
 * To change this template use File | Settings | File Templates.
 */
public class BatchesManager {

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
     * For testing purposes
     * @param batch
     * @throws ModelException
     */

    public static void addBatch(Batch batch) throws ModelException {

        Database db = new Database();

        try {
            db.startTransaction();
            db.getBatchesDAO().add(batch);
            db.endTransaction(true);
        }
        catch (DatabaseException e) {
            db.endTransaction(false);
            throw new ModelException(e.getMessage(), e);
        }
    }

    public static List<Batch> getAllBatches() throws ModelException {

        Database db = new Database();
        List<Batch> batches;

        try {
            db.startTransaction();
            batches = db.getBatchesDAO().getAll();
            db.endTransaction(true);
        }
        catch (DatabaseException e) {
            db.endTransaction(false);
            throw new ModelException(e.getMessage(), e);
        }

        return batches;
    }

    /**
     * Update batch.
     *
     * @param batch the batch
     * @throws ModelException the model exception
     */
    public static void updateBatch(Batch batch) throws ModelException {

        Database db = new Database();

        try {
            db.startTransaction();
            db.getBatchesDAO().update(batch);
            db.endTransaction(true);
        }
        catch (DatabaseException e) {
            db.endTransaction(false);
            throw new ModelException(e.getMessage(), e);
        }
    }

    public static void deleteAllBatches() throws ModelException {

        Database db = new Database();

        try {
            db.startTransaction();
            db.getBatchesDAO().deleteAll();
            db.endTransaction(true);
        }
        catch (DatabaseException e) {
            db.endTransaction(false);
            throw new ModelException(e.getMessage(), e);
        }
    }

    /**
     * Gets sample image of the project.
     *
     * @param projectId the project id
     * @throws ModelException the model exception
     */
    public static Batch getSampleImage(int projectId) throws ModelException {
        Database db = new Database();

        try {
            db.startTransaction();
            Batch batch = db.getBatchesDAO().getSampleImage(projectId);
            db.endTransaction(true);
            return batch;
        } catch (DatabaseException e) {
            db.endTransaction(false);
            throw new ModelException(e.getMessage(), e);
        }
    }

    public void addList(List<Batch> batchList) throws ModelException {
        Database db = new Database();

        try {
            db.startTransaction();
            db.getBatchesDAO().addList(batchList);
            db.endTransaction(true);
        } catch (DatabaseException e) {
            db.endTransaction(false);
            throw new ModelException(e.getMessage(), e);
        }
    }

    public Batch getNextAvailableBatch(int projectId) throws ModelException {
        Database db = new Database();

        try {
            db.startTransaction();
            Batch batch = db.getBatchesDAO().getNextAvailableBatch(projectId);
            db.endTransaction(true);
            return batch;
        } catch (DatabaseException e) {
            db.endTransaction(false);
            throw new ModelException(e.getMessage(), e);
        }
    }

    public Batch getBatchByBatchId(int batchId) throws ModelException {
        Database db = new Database();

        try {
            db.startTransaction();
            Batch batch = db.getBatchesDAO().getBatchByBatchId(batchId);
            db.endTransaction(true);
            return batch;
        } catch (DatabaseException e) {
            db.endTransaction(false);
            throw new ModelException(e.getMessage(), e);
        }
    }
}