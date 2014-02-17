package server.database;

import java.sql.*;
import java.util.logging.*;

/**
 * Created with IntelliJ IDEA.
 * User: Steven
 * Date: 2/11/14
 * Time: 6:09 PM
 * To change this template use File | Settings | File Templates.
 */
public class Database {

    /**
     * The logger
     */
    private static Logger logger;

    static {
        logger = Logger.getLogger("recordindexer");
    }

    /**
     * Initialize the database by loading the database driver.
     *
     * @throws DatabaseException the database exception
     */
    public static void initialize() throws DatabaseException {
        logger.entering("server.database.Database", "initialize");

        final String driverName = "org.sqlite.JDBC";
        try {
            Class.forName(driverName);
        } catch (Exception e) {
            throw new DatabaseException(e.getMessage());
        }

        logger.exiting("server.database.Database", "initialize");
    }

    private BatchesDAO batchesDAO;
    private FieldsDAO fieldsDAO;
    private ProjectsDAO projectsDAO;
    private UsersDAO usersDAO;
    private ValuesDAO valuesDAO;
    private Connection connection;

    /**
     * Instantiates a new Database.
     */
    public Database() {
        batchesDAO = new BatchesDAO(this);
        fieldsDAO = new FieldsDAO(this);
        projectsDAO = new ProjectsDAO(this);
        usersDAO = new UsersDAO(this);
        valuesDAO = new ValuesDAO(this);

        connection = null;
    }

    /**
     * Gets the batchesDAO.
     *
     * @return the batchesDAO
     */
    public BatchesDAO getBatchesDAO() {
        return batchesDAO;
    }

    /**
     * Gets the fieldsDAO.
     *
     * @return the fieldsDAO
     */
    public FieldsDAO getFieldsDAO() {
        return fieldsDAO;
    }

    /**
     * Gets the projectsDAO.
     *
     * @return the projectsDAO
     */
    public ProjectsDAO getProjectsDAO() {
        return projectsDAO;
    }

    /**
     * Gets the usersDAO.
     *
     * @return the usersDAO
     */
    public UsersDAO getUsersDAO() {
        return usersDAO;
    }

    /**
     * Gets valuesDAO.
     *
     * @return the valuesDAO
     */
    public ValuesDAO getValuesDAO() {
        return valuesDAO;
    }

    /**
     * Gets the connection.
     *
     * @return the connection
     */
    public Connection getConnection() {
        return connection;
    }

    /**
     * Start a database transaction.
     *
     * @throws DatabaseException the database exception
     */
    public void startTransaction() throws DatabaseException {
        logger.entering("server.database.Database", "startTransaction");

        final String jdbc = "jdbc:sqlite";
        final String dbName = "C:/Dropbox/CS240/P1_Record_Indexer/database/indexing_tables.sqlite";
        final String dbUrl = jdbc + ":" + dbName;
        try {
            connection = DriverManager.getConnection(dbUrl);
            connection.setAutoCommit(false);
        } catch (Exception e) {
            throw new DatabaseException(e.getMessage());
        }

        logger.exiting("server.database.Database", "startTransaction");
    }

    /**
     * End the database transaction.
     *
     * @param commit If true, commit.  If false, rollback.
     */
    public void endTransaction(boolean commit) {
        logger.entering("server.database.Database", "endTransaction");

        try {
            if (commit) {
                connection.commit();
            } else {
                connection.rollback();
            }
            connection.close();
        } catch (Exception e) {

        } finally {
            connection = null;
        }

        logger.exiting("server.database.Database", "endTransaction");
    }

}
