package shared.communication;

import shared.model.Value;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Steven
 * Date: 3/6/14
 * Time: 3:07 PM
 * To change this template use File | Settings | File Templates.
 */
public class Search_Result extends Operation_Result {

    private List<Value> values;
    private List<String> paths;

    public Search_Result() {
        super();
        values = null;
        paths = null;
    }

    public Search_Result(List<Value> values, List<String> paths) {
        this.values = values;
        this.paths = paths;
        failed = false;
    }

    public List<Value> getValues() {
        return values;
    }

    public void setValues(List<Value> values) {
        this.values = values;
    }

    public List<String> getPaths() {
        return paths;
    }

    public void setPaths(List<String> paths) {
        this.paths = paths;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        if (failed) {
            sb.append("FAILED\n");
        } else {
            for (int i = 0; i < values.size(); i++) {
                sb.append(values.get(i).getBatchId()).append("\n");
                sb.append(paths.get(i)).append("\n");
                sb.append(values.get(i).getRecordId()).append("\n");
                sb.append(values.get(i).getFieldId()).append("\n");
            }
        }
        return sb.toString();
    }
}
