package shared.communication;

import shared.model.Batch;

/**
 * Created with IntelliJ IDEA.
 * User: Steven
 * Date: 2/15/14
 * Time: 2:06 PM
 * To change this template use File | Settings | File Templates.
 */
public class DeleteBatch_Params {

    private Batch batch;

    public DeleteBatch_Params() {
        batch = null;
    }

    public Batch getBatch() {
        return batch;
    }

    public void setBatch(Batch batch) {
        this.batch = batch;
    }

}
