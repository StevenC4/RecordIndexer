package shared.model;

import server.database.Database;
import server.database.DatabaseException;

import java.util.ArrayList;
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

    /*
     * For testing purposes
     */

    public void addField(Field field) throws ModelException {

        Database db = new Database();

        try {
            db.startTransaction();
            db.getFieldsDAO().add(field);
            db.endTransaction(true);
        }
        catch (DatabaseException e) {
            db.endTransaction(false);
            throw new ModelException(e.getMessage(), e);
        }
    }

    public List<Field> getAllFields() throws ModelException {
        Database db = new Database();
        List<Field> fields;

        try {
            db.startTransaction();
            fields = db.getFieldsDAO().getAll();
            db.endTransaction(true);
        }
        catch (DatabaseException e) {
            db.endTransaction(false);
            throw new ModelException(e.getMessage(), e);
        }

        return fields;
    }

    /*
     * End testing
     */

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
