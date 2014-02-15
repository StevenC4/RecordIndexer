package shared.communication;

import shared.model.Field;

/**
 * Created with IntelliJ IDEA.
 * User: Steven
 * Date: 2/15/14
 * Time: 2:07 PM
 * To change this template use File | Settings | File Templates.
 */
public class AddField_Params {
    
    private Field field;

    public AddField_Params() {
        field = null;
    }

    public Field getField() {
        return field;
    }

    public void setField(Field field) {
        this.field = field;
    }
    
}
