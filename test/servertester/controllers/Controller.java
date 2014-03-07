package servertester.controllers;

import java.util.*;

import client.communication.ClientCommunicator;
import servertester.views.*;
import shared.communication.*;
import shared.model.User;

public class Controller implements IController {

	private IView _view;
    ClientCommunicator clientCommunicator;
	
	public Controller() {
        clientCommunicator = new ClientCommunicator();
		return;
	}
	
	public IView getView() {
		return _view;
	}
	
	public void setView(IView value) {
		_view = value;
	}
	
	// IController methods
	//
	
	@Override
	public void initialize() {
		getView().setHost("localhost");
//		getView().setPort("39640");
		getView().setPort("8081");
		operationSelected();
	}

	@Override
	public void operationSelected() {
		ArrayList<String> paramNames = new ArrayList<String>();
		paramNames.add("User");
		paramNames.add("Password");
		
		switch (getView().getOperation()) {
		case VALIDATE_USER:
			break;
		case GET_PROJECTS:
			break;
		case GET_SAMPLE_IMAGE:
			paramNames.add("Project");
			break;
		case DOWNLOAD_BATCH:
			paramNames.add("Project");
			break;
		case GET_FIELDS:
			paramNames.add("Project");
			break;
		case SUBMIT_BATCH:
			paramNames.add("Batch");
			paramNames.add("Record Values");
			break;
		case SEARCH:
			paramNames.add("Fields");
			paramNames.add("Search Values");
			break;
		default:
			assert false;
			break;
		}
		
		getView().setRequest("");
		getView().setResponse("");
		getView().setParameterNames(paramNames.toArray(new String[paramNames.size()]));
	}

	@Override
	public void executeOperation() {
		switch (getView().getOperation()) {
		case VALIDATE_USER:
			validateUser();
			break;
		case GET_PROJECTS:
			getProjects();
			break;
		case GET_SAMPLE_IMAGE:
			getSampleImage();
			break;
		case DOWNLOAD_BATCH:
			downloadBatch();
			break;
		case GET_FIELDS:
			getFields();
			break;
		case SUBMIT_BATCH:
			submitBatch();
			break;
		case SEARCH:
			search();
			break;
		default:
			assert false;
			break;
		}
	}
	
	private void validateUser() {
        ValidateUser_Params param = new ValidateUser_Params();
        User user = new User();
        user.setUsername(getView().getParameterValues()[0]);
        user.setPassword(getView().getParameterValues()[1]);
        param.setUser(user);
        getView().setRequest("Validate User:\nUsername: " + user.getUsername() + "\nPassword: " + user.getPassword());
        try {
            ValidateUser_Result result = clientCommunicator.ValidateUser(param);
            getView().setResponse(result.toString());
        } catch (Exception e) {}
	}
	
	private void getProjects() {
        ValidateUser_Params param = new ValidateUser_Params();
        User user = new User();
        user.setUsername(getView().getParameterValues()[0]);
        user.setPassword(getView().getParameterValues()[1]);
        getView().setRequest("Get Project:\nUsername: " + user.getUsername() + "\nPassword: " + user.getPassword());
        param.setUser(user);
        try {
            GetProjects_Result result = clientCommunicator.GetProjects(param);
            getView().setResponse(result.toString());
        } catch (Exception e) {}
	}
	
	private void getSampleImage() {
        GetSampleImage_Params params = new GetSampleImage_Params();
        User user = new User();
        user.setUsername(getView().getParameterValues()[0]);
        user.setPassword(getView().getParameterValues()[1]);
        params.setUser(user);
        params.setProjectId(Integer.parseInt(getView().getParameterValues()[2].trim()));
        try {
            GetSampleImage_Result result = clientCommunicator.GetSampleImage(params);
            getView().setResponse(result.toString());
        } catch (Exception e) {}
	}
	
	private void downloadBatch() {
        DownloadBatch_Params params = new DownloadBatch_Params();
        User user = new User();
        user.setUsername(getView().getParameterValues()[0]);
        user.setPassword(getView().getParameterValues()[1]);
        params.setUser(user);
        params.setProjectId(Integer.parseInt(getView().getParameterValues()[2].trim()));
        try {
            DownloadBatch_Result result = clientCommunicator.DownloadBatch(params);
            getView().setResponse(result.toString());
        } catch (Exception e) {}
	}
	
	private void getFields() {
        GetFields_Params params = new GetFields_Params();
        User user = new User();
        user.setUsername(getView().getParameterValues()[0]);
        user.setPassword(getView().getParameterValues()[1]);
        params.setUser(user);
        params.setProjectId(Integer.parseInt(getView().getParameterValues()[2].trim()));
        try {
            GetFields_Result result = clientCommunicator.GetFields(params);
            getView().setResponse(result.toString());
        } catch (Exception e) {}
	}
	
	private void submitBatch() {
        SubmitBatch_Params params = new SubmitBatch_Params();
        User user = new User();
        user.setUsername(getView().getParameterValues()[0]);
        user.setPassword(getView().getParameterValues()[1]);
        params.setUser(user);
        params.setBatchId(Integer.parseInt(getView().getParameterValues()[2].trim()));
        params.setFieldValues(getView().getParameterValues()[3]);
        try {
            SubmitBatch_Result result = clientCommunicator.SubmitBatch(params);
            getView().setResponse(result.toString());
        } catch (Exception e) {}
	}
	
	private void search() {
        Search_Params params = new Search_Params();
        User user = new User();
        user.setUsername(getView().getParameterValues()[0]);
        user.setPassword(getView().getParameterValues()[1]);
        params.setUser(user);
        params.setFields(getView().getParameterValues()[2]);
        params.setSearchValues(getView().getParameterValues()[3]);
        try {
            Search_Result result = clientCommunicator.Search(params);
            getView().setResponse(result.toString());
        } catch (Exception e) {}
	}
}

