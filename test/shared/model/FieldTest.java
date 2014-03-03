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
        assertEquals(fieldDefault.getFieldId(), -1);
        assertEquals(fieldSet.getFieldId(), fieldId);
    }

    @Test
    public void testSetFieldId() throws Exception {
        fieldDefault.setFieldId(fieldId);
        assertEquals(fieldDefault.getFieldId(), fieldId);
    }

    @Test
    public void testSetTitle() throws Exception {
        assertNull(fieldDefault.getTitle());
        assertEquals(fieldSet.getTitle(), title);
    }

    @Test
    public void testGetTitle() throws Exception {
        fieldDefault.setTitle(title);
        assertEquals(fieldDefault.getTitle(), title);
    }

    @Test
    public void testGetProjectId() throws Exception {
        assertEquals(fieldDefault.getProjectId(), -1);
        assertEquals(fieldSet.getProjectId(), projectId);
    }

    @Test
    public void testSetProjectId() throws Exception {
        fieldDefault.setProjectId(projectId);
        assertEquals(fieldDefault.getProjectId(), projectId);
    }

    @Test
    public void testGetxCoord() throws Exception {
        assertEquals(fieldDefault.getxCoord(), -1);
        assertEquals(fieldSet.getxCoord(), xCoord);
    }

    @Test
    public void testSetxCoord() throws Exception {
        fieldDefault.setxCoord(xCoord);
        assertEquals(fieldDefault.getxCoord(), xCoord);
    }

    @Test
    public void testGetWidth() throws Exception {
        assertEquals(fieldDefault.getWidth(), -1);
        assertEquals(fieldSet.getWidth(), width);
    }

    @Test
    public void testSetWidth() throws Exception {
        fieldDefault.setWidth(width);
        assertEquals(fieldDefault.getWidth(), width);
    }

    @Test
    public void testGetHelpHTML() throws Exception {
        assertNull(fieldDefault.getHelpHTML());
        assertEquals(fieldSet.getHelpHTML(), htmlHelp);
    }

    @Test
    public void testSetHelpHTML() throws Exception {
        fieldDefault.setHelpHTML(htmlHelp);
        assertEquals(fieldDefault.getHelpHTML(), htmlHelp);
    }

    @Test
    public void testGetKnownData() throws Exception {
        assertNull(fieldDefault.getKnownData());
        assertEquals(fieldSet.getKnownData(), knownData);
    }

    @Test
    public void testSetKnownData() throws Exception {
        fieldDefault.setKnownData(knownData);
        assertEquals(fieldDefault.getKnownData(), knownData);
    }

    @Test
    public void testGetPosition() throws Exception {
        assertEquals(fieldDefault.getPosition(), -1);
        assertEquals(fieldSet.getPosition(), position);
    }

    @Test
    public void testSetPosition() throws Exception {
        fieldDefault.setPosition(position);
        assertEquals(fieldDefault.getPosition(), position);
    }
}