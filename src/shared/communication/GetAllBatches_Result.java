package shared.communication;

import shared.model.Batch;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Steven
 * Date: 2/15/14
 * Time: 2:06 PM
 * To change this template use File | Settings | File Templates.
 */
public class GetAllBatches_Result {

    private List<Batch> batches;

    public GetAllBatches_Result() {
        batches = null;
    }

    public List<Batch> getBatches() {
        return batches;
    }

    public void setBatches(List<Batch> batches) {
        this.batches = batches;
    }

}
