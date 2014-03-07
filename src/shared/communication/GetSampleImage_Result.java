package shared.communication;

import shared.model.Batch;

/**
 * Created with IntelliJ IDEA.
 * User: Steven
 * Date: 3/6/14
 * Time: 2:48 PM
 * To change this template use File | Settings | File Templates.
 */
public class GetSampleImage_Result extends Operation_Result {

    private Batch batch;

    public GetSampleImage_Result() {
        super();
        batch = null;
    }

    public GetSampleImage_Result(Batch batch) {
        super();
        this.batch = batch;
    }

    public void setBatch(Batch batch) {
        this.batch = batch;
    }

    public Batch getBatch() {
        return batch;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        if (failed) {
            sb.append("FAILED\n");
        } else {
            sb.append(batch.getPath()).append("\n");
        }
        return sb.toString();
    }
}
