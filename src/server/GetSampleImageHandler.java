package server;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;
import shared.communication.GetSampleImage_Params;
import shared.communication.Operation_Result;
import shared.model.BatchesManager;
import shared.model.UsersManager;

import java.io.IOException;
import java.net.HttpURLConnection;

/**
 * Created with IntelliJ IDEA.
 * User: Steven
 * Date: 3/2/14
 * Time: 3:12 PM
 * To change this template use File | Settings | File Templates.
 */
public class GetSampleImageHandler implements HttpHandler {

    private XStream xmlStream = new XStream(new DomDriver());
    UsersManager usersManager = new UsersManager();
    BatchesManager batchesManager = new BatchesManager();

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        GetSampleImage_Params params = (GetSampleImage_Params)xmlStream.fromXML(exchange.getRequestBody());
        Operation_Result result;
        String resultString;
        try {
            boolean validated = usersManager.validateUser(params.getUser().getUsername(), params.getUser().getPassword());
            if (validated) {
                String path = batchesManager.getSampleImage(params.getProjectId());
                resultString = path + "\n";
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
