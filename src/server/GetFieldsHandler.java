package server;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;
import shared.communication.GetFields_Params;
import shared.communication.GetFields_Result;
import shared.communication.Operation_Result;
import shared.communication.ValidateUser_Params;
import shared.model.*;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Steven
 * Date: 3/2/14
 * Time: 9:50 PM
 * To change this template use File | Settings | File Templates.
 */
public class GetFieldsHandler implements HttpHandler {

    private XStream xmlStream = new XStream(new DomDriver());
    UsersManager usersManager = new UsersManager();
    ProjectsManager projectsManager = new ProjectsManager();
    FieldsManager fieldsManager = new FieldsManager();

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        GetFields_Params params = (GetFields_Params)xmlStream.fromXML(exchange.getRequestBody());
        StringBuilder sb;
        String resultString;

        int httpStatus = HttpURLConnection.HTTP_OK;
        int length = 0;
        GetFields_Result result = new GetFields_Result();

        try {
            boolean validated = usersManager.validateUser(params.getUser().getUsername(), params.getUser().getPassword());
            if (validated) {
                List<Field> fields;

                Integer projectId;
                String projectIdString = (String) params.getProjectId();

                try {
                    projectId = Integer.parseInt(projectIdString);
                } catch (Exception e) {
                    if (projectIdString.equals("")) {
                        projectId = null;
                    } else {
                        throw new Exception("Project ID is not a number or a valid input");
                    }
                }

                if (projectId != null) {
                    fields = fieldsManager.getProjectFields(projectId);
                    if (fields.size() == 0) {
                        throw new Exception("There project ID provided is not valid");
                    }
                } else {
                    List<Project> projects = projectsManager.getAllProjects();
                    fields = new ArrayList<Field>();
                    for (Project project : projects) {
                        fields.addAll(fieldsManager.getProjectFields(project.getProjectId()));
                    }
                }

                result.setFields(fields);
            } else {
                result.setFailed(true);
            }

//            httpStatus = HttpURLConnection.HTTP_OK;
        } catch (Exception e) {
            result.setFailed(true);
//            httpStatus = HttpURLConnection.HTTP_INTERNAL_ERROR;
        } finally {
            exchange.sendResponseHeaders(httpStatus, length);
            xmlStream.toXML(result, exchange.getResponseBody());
            exchange.getResponseBody().close();
        }
    }
}
