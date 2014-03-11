package server;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;
import shared.communication.Operation_Result;
import shared.communication.SubmitBatch_Params;
import shared.communication.SubmitBatch_Result;
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
 * Time: 8:19 PM
 * To change this template use File | Settings | File Templates.
 */
public class SubmitBatchHandler implements HttpHandler {

    private XStream xmlStream = new XStream(new DomDriver());
    UsersManager usersManager = new UsersManager();
    FieldsManager fieldsManager = new FieldsManager();
    BatchesManager batchesManager = new BatchesManager();
    ValuesManager valuesManager = new ValuesManager();

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        SubmitBatch_Params params = (SubmitBatch_Params)xmlStream.fromXML(exchange.getRequestBody());
        SubmitBatch_Result result = new SubmitBatch_Result();

        int httpStatus = HttpURLConnection.HTTP_OK;
        int length = 0;

        try {
            boolean validated = usersManager.validateUser(params.getUser().getUsername(), params.getUser().getPassword());
            if (validated) {

                int batchId = params.getBatchId();
                User user = params.getUser();
                if (usersManager.getUserByUsername(user.getUsername()).getCurrentBatch() != batchId) {
                    throw new Exception("The batch ID inputted does not match the batch ID checked out to the user");
                }
                int projectId = batchesManager.getBatchByBatchId(batchId).getProjectId();
                int recordId = valuesManager.getNextRecordId();
                List<Field> fields = fieldsManager.getProjectFields(projectId);
                List<Value> values = new ArrayList<Value>();
                Batch batch = batchesManager.getBatchByBatchId(batchId);


                int recordNum = 1;
                String[] recordArray = params.getFieldValues().split(";");
                for (int i = 0; i < recordArray.length; i++) {
                    String[] valuesArray = recordArray[i].split(",", -1);
                    for (int j = 0; j < fields.size(); j++) {
                        values.add(new Value(-1, projectId, fields.get(j).getFieldId(), batchId, recordId, recordNum, valuesArray[j]));
                    }
                    recordId++;
                    recordNum++;
                }

                user = usersManager.getUserByUsername(user.getUsername());
                user.setIndexedRecords(user.getIndexedRecords() + recordArray.length);
                user.setCurrentBatch(-1);

                batch.setStatus("submitted");

                valuesManager.addList(values);
                usersManager.updateUser(user);
                batchesManager.updateBatch(batch);
            } else {
                result.setFailed(true);
            }

//            httpStatus = HttpURLConnection.HTTP_OK;
        } catch (Exception e) {
//            httpStatus = HttpURLConnection.HTTP_INTERNAL_ERROR;
            result.setFailed(true);
        } finally {
            exchange.sendResponseHeaders(httpStatus, length);
            xmlStream.toXML(result, exchange.getResponseBody());
            exchange.getResponseBody().close();
        }
    }
}
