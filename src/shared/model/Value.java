package shared.model;

/**
 * Created with IntelliJ IDEA.
 * User: Steven
 * Date: 2/11/14
 * Time: 3:20 PM
 * To change this template use File | Settings | File Templates.
 */
public class Value {

    /**
     * The unique ID of the value
     */
    private int valueId;

    /**
     * The ID of the project to which the value corresponds
     */
    private int projectId;

    /**
     * The ID of the field the to which the value corresponds
     */
    private int fieldId;

    private int recordId;

    /**
     * The ID of the batch to which the value corresponds
     */
    private int batchId;

    private int recordNum;

    /**
     * The actual value
     */
    private String value;

    /**
     * Instantiates a new Value with default values.
     */
    public Value() {
        setValueId(-1);
        setProjectId(-1);
        setFieldId(-1);
        setBatchId(-1);
        setRecordId(-1);
        setRecordNum(-1);
        setValue(null);
    }

    /**
     * Instantiates a new Value.
     *
     * @param valueId the unique value id
     * @param projectId the project id
     * @param fieldId the field id
     * @param batchId the batch id
     * @param value the value of the field
     */
    public Value(int valueId, int projectId, int fieldId, int batchId, int recordId, int recordNum, String value) {
        setValueId(valueId);
        setProjectId(projectId);
        setFieldId(fieldId);
        setBatchId(batchId);
        setRecordId(recordId);
        setRecordNum(recordNum);
        setValue(value);
    }


    public int getRecordNum() {
        return recordNum;
    }

    public void setRecordNum(int recordNum) {
        this.recordNum = recordNum;
    }

    public int getRecordId() {
        return recordId;
    }

    public void setRecordId(int recordId) {
        this.recordId = recordId;
    }

    /**
     * Gets the unique value id.
     *
     * @return the unique value id
     */
    public int getValueId() {
        return valueId;
    }

    /**
     * Sets the unique value id.
     *
     * @param valueId the unique value id
     */
    public void setValueId(int valueId) {
        this.valueId = valueId;
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
     * Gets the field id.
     *
     * @return the field id
     */
    public int getFieldId() {
        return fieldId;
    }

    /**
     * Sets the field id.
     *
     * @param fieldId the field id
     */
    public void setFieldId(int fieldId) {
        this.fieldId = fieldId;
    }

    /**
     * Gets the batch id.
     *
     * @return the batch id
     */
    public int getBatchId() {
        return batchId;
    }

    /**
     * Sets the batch id.
     *
     * @param batchId the batch id
     */
    public void setBatchId(int batchId) {
        this.batchId = batchId;
    }

    /**
     * Gets the value of the field.
     *
     * @return the value of the field
     */
    public String getValue() {
        return value;
    }

    /**
     * Sets the value of the field.
     *
     * @param value the value of the field
     */
    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Value value1 = (Value) o;

        if (batchId != value1.batchId) return false;
        if (fieldId != value1.fieldId) return false;
        if (projectId != value1.projectId) return false;
        if (recordId != value1.recordId) return false;
        if (valueId != value1.valueId) return false;
        if (!value.toLowerCase().equals(value1.value.toLowerCase())) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = valueId;
        result = 31 * result + projectId;
        result = 31 * result + fieldId;
        result = 31 * result + recordId;
        result = 31 * result + batchId;
        result = 31 * result + value.toLowerCase().hashCode();
        return result;
    }
}
