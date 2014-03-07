package shared.model;

import server.database.Database;
import server.database.DatabaseException;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Steven
 * Date: 2/11/14
 * Time: 3:48 PM
 * To change this template use File | Settings | File Templates.
 */
public class FieldsManager {

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

    public static void deleteAllFields() throws ModelException {

        Database db = new Database();

        try {
            db.startTransaction();
            db.getFieldsDAO().deleteAll();
            db.endTransaction(true);
        }
        catch (DatabaseException e) {
            db.endTransaction(false);
            throw new ModelException(e.getMessage(), e);
        }
    }

    public void addList(List<Field> fieldList) throws ModelException {

        Database db = new Database();

        try {
            db.startTransaction();
            db.getFieldsDAO().addList(fieldList);
            db.endTransaction(true);
        }
        catch (DatabaseException e) {
            db.endTransaction(false);
            throw new ModelException(e.getMessage(), e);
        }
    }

    public List<Field> getProjectFields(int projectId) throws ModelException {
        Database db = new Database();

        try {
            db.startTransaction();
            List<Field> fields = db.getFieldsDAO().getProjectFields(projectId);
            db.endTransaction(true);
            return fields;
        }
        catch (DatabaseException e) {
            db.endTransaction(false);
            throw new ModelException(e.getMessage(), e);
        }
    }
}
