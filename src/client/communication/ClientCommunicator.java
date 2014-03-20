package client.communication;

import client.ClientException;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;
import shared.communication.*;
import shared.model.Batch;
import shared.model.Field;
import sun.misc.IOUtils;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Steven
 * Date: 2/15/14
 * Time: 1:56 PM
 * To change this template use File | Settings | File Templates.
 */
public class ClientCommunicator {

    /**
     * Instantiates a new ClientCommunicator.
     */

    private XStream xmlStream;

    private static String SERVER_HOST = "localhost";
    private static int SERVER_PORT = 8081;
    private static String URL_PREFIX;
    private static String URL_PREFIX_2;

    public ClientCommunicator() {
        xmlStream = new XStream(new DomDriver());
        setPaths();
    }

    public ClientCommunicator(int port) {
        SERVER_PORT = port;
        xmlStream = new XStream(new DomDriver());
        setPaths();
    }

    public ClientCommunicator(String host) {
        SERVER_HOST = host;
        xmlStream = new XStream(new DomDriver());
        setPaths();
    }

    public ClientCommunicator(String host, int port) {
        SERVER_HOST = host;
        SERVER_PORT = port;
        xmlStream = new XStream(new DomDriver());
        setPaths();
    }

    public String getServerHost() {
        return SERVER_HOST;
    }

    public void setServerHost(String host) {
        SERVER_HOST = host;
        setPaths();
    }

    public int getServerPort() {
        return SERVER_PORT;
    }

    public void setServerPort(int port) {
        SERVER_PORT = port;
        setPaths();
    }

    public void setPaths()
    {
        URL_PREFIX = "http://" + SERVER_HOST + ":" + SERVER_PORT;
        URL_PREFIX_2 = URL_PREFIX + "/";
    }

    /**
     * Validate the user user.
     *
     * @param params the parameters for validation
     * @return an Operation_Result object containing the result string
     * @throws ClientException the client exception
     */
    public ValidateUser_Result ValidateUser(ValidateUser_Params params) throws ClientException {

        ValidateUser_Result result;

        Object object = doPost("/ValidateUser", params);
        if (object instanceof ValidateUser_Result) {
            result = (ValidateUser_Result)object;
        } else {
            result = new ValidateUser_Result();
            result.setFailed(true);
        }

        return result;
    }

    /**
     * Get projects.
     *
     * @param params the parameters for validation
     * @return an Operation_Result object containing the result string
     * @throws ClientException the client exception
     */
    public GetProjects_Result GetProjects(ValidateUser_Params params) throws ClientException {
        GetProjects_Result result;

        Object object = doPost("/GetProjects", params);
        if (object instanceof GetProjects_Result) {
            result = (GetProjects_Result)object;
        } else {
            result = new GetProjects_Result();
            result.setFailed(true);
        }

        return result;
    }

    /**
     * Get a sample image.
     *
     * @param params the parameters for validation and selecting an image
     * @return an Operation_Result object containing the result string
     * @throws ClientException the client exception
     */
    public GetSampleImage_Result GetSampleImage(GetSampleImage_Params params) throws ClientException {
        GetSampleImage_Result result;

        Object object = doPost("/GetSampleImage", params);
        if (object instanceof GetSampleImage_Result) {
            result = (GetSampleImage_Result)object;

            if (!result.getFailed()) {
                Batch batch = result.getBatch();
                String path = batch.getPath();
                path = URL_PREFIX_2 + path;
                batch.setPath(path);
                result.setBatch(batch);
            }
        } else {
            result = new GetSampleImage_Result();
            result.setFailed(true);
        }

        return result;
    }

    /**
     * Download a batch.
     *
     * @param params the parameters for validation and selecting a batch to download
     * @return an Operation_Result object containing the result string
     * @throws ClientException the client exception
     */
    public DownloadBatch_Result DownloadBatch(DownloadBatch_Params params) throws ClientException {
        DownloadBatch_Result result;

        Object object = doPost("/DownloadBatch", params);
        if (object instanceof DownloadBatch_Result) {
            result = (DownloadBatch_Result)object;

            if (!result.getFailed()) {
                result.getBatch().setPath(URL_PREFIX_2 + result.getBatch().getPath());
                List<Field> fields = new ArrayList<Field>();
                for (Field field : result.getFields()) {
                    field.setHelpHTML(URL_PREFIX_2 + field.getHelpHTML());
                    if (field.getKnownData() != null) {
                        field.setKnownData(URL_PREFIX_2 + field.getKnownData());
                    }
                    fields.add(field);
                }
                result.setFields(fields);
            }
        } else {
            result = new DownloadBatch_Result();
            result.setFailed(true);
        }

        return result;
    }

