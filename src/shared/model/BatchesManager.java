package shared.model;

import server.database.Database;
import server.database.DatabaseException;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Steven
 * Date: 2/11/14
 * Time: 3:52 PM
 * To change this template use File | Settings | File Templates.
 */
public class BatchesManager {
    
    public static void initialize() throws ModelException {
        try {
            Database.initialize();
        }
        catch (DatabaseException e) {
            throw new ModelException(e.getMessage(), e);
        }
    }

    public static List<Batch> getAllBatches() throws ModelException {

        Database db = new Database();

        try {
            db.startTransaction();
            List<Batch> batches = db.getBatchesDAO().getAll();
            db.endTransaction(true);
            return batches;
        }
        catch (DatabaseException e) {
            db.endTransaction(false);
            throw new ModelException(e.getMessage(), e);
        }
    }

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

    public static void deleteBatch(Batch batch) throws ModelException {

        Database db = new Database();

        try {
            db.startTransaction();
            db.getBatchesDAO().delete(batch);
            db.endTransaction(true);
        }
        catch (DatabaseException e) {
            db.endTransaction(false);
            throw new ModelException(e.getMessage(), e);
        }
    }
}