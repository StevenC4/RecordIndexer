package view;

import client.communication.ClientCommunicator;
import shared.model.Batch;
import shared.model.Field;
import shared.model.Project;
import shared.model.User;

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

    List<BatchStateListener> listeners;

    public BatchState() {
        clientCommunicator = new ClientCommunicator();
        user = null;
        listeners = new ArrayList<BatchStateListener>();
    }

    public BatchState(User user) {
        clientCommunicator = new ClientCommunicator();
        this.user = user;
        listeners = new ArrayList<BatchStateListener>();
    }

    public BatchState(ClientCommunicator clientCommunicator, User user) {
        this.clientCommunicator = clientCommunicator;
        this.user = user;
        listeners = new ArrayList<BatchStateListener>();
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

    public void setCurrentProject(Project project) {
        currentProject = project;
    }

    public Project getCurrentProject() {
        return currentProject;
    }

    public void setCurrentBatch(Batch batch) {
        this.currentBatch = batch;
        notifyBatchDownloaded();
    }

    public Batch getCurrentBatch() {
        return currentBatch;
    }

    public void setCurrentFields(List<Field> fields) {
        this.fields = fields;
    }

    public List<Field> getCurrentFields() {
        return fields;
    }

    public void setSelectedField(Field field) {
        this.selectedField = field;
        notifyFieldSelected();
    }

    public Field getSelectedField() {
        return selectedField;
    }

    public void notifyBatchDownloaded() {
        for (BatchStateListener listener : listeners) {
            listener.batchDownloaded();
        }
    }

    public void notifyFieldSelected() {
        for (BatchStateListener listener : listeners) {
            listener.fieldSelected();
        }
    }

    public void notifyRecordSelected() {
        for (BatchStateListener listener : listeners) {
            listener.recordSelected();
        }
    }

    public interface BatchStateListener {
        void batchDownloaded();
        void fieldSelected();
        void recordSelected();
        void batchSubmitted();
    }
}
