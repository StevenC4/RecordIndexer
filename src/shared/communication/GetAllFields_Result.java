package shared.communication;

import shared.model.Field;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Steven
 * Date: 2/15/14
 * Time: 2:07 PM
 * To change this template use File | Settings | File Templates.
 */
public class GetAllFields_Result {

    private List<Field> fields;

    public GetAllFields_Result() {
        fields = null;
    }

    public List<Field> getFields() {
        return fields;
    }

    public void setFields(List<Field> fields) {
        this.fields = fields;
    }

}
