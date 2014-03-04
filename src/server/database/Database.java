package server.database;

import java.io.File;
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

    private static final String DATABASE_DIRECTORY = "database";
    private static final String DATABASE_FILE = "indexing_tables.sqlite";
    private static final String DATABASE_URL = "jdbc:sqlite:" + DATABASE_DIRECTORY + File.separator + DATABASE_FILE;

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

        try {
            final String driverName = "org.sqlite.JDBC";
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

        try {
//            assert (connection != null);
            connection = DriverManager.getConnection(DATABASE_URL);
            connection.setAutoCommit(false);
        } catch (Exception e) {
            throw new DatabaseException(e.getMessage());
        }

        logger.exiting("server.database.Database", "startTransaction");
    }

    public boolean inTransaction() {
        return (connection != null);
    }

    /**
     * End the database transaction.
     *
     * @param commit If true, commit.  If false, rollback.
     */
    public void endTransaction(boolean commit) {
        logger.entering("server.database.Database", "endTransaction");

        if (connection != null) {
            try {
                if (commit) {
                    connection.commit();
                } else {
                    connection.rollback();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                safeClose(connection);
                connection = null;
            }
        }

        logger.exiting("server.database.Database", "endTransaction");
    }

    public static void safeClose(Connection conn) {
        if (conn != null) {
            try {
                conn.close();
            }
            catch (SQLException e) {}
        }
    }

    public static void safeClose(Statement stmt) {
        if (stmt != null) {
            try {
                stmt.close();
            }
            catch (SQLException e) {}
        }
    }

    public static void safeClose(PreparedStatement stmt) {
        if (stmt != null) {
            try {
                stmt.close();
            }
            catch (SQLException e) {}
        }
    }

    public static void safeClose(ResultSet rs) {
        if (rs != null) {
            try {
                rs.close();
            }
            catch (SQLException e) {}
        }
    }

}
