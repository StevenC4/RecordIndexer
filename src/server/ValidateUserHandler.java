package server;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;
import shared.communication.Operation_Result;
import shared.communication.ValidateUser_Params;
import shared.model.User;
import shared.model.UsersManager;

import java.io.IOException;
import java.net.HttpURLConnection;

/**
 * Created with IntelliJ IDEA.
 * User: Steven
 * Date: 2/27/14
 * Time: 5:34 PM
 * To change this template use File | Settings | File Templates.
 */
public class ValidateUserHandler implements HttpHandler {

    private XStream xmlStream = new XStream(new DomDriver());
    UsersManager usersManager = new UsersManager();

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        ValidateUser_Params params = (ValidateUser_Params)xmlStream.fromXML(exchange.getRequestBody());
        String resultString;

        Operation_Result result = null;
        int httpStatus = HttpURLConnection.HTTP_INTERNAL_ERROR;
        int statusInt = -1;

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