    /**
     * Submit a batch.
     *
     * @param params the parameters for validation and submitting a batch
     * @return an Operation_Result object containing the result string
     * @throws ClientException the client exception
     */
    public SubmitBatch_Result SubmitBatch(SubmitBatch_Params params) throws ClientException {
        SubmitBatch_Result result;

        Object object = doPost("/SubmitBatch", params);
        if (object instanceof SubmitBatch_Result) {
            result = (SubmitBatch_Result)object;
        } else {
            result = new SubmitBatch_Result();
            result.setFailed(true);
        }

        return result;
    }

    /**
     * Get fields.
     *
     * @param params the parameters for validation and getting fields
     * @return an Operation_Result object containing the result string
     * @throws ClientException the client exception
     */
    public GetFields_Result GetFields(GetFields_Params params) throws ClientException {
        GetFields_Result result;

        Object object = doPost("/GetFields", params);
        if (object instanceof GetFields_Result) {
            result = (GetFields_Result)object;
        } else {
            result = new GetFields_Result();
            result.setFailed(true);
        }

        return result;
    }

    /**
     * Search records that have been indexed.
     *
     * @param params the parameters for validation and the search
     * @return an Operation_Result object containing the result string
     * @throws ClientException the client exception
     */
    public Search_Result Search(Search_Params params) throws ClientException {
        Search_Result result;

        Object object = doPost("/Search", params);
        if (object instanceof Search_Result) {
            result = (Search_Result)object;

            if (!result.getFailed()) {
                List<String> paths = new ArrayList<String>();
                for (String path : result.getPaths()) {
                    paths.add(URL_PREFIX_2 + path);
                }
                result.setPaths(paths);
            }
        } else {
            result = new Search_Result();
            result.setFailed(true);
        }

        return result;
    }

    /**
     * Download file.
     *
     * @param urlPath the path for the requested resource
     * @return an Operation_Result object containing the result string
     */
    public DownloadFile_Results DownloadFile(String urlPath) throws ClientException {
        DownloadFile_Results result;

        Object object;
        if (urlPath.contains(URL_PREFIX)) {
            object = doGet(urlPath);
        } else {
            object = doGet("/" + urlPath);
        }
        if (object instanceof Operation_Result) {
            result = (DownloadFile_Results)object;
        } else {
            result = new DownloadFile_Results();
            result.setFailed(true);
        }

        return result;
    }

    /**
     * Do a GET.
     *
     * @param urlPath the path
     * @return the requested data
     * @throws ClientException
     */
    private Object doGet(String urlPath) throws ClientException {
        // Make HTTP GET request to the specified URL,
        // and return the object returned by the server
        DownloadFile_Results result = new DownloadFile_Results();
        try {
            URL url;
            if (urlPath.contains(URL_PREFIX)) {
                url = new URL(urlPath);
            } else {
                url = new URL(URL_PREFIX + urlPath);
            }
            HttpURLConnection connection = (HttpURLConnection)url.openConnection();
            connection.setRequestMethod("GET");
            connection.connect();

            if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                InputStream is = connection.getInputStream();
                ByteArrayOutputStream bOut = new ByteArrayOutputStream();

                byte[] bytes = new byte[1024];
                int read = 0;
                while ((read = is.read(bytes, 0, bytes.length)) != -1) {
                    bOut.write(bytes, 0, read);
                }
                bOut.flush();
                result.setBytes(bOut.toByteArray());
            } else {
                throw new ClientException(String.format("doGet failed"));
            }
        } catch (Exception e) {
            throw new ClientException(String.format("doGet failed: %s", e.getMessage()), e);
        }
        return result;
    }

    /**
     * Do a POST
     *
     * @param urlPath the path
     * @param postData the data being posted
     * @throws ClientException
     */
    private Operation_Result doPost(String urlPath, Object postData) throws ClientException {
        // Make HTTP POST request to the specified URL,
        // passing in the specified postData object
        Operation_Result result = new Operation_Result();

        try {
            URL url = new URL(URL_PREFIX + urlPath);
            HttpURLConnection connection = (HttpURLConnection)url.openConnection();
            connection.setRequestMethod("POST");
            connection.setDoOutput(true);
            connection.setDoInput(true);
            connection.connect();
            xmlStream.toXML(postData, connection.getOutputStream());
            connection.getOutputStream().close();

            if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                result = (Operation_Result)xmlStream.fromXML(connection.getInputStream());
            } else {
                result = new Operation_Result();
                result.setFailed(true);
            }
        }
        catch (Exception e) {
            result.setFailed(true);
//            throw new ClientException(String.format("doPost failed: %s", e.getMessage()), e);
        }
        return result;
    }
}