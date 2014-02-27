package server.database;

import shared.model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

/**
 * Created with IntelliJ IDEA.
 * User: Steven
 * Date: 2/12/14
 * Time: 10:28 AM
 * To change this template use File | Settings | File Templates.
 */
public class UsersDAO {

    /**
     * The logger
     */
    private static Logger logger;

    static {
        logger = Logger.getLogger("recordindexer");
    }

    /**
     * The database object
     */
    private Database db;

    /**
     * Instantiates a new UsersDAO.
     *
     * @param db the database
     */
    UsersDAO(Database db) {
        this.db = db;
    }

    /**
     * Gets all the users from the database.
     *
     * @return all the users from the database
     * @throws DatabaseException the database exception
     */
    public List<User> getAll() throws DatabaseException {

        logger.entering("server.database.UsersDAO", "getAll");

        List<User> users = new ArrayList<User>();
        PreparedStatement statement = null;
        ResultSet rs = null;

        try {
            String query = "SELECT * FROM users";
            statement = db.getConnection().prepareStatement(query);

            rs = statement.executeQuery();
            while (rs.next()) {
                User user = new User();
                user.setUserId(rs.getInt("user_id"));
                user.setUsername(rs.getString("username"));
                user.setPassword(rs.getString("password"));
                user.setFirstName(rs.getString("first_name"));
                user.setLastName(rs.getString("last_name"));
                user.setEmail(rs.getString("email"));
                user.setIndexedRecords(rs.getInt("indexed_records"));
                users.add(user);
            }
        } catch (Exception e) {
            throw new DatabaseException(e.getMessage(), e);
        } finally {
            Database.safeClose(rs);
            Database.safeClose(statement);
        }

        logger.exiting("server.database.UsersDAO", "getAll");

        return users;
    }

    /**
     * Add the user to the database.
     *
     * @param user the user
     * @throws DatabaseException the database exception
     */
    public void add(User user) throws DatabaseException {

        logger.entering("server.database.UsersDAO", "add");

        String query = "INSERT INTO users" +
                "(username, password, first_name, last_name, email, indexed_records) VALUES" +
                "(?,?,?,?,?,?)";

        try {
            PreparedStatement statement = db.getConnection().prepareStatement(query);

            statement.setString(1, user.getUsername());
            statement.setString(2, user.getPassword());
            statement.setString(3, user.getFirstName());
            statement.setString(4, user.getLastName());
            statement.setString(5, user.getEmail());
            statement.setInt(6, user.getIndexedRecords());

            statement.executeUpdate();
        } catch (Exception e) {
            throw new DatabaseException(e.getMessage(), e);
        }

        logger.exiting("server.database.UsersDAO", "add");
    }

    /**
     * Update the user in the database.
     *
     * @param user the user
     * @throws DatabaseException the database exception
     */
    public void update(User user) throws DatabaseException {

        logger.entering("server.database.UsersDAO", "update");

        String query = "UPDATE users SET " +
                "username = ?, " +
                "password = ?, " +
                "first_name = ?, " +
                "last_name = ?, " +
                "email = ?, " +
                "indexed_records = ?, " +
                "WHERE user_id = ?" +
                "(?,?,?,?,?,?,?)";

        try {
            PreparedStatement statement = db.getConnection().prepareStatement(query);

            statement.setString(1, user.getUsername());
            statement.setString(2, user.getPassword());
            statement.setString(3, user.getFirstName());
            statement.setString(4, user.getLastName());
            statement.setString(5, user.getEmail());
            statement.setInt(6, user.getIndexedRecords());
            statement.setInt(7, user.getUserId());

            statement.executeUpdate();
        } catch (Exception e) {
            throw new DatabaseException(e.getMessage(), e);
        }

        logger.exiting("server.database.UsersDAO", "update");
    }

    /**
     * Delete the user from the database.
     *
     * @param user the user
     * @throws DatabaseException the database exception
     */
    public void delete(User user) throws DatabaseException {

        logger.entering("server.database.UsersDAO", "delete");

        String query = "DELETE users WHERE user_id = ?";

        try {
            PreparedStatement statement = db.getConnection().prepareStatement(query);

            statement.setInt(1, user.getUserId());

            statement.executeUpdate();
        } catch (Exception e) {
            throw new DatabaseException(e.getMessage(), e);
        }

        logger.exiting("server.database.UsersDAO", "delete");
    }

    public void deleteAll() throws DatabaseException {

        logger.entering("server.database.UsersDAO", "deleteAll");

        String query = "DELETE FROM users";
        String resetIncrement = "UPDATE sqlite_sequence SET seq=? WHERE name=?";

        try {
            PreparedStatement statement = db.getConnection().prepareStatement(query);
            statement.executeUpdate();
            statement = db.getConnection().prepareStatement(resetIncrement);
            statement.setInt(1, 0);
            statement.setString(2, "users");
            statement.executeUpdate();
        } catch (Exception e) {
            throw new DatabaseException(e.getMessage(), e);
        }

        logger.exiting("server.database.UsersDAO", "deleteAll");
    }

    /**
     * Validate a user's username-password combination.
     *
     * @param username the username
     * @param password the password
     * @return true if username and password combination found in the database, false otherwise
     * @throws DatabaseException
     */
    public boolean validateUser(String username, String password) throws DatabaseException {

        logger.entering("server.database.UsersDAO", "validate");

        boolean isValid;
        String query = "SELECT password FROM users WHERE username = ?";

        try {
            PreparedStatement statement = db.getConnection().prepareStatement(query);

            statement.setString(1, username);

            ResultSet rs = statement.executeQuery();
            if (rs.next() && rs.getString("password").equals(password)) {
                isValid = true;
            } else {
                isValid = false;
            }

        } catch (Exception e) {
            throw new DatabaseException(e.getMessage(), e);
        }

        logger.exiting("server.database.UsersDAO", "validate");

        return isValid;
    }

    public void addList(List<User> userList) throws DatabaseException {
        logger.entering("server.database.UsersDAO", "addList");

        try {
            for (int i = 0; i < userList.size(); i++) {
                String query = "INSERT INTO users" +
                    "(username, password, first_name, last_name, email, indexed_records) VALUES" +
                    "(?,?,?,?,?,?)";
                PreparedStatement statement = db.getConnection().prepareStatement(query);

                statement.setString(1, userList.get(i).getUsername());
                statement.setString(2, userList.get(i).getPassword());
                statement.setString(3, userList.get(i).getFirstName());
                statement.setString(4, userList.get(i).getLastName());
                statement.setString(5, userList.get(i).getEmail());
                statement.setInt(6, userList.get(i).getIndexedRecords());

                statement.executeUpdate();
            }
        } catch (Exception e) {
            throw new DatabaseException(e.getMessage(), e);
        }

        logger.exiting("server.database.UsersDAO", "addList");
    }
}
