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
                value.setRecordId(rs.getInt("record_id"));
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
                "(project_id, field_id, batch_id, record_id, value) VALUES" +
                "(?,?,?,?,?)";

        try {
            PreparedStatement statement = db.getConnection().prepareStatement(query);

            statement.setInt(1, value.getProjectId());
            statement.setInt(2, value.getFieldId());
            statement.setInt(3, value.getBatchId());
            statement.setInt(4, value.getRecordId());
            statement.setString(5, value.getValue());

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
                "record_id = ?, " +
                "value = ? " +
                "WHERE value_id = ?" +
                "(?,?,?,?,?,?)";

        try {
            PreparedStatement statement = db.getConnection().prepareStatement(query);

            statement.setInt(1, value.getProjectId());
            statement.setInt(2, value.getFieldId());
            statement.setInt(3, value.getBatchId());
            statement.setInt(4, value.getRecordId());
            statement.setString(5, value.getValue());
            statement.setString(6, value.getValue());

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

    public void deleteAll() throws DatabaseException {

        logger.entering("server.database.ValuesDAO", "deleteAll");

        String query = "DELETE FROM entered_values";
        String resetIncrement = "UPDATE sqlite_sequence SET seq=? WHERE name=?";

        try {
            PreparedStatement statement = db.getConnection().prepareStatement(query);
            statement.executeUpdate();
            statement = db.getConnection().prepareStatement(resetIncrement);
            statement.setInt(1, 0);
            statement.setString(2, "entered_values");
            statement.executeUpdate();
        } catch (Exception e) {
            throw new DatabaseException(e.getMessage(), e);
        }

        logger.exiting("server.database.ValuesDAO", "deleteAll");
    }

    /**
     * Search the database for values corresponding to certain fields.
     *
     * @param fields the fields to search for the values
     * @param searchValues the search values corresponding to each field
     * @return the list of values that match the search criteria
     */
    public List<Value> search(String fields, String searchValues) {
        return null;
    }

    public void addList(List<Value> valueList) throws DatabaseException {

        logger.entering("server.database.ValuesDAO", "addList");

        try {
            for (int i = 0; i < valueList.size(); i++) {
                String query = "INSERT INTO entered_values" +
                        "(project_id, field_id, batch_id, record_id, value) VALUES" +
                        "(?,?,?,?,?)";
                PreparedStatement statement = db.getConnection().prepareStatement(query);

                statement.setInt(1, valueList.get(i).getProjectId());
                statement.setInt(2, valueList.get(i).getFieldId());
                statement.setInt(3, valueList.get(i).getBatchId());
                statement.setInt(4, valueList.get(i).getRecordId());
                statement.setString(5, valueList.get(i).getValue());

                statement.executeUpdate();
            }
        } catch (Exception e) {
            throw new DatabaseException(e.getMessage(), e);
        }

        logger.exiting("server.database.ValuesDAO", "addList");
    }

    public int getNextRecordId() throws DatabaseException {
        logger.entering("server.database.ValuesDAO", "getNextRecordId");

        int recordId;

        try {
            String query = "SELECT MAX(record_id) FROM entered_values";
            PreparedStatement statement = db.getConnection().prepareStatement(query);

            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                recordId = rs.getInt("record_id") + 1;
            } else {
                recordId = 1;
            }
        } catch (Exception e) {
            throw new DatabaseException(e.getMessage(), e);
        }

        logger.exiting("server.database.ValuesDAO", "getNextRecordId");

        return recordId;
    }

    public List<Value> searchValues(String fields, String searchValues) throws DatabaseException {
        logger.entering("server.database.ValuesDAO", "searchValues");

        List<Value> values;

        try {
            values = new ArrayList<Value>();

            String[] fieldsArray = fields.toLowerCase().split(",");
            String[] searchValuesArray = searchValues.toLowerCase().split(",");

            String query = "SELECT * FROM entered_values WHERE ";

            for (int i = 0; i < fieldsArray.length; i++) {
                query += "field_id=? AND LOWER(value)=? ";
                if (i < fieldsArray.length - 1) {
                    query += "OR ";
                }
            }
            PreparedStatement statement = db.getConnection().prepareStatement(query);

            for (int i = 0; i < fieldsArray.length; i++) {
                statement.setInt(2 * i, Integer.parseInt(fieldsArray[i]));
                statement.setString((2 * i) + 1, searchValuesArray[i]);
            }

            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                values.add(new Value(rs.getInt("value_id"), rs.getInt("project_id"), rs.getInt("field_id"), rs.getInt("record_id"), rs.getInt("batch_id"), rs.getString("value")));
            } else {
                throw new Exception("The search did not turn up any results");
            }
        } catch (Exception e) {
            throw new DatabaseException(e.getMessage(), e);
        }

        logger.exiting("server.database.ValuesDAO", "searchValues");

        return values;
    }
}
