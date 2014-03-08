package server;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

import java.io.File;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

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
        int httpStatus = HttpURLConnection.HTTP_OK;
        int length = 0;
        byte[] bytes = new byte[0];
        try {
            URI uri = exchange.getRequestURI();
            File file = new File("project_data" + File.separator + "Records" + File.separator + uri.getPath());
            bytes = Files.readAllBytes(Paths.get(file.getPath()));
        } catch (Exception e) {
            httpStatus = HttpURLConnection.HTTP_INTERNAL_ERROR;
            length = -1;
        } finally {
            exchange.sendResponseHeaders(httpStatus, length);
            exchange.getResponseBody().write(bytes);
            exchange.getResponseBody().close();
        }

    }
}
