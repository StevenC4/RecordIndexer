package shared.model;

import static org.junit.Assert.*;
import org.junit.Test;
import org.junit.Before;
import org.junit.After;

/**
 * Field Tester.
 *
 * @author <Authors name>
 * @since <pre>Mar 3, 2014</pre>
 * @version 1.0
 */
public class FieldTest {

    private Field fieldDefault;
    private Field fieldSet;
    private int fieldId;
    private String title;
    private int position;
    private int projectId;
    private int xCoord;
    private int width;
    private String htmlHelp;
    private String knownData;

    @Before
    public void before() throws Exception {
        fieldDefault = new Field();
        fieldId = 1;
        title = "title";
        position = 2;
        projectId = 3;
        xCoord = 4;
        width = 5;
        htmlHelp = "html_help";
        knownData = "known_data";
        fieldSet = new Field(fieldId, title, position, projectId, xCoord, width, htmlHelp, knownData);
    }

    @After
    public void after() throws Exception {
    }

    @Test
    public void testGetFieldId() throws Exception {
        assertEquals(-1, fieldDefault.getFieldId());
        assertEquals(fieldId, fieldSet.getFieldId());
    }

    @Test
    public void testSetFieldId() throws Exception {
        fieldDefault.setFieldId(fieldId);
        assertEquals(fieldId, fieldDefault.getFieldId());
    }

    @Test
    public void testSetTitle() throws Exception {
        assertNull(fieldDefault.getTitle());
        assertEquals(title, fieldSet.getTitle());
    }

    @Test
    public void testGetTitle() throws Exception {
        fieldDefault.setTitle(title);
        assertEquals(title, fieldDefault.getTitle());
    }

    @Test
    public void testGetProjectId() throws Exception {
        assertEquals(-1, fieldDefault.getProjectId());
        assertEquals(projectId, fieldSet.getProjectId());
    }

    @Test
    public void testSetProjectId() throws Exception {
        fieldDefault.setProjectId(projectId);
        assertEquals(projectId, fieldDefault.getProjectId());
    }

    @Test
    public void testGetxCoord() throws Exception {
        assertEquals(-1, fieldDefault.getxCoord());
        assertEquals(xCoord, fieldSet.getxCoord());
    }

    @Test
    public void testSetxCoord() throws Exception {
        fieldDefault.setxCoord(xCoord);
        assertEquals(xCoord, fieldDefault.getxCoord());
    }

    @Test
    public void testGetWidth() throws Exception {
        assertEquals(-1, fieldDefault.getWidth());
        assertEquals(width, fieldSet.getWidth());
    }

    @Test
    public void testSetWidth() throws Exception {
        fieldDefault.setWidth(width);
        assertEquals(width, fieldDefault.getWidth());
    }

    @Test
    public void testGetHelpHTML() throws Exception {
        assertNull(fieldDefault.getHelpHTML());
        assertEquals(htmlHelp, fieldSet.getHelpHTML());
    }

    @Test
    public void testSetHelpHTML() throws Exception {
        fieldDefault.setHelpHTML(htmlHelp);
        assertEquals(htmlHelp, fieldDefault.getHelpHTML());
    }

    @Test
    public void testGetKnownData() throws Exception {
        assertNull(fieldDefault.getKnownData());
        assertEquals(knownData, fieldSet.getKnownData());
    }

    @Test
    public void testSetKnownData() throws Exception {
        fieldDefault.setKnownData(knownData);
        assertEquals(knownData, fieldDefault.getKnownData());
    }

    @Test
    public void testGetPosition() throws Exception {
        assertEquals(-1, fieldDefault.getPosition());
        assertEquals(position, fieldSet.getPosition());
    }

    @Test
    public void testSetPosition() throws Exception {
        fieldDefault.setPosition(position);
        assertEquals(position, fieldDefault.getPosition());
    }
}