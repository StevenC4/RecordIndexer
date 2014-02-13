package shared.model;

/**
 * Created with IntelliJ IDEA.
 * User: Steven
 * Date: 2/11/14
 * Time: 3:19 PM
 * To change this template use File | Settings | File Templates.
 */
public class Field {

    private int fieldId;
    private int position;
    private int projectId;
    private int xCoord;
    private int width;
    private String helpHTML;
    private String knownData;

    /**
     * Instantiates a new Field.
     */
    public Field() {
        setFieldId(-1);
        setPosition(-1);
        setProjectId(-1);
        setxCoord(-1);
        setWidth(-1);
        setHelpHTML(null);
        setKnownData(null);
    }

    /**
     * Instantiates a new Field.
     *
     * @param fieldId the unique field id
     * @param position the column position of the field within the table
     * @param projectId the unique project id
     * @param xCoord the left-most x coordinate of the field within the image
     * @param width the width of the field within the image
     * @param helpHTML the HTML page containing the help instructions for the field
     * @param knownData the known data for the particular field
     */
    public Field(int fieldId, int position, int projectId, int xCoord, int width, String helpHTML, String knownData) {
        setFieldId(fieldId);
        setPosition(position);
        setProjectId(projectId);
        setxCoord(xCoord);
        setWidth(width);
        setHelpHTML(helpHTML);
        setKnownData(knownData);
    }

    /**
     * Gets the unique field id.
     *
     * @return the unique field id
     */
    public int getFieldId() {
        return fieldId;
    }

    /**
     * Sets the unique field id.
     *
     * @param fieldId the unique field id
     */
    public void setFieldId(int fieldId) {
        this.fieldId = fieldId;
    }

    /**
     * Gets the position of the field within the table.
     *
     * @return the position of the field within the table
     */
    public int getPosition() {
        return position;
    }

    /**
     * Sets the position of the field within the table.
     *
     * @param position the position of the field within the table
     */
    public void setPosition(int position) {
        this.position = position;
    }

    /**
     * Gets the project id.
     *
     * @return the project id
     */
    public int getProjectId() {
        return projectId;
    }

    /**
     * Sets the project id.
     *
     * @param projectId the project id
     */
    public void setProjectId(int projectId) {
        this.projectId = projectId;
    }

    /**
     * Gets the left-most x coordinate of the field within the image.
     *
     * @return the left-most x coordinate of the field within the image
     */
    public int getxCoord() {
        return xCoord;
    }

    /**
     * Sets the left-most x coordinate within the image.
     *
     * @param xCoord the left-most x coordinate within the image
     */
    public void setxCoord(int xCoord) {
        this.xCoord = xCoord;
    }

    /**
     * Gets the width of the field within the image.
     *
     * @return the width of the field within the image
     */
    public int getWidth() {
        return width;
    }

    /**
     * Sets the width of the field within the image.
     *
     * @param width the width of the field within the image
     */
    public void setWidth(int width) {
        this.width = width;
    }

    /**
     * Gets the HTML page containing the help instructions for the field.
     *
     * @return the HTML page containing the help instructions for the field
     */
    public String getHelpHTML() {
        return helpHTML;
    }

    /**
     * Sets the HTML page containing the help instructions for the field.
     *
     * @param helpHTML the HTML page containing the help instructions for the field
     */
    public void setHelpHTML(String helpHTML) {
        this.helpHTML = helpHTML;
    }

    /**
     * Gets the known data for the particular field.
     *
     * @return the known data for the particular field
     */
    public String getKnownData() {
        return knownData;
    }

    /**
     * Sets the known data for the particular field.
     *
     * @param knownData the known data for the particular field
     */
    public void setKnownData(String knownData) {
        this.knownData = knownData;
    }
}
