package server.database;

import java.util.logging.Logger;

/**
 * Created with IntelliJ IDEA.
 * User: Steven
 * Date: 2/12/14
 * Time: 10:28 AM
 * To change this template use File | Settings | File Templates.
 */
public class UsersDAO {

    private static Logger logger;

    static {
        logger = Logger.getLogger("usermanager");
    }

    private Database db;

    UsersDAO(Database db) {
        this.db = db;
    }
}
