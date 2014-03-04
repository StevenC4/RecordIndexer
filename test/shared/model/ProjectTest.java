package shared.model;

import static org.junit.Assert.*;
import org.junit.Test;
import org.junit.Before;
import org.junit.After;

/**
 * Project Tester.
 *
 * @author <Authors name>
 * @since <pre>Mar 3, 2014</pre>
 * @version 1.0
 */
public class ProjectTest {

    Project projectDefault;
    Project projectSet;
    int projectId;
    String title;
    int recordsPerImage;
    int firstYCoord;
    int recordHeight;

    @Before
    public void before() throws Exception {
        projectDefault = new Project();
        projectId = 1;
        title = "title";
        recordsPerImage = 2;
        firstYCoord = 3;
        recordHeight = 4;
        projectSet = new Project(projectId, title, recordsPerImage, firstYCoord, recordHeight);
    }

    @After
    public void after() throws Exception {
    }

    @Test
    public void testGetProjectId() throws Exception {
        assertEquals(-1, projectDefault.getProjectId());
        assertEquals(projectId, projectSet.getProjectId());
    }

    @Test
    public void testSetProjectId() throws Exception {
        projectDefault.setProjectId(projectId);
        assertEquals(projectId, projectDefault.getProjectId());
    }

    @Test
    public void testGetTitle() throws Exception {
        assertNull(projectDefault.getTitle());
        assertEquals(title, projectSet.getTitle());
    }

    @Test
    public void testSetTitle() throws Exception {
        projectDefault.setTitle(title);
        assertEquals(title, projectDefault.getTitle());
    }

    @Test
    public void testGetRecordsPerImage() throws Exception {
        assertEquals(-1, projectDefault.getRecordsPerImage());
        assertEquals(recordsPerImage, projectSet.getRecordsPerImage());
    }

    @Test
    public void testSetRecordsPerImage() throws Exception {
        projectDefault.setRecordsPerImage(recordsPerImage);
        assertEquals(recordsPerImage, projectDefault.getRecordsPerImage());
    }

    @Test
    public void testGetFirstYCoord() throws Exception {
        assertEquals(-1, projectDefault.getFirstYCoord());
        assertEquals(firstYCoord, projectSet.getFirstYCoord());
    }

    @Test
    public void testSetFirstYCoord() throws Exception {
        projectDefault.setFirstYCoord(firstYCoord);
        assertEquals(firstYCoord, projectDefault.getFirstYCoord());
    }

    @Test
    public void testGetRecordHeight() throws Exception {
        assertEquals(-1, projectDefault.getRecordsPerImage());
        assertEquals(recordsPerImage, projectSet.getRecordsPerImage());
    }

    @Test
    public void testSetRecordHeight() throws Exception {
        projectDefault.setRecordHeight(recordHeight);
        assertEquals(recordHeight, projectDefault.getRecordHeight());
    }
}
