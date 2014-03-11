package server.database;

import shared.model.Value;
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

    /*
     * For testing
     */

    public void add(Value value) throws DatabaseException {

        logger.entering("server.database.ValuesDAO", "add");

        String query = "INSERT INTO entered_values" +
                "(value_id, project_id, field_id, batch_id, record_id, record_num, value) VALUES" +
                "(?,?,?,?,?,?,?)";

        try {
            PreparedStatement statement = db.getConnection().prepareStatement(query);

            statement.setInt(1, value.getValueId());
            statement.setInt(2, value.getProjectId());
            statement.setInt(3, value.getFieldId());
            statement.setInt(4, value.getBatchId());
            statement.setInt(5, value.getRecordId());
            statement.setInt(6, value.getRecordNum());
            statement.setString(7, value.getValue());

            statement.executeUpdate();
        } catch (Exception e) {
            throw new DatabaseException(e.getMessage(), e);
        }

        logger.exiting("server.database.ValuesDAO", "add");
    }

    public List<Value> getAll() throws DatabaseException {

        logger.entering("server.database.ValuesDAO", "addList");

        List<Value> values = new ArrayList<Value>();
        String query = "SELECT * FROM entered_values";

        try {
            PreparedStatement statement = db.getConnection().prepareStatement(query);

            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                values.add(new Value(rs.getInt("value_id"), rs.getInt("project_id"), rs.getInt("field_id"),
                                     rs.getInt("batch_id"), rs.getInt("record_id"), rs.getInt("record_num"),
                                     rs.getString("value")));
            }
        } catch (Exception e) {
            throw new DatabaseException(e.getMessage(), e);
        }

        logger.exiting("server.database.ValuesDAO", "addList");

        return values;
    }

    /*
     * End testing
     */

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

    public void addList(List<Value> valueList) throws DatabaseException {

        logger.entering("server.database.ValuesDAO", "addList");

        try {
            for (int i = 0; i < valueList.size(); i++) {
                String query = "INSERT INTO entered_values" +
                        "(project_id, field_id, batch_id, record_id, record_num, value) VALUES" +
                        "(?,?,?,?,?,?)";
                PreparedStatement statement = db.getConnection().prepareStatement(query);

                statement.setInt(1, valueList.get(i).getProjectId());
                statement.setInt(2, valueList.get(i).getFieldId());
                statement.setInt(3, valueList.get(i).getBatchId());
                statement.setInt(4, valueList.get(i).getRecordId());
                statement.setInt(5, valueList.get(i).getRecordNum());
                statement.setString(6, valueList.get(i).getValue());

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
                recordId = rs.getInt("MAX(record_id)") + 1;
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
                for (int j = 0; j < searchValuesArray.length; j++) {
                    query += "(field_id=? AND LOWER(value)=?) ";
                    if (i < fieldsArray.length - 1 || j < searchValuesArray.length - 1) {
                        query += "OR ";
                    }
                }
            }
            PreparedStatement statement = db.getConnection().prepareStatement(query);

            int count = 1;
            for (int i = 0; i < fieldsArray.length; i++) {
                for (int j = 0; j < searchValuesArray.length; j++) {
                    statement.setInt(count++, Integer.parseInt(fieldsArray[i]));
                    statement.setString(count++, searchValuesArray[j]);
                }
            }

            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                values.add(new Value(rs.getInt("value_id"), rs.getInt("project_id"), rs.getInt("field_id"), rs.getInt("batch_id"), rs.getInt("record_id"), rs.getInt("record_num"), rs.getString("value")));
                while (rs.next()) {
                    values.add(new Value(rs.getInt("value_id"), rs.getInt("project_id"), rs.getInt("field_id"), rs.getInt("batch_id"), rs.getInt("record_id"), rs.getInt("record_num"), rs.getString("value")));
                }
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
