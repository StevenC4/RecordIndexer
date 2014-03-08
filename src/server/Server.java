package server;

import com.sun.net.httpserver.*;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;
import server.database.Database;

import java.io.*;
import java.net.*;

/**
 * Created with IntelliJ IDEA.
 * User: Steven
 * Date: 2/15/14
 * Time: 2:30 PM
 * To change this template use File | Settings | File Templates.
 */
public class Server {

    private static int SERVER_PORT = 8081;
    private static final int MAX_WAITING_CONNECTIONS = 10;

    private HttpServer server;

    private Server() {
        return;
    }

    private Server(int port) {
        SERVER_PORT = port;
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
            server = HttpServer.create(new InetSocketAddress(SERVER_PORT),
                    MAX_WAITING_CONNECTIONS);
        }
        catch (IOException e) {
            System.out.println("Could not create HTTP server: " + e.getMessage());
            e.printStackTrace();
            return;
        }

        server.setExecutor(null); // use the default executor

        server.createContext("/DownloadBatch", new DownloadBatchHandler());
        server.createContext("/GetFields", new GetFieldsHandler());
        server.createContext("/GetProjects", new GetProjectsHandler());
        server.createContext("/GetSampleImage", new GetSampleImageHandler());
        server.createContext("/Search", new SearchHandler());
        server.createContext("/SubmitBatch", new SubmitBatchHandler());
        server.createContext("/ValidateUser", new ValidateUserHandler());
        server.createContext("/", new DownloadFileHandler());

        server.start();
    }

    public static Server main1(String[] args) {
        Server server;
        try {
            int port = Integer.parseInt(args[0]);
            server = new Server(port);
        } catch (Exception e) {
            server = new Server();
        }

        return server;
    }

    public static void main(String[] args) {
        Server server;
        try {
            int port = Integer.parseInt(args[0]);
            new Server(port).run();
        } catch (Exception e) {
            new Server().run();
        }
    }

    public void stop() {
        server.stop(1);
    }
}
