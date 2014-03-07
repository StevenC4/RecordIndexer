package server;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;
import shared.communication.Operation_Result;
import shared.communication.Search_Params;
import shared.communication.Search_Result;
import shared.communication.ValidateUser_Params;
import shared.model.BatchesManager;
import shared.model.UsersManager;
import shared.model.Value;
import shared.model.ValuesManager;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Steven
 * Date: 3/3/14
 * Time: 12:18 AM
 * To change this template use File | Settings | File Templates.
 */
public class SearchHandler implements HttpHandler {

    private XStream xmlStream = new XStream(new DomDriver());
    UsersManager usersManager = new UsersManager();
    BatchesManager batchesManager = new BatchesManager();
    ValuesManager valuesManager = new ValuesManager();

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        Search_Params params = (Search_Params)xmlStream.fromXML(exchange.getRequestBody());
        StringBuilder sb;
        Search_Result result = new Search_Result();

        int httpStatus = HttpURLConnection.HTTP_OK;
        int length = 0;

        try {
            boolean validated = usersManager.validateUser(params.getUser().getUsername(), params.getUser().getPassword());
            if (validated) {
                List<Value> values = valuesManager.searchValues(params.getFields(), params.getSearchValues());
                List<String> paths = new ArrayList<String>();
                for (int i = 0; i < values.size(); i++) {
                    paths.add(batchesManager.getBatchByBatchId(values.get(i).getBatchId()).getPath());
                }

                result.setValues(values);
                result.setPaths(paths);
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
