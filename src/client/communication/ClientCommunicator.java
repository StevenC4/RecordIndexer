package client.communication;

import client.ClientException;
import shared.communication.*;

/**
 * Created with IntelliJ IDEA.
 * User: Steven
 * Date: 2/15/14
 * Time: 1:56 PM
 * To change this template use File | Settings | File Templates.
 */
public class ClientCommunicator {

    public ClientCommunicator() {
    }

    public GetAllBatches_Result getAllBatches() throws ClientException {
        return (GetAllBatches_Result)doGet("/GetAllBatches");
    }

    public void addBatch(AddBatch_Params params) throws ClientException {
        doPost("/AddBatch", params);
    }

    public void updateBatch(UpdateBatch_Params params) throws ClientException {
        doPost("/UpdateBatch", params);
    }

    public void deleteBatch(DeleteBatch_Params params) throws ClientException {
        doPost("/DeleteBatch", params);
    }

    public GetAllFields_Result getAllFields() throws ClientException {
        return (GetAllFields_Result)doGet("/GetAllFields");
    }

    public void addField(AddField_Params params) throws ClientException {
        doPost("/AddField", params);
    }

    public void updateField(UpdateField_Params params) throws ClientException {
        doPost("/UpdateField", params);
    }

    public void deleteField(DeleteField_Params params) throws ClientException {
        doPost("/DeleteField", params);
    }

    public GetAllProjects_Result getAllProjects() throws ClientException {
        return (GetAllProjects_Result)doGet("/GetAllProjects");
    }

    public void addProject(AddProject_Params params) throws ClientException {
        doPost("/AddProject", params);
    }

    public void updateProject(UpdateProject_Params params) throws ClientException {
        doPost("/UpdateProject", params);
    }

    public void deleteProject(DeleteProject_Params params) throws ClientException {
        doPost("/DeleteProject", params);
    }

    public GetAllUsers_Result getAllUsers() throws ClientException {
        return (GetAllUsers_Result)doGet("/GetAllUsers");
    }

    public void addUser(AddUser_Params params) throws ClientException {
        doPost("/AddUser", params);
    }

    public void updateUser(UpdateUser_Params params) throws ClientException {
        doPost("/UpdateUser", params);
    }

    public void deleteUser(DeleteUser_Params params) throws ClientException {
        doPost("/DeleteUser", params);
    }

    public GetAllValues_Result getAllValues() throws ClientException {
        return (GetAllValues_Result)doGet("/GetAllValues");
    }

    public void addValue(AddValue_Params params) throws ClientException {
        doPost("/AddValue", params);
    }

    public void updateValue(UpdateValue_Params params) throws ClientException {
        doPost("/UpdateValue", params);
    }

    public void deleteValue(DeleteValue_Params params) throws ClientException {
        doPost("/DeleteValue", params);
    }

    private Object doGet(String urlPath) throws ClientException {
        // Make HTTP GET request to the specified URL,
        // and return the object returned by the server
        return null;
    }

    private void doPost(String urlPath, Object postData) throws ClientException {
        // Make HTTP POST request to the specified URL,
        // passing in the specified postData object
    }

}
