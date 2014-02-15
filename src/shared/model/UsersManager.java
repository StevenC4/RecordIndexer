package shared.model;

import server.database.Database;
import server.database.DatabaseException;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Steven
 * Date: 2/11/14
 * Time: 3:31 PM
 * To change this template use File | Settings | File Templates.
 */
public class UsersManager {

    public static void initialize() throws ModelException {
        try {
            Database.initialize();
        }
        catch (DatabaseException e) {
            throw new ModelException(e.getMessage(), e);
        }
    }

    public static List<User> getAllUsers() throws ModelException {

        Database db = new Database();

        try {
            db.startTransaction();
            List<User> users = db.getUsersDAO().getAll();
            db.endTransaction(true);
            return users;
        }
        catch (DatabaseException e) {
            db.endTransaction(false);
            throw new ModelException(e.getMessage(), e);
        }
    }

    public static void addUser(User user) throws ModelException {

        Database db = new Database();

        try {
            db.startTransaction();
            db.getUsersDAO().add(user);
            db.endTransaction(true);
        }
        catch (DatabaseException e) {
            db.endTransaction(false);
            throw new ModelException(e.getMessage(), e);
        }
    }

    public static void updateUser(User user) throws ModelException {

        Database db = new Database();

        try {
            db.startTransaction();
            db.getUsersDAO().update(user);
            db.endTransaction(true);
        }
        catch (DatabaseException e) {
            db.endTransaction(false);
            throw new ModelException(e.getMessage(), e);
        }
    }

    public static void deleteUser(User user) throws ModelException {

        Database db = new Database();

        try {
            db.startTransaction();
            db.getUsersDAO().delete(user);
            db.endTransaction(true);
        }
        catch (DatabaseException e) {
            db.endTransaction(false);
            throw new ModelException(e.getMessage(), e);
        }
    }
}
