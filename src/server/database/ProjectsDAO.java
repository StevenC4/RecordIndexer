package server.database;

import shared.model.Project;

import java.util.logging.Logger;

/**
 * Created with IntelliJ IDEA.
 * User: Steven
 * Date: 2/12/14
 * Time: 10:27 AM
 * To change this template use File | Settings | File Templates.
 */
public class ProjectsDAO {

    private static Logger logger;

    static {
        logger = Logger.getLogger("projectmanager");
    }

    private Database db;

    ProjectsDAO(Database db)  {
        this.db = db;
    }

    public boolean addProject(Project project) {
        return false;
    }
}
