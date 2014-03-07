package shared.communication;

import shared.model.Field;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Steven
 * Date: 3/6/14
 * Time: 2:38 PM
 * To change this template use File | Settings | File Templates.
 */
public class GetFields_Result extends Operation_Result {

    private List<Field> fields;

    public GetFields_Result() {
        super();
        fields = null;
    }

    public GetFields_Result(List<Field> fields) {
        super();
        this.fields = fields;
    }

    public List<Field> getFields() {
        return fields;
    }

    public void setFields(List<Field> fields) {
        this.fields = fields;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        if (failed) {
            sb.append("FAILED\n");
        } else {
            for (Field field : fields) {
                sb.append(field.getProjectId()).append("\n");
                sb.append(field.getFieldId()).append("\n");
                sb.append(field.getTitle()).append("\n");
            }
        }
        return sb.toString();
    }
}
