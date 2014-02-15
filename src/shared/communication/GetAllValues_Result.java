package shared.communication;

import shared.model.Value;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Steven
 * Date: 2/15/14
 * Time: 2:09 PM
 * To change this template use File | Settings | File Templates.
 */
public class GetAllValues_Result {

    private List<Value> values;

    public GetAllValues_Result() {
        values = null;
    }

    public List<Value> getValues() {
        return values;
    }

    public void setValues(List<Value> values) {
        this.values = values;
    }

}
