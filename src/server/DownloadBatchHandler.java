package server;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;
import shared.communication.DownloadBatch_Params;
import shared.communication.DownloadBatch_Result;
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

        int httpStatus = HttpURLConnection.HTTP_INTERNAL_ERROR;
        int length = 0;
        DownloadBatch_Result result = new DownloadBatch_Result();

        StringBuilder sb;
        String resultString;

        try {
            boolean validated = usersManager.validateUser(params.getUser().getUsername(), params.getUser().getPassword());

            if (validated) {
                sb = new StringBuilder();

                if (usersManager.getCurrentBatch(params.getUser()) != -1) {
                    throw new Exception("User already has a batch checked out.");
                }

                Project project = projectsManager.getProjectById(params.getProjectId());
                Batch batch = batchesManager.getNextAvailableBatch(params.getProjectId());
                List<Field> fields = fieldsManager.getProjectFields(params.getProjectId());

                batch.setStatus("checked out");
                batchesManager.updateBatch(batch);

                User user = usersManager.getUserByUsername(params.getUser().getUsername());
                user.setCurrentBatch(batch.getBatchId());
                usersManager.updateUser(user);

                result.setProject(project);
                result.setBatch(batch);
                result.setFields(fields);
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