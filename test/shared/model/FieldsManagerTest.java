package shared.model;

import static org.junit.Assert.*;
import org.junit.Test;
import org.junit.Before;
import org.junit.After;
import server.database.Database;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Steven
 * Date: 3/6/14
 * Time: 5:44 PM
 * To change this template use File | Settings | File Templates.
 */
public class FieldsManagerTest {

    FieldsManager fieldsManager;

    @Before
    public void before() throws Exception {
        fieldsManager = new FieldsManager();
        fieldsManager.initialize();
        FieldsManager.deleteAllFields();
    }

    @After
    public void after() throws Exception {
    }

    /**
     *
     * Method: deleteAllFields()
     *
     */
    @Test
    public void testDeleteAllFields() throws Exception {
        fieldsManager.addField(new Field(1, "title", 1, 1, 350, 15, "help.html", "knownData.html"));
        fieldsManager.deleteAllFields();
        assertEquals(0, fieldsManager.getAllFields().size());
    }

    /**
     *
     * Method: addList(List<Field> fieldList)
     *
     */
    @Test
    public void testAddList() throws Exception {
        fieldsManager.deleteAllFields();
        List<Field> fields = new ArrayList<Field>();
        fields.add(new Field(1, "title3", 5, 4, 351, 11, "help1.html", "knownData1.html"));
        fields.add(new Field(2, "title1", 3, 8, 352, 12, "help2.html", "knownData1.html"));
        fields.add(new Field(3, "title2", 2, 6, 353, 13, "help3.html", "knownData1.html"));
        fields.add(new Field(4, "title4", 6, 1, 354, 14, "help4.html", "knownData1.html"));
        fieldsManager.addList(fields);
        List<Field> returnedList = fieldsManager.getAllFields();
        assertEquals(fields, returnedList);
    }

    /**
     *
     * Method: getProjectFields(int projectId)
     *
     */
    @Test
    public void testGetProjectFields() throws Exception {
        fieldsManager.deleteAllFields();
        List<Field> fields = new ArrayList<Field>();
        Field field1 = new Field(1, "title3", 5, 4, 351, 11, "help1.html", "knownData1.html");
        Field field2 = new Field(2, "title1", 3, 2, 352, 12, "help2.html", "knownData1.html");
        Field field3 = new Field(3, "title2", 2, 2, 353, 13, "help3.html", "knownData1.html");
        Field field4 = new Field(4, "title4", 6, 1, 354, 14, "help4.html", "knownData1.html");
        fieldsManager.addField(field1);
        fieldsManager.addField(field2);
        fieldsManager.addField(field3);
        fieldsManager.addField(field4);
        List<Field> returnedList = fieldsManager.getProjectFields(2);
        assertEquals(field2, returnedList.get(0));
        assertEquals(field3, returnedList.get(1));
    }


}
