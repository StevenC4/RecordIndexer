package server;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;
import shared.communication.DownloadBatch_Params;
import shared.communication.Operation_Result;
import shared.model.*;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Steven
 * Date: 3/2/14
 * Time: 3:39 PM
 * To change this template use File | Settings | File Templates.
 */
public class DownloadBatchHandler implements HttpHandler {

    private XStream xmlStream = new XStream(new DomDriver());
    UsersManager usersManager = new UsersManager();
    ProjectsManager projectsManager = new ProjectsManager();
    BatchesManager batchesManager = new BatchesManager();
    FieldsManager fieldsManager = new FieldsManager();

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        DownloadBatch_Params params = (DownloadBatch_Params)xmlStream.fromXML(exchange.getRequestBody());
        Operation_Result result;
        StringBuilder sb;
        String resultString;

        try {
            boolean validated = usersManager.validateUser(params.getUser().getUsername(), params.getUser().getPassword());
            if (validated) {
                sb = new StringBuilder();

                // Does user have a batch assigned to them?
                if (usersManager.getCurrentBatch(params.getUser()) != -1) {
                    throw new Exception("User already has a batch checked out.");
                }

                Project project = projectsManager.getProjectById(params.getProjectId());
                Batch batch = batchesManager.getNextAvailableBatch(params.getProjectId());
                List<Field> fields = fieldsManager.getProjectFields(params.getProjectId());

                sb.append(batch.getBatchId()).append("\n");
                sb.append(project.getProjectId()).append("\n");
                sb.append(batch.getPath()).append("\n");
                sb.append(project.getFirstYCoord()).append("\n");
                sb.append(project.getRecordsPerImage()).append("\n");
                sb.append(fields.size()).append("\n");
                for (Field field : fields) {
                    sb.append(field.getFieldId()).append("\n");
                    sb.append(field.getPosition()).append("\n");
                    sb.append(field.getTitle()).append("\n");
                    sb.append(field.getHelpHTML()).append("\n");
                    sb.append(field.getxCoord()).append("\n");
                    sb.append(field.getWidth()).append("\n");
                    if (field.getKnownData() != null) {
                        sb.append(field.getKnownData()).append("\n");
                    }
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
