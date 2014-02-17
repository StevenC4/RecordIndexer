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

    /**
     * Gets all fields from the database.
     *
     * @return all the fields
     * @throws DatabaseException the database exception
     */
    public List<Field> getAll() throws DatabaseException {

        logger.entering("server.database.FieldsDAO", "getAll");

        String query = "SELECT * FROM fields";
        List<Field> fields;

        try {
            PreparedStatement statement = db.getConnection().prepareStatement(query);
            ResultSet rs = statement.executeQuery();

            fields = new ArrayList<Field>();
            while (rs.next()) {
                Field field = new Field();
                field.setFieldId(rs.getInt("field_id"));
                field.setPosition(rs.getInt("position"));
                field.setProjectId(rs.getInt("project_id"));
                field.setxCoord(rs.getInt("x_coord"));
                field.setWidth(rs.getInt("width"));
                field.setHelpHTML(rs.getString("help_html"));
                field.setKnownData(rs.getString("known_data"));
                fields.add(field);
            }
        } catch (Exception e) {
            throw new DatabaseException(e.getMessage(), e);
        }

        logger.exiting("server.database.FieldsDAO", "getAll");

        return fields;
    }

    /**
     * Add the field to the database.
     *
     * @param field the field
     * @throws DatabaseException the database exception
     */
    public void add(Field field) throws DatabaseException {

        logger.entering("server.database.FieldsDAO", "add");

        String query = "INSERT INTO fields" +
                "(position, project_id, x_coord, width, help_html, known_data) VALUES" +
                "(?,?,?,?,?,?)";

        try {
            PreparedStatement statement = db.getConnection().prepareStatement(query);

            statement.setInt(1, field.getPosition());
            statement.setInt(2, field.getProjectId());
            statement.setInt(3, field.getxCoord());
            statement.setInt(4, field.getWidth());
            statement.setString(5, field.getHelpHTML());
            statement.setString(6, field.getKnownData());

            statement.executeUpdate();
        } catch (Exception e) {
            throw new DatabaseException(e.getMessage(), e);
        }

        logger.exiting("server.database.FieldsDAO", "add");
    }

    /**
     * Update the field in the database.
     *
     * @param field the field
     * @throws DatabaseException the database exception
     */
    public void update(Field field) throws DatabaseException {

        logger.entering("server.database.FieldsDAO", "update");

        String query = "UPDATE fields SET " +
                "position = ?, " +
                "project_id = ?, " +
                "x_coord = ?, " +
                "width = ? " +
                "help_html = ? " +
                "known_data = ? " +
                "WHERE field_id = ?" +
                "(?,?,?,?,?)";

        try {
            PreparedStatement statement = db.getConnection().prepareStatement(query);

            statement.setInt(1, field.getPosition());
            statement.setInt(2, field.getProjectId());
            statement.setInt(3, field.getxCoord());
            statement.setInt(4, field.getWidth());
            statement.setString(5, field.getHelpHTML());
            statement.setString(6, field.getKnownData());
            statement.setInt(7, field.getFieldId());

            statement.executeUpdate();
        } catch (Exception e) {
            throw new DatabaseException(e.getMessage(), e);
        }

        logger.exiting("server.database.FieldsDAO", "update");
    }

    /**
     * Delete the field from the database.
     *
     * @param field the field
     * @throws DatabaseException the database exception
     */
    public void delete(Field field) throws DatabaseException {

        logger.entering("server.database.FieldsDAO", "delete");

        String query = "DELETE fields WHERE field_id = ?";

        try {
            PreparedStatement statement = db.getConnection().prepareStatement(query);

            statement.setInt(1, field.getFieldId());

            statement.executeUpdate();
        } catch (Exception e) {
            throw new DatabaseException(e.getMessage(), e);
        }

        logger.exiting("server.database.FieldsDAO", "delete");
    }
    
}