package shared.model;

import static org.junit.Assert.*;
import org.junit.Test;
import org.junit.Before;
import org.junit.After;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Steven
 * Date: 3/6/14
 * Time: 5:45 PM
 * To change this template use File | Settings | File Templates.
 */
public class ValuesManagerTest {

    ValuesManager valuesManager;

    @Before
    public void before() throws Exception {
        valuesManager = new ValuesManager();
        ValuesManager.initialize();
        valuesManager.deleteAllValues();
    }

    @After
    public void after() throws Exception {
    }

    /**
     *
     * Method: deleteAllValues()
     *
     */
    @Test
    public void testDeleteAllValues() throws Exception {
        valuesManager.addValue(new Value());
        valuesManager.deleteAllValues();
        assertEquals(0, valuesManager.getAllValues().size());
    }

    /**
     *
     * Method: addList(List<Value> valueList)
     *
     */
    @Test
    public void testAddList() throws Exception {
        valuesManager.deleteAllValues();
        List<Value> values = new ArrayList<Value>();
        values.add(new Value(1, 1, 1, 1, 1, 1, "test1"));
        values.add(new Value(2, 3, 4, 5, 6, 2, "test2"));
        values.add(new Value(3, 5, 7, 9, 11, 3, "test3"));
        values.add(new Value(4, 2, 1, 3, 7, 4, "test4"));
        valuesManager.addList(values);
        List<Value> returnedList = valuesManager.getAllValues();
        assertEquals(values, returnedList);
    }

    /**
     *
     * Method: getNextRecordId()
     *
     */
    @Test
    public void testGetNextRecordId() throws Exception {
        valuesManager.deleteAllValues();
        assertEquals(1, valuesManager.getNextRecordId());
        List<Value> values = new ArrayList<Value>();
        values.add(new Value(1, 1, 1, 1, 6, 1, "test1"));
        values.add(new Value(2, 3, 4, 5, 6, 2, "test2"));
        values.add(new Value(3, 5, 7, 9, 6, 3, "test3"));
        values.add(new Value(4, 2, 1, 3, 6, 4, "test4"));
        valuesManager.addList(values);
        int nextRecordId = valuesManager.getNextRecordId();
        assertEquals(7, nextRecordId);
    }

    /**
     *
     * Method: searchValues(String fields, String searchValues)
     *
     */
    @Test
    public void testSearchValues() throws Exception {
        valuesManager.deleteAllValues();
        List<Value> values = new ArrayList<Value>();
        values.add(new Value(1, 1, 1, 1, 1, 1, "Teagan"));
        values.add(new Value(2, 1, 2, 1, 1, 2, "22"));
        values.add(new Value(3, 1, 3, 1, 1, 3, "Germany"));
        values.add(new Value(4, 1, 4, 1, 1, 4, "Blue"));
        values.add(new Value(5, 1, 1, 2, 1, 5, "Steven"));
        values.add(new Value(6, 1, 2, 2, 1, 6, "20"));
        values.add(new Value(7, 1, 3, 2, 1, 7, "USA"));
        values.add(new Value(8, 1, 4, 2, 1, 8, "purple"));
        values.add(new Value(9, 1, 1, 3, 1, 9, "Melanie"));
        values.add(new Value(10, 1, 2, 3, 1, 10, "24"));
        values.add(new Value(11, 1, 3, 3, 1, 11, "Australia"));
        values.add(new Value(12, 1, 4, 3, 1, 12, "Teal"));
        values.add(new Value(13, 2, 5, 4, 2, 13, "Tyrone"));
        values.add(new Value(14, 2, 6, 4, 2, 14, "19"));
        values.add(new Value(15, 2, 7, 4, 2, 15, "Zimbabwe"));
        values.add(new Value(16, 2, 8, 4, 2, 16, "Red"));
        valuesManager.addList(values);
        String fieldsList = "1,2,4";
        String valuesList = "steven,24,red";
        List<Value> searchResults = valuesManager.searchValues(fieldsList, valuesList);
        assertEquals(new Value(5, 1, 1, 2, 1, 1, "Steven"), searchResults.get(0));
        assertEquals(new Value(10, 1, 2, 3, 1, 2, "24"), searchResults.get(1));
    }
}
