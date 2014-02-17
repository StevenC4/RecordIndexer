package server.database;

import shared.model.Value;

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
public class ValuesDAO {

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
     * Instantiates a new ValuesDAO.
     * @param db
     */
    ValuesDAO(Database db) {
        this.db = db;
    }

    /**
     * Gets all the values from the database.
     *
     * @return all the values from the database
     * @throws DatabaseException the database exception
     */
    public List<Value> getAll() throws DatabaseException {

        logger.entering("server.database.ValuesDAO", "getAll");

        String query = "SELECT * FROM entered_values";
        List<Value> values;

        try {
            PreparedStatement statement = db.getConnection().prepareStatement(query);
            ResultSet rs = statement.executeQuery();

            values = new ArrayList<Value>();
            while (rs.next()) {
                Value value = new Value();
                value.setValueId(rs.getInt("value_id"));
                value.setProjectId(rs.getInt("project_id"));
                value.setFieldId(rs.getInt("field_id"));
                value.setBatchId(rs.getInt("batch_id"));
                value.setValue(rs.getString("value"));
                values.add(value);
            }
        } catch (Exception e) {
            throw new DatabaseException(e.getMessage(), e);
        }

        logger.exiting("server.database.ValuesDAO", "getAll");

        return values;
    }

    /**
     * Add the value to the database.
     *
     * @param value the value
     * @throws DatabaseException the database exception
     */
    public void add(Value value) throws DatabaseException {

        logger.entering("server.database.ValuesDAO", "add");

        String query = "INSERT INTO entered_values" +
                "(project_id, field_id, batch_id, value) VALUES" +
                "(?,?,?,?)";

        try {
            PreparedStatement statement = db.getConnection().prepareStatement(query);

            statement.setInt(1, value.getProjectId());
            statement.setInt(2, value.getFieldId());
            statement.setInt(3, value.getBatchId());
            statement.setString(4, value.getValue());

            statement.executeUpdate();
        } catch (Exception e) {
            throw new DatabaseException(e.getMessage(), e);
        }

        logger.exiting("server.database.ValuesDAO", "add");
    }

    /**
     * Update the value in the database.
     *
     * @param value the value
     * @throws DatabaseException the database exception
     */
    public void update(Value value) throws DatabaseException {

        logger.entering("server.database.ValuesDAO", "update");

        String query = "UPDATE entered_values SET " +
                "project_id = ?, " +
                "field_id = ?, " +
                "batch_id = ?, " +
                "value = ? " +
                "WHERE value_id = ?" +
                "(?,?,?,?,?)";

        try {
            PreparedStatement statement = db.getConnection().prepareStatement(query);

            statement.setInt(1, value.getProjectId());
            statement.setInt(2, value.getFieldId());
            statement.setInt(3, value.getBatchId());
            statement.setString(4, value.getValue());
            statement.setString(5, value.getValue());

            statement.executeUpdate();
        } catch (Exception e) {
            throw new DatabaseException(e.getMessage(), e);
        }

        logger.exiting("server.database.ValuesDAO", "update");
    }

    /**
     * Delete the value from the database.
     *
     * @param value the value
     * @throws DatabaseException the database exception
     */
    public void delete(Value value) throws DatabaseException {

        logger.entering("server.database.ValuesDAO", "delete");

        String query = "DELETE entered_values WHERE value_id = ?";

        try {
            PreparedStatement statement = db.getConnection().prepareStatement(query);

            statement.setInt(1, value.getValueId());

            statement.executeUpdate();
        } catch (Exception e) {
            throw new DatabaseException(e.getMessage(), e);
        }

        logger.exiting("server.database.ValuesDAO", "delete");
    }
    
}
