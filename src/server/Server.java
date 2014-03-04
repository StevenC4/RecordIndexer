package server;

import com.sun.net.httpserver.*;
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

    private static final int SERVER_PORT_NUMBER = 8080;
    private static final int MAX_WAITING_CONNECTIONS = 10;

    private static Logger logger;

    static {
        try {
            initLog();
        }
        catch (IOException e) {
            System.out.println("Could not initialize log: " + e.getMessage());
        }
    }

    private static void initLog() throws IOException {

        Level logLevel = Level.FINE;

        logger = Logger.getLogger("recordindexer");
        logger.setLevel(logLevel);
        logger.setUseParentHandlers(false);

        Handler consoleHandler = new ConsoleHandler();
        consoleHandler.setLevel(logLevel);
        consoleHandler.setFormatter(new SimpleFormatter());
        logger.addHandler(consoleHandler);

        FileHandler fileHandler = new FileHandler("log.txt", false);
        fileHandler.setLevel(logLevel);
        fileHandler.setFormatter(new SimpleFormatter());
        logger.addHandler(fileHandler);
    }

    private HttpServer server;

    private Server() {
        return;
    }

    private void run() {

        logger.info("Initializing Model");

        try {
            BatchesManager.initialize();
            FieldsManager.initialize();
            ProjectsManager.initialize();
            UsersManager.initialize();
            ValuesManager.initialize();
        }
        catch (ModelException e) {
            logger.log(Level.SEVERE, e.getMessage(), e);
            return;
        }

        logger.info("Initializing HTTP Server");

        try {
            server = HttpServer.create(new InetSocketAddress(SERVER_PORT_NUMBER),
                    MAX_WAITING_CONNECTIONS);
        }
        catch (IOException e) {
            logger.log(Level.SEVERE, e.getMessage(), e);
            return;
        }

        server.setExecutor(null); // use the default executor

        server.createContext("/ValidateUser", new ValidateUserHandler());
        server.createContext("/GetProjects", new GetProjectsHandler());
        server.createContext("/GetSampleImage", new GetProjectsHandler());
        server.createContext("/DownloadBatch", new DownloadBatchHandler());
        server.createContext("/SubmitBatch", new SubmitBatchHandler());
        server.createContext("/GetFields", new GetFieldsHandler());
        server.createContext("/Search", new SearchHandler());
        server.createContext("/DownloadFile", new DownloadFileHandler());

        logger.info("Starting HTTP Server");

        server.start();
    }

    public static void main(String[] args) {
        new Server().run();
    }
}
