package server.database;

import shared.model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
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

        String query = "SELECT * FROM users";
        List<User> users;

        try {
            PreparedStatement statement = db.getConnection().prepareStatement(query);
            ResultSet rs = statement.executeQuery();

            users = new ArrayList<User>();
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
    
}
