package view;

import client.communication.ClientCommunicator;
import shared.model.Batch;
import shared.model.Field;
import shared.model.Project;
import shared.model.User;

import java.awt.geom.Point2D;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Steven
 * Date: 3/21/14
 * Time: 12:29 PM
 * To change this template use File | Settings | File Templates.
 */
public class BatchState implements Serializable {

    transient ClientCommunicator clientCommunicator;

    User user;

    Project currentProject;  //TODO Constructor
    Batch currentBatch;      //TODO Constructor
    private List<Field> fields;           //TODO Constructor
    private Field selectedField;          //TODO Constructor
    private int selectedRecord;

    private Point2D coordinates;
    private float zoomScale;

    String[][] recordValues;
    List<BatchStateListener> listeners;

    public BatchState(User user) {
        clientCommunicator = new ClientCommunicator();
        this.user = user;
        listeners = new ArrayList<BatchStateListener>();

        if (coordinates == null) {
            coordinates = new Point2D.Double(0, 0);
            zoomScale = 1;
        }
    }

    public void addListener(BatchStateListener batchStateListener) {
        listeners.add(batchStateListener);
    }

    public User getUser() {
        return user;
    }

    public ClientCommunicator getClientCommunicator() {
        return clientCommunicator;
    }

    public void downloadBatch(Project project, Batch batch, List<Field> fields) {
        currentProject = project;
        currentBatch = batch;
        this.fields = fields;
        selectedField = fields.get(0);
        selectedRecord = 0;
        recordValues = new String[project.getRecordsPerImage()][fields.size()];

        for (int i = 0; i < currentProject.getRecordsPerImage(); i++) {
            for (int j = 0; j < fields.size(); j++) {
                recordValues[i][j] = "";
            }
        }

        notifyBatchDownloaded();
    }

    public void setCurrentProject(Project project) {
        currentProject = project;
    }

    public Project getCurrentProject() {
        return currentProject;
    }

    public Batch getCurrentBatch() {
        return currentBatch;
    }

    public List<Field> getCurrentFields() {
        return fields;
    }

    public void setSelectedField(Field field) {
        this.selectedField = field;
        notifyCellSelected();
    }

    public Field getSelectedField() {
        return selectedField;
    }

    public void setSelectedRecord(int record) {
        this.selectedRecord = record;
        notifyCellSelected();
    }

    public int getSelectedRecord() {
        return selectedRecord;
    }

    public void setSelectedCell(Field field, int record) {
        this.selectedField = field;
        this.selectedRecord = record;
        notifyCellSelected();
    }

    public void setCoordinates(Point2D coordinates) {
        this.coordinates = coordinates;
    }

    public Point2D getCoordinates() {
        return coordinates;
    }

    public void updateCell(String value, int row, int col) {
        recordValues[row][col] = value;
        notifyCellUpdated(value, row, col);
    }

    public String getCellContents(int row, int col) {
        return recordValues[row][col];
    }

    public float getZoomScale() {
        return zoomScale;
    }

    public void incrementZoom() {
        zoomScale /= .75;
        notifyZoomed();
    }

    public void decrementZoom() {
        zoomScale  *= .75;
        notifyZoomed();
    }

    private void notifyBatchDownloaded() {
        for (BatchStateListener listener : listeners) {
            listener.batchDownloaded();
        }
    }

    private void notifyCellSelected() {
        for (BatchStateListener listener : listeners) {
            listener.cellSelected();
        }
    }

    private void notifyZoomed() {
        for (BatchStateListener listener : listeners) {
            listener.imageZoomed();
        }
    }

    private void notifyCellUpdated(String value, int row, int col) {
        for (BatchStateListener listener : listeners) {
            listener.cellUpdated(value, row, col);
        }
    }

    private void notifyBatchSubmitted() {
        for (BatchStateListener listener : listeners) {
            listener.batchSubmitted();
        }
    }

    public interface BatchStateListener {
        void batchDownloaded();
        void cellSelected();
        void imageZoomed();
        void cellUpdated(String value, int row, int col);
        void batchSubmitted();
    }
}
