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
        assertEquals(-1, batchDefault.getBatchId());
        assertEquals(batchId, batchSet.getBatchId());
    }

    @Test
    public void testSetBatchId() throws Exception {
        batchDefault.setBatchId(batchId);
        assertEquals(batchId, batchDefault.getBatchId());
    }

    @Test
    public void testGetProjectId() throws Exception {
        assertEquals(-1, batchDefault.getProjectId());
        assertEquals(projectId, batchSet.getProjectId());
    }

    @Test
    public void testSetProjectId() throws Exception {
        batchDefault.setProjectId(projectId);
        assertEquals(projectId, batchDefault.getProjectId());
    }

    @Test
    public void testGetPath() throws Exception {
        assertNull(batchDefault.getPath());
        assertEquals(path, batchSet.getPath());
    }

    @Test
    public void testSetPath() throws Exception {
        batchDefault.setPath(path);
        assertEquals(path, batchDefault.getPath());
    }

    @Test
    public void testGetStatus() throws Exception {
        assertNull(batchDefault.getStatus());
        assertEquals(status, batchSet.getStatus());
    }

    @Test
    public void testSetStatus() throws Exception {
        batchDefault.setStatus(status);
        assertEquals(status, batchDefault.getStatus());
    }
}
