package server;

import com.sun.net.httpserver.*;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;
import server.database.Database;
import shared.model.*;
import java.io.*;
import java.net.*;
import java.util.logging.*;

/**
 * Created with IntelliJ IDEA.
 * User: Steven
 * Date: 2/15/14
 * Time: 2:30 PM
 * To change this template use File | Settings | File Templates.
 */
public class Server {

    private static final int SERVER_PORT_NUMBER = 8081;
    private static final int MAX_WAITING_CONNECTIONS = 10;

    private HttpServer server;
    private XStream xmlStream = new XStream(new DomDriver());

    private Server() {
        return;
    }

    private void run() {
        try {
            Database.initialize();
        }
        catch (Exception e) {
            System.out.println("Could not initialize database: " + e.getMessage());
            e.printStackTrace();
            return;
        }

        try {
            server = HttpServer.create(new InetSocketAddress(SERVER_PORT_NUMBER),
                    MAX_WAITING_CONNECTIONS);
        }
        catch (IOException e) {
            System.out.println("Could not create HTTP server: " + e.getMessage());
            e.printStackTrace();
            return;
        }

        server.setExecutor(null); // use the default executor

        server.createContext("/DownloadBatch", new DownloadBatchHandler());
        server.createContext("/DownloadFile", new DownloadFileHandler());
        server.createContext("/GetFields", new GetFieldsHandler());
        server.createContext("/GetProjects", new GetProjectsHandler());
        server.createContext("/GetSampleImage", new GetSampleImageHandler());
        server.createContext("/Search", new SearchHandler());
        server.createContext("/SubmitBatch", new SubmitBatchHandler());
        server.createContext("/ValidateUser", new ValidateUserHandler());
        server.createContext("/", emptyHandler);

        server.start();
    }

    private HttpHandler emptyHandler = new HttpHandler() {

        @Override
        public void handle(HttpExchange exchange) throws IOException {
            System.out.println("GOT HERE");
        }
    };

    public static void main(String[] args) {
        new Server().run();
    }
}
