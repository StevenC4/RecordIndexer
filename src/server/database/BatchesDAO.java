package server.database;

import java.util.logging.Logger;

/**
 * Created with IntelliJ IDEA.
 * User: Steven
 * Date: 2/12/14
 * Time: 10:27 AM
 * To change this template use File | Settings | File Templates.
 */
public class BatchesDAO {

    private static Logger logger;

    static {
        logger = Logger.getLogger("batchmanager");
    }

    private Database db;

    BatchesDAO(Database db) {
        this.db = db;
    }
}
