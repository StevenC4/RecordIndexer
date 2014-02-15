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

    public static void initialize() throws ModelException {
        try {
            Database.initialize();
        }
        catch (DatabaseException e) {
            throw new ModelException(e.getMessage(), e);
        }
    }

    public static List<Value> getAllValues() throws ModelException {

        Database db = new Database();

        try {
            db.startTransaction();
            List<Value> values = db.getValuesDAO().getAll();
            db.endTransaction(true);
            return values;
        }
        catch (DatabaseException e) {
            db.endTransaction(false);
            throw new ModelException(e.getMessage(), e);
        }
    }

    public static void addValue(Value value) throws ModelException {

        Database db = new Database();

        try {
            db.startTransaction();
            db.getValuesDAO().add(value);
            db.endTransaction(true);
        }
        catch (DatabaseException e) {
            db.endTransaction(false);
            throw new ModelException(e.getMessage(), e);
        }
    }

    public static void updateValue(Value value) throws ModelException {

        Database db = new Database();

        try {
            db.startTransaction();
            db.getValuesDAO().update(value);
            db.endTransaction(true);
        }
        catch (DatabaseException e) {
            db.endTransaction(false);
            throw new ModelException(e.getMessage(), e);
        }
    }

    public static void deleteValue(Value value) throws ModelException {

        Database db = new Database();

        try {
            db.startTransaction();
            db.getValuesDAO().delete(value);
            db.endTransaction(true);
        }
        catch (DatabaseException e) {
            db.endTransaction(false);
            throw new ModelException(e.getMessage(), e);
        }
    }
}
