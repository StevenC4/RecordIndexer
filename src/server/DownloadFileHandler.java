package server;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;
import shared.communication.DownloadFile_Params;
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
        DownloadFile_Params params = (DownloadFile_Params)xmlStream.fromXML(exchange.getRequestBody());
        String resultString;

        Operation_Result result = null;
        int httpStatus = HttpURLConnection.HTTP_INTERNAL_ERROR;
        int length = 0;

        try {
                resultString = "True";



            result = new Operation_Result(resultString);
            httpStatus = HttpURLConnection.HTTP_OK;
        } catch (Exception e) {
            result = new Operation_Result("FAILED\n");
            httpStatus = HttpURLConnection.HTTP_INTERNAL_ERROR;
        } finally {
            exchange.sendResponseHeaders(httpStatus, length);
            xmlStream.toXML(result, exchange.getResponseBody());
            exchange.getResponseBody().close();
        }
    }
}
