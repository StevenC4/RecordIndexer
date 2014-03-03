package server;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;
import shared.communication.GetFields_Params;
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
        Operation_Result result;
        StringBuilder sb;
        String resultString;
        try {
            boolean validated = usersManager.validateUser(params.getUser().getUsername(), params.getUser().getPassword());
            if (validated) {
                List<Field> fields;
                sb = new StringBuilder();


                int projectId = params.getProjectId();
                if (projectId != -1) {
                    fields = fieldsManager.getProjectFields(projectId);
                } else {
                    List<Project> projects = projectsManager.getAllProjects();
                    fields = new ArrayList<Field>();
                    for (Project project : projects) {
                        fields.addAll(fieldsManager.getProjectFields(project.getProjectId()));
                    }
                }

                for (Field field : fields) {
                    sb.append(field.getProjectId()).append("\n");
                    sb.append(field.getFieldId()).append("\n");
                    sb.append(field.getTitle()).append("\n");
                }

                resultString = sb.toString();
            } else {
                resultString = "FAILED\n";
            }
            result = new Operation_Result(resultString);
        } catch (Exception e) {
            exchange.sendResponseHeaders(HttpURLConnection.HTTP_INTERNAL_ERROR, -1);
            result = new Operation_Result("FAILED\n");
            xmlStream.toXML(result, exchange.getResponseBody());
            exchange.getResponseBody().close();
        }
        exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
        xmlStream.toXML(result, exchange.getResponseBody());
        exchange.getResponseBody().close();
    }
}
