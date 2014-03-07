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
 * Time: 5:43 PM
 * To change this template use File | Settings | File Templates.
 */
public class BatchesManagerTest {

    BatchesManager batchesManager;

    @Before
    public void before() throws Exception {
        Database.initialize();
        batchesManager = new BatchesManager();
        batchesManager.deleteAllBatches();
    }

    @After
    public void after() throws Exception {
    }

    /**
     *
     * Method: updateBatch(Batch batch)
     *
     */
    @Test
    public void testUpdateBatch() throws Exception {
        Batch batchBefore = new Batch(1, 1, "/images/test.png", "new");
        Batch batchAfter = new Batch(1, 1, "/images/test.png", "checked out");
        batchesManager.addBatch(batchBefore);
        Batch batchReturned = batchesManager.getAllBatches().get(0);
        assertEquals(batchBefore, batchReturned);

        batchReturned.setStatus(batchAfter.getStatus());
        batchesManager.updateBatch(batchReturned);
        batchReturned = batchesManager.getAllBatches().get(0);
        assertEquals(batchAfter, batchReturned);
    }

    /**
     *
     * Method: deleteAllBatches()
     *
     */
    @Test
    public void testDeleteAllBatches() throws Exception {
        batchesManager.addBatch(new Batch(5, 32, "k.bmp", "completed"));
        batchesManager.deleteAllBatches();
        assertEquals(0, batchesManager.getAllBatches().size());
    }

    /**
     *
     * Method: getSampleImage(int projectId)
     *
     */
    @Test
    public void testGetSampleImage() throws Exception {
        Batch batch1 = new Batch(1, 1, "/images/test1.png", "new");
        Batch batch2 = new Batch(2, 1, "/images/test2.png", "new");
        Batch batch3 = new Batch(3, 2, "/images/test3.png", "new");
        Batch batch4 = new Batch(4, 2, "/images/test4.png", "new");
        batchesManager.addBatch(batch1);
        batchesManager.addBatch(batch2);
        batchesManager.addBatch(batch3);
        batchesManager.addBatch(batch4);
        assertEquals(batch1, batchesManager.getSampleImage(1));
        assertEquals(batch3, batchesManager.getSampleImage(2));
    }

    /**
     *
     * Method: addList(List<Batch> batchList)
     *
     */
    @Test
    public void testAddList() throws Exception {
        batchesManager.deleteAllBatches();
        List<Batch> batches = new ArrayList<Batch>();
        batches.add(new Batch(1, 1, "1", "test1"));
        batches.add(new Batch(2, 3, "2", "test2"));
        batches.add(new Batch(3, 5, "4", "test3"));
        batches.add(new Batch(4, 2, "6", "test4"));
        batchesManager.addList(batches);
        List<Batch> returnedList = batchesManager.getAllBatches();
        assertEquals(batches.size(), returnedList.size());
        for (int i = 0; i < batches.size(); i++) {
            assertEquals(batches.get(i), returnedList.get(i));
        }
    }

    /**
     *
     * Method: getNextAvailableBatch(int projectId)
     *
     */
    @Test
    public void testGetNextAvailableBatch() throws Exception {
        batchesManager.deleteAllBatches();
        Batch batch1 = new Batch(1, 1, "1.jpg", "completed");
        Batch batch2 = new Batch(2, 1, "2.jpg", "checked out");
        Batch batch3 = new Batch(3, 1, "3.jpg", "new");
        Batch batch4 = new Batch(4, 1, "4.jpg", "new");
        batchesManager.addBatch(batch1);
        batchesManager.addBatch(batch2);
        batchesManager.addBatch(batch3);
        batchesManager.addBatch(batch4);
        assertEquals(batch3, batchesManager.getNextAvailableBatch(1));
    }

    /**
     *
     * Method: getBatchByBatchId(int batchId)
     *
     */
    @Test
    public void testGetBatchByBatchId() throws Exception {
        batchesManager.deleteAllBatches();
        Batch batch1 = new Batch(1, 1, "1.jpg", "completed");
        Batch batch2 = new Batch(2, 1, "2.jpg", "checked out");
        Batch batch3 = new Batch(3, 1, "3.jpg", "new");
        Batch batch4 = new Batch(4, 1, "4.jpg", "new");
        batchesManager.addBatch(batch1);
        batchesManager.addBatch(batch2);
        batchesManager.addBatch(batch3);
        batchesManager.addBatch(batch4);
        assertEquals(batch1, batchesManager.getBatchByBatchId(1));
        assertEquals(batch3, batchesManager.getBatchByBatchId(3));
        assertEquals(batch2, batchesManager.getBatchByBatchId(2));
        assertEquals(batch4, batchesManager.getBatchByBatchId(4));
    }
}
