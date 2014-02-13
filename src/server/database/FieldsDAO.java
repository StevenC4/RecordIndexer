package server.database;

import java.util.logging.Logger;

/**
 * Created with IntelliJ IDEA.
 * User: Steven
 * Date: 2/12/14
 * Time: 10:27 AM
 * To change this template use File | Settings | File Templates.
 */
public class FieldsDAO {

    private static Logger logger;

    static {
        logger = Logger.getLogger("fieldmanager");
    }

    private Database db;

    FieldsDAO (Database db) {
        this.db = db;
    }
}
