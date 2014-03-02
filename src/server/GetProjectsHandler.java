package server;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;
import shared.communication.Operation_Result;
import shared.communication.ValidateUser_Params;
import shared.model.Project;
import shared.model.ProjectsManager;
import shared.model.UsersManager;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Steven
 * Date: 3/2/14
 * Time: 2:51 PM
 * To change this template use File | Settings | File Templates.
 */
public class GetProjectsHandler implements HttpHandler {

    private XStream xmlStream = new XStream(new DomDriver());
    UsersManager usersManager = new UsersManager();
    ProjectsManager projectsManager = new ProjectsManager();

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        ValidateUser_Params params = (ValidateUser_Params)xmlStream.fromXML(exchange.getRequestBody());
        Operation_Result result = null;
        String resultString;
        try {
            boolean validated = usersManager.validateUser(params.getUser().getUsername(), params.getUser().getPassword());
            if (validated) {
                List<Project> projects = projectsManager.getAllProjects();

                resultString = "";
                for (int i = 0; i < projects.size(); i++) {
                    resultString += projects.get(i).getProjectId() + "\n";
                    resultString += projects.get(i).getTitle() + "\n";
                }
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
