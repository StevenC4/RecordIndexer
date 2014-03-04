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
 * Date: 3/4/14
 * Time: 3:26 PM
 * To change this template use File | Settings | File Templates.
 */
public class DownloadFileHandler implements HttpHandler {

    // Process DeleteField request
    // 1. Deserialize the request object from the request body
    // 2. Extract the field to be deleted from the request object
    // 3. Call the model to delete the field

    private XStream xmlStream = new XStream(new DomDriver());

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        ValidateUser_Params params = (ValidateUser_Params)xmlStream.fromXML(exchange.getRequestBody());
        StringBuilder sb;
        String resultString;

        Operation_Result result = null;
        int httpStatus = HttpURLConnection.HTTP_INTERNAL_ERROR;
        int statusInt = -1;

        try {
            boolean validated = false;
            if (validated) {
                List<Project> projects = null;
                sb = new StringBuilder();

                for (int i = 0; i < projects.size(); i++) {
                    sb.append(projects.get(i).getProjectId()).append("\n");
                    sb.append(projects.get(i).getTitle()).append("\n");
                }
                resultString = sb.toString();
            } else {
                resultString = "FAILED\n";
            }
            result = new Operation_Result(resultString);
            httpStatus = HttpURLConnection.HTTP_OK;
            statusInt = 0;
        } catch (Exception e) {
            result = new Operation_Result("FAILED\n");
            httpStatus = HttpURLConnection.HTTP_INTERNAL_ERROR;
            statusInt = -1;
        } finally {
            exchange.sendResponseHeaders(httpStatus, statusInt);
            xmlStream.toXML(result, exchange.getResponseBody());
            exchange.getResponseBody().close();
        }
    }
}
