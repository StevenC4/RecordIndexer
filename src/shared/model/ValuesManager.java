package shared.model;

import server.database.Database;
import server.database.DatabaseException;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Steven
 * Date: 2/11/14
 * Time: 3:59 PM
 * To change this template use File | Settings | File Templates.
 */
public class ValuesManager {

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

    public static void deleteAllValues() throws ModelException {

        Database db = new Database();

        try {
            db.startTransaction();
            db.getValuesDAO().deleteAll();
            db.endTransaction(true);
        }
        catch (DatabaseException e) {
            db.endTransaction(false);
            throw new ModelException(e.getMessage(), e);
        }
    }

    public void addList(List<Value> valueList) throws ModelException {

        Database db = new Database();

        try {
            db.startTransaction();
            db.getValuesDAO().addList(valueList);
            db.endTransaction(true);
        }
        catch (DatabaseException e) {
            db.endTransaction(false);
            throw new ModelException(e.getMessage(), e);
        }
    }

    public int getNextRecordId() throws ModelException {
        Database db = new Database();

        try {
            db.startTransaction();
            int recordId = db.getValuesDAO().getNextRecordId();
            db.endTransaction(true);
            return recordId;
        }
        catch (DatabaseException e) {
            db.endTransaction(false);
            throw new ModelException(e.getMessage(), e);
        }
    }

    public List<Value> searchValues(String fields, String searchValues) throws ModelException {
        Database db = new Database();

        try {
            db.startTransaction();
            List<Value> values = db.getValuesDAO().searchValues(fields, searchValues);
            db.endTransaction(true);
            return values;
        }
        catch (DatabaseException e) {
            db.endTransaction(false);
            throw new ModelException(e.getMessage(), e);
        }
    }
}
