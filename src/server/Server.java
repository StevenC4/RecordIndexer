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

        server.createContext("/GetAllBatches", getAllBatchesHandler);
        server.createContext("/AddBatch", addBatchHandler);
        server.createContext("/UpdateBatch", updateBatchHandler);
        server.createContext("/DeleteBatch", deleteBatchHandler);

        logger.info("Starting HTTP Server");

        server.start();
    }

    private HttpHandler getAllBatchesHandler = new HttpHandler() {

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

    private HttpHandler addBatchHandler = new HttpHandler() {

        @Override
        public void handle(HttpExchange exchange) throws IOException {

            // Process AddBatch request
            // 1. Deserialize the request object from the request body
            // 2. Extract the new batch object from the request object
            // 3. Call the model to add the new batch

        }
    };

    private HttpHandler updateBatchHandler = new HttpHandler() {

        @Override
        public void handle(HttpExchange exchange) throws IOException {

            // Process UpdateBatch request
            // 1. Deserialize the request object from the request body
            // 2. Extract the batch to be updated from the request object
            // 3. Call the model to update the batch

        }
    };

    private HttpHandler deleteBatchHandler = new HttpHandler() {

        @Override
        public void handle(HttpExchange exchange) throws IOException {

            // Process DeleteBatch request
            // 1. Deserialize the request object from the request body
            // 2. Extract the batch to be deleted from the request object
            // 3. Call the model to delete the batch

        }
    };

    private HttpHandler getAllFieldsHandler = new HttpHandler() {

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

    private HttpHandler addFieldHandler = new HttpHandler() {

        @Override
        public void handle(HttpExchange exchange) throws IOException {

            // Process AddField request
            // 1. Deserialize the request object from the request body
            // 2. Extract the new field object from the request object
            // 3. Call the model to add the new field

        }
    };

    private HttpHandler updateFieldHandler = new HttpHandler() {

        @Override
        public void handle(HttpExchange exchange) throws IOException {

            // Process UpdateField request
            // 1. Deserialize the request object from the request body
            // 2. Extract the field to be updated from the request object
            // 3. Call the model to update the field

        }
    };

    private HttpHandler deleteFieldHandler = new HttpHandler() {

        @Override
        public void handle(HttpExchange exchange) throws IOException {

            // Process DeleteField request
            // 1. Deserialize the request object from the request body
            // 2. Extract the field to be deleted from the request object
            // 3. Call the model to delete the field

        }
    };

    private HttpHandler getAllProjectsHandler = new HttpHandler() {

        @Override
        public void handle(HttpExchange exchange) throws IOException {

            // Process GetAllProjects request
            // 1. Call model to get a list of all projects
            // 2. Create a result object
            // 3. Populate it with the list of projects
            // 4. Serialize the result object
            // 5. Return the serialized result object in the response body

        }
    };

    private HttpHandler addProjectHandler = new HttpHandler() {

        @Override
        public void handle(HttpExchange exchange) throws IOException {

            // Process AddProject request
            // 1. Deserialize the request object from the request body
            // 2. Extract the new project object from the request object
            // 3. Call the model to add the new project

        }
    };

    private HttpHandler updateProjectHandler = new HttpHandler() {

        @Override
        public void handle(HttpExchange exchange) throws IOException {

            // Process UpdateProject request
            // 1. Deserialize the request object from the request body
            // 2. Extract the project to be updated from the request object
            // 3. Call the model to update the project

        }
    };

    private HttpHandler deleteProjectHandler = new HttpHandler() {

        @Override
        public void handle(HttpExchange exchange) throws IOException {

            // Process DeleteProject request
            // 1. Deserialize the request object from the request body
            // 2. Extract the project to be deleted from the request object
            // 3. Call the model to delete the project

        }
    };

    private HttpHandler getAllUsersHandler = new HttpHandler() {

        @Override
        public void handle(HttpExchange exchange) throws IOException {

            // Process GetAllUsers request
            // 1. Call model to get a list of all users
            // 2. Create a result object
            // 3. Populate it with the list of users
            // 4. Serialize the result object
            // 5. Return the serialized result object in the response body

        }
    };

    private HttpHandler addUserHandler = new HttpHandler() {

        @Override
        public void handle(HttpExchange exchange) throws IOException {

            // Process AddUser request
            // 1. Deserialize the request object from the request body
            // 2. Extract the new user object from the request object
            // 3. Call the model to add the new user

        }
    };

    private HttpHandler updateUserHandler = new HttpHandler() {

        @Override
        public void handle(HttpExchange exchange) throws IOException {

            // Process UpdateUser request
            // 1. Deserialize the request object from the request body
            // 2. Extract the user to be updated from the request object
            // 3. Call the model to update the user

        }
    };

    private HttpHandler deleteUserHandler = new HttpHandler() {

        @Override
        public void handle(HttpExchange exchange) throws IOException {

            // Process DeleteUser request
            // 1. Deserialize the request object from the request body
            // 2. Extract the user to be deleted from the request object
            // 3. Call the model to delete the user

        }
    };

    private HttpHandler getAllValuesHandler = new HttpHandler() {

        @Override
        public void handle(HttpExchange exchange) throws IOException {

            // Process GetAllValues request
            // 1. Call model to get a list of all values
            // 2. Create a result object
            // 3. Populate it with the list of values
            // 4. Serialize the result object
            // 5. Return the serialized result object in the response body

        }
    };

    private HttpHandler addValueHandler = new HttpHandler() {

        @Override
        public void handle(HttpExchange exchange) throws IOException {

            // Process AddValue request
            // 1. Deserialize the request object from the request body
            // 2. Extract the new value object from the request object
            // 3. Call the model to add the new value

        }
    };

    private HttpHandler updateValueHandler = new HttpHandler() {

        @Override
        public void handle(HttpExchange exchange) throws IOException {

            // Process UpdateValue request
            // 1. Deserialize the request object from the request body
            // 2. Extract the value to be updated from the request object
            // 3. Call the model to update the value

        }
    };

    private HttpHandler deleteValueHandler = new HttpHandler() {

        @Override
        public void handle(HttpExchange exchange) throws IOException {

            // Process DeleteValue request
            // 1. Deserialize the request object from the request body
            // 2. Extract the value to be deleted from the request object
            // 3. Call the model to delete the value

        }
    };

    public static void main(String[] args) {
        new Server().run();
    }

}
