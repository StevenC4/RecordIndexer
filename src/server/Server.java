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

        server.createContext("/ValidateUser", validateUserHandler);
        server.createContext("/GetProjects", getProjectsHandler);
        server.createContext("/GetSampleImage", getSampleImageHandler);
        server.createContext("/DownloadBatch", downloadBatchHandler);
        server.createContext("/SubmitBatch", submitBatchHandler);
        server.createContext("/GetFields", getFieldsHandler);
        server.createContext("/Search", searchHandler);
        server.createContext("/DownloadFile", downloadFileHandler);

        logger.info("Starting HTTP Server");

        server.start();
    }

    private HttpHandler validateUserHandler = new ValidateUserHandler();
    /*HttpHandler() {

        @Override
        public void handle(HttpExchange exchange) throws IOException {

            // Process GetAllBatches request
            // 1. Call model to get a list of all batches
            // 2. Create a result object
            // 3. Populate it with the list of batches
            // 4. Serialize the result object
            // 5. Return the serialized result object in the response body

        }
    };
*/
    private HttpHandler getProjectsHandler = new HttpHandler() {

        @Override
        public void handle(HttpExchange exchange) throws IOException {

            // Process AddBatch request
            // 1. Deserialize the request object from the request body
            // 2. Extract the new batch object from the request object
            // 3. Call the model to add the new batch

        }
    };

    private HttpHandler getSampleImageHandler = new HttpHandler() {

        @Override
        public void handle(HttpExchange exchange) throws IOException {

            // Process UpdateBatch request
            // 1. Deserialize the request object from the request body
            // 2. Extract the batch to be updated from the request object
            // 3. Call the model to update the batch

        }
    };

    private HttpHandler downloadBatchHandler = new HttpHandler() {

        @Override
        public void handle(HttpExchange exchange) throws IOException {

            // Process DeleteBatch request
            // 1. Deserialize the request object from the request body
            // 2. Extract the batch to be deleted from the request object
            // 3. Call the model to delete the batch

        }
    };

    private HttpHandler submitBatchHandler = new HttpHandler() {

        @Override
        public void handle(HttpExchange exchange) throws IOException {

            // Process GetAllFields request
            // 1. Call model to get a list of all fields
            // 2. Create a result object
            // 3. Populate it with the list of fields
            // 4. Serialize the result object
            // 5. Return the serialized result object in the response body

        }
    };

    private HttpHandler getFieldsHandler = new HttpHandler() {

        @Override
        public void handle(HttpExchange exchange) throws IOException {

            // Process AddField request
            // 1. Deserialize the request object from the request body
            // 2. Extract the new field object from the request object
            // 3. Call the model to add the new field

        }
    };

    private HttpHandler searchHandler = new HttpHandler() {

        @Override
        public void handle(HttpExchange exchange) throws IOException {

            // Process UpdateField request
            // 1. Deserialize the request object from the request body
            // 2. Extract the field to be updated from the request object
            // 3. Call the model to update the field

        }
    };

    private HttpHandler downloadFileHandler = new HttpHandler() {

        @Override
        public void handle(HttpExchange exchange) throws IOException {

            // Process DeleteField request
            // 1. Deserialize the request object from the request body
            // 2. Extract the field to be deleted from the request object
            // 3. Call the model to delete the field

        }
    };

    public static void main(String[] args) {
        new Server().run();
    }

}
