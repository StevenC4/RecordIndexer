package shared.model;

import static org.junit.Assert.*;
import org.junit.Test;
import org.junit.Before;
import org.junit.After;


public class BatchTest {

    private Batch batchDefault;
    private Batch batchSet;
    private int batchId;
    private int projectId;
    private String path;
    private String status;

    @Before
    public void before() throws Exception {
        batchDefault = new Batch();
        batchId = 1;
        projectId = 2;
        path = "temp_path";
        status = "temp_status";
        batchSet = new Batch(batchId, projectId, path, status);
    }

    @After
    public void after() throws Exception {}

    @Test
    public void testGetBatchId() throws Exception {
        assertEquals(batchDefault.getBatchId(), -1);
        assertEquals(batchSet.getBatchId(), batchId);
    }

    @Test
    public void testSetBatchId() throws Exception {
        batchDefault.setBatchId(batchId);
        assertEquals(batchDefault.getBatchId(), batchId);
    }

    @Test
    public void testGetProjectId() throws Exception {
        assertEquals(batchDefault.getProjectId(), -1);
        assertEquals(batchSet.getProjectId(), projectId);
    }

    @Test
    public void testSetProjectId() throws Exception {
        batchDefault.setProjectId(projectId);
        assertEquals(batchDefault.getProjectId(), projectId);
    }

    @Test
    public void testGetPath() throws Exception {
        assertNull(batchDefault.getPath());
        assertEquals(batchSet.getPath(), path);
    }

    @Test
    public void testSetPath() throws Exception {
        batchDefault.setPath(path);
        assertEquals(batchDefault.getPath(), path);
    }

    @Test
    public void testGetStatus() throws Exception {
        assertNull(batchDefault.getStatus());
        assertEquals(batchSet.getStatus(), status);
    }

    @Test
    public void testSetStatus() throws Exception {
        batchDefault.setStatus(status);
        assertEquals(batchDefault.getStatus(), status);
    }
}
