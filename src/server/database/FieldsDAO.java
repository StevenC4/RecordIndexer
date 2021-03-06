package server.database;

import shared.model.Field;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

/**
 * Created with IntelliJ IDEA.
 * User: Steven
 * Date: 2/12/14
 * Time: 10:27 AM
 * To change this template use File | Settings | File Templates.
 */
public class FieldsDAO {

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
     * Instantiates a new FieldsDAO.
     *
     * @param db the database object
     */
    FieldsDAO (Database db) {
        this.db = db;
    }

    /*
     * For testing
     */

    public void add(Field field) throws DatabaseException {

        logger.entering("server.database.FieldsDAO", "add");

        String query = "INSERT INTO fields" +
                "(field_id, title, position, project_id, x_coord, width, help_html, known_data) VALUES" +
                "(?,?,?,?,?,?,?,?)";

        try {
            PreparedStatement statement = db.getConnection().prepareStatement(query);
            statement.setInt(1, field.getFieldId());
            statement.setString(2, field.getTitle());
            statement.setInt(3, field.getPosition());
            statement.setInt(4, field.getProjectId());
            statement.setInt(5, field.getxCoord());
            statement.setInt(6, field.getWidth());
            statement.setString(7, field.getHelpHTML());
            statement.setString(8, field.getKnownData());
            statement.executeUpdate();
        } catch (Exception e) {
            throw new DatabaseException(e.getMessage(), e);
        }

        logger.exiting("server.database.FieldsDAO", "add");
    }

    public List<Field> getAll() throws DatabaseException {

        logger.entering("server.database.FieldsDAO", "getAll");

        List<Field> fields = new ArrayList<Field>();
        String query = "SELECT * FROM fields";

        try {
            PreparedStatement statement = db.getConnection().prepareStatement(query);

            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                fields.add(new Field(rs.getInt("field_id"), rs.getString("title"), rs.getInt("position"),
                                     rs.getInt("project_id"), rs.getInt("x_coord"), rs.getInt("width"),
                                     rs.getString("help_html"), rs.getString("known_data")));
            }
        } catch (Exception e) {
            throw new DatabaseException(e.getMessage(), e);
        }

        logger.exiting("server.database.FieldsDAO", "getAll");

        return fields;
    }

    /*
     * End tesing
     */

    public void deleteAll() throws DatabaseException {

        logger.entering("server.database.FieldsDAO", "deleteAll");

        String query = "DELETE FROM fields";
        String resetIncrement = "UPDATE sqlite_sequence SET seq=? WHERE name=?";

        try {
            PreparedStatement statement = db.getConnection().prepareStatement(query);
            statement.executeUpdate();
            statement = db.getConnection().prepareStatement(resetIncrement);
            statement.setInt(1, 0);
            statement.setString(2, "fields");
            statement.executeUpdate();
        } catch (Exception e) {
            throw new DatabaseException(e.getMessage(), e);
        }

        logger.exiting("server.database.FieldsDAO", "deleteAll");
    }

    public void addList(List<Field> fieldList) throws DatabaseException{

        logger.entering("server.database.FieldsDAO", "addList");

        try {
            for (int i = 0; i < fieldList.size(); i++) {
                String query = "INSERT INTO fields" +
                        "(project_id, title, position, x_coord, width, help_html, known_data) VALUES" +
                        "(?,?,?,?,?,?,?)";
                PreparedStatement statement = db.getConnection().prepareStatement(query);

                statement.setInt(1, fieldList.get(i).getProjectId());
                statement.setString(2, fieldList.get(i).getTitle());
                statement.setInt(3, fieldList.get(i).getPosition());
                statement.setInt(4, fieldList.get(i).getxCoord());
                statement.setInt(5, fieldList.get(i).getWidth());
                statement.setString(6, fieldList.get(i).getHelpHTML());
                statement.setString(7, fieldList.get(i).getKnownData());

                statement.executeUpdate();
            }
        } catch (Exception e) {
            throw new DatabaseException(e.getMessage(), e);
        }

        logger.exiting("server.database.FieldsDAO", "addList");
    }

    public List<Field> getProjectFields(int projectId) throws DatabaseException {
        logger.entering("server.database.FieldsDAO", "getProjectFields");

        List<Field> fields;

        try {
            String query = "SELECT * FROM fields WHERE project_id=?";
            PreparedStatement statement = db.getConnection().prepareStatement(query);
            statement.setInt(1, projectId);

            ResultSet rs = statement.executeQuery();
            fields = new ArrayList<Field>();
            while (rs.next()) {
                fields.add(new Field(rs.getInt("field_id"), rs.getString("title"), rs.getInt("position"), rs.getInt("project_id"),
                        rs.getInt("x_coord"), rs.getInt("width"), rs.getString("help_html"), rs.getString("known_data")));
            }
        } catch (Exception e) {
            throw new DatabaseException(e.getMessage(), e);
        }

        logger.exiting("server.database.FieldsDAO", "getProjectFields");

        return fields;
    }
}