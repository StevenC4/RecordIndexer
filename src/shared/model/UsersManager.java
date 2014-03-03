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
     * Gets all users.
     *
     * @return the all users
     * @throws ModelException the model exception
     */
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

    /**
     * Add user.
     *
     * @param user the user
     * @throws ModelException the model exception
     */
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

    /**
     * Update user.
     *
     * @param user the user
     * @throws ModelException the model exception
     */
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

    /**
     * Delete user.
     *
     * @param user the user
     * @throws ModelException the model exception
     */
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

    /**
     * Validate user.
     *
     * @param username the username
     * @param password the password
     * @throws ModelException the model exception
     */
    public static boolean validateUser(String username, String password) throws ModelException {
        Database db = new Database();

        boolean validated;

        try {
            db.startTransaction();
            validated = db.getUsersDAO().validateUser(username, password);
            db.endTransaction(true);
        } catch (DatabaseException e) {
            db.endTransaction(false);
            throw new ModelException(e.getMessage(), e);
        }

        return validated;
    }

    public static void deleteAllUsers() throws ModelException {

        Database db = new Database();

        try {
            db.startTransaction();
            db.getUsersDAO().deleteAll();
            db.endTransaction(true);
        }
        catch (DatabaseException e) {
            db.endTransaction(false);
            throw new ModelException(e.getMessage(), e);
        }
    }

    public void addList(List<User> userList) throws ModelException {
        Database db = new Database();

        try {
            db.startTransaction();
            db.getUsersDAO().addList(userList);
            db.endTransaction(true);
        }
        catch (DatabaseException e) {
            db.endTransaction(false);
            throw new ModelException(e.getMessage(), e);
        }
    }

    public int getCurrentBatch(User user) throws ModelException {
        Database db = new Database();

        try {
            db.startTransaction();
            int currentBatch = db.getUsersDAO().getCurrentBatch(user);
            db.endTransaction(true);
            return currentBatch;
        }
        catch (DatabaseException e) {
            db.endTransaction(false);
            throw new ModelException(e.getMessage(), e);
        }
    }
}
