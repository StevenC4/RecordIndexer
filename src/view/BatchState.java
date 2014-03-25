package view;

import client.communication.ClientCommunicator;
import shared.model.Batch;
import shared.model.Project;
import shared.model.User;

import java.io.Serializable;

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
    Project currentProject;
    Batch currentBatch;

    public BatchState() {
        clientCommunicator = new ClientCommunicator();
        user = null;
    }

    public BatchState(User user) {
        clientCommunicator = new ClientCommunicator();
        this.user = user;
    }

    public BatchState(ClientCommunicator clientCommunicator, User user) {
        this.clientCommunicator = clientCommunicator;
        this.user = user;
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
    }

    public Batch getCurrentBatch() {
        return currentBatch;
    }

    interface BatchStateListener {

    }
}
