package shared.model;

import static org.junit.Assert.*;
import org.junit.Test;
import org.junit.Before;
import org.junit.After;

/**
 * Value Tester.
 *
 * @author <Authors name>
 * @since <pre>Mar 3, 2014</pre>
 * @version 1.0
 */
public class ValueTest {

    Value valueDefault;
    Value valueSet;
    int valueId;
    int projectId;
    int fieldId;
    int recordId;
    int batchId;
    int recordNum;
    String value;

    @Before
    public void before() throws Exception {
        valueDefault = new Value();
        valueId = 1;
        projectId = 2;
        fieldId = 3;
        recordId = 4;
        batchId = 5;
        recordNum = 6;
        value = "value";
        valueSet = new Value(valueId, projectId, fieldId, batchId, recordId, recordNum, value);
    }

    @After
    public void after() throws Exception {
    }

    @Test
    public void testGetRecordId() throws Exception {
        assertEquals(-1, valueDefault.getRecordId());
        assertEquals(recordId, valueSet.getRecordId());
    }

    @Test
    public void testSetRecordId() throws Exception {
        valueDefault.setRecordId(recordId);
        assertEquals(recordId, valueDefault.getRecordId());
    }

    @Test
    public void testGetValueId() throws Exception {
        assertEquals(-1, valueDefault.getValueId());
        assertEquals(valueId, valueSet.getValueId());
    }

    @Test
    public void testSetValueId() throws Exception {
        valueDefault.setValueId(valueId);
        assertEquals(valueId, valueDefault.getValueId());
    }

    @Test
    public void testGetProjectId() throws Exception {
        assertEquals(-1, valueDefault.getProjectId());
        assertEquals(projectId, valueSet.getProjectId());
    }

    @Test
    public void testSetProjectId() throws Exception {
        valueDefault.setProjectId(projectId);
        assertEquals(projectId, valueDefault.getProjectId());
    }

    @Test
    public void testGetFieldId() throws Exception {
        assertEquals(-1, valueDefault.getFieldId());
        assertEquals(fieldId, valueSet.getFieldId());
    }

    @Test
    public void testSetFieldId() throws Exception {
        valueDefault.setFieldId(fieldId);
        assertEquals(fieldId, valueDefault.getFieldId());
    }

    @Test
    public void testGetBatchId() throws Exception {
        assertEquals(-1, valueDefault.getBatchId());
        assertEquals(batchId, valueSet.getBatchId());
    }

    @Test
    public void testSetBatchId() throws Exception {
        valueDefault.setBatchId(batchId);
        assertEquals(batchId, valueDefault.getBatchId());
    }

    @Test
    public void testGetValue() throws Exception {
        assertNull(valueDefault.getValue());
        assertEquals(value, valueSet.getValue());
    }

    @Test
    public void testSetValue() throws Exception {
        valueDefault.setValue(value);
        assertEquals(value, valueDefault.getValue());
    }
}