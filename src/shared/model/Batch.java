package shared.model;

/**
 * Created with IntelliJ IDEA.
 * User: Steven
 * Date: 2/11/14
 * Time: 3:19 PM
 * To change this template use File | Settings | File Templates.
 */
public class Batch {

    private int batchId;
    private int projectId;
    private String path;
    private String status;

    /**
     * Instantiates a new Batch.
     */
    public Batch() {
        setBatchId(-1);
        setProjectId(-1);
        setPath(null);
        setStatus(null);
    }

    /**
     * Instantiates a new Batch.
     *
     * @param batchId the unique batch id
     * @param projectId the unique project id
     * @param path the image's path
     * @param status the status of the batch
     */
    public Batch(int batchId, int projectId, String path, String status) {
        setBatchId(batchId);
        setProjectId(projectId);
        setPath(path);
        setStatus(status);
    }

    /**
     * Gets the unique batch id.
     *
     * @return the unique batch id
     */
    public int getBatchId() {
        return batchId;
    }

    /**
     * Sets the unique batch id.
     *
     * @param batchId the unique batch id
     */
    public void setBatchId(int batchId) {
        this.batchId = batchId;
    }

    /**
     * Gets the unique project id.
     *
     * @return the unique project id
     */
    public int getProjectId() {
        return projectId;
    }

    /**
     * Sets the unique project id.
     *
     * @param projectId the unique project id
     */
    public void setProjectId(int projectId) {
        this.projectId = projectId;
    }

    /**
     * Gets the image's path.
     *
     * @return the image's path
     */
    public String getPath() {
        return path;
    }

    /**
     * Sets the image's path.
     *
     * @param path the image's path
     */
    public void setPath(String path) {
        this.path = path;
    }

    /**
     * Gets the status of the batch.
     *
     * @return the status of the batch
     */
    public String getStatus() {
        return status;
    }

    /**
     * Sets the status of the batch.
     *
     * @param status the status of the batch
     */
    public void setStatus(String status) {
        this.status = status;
    }
}
