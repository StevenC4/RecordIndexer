/**
 * Created with IntelliJ IDEA.
 * User: Steven
 * Date: 3/10/14
 * Time: 9:59 AM
 * To change this template use File | Settings | File Templates.
 */

import client.communication.ClientCommunicatorTest;
import server.database.DatabaseTest;
import shared.model.*;

//import static junit.textui.TestRunner.*;
import static org.junit.runner.JUnitCore.runClasses;

public class RunAllTests {
    public static void main(String[] args) {

        runClasses(ClientCommunicatorTest.class, DatabaseTest.class, BatchesManagerTest.class, BatchTest.class, FieldsManagerTest.class,
                   FieldTest.class, ProjectsManagerTest.class, ProjectTest.class, UsersManagerTest.class, UserTest.class,
                   ValuesManagerTest.class, ValueTest.class);

    }
}
