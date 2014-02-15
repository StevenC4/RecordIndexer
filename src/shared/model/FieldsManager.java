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

    public static void initialize() throws ModelException {
        try {
            Database.initialize();
        }
        catch (DatabaseException e) {
            throw new ModelException(e.getMessage(), e);
        }
    }

    public static List<Field> getAllFields() throws ModelException {

        Database db = new Database();

        try {
            db.startTransaction();
            List<Field> fields = db.getFieldsDAO().getAll();
            db.endTransaction(true);
            return fields;
        }
        catch (DatabaseException e) {
            db.endTransaction(false);
            throw new ModelException(e.getMessage(), e);
        }
    }

    public static void addField(Field field) throws ModelException {

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

    public static void updateField(Field field) throws ModelException {

        Database db = new Database();

        try {
            db.startTransaction();
            db.getFieldsDAO().update(field);
            db.endTransaction(true);
        }
        catch (DatabaseException e) {
            db.endTransaction(false);
            throw new ModelException(e.getMessage(), e);
        }
    }

    public static void deleteField(Field field) throws ModelException {

        Database db = new Database();

        try {
            db.startTransaction();
            db.getFieldsDAO().delete(field);
            db.endTransaction(true);
        }
        catch (DatabaseException e) {
            db.endTransaction(false);
            throw new ModelException(e.getMessage(), e);
        }
    }
}
