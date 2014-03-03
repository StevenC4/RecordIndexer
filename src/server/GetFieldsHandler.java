package server;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;
import shared.communication.GetFields_Params;
import shared.communication.Operation_Result;
import shared.communication.ValidateUser_Params;
import shared.model.UsersManager;

import java.io.IOException;
import java.net.HttpURLConnection;

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

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        GetFields_Params params = (GetFields_Params)xmlStream.fromXML(exchange.getRequestBody());
        Operation_Result result;
        String resultString;
        try {
            boolean validated = usersManager.validateUser(params.getUser().getUsername(), params.getUser().getPassword());
            if (validated) {
                resultString = "TRUE\n" +
                        params.getUser().getFirstName() + "\n" +
                        params.getUser().getLastName() + "\n" +
                        params.getUser().getIndexedRecords() + "\n";
            } else {
                resultString = "FALSE\n";
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
