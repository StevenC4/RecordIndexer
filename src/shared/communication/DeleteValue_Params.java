package shared.communication;

import shared.model.Value;

/**
 * Created with IntelliJ IDEA.
 * User: Steven
 * Date: 2/15/14
 * Time: 2:09 PM
 * To change this template use File | Settings | File Templates.
 */
public class DeleteValue_Params {

    private Value value;

    public DeleteValue_Params() {
        value = null;
    }

    public Value getValue() {
        return value;
    }

    public void setValue(Value value) {
        this.value = value;
    }

}
