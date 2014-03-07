package server;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;
import shared.communication.GetProjects_Result;
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
        StringBuilder sb;

        GetProjects_Result result = new GetProjects_Result();
        int httpStatus = HttpURLConnection.HTTP_INTERNAL_ERROR;
        int length = 0;

        try {
            boolean validated = usersManager.validateUser(params.getUser().getUsername(), params.getUser().getPassword());
            if (validated) {
                List<Project> projects = projectsManager.getAllProjects();
                result.setProjects(projects);
            } else {
                result.setFailed(true);
            }

            httpStatus = HttpURLConnection.HTTP_OK;
        } catch (Exception e) {
            result.setFailed(true);
            httpStatus = HttpURLConnection.HTTP_INTERNAL_ERROR;
        } finally {
            exchange.sendResponseHeaders(httpStatus, length);
            xmlStream.toXML(result, exchange.getResponseBody());
            exchange.getResponseBody().close();
        }
    }
}
