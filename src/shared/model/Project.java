package shared.model;

/**
 * Created with IntelliJ IDEA.
 * User: Steven
 * Date: 2/11/14
 * Time: 3:18 PM
 * To change this template use File | Settings | File Templates.
 */
public class Project {

    /**
     * The unique project ID
     */
    int projectId;

    /**
     * The title of the project
     */
    String title;

    /**
     * The number of records per image
     */
    int recordsPerImage;

    /**
     * The uppermost y-coordinate of the first record in the image
     */
    int firstYCoord;

    /**
     * The height of each record
     */
    int recordHeight;

    /**
     * Instantiates a new Project with default values.
     */
    public Project() {
        setProjectId(-1);
        setTitle(null);
        setRecordsPerImage(-1);
        setFirstYCoord(-1);
        setRecordHeight(-1);
    }

    /**
     * Instantiates a new Project.
     *
     * @param projectId the unique project id
     * @param title the title of the project
     * @param recordsPerImage the number of records per image
     * @param firstYCoord the y coordinate corresponding to the first row within the image
     * @param recordHeight the record height in pixels
     */
    public Project(int projectId, String title, int recordsPerImage, int firstYCoord, int recordHeight) {
        setProjectId(projectId);
        setTitle(title);
        setRecordsPerImage(recordsPerImage);
        setFirstYCoord(firstYCoord);
        setRecordHeight(recordHeight);
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
     * Gets the title of the project.
     *
     * @return the title of the project
     */
    public String getTitle() {
        return title;
    }

    /**
     * Sets the title of the project.
     *
     * @param title the title of the project
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Gets the number of records per image.
     *
     * @return the number of records per image
     */
    public int getRecordsPerImage() {
        return recordsPerImage;
    }

    /**
     * Sets the number of records per image.
     *
     * @param recordsPerImage the number of records per image
     */
    public void setRecordsPerImage(int recordsPerImage) {
        this.recordsPerImage = recordsPerImage;
    }

    /**
     * Gets the y coordinate corresponding to the first row within the image.
     *
     * @return the y coordinate corresponding to the first row within the image
     */
    public int getFirstYCoord() {
        return firstYCoord;
    }

    /**
     * Sets the y coordinate corresponding to the first row within the image.
     *
     * @param firstYCoord the y coordinate corresponding to the first row within the image
     */
    public void setFirstYCoord(int firstYCoord) {
        this.firstYCoord = firstYCoord;
    }

    /**
     * Gets the record height in pixels.
     *
     * @return the record height in pixels
     */
    public int getRecordHeight() {
        return recordHeight;
    }

    /**
     * Sets the record height in pixels.
     *
     * @param recordHeight the record height in pixels
     */
    public void setRecordHeight(int recordHeight) {
        this.recordHeight = recordHeight;
    }
}
