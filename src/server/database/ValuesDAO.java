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
