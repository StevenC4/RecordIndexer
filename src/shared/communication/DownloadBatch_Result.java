package shared.communication;

import shared.model.Batch;
import shared.model.Field;
import shared.model.Project;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Steven
 * Date: 3/6/14
 * Time: 2:24 PM
 * To change this template use File | Settings | File Templates.
 */
public class DownloadBatch_Result extends Operation_Result {

    private Project project;
    private Batch batch;
    private List<Field> fields;

    public DownloadBatch_Result() {
        super();
        project = null;
        batch = null;
        fields = null;
    }

    public DownloadBatch_Result(Project project, Batch batch, List<Field> fields) {
        super();
        this.project = project;
        this.batch = batch;
        this.fields = fields;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public Batch getBatch() {
        return batch;
    }

    public void setBatch(Batch batch) {
        this.batch = batch;
    }

    public List<Field> getFields() {
        return fields;
    }

    public void setFields(List<Field> fields) {
        this.fields = fields;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        if (failed)
        {
            sb.append("FAILED\n");
        } else {
            sb.append(batch.getBatchId()).append("\n");
            sb.append(project.getProjectId()).append("\n");
            sb.append(batch.getPath()).append("\n");
            sb.append(project.getFirstYCoord()).append("\n");
            sb.append(project.getRecordHeight()).append("\n");
            sb.append(project.getRecordsPerImage()).append("\n");
            sb.append(fields.size()).append("\n");
            for (Field field : fields) {
                sb.append(field.getFieldId()).append("\n");
                sb.append(field.getPosition()).append("\n");
                sb.append(field.getTitle()).append("\n");
                sb.append(field.getHelpHTML()).append("\n");
                sb.append(field.getxCoord()).append("\n");
                sb.append(field.getWidth()).append("\n");
                if (field.getKnownData() != null) {
                    sb.append(field.getKnownData()).append("\n");
                }
            }
        }
        return sb.toString();
    }
}
