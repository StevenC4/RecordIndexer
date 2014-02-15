package shared.communication;

import shared.model.Batch;

/**
 * Created with IntelliJ IDEA.
 * User: Steven
 * Date: 2/15/14
 * Time: 2:06 PM
 * To change this template use File | Settings | File Templates.
 */
public class AddBatch_Params {

    private Batch batch;

    public AddBatch_Params() {
        batch = null;
    }

    public Batch getBatch() {
        return batch;
    }

    public void setContact(Batch batch) {
        this.batch = batch;
    }

}
