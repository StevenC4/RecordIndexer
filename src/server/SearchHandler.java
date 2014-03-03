package server;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;
import shared.communication.Operation_Result;
import shared.communication.Search_Params;
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
        Operation_Result result;
        StringBuilder sb;
        String resultString;
        try {
            boolean validated = usersManager.validateUser(params.getUser().getUsername(), params.getUser().getPassword());
            if (validated) {
                sb = new StringBuilder();

                List<Value> values = valuesManager.searchValues(params.getFields(), params.getSearchValues());
                List<String> paths = new ArrayList<String>();
                for (int i = 0; i < values.size(); i++) {
                    paths.add(batchesManager.getPathByBatchId(values.get(i).getBatchId()));
                }

                for (int i = 0; i < values.size(); i++) {
                    sb.append(values.get(i).getBatchId()).append("\n");
                    sb.append(paths.get(i)).append("\n");
                    sb.append(values.get(i).getRecordId()).append("\n");
                    sb.append(values.get(i).getFieldId()).append("\n");
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
