package server.database;

import shared.model.User;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
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
                "current_batch = ? " +
                "WHERE user_id = ?";

        try {
            PreparedStatement statement = db.getConnection().prepareStatement(query);

            statement.setString(1, user.getUsername());
            statement.setString(2, user.getPassword());
            statement.setString(3, user.getFirstName());
            statement.setString(4, user.getLastName());
            statement.setString(5, user.getEmail());
            statement.setInt(6, user.getIndexedRecords());
            statement.setInt(7, user.getCurrentBatch());
            statement.setInt(8, user.getUserId());

            statement.executeUpdate();
        } catch (Exception e) {
            throw new DatabaseException(e.getMessage(), e);
        }

        logger.exiting("server.database.UsersDAO", "update");
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
                    "(username, password, first_name, last_name, email, indexed_records, current_batch) VALUES" +
                    "(?,?,?,?,?,?,?)";
                PreparedStatement statement = db.getConnection().prepareStatement(query);

                statement.setString(1, userList.get(i).getUsername());
                statement.setString(2, userList.get(i).getPassword());
                statement.setString(3, userList.get(i).getFirstName());
                statement.setString(4, userList.get(i).getLastName());
                statement.setString(5, userList.get(i).getEmail());
                statement.setInt(6, userList.get(i).getIndexedRecords());
                statement.setInt(7, userList.get(i).getCurrentBatch());

                statement.executeUpdate();
            }
        } catch (Exception e) {
            throw new DatabaseException(e.getMessage(), e);
        }

        logger.exiting("server.database.UsersDAO", "addList");
    }

    public int getCurrentBatch(User user) throws DatabaseException {
        logger.entering("server.database.UsersDAO", "getCurrentBatch");

        int currentBatch;

        try {
            String query = "SELECT current_batch from users WHERE username=? AND password=?";
            PreparedStatement statement = db.getConnection().prepareStatement(query);

            statement.setString(1, user.getUsername());
            statement.setString(2, user.getPassword());

            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                currentBatch = rs.getInt("current_batch");
            } else {
                throw new Exception("There are not projects associated with the given user");
            }

        } catch (Exception e) {
            throw new DatabaseException(e.getMessage(), e);
        }

        logger.exiting("server.database.UsersDAO", "getCurrentBatch");

        return currentBatch;
    }

    public User getUserByUsername(String username) throws DatabaseException {
        logger.entering("server.database.UsersDAO", "getUserByUsername");

        User user;

        try {
            String query = "SELECT * from users WHERE username=?";
            PreparedStatement statement = db.getConnection().prepareStatement(query);

            statement.setString(1, username);

            statement.executeQuery();

            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                user = new User(rs.getInt("user_id"), rs.getString("username"), rs.getString("password"),
                                rs.getString("first_name"), rs.getString("password"), rs.getString("email"),
                                rs.getInt("indexed_records"), rs.getInt("current_batch"));
            } else {
                throw new Exception("There are no users associated with the given username");
            }

        } catch (Exception e) {
            throw new DatabaseException(e.getMessage(), e);
        }

        logger.exiting("server.database.UsersDAO", "getUserByUsername");

        return user;
    }
}
