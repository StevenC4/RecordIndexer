package client.communication;

import client.ClientException;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;
import shared.communication.*;

import java.net.HttpURLConnection;
import java.net.URL;

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

    public ClientCommunicator() {
        xmlStream = new XStream(new DomDriver());
    }

    /**
     * Validate the user user.
     *
     * @param params the parameters for validation
     * @return an Operation_Result object containing the result string
     * @throws ClientException the client exception
     */
    public Operation_Result ValidateUser(ValidateUser_Params params) throws ClientException {
        return (Operation_Result)doPost("/ValidateUser", params);
    }

    /**
     * Get projects.
     *
     * @param params the parameters for validation
     * @return an Operation_Result object containing the result string
     * @throws ClientException the client exception
     */
    public Operation_Result GetProjects(ValidateUser_Params params) throws ClientException {
        return (Operation_Result)doPost("/GetProjects", params);
    }

    /**
     * Get a sample image.
     *
     * @param params the parameters for validation and selecting an image
     * @return an Operation_Result object containing the result string
     * @throws ClientException the client exception
     */
    public Operation_Result GetSampleImage(GetSampleImage_Params params) throws ClientException {
        return (Operation_Result)doPost("/GetSampleImage", params);
    }

    /**
     * Download a batch.
     *
     * @param params the parameters for validation and selecting a batch to download
     * @return an Operation_Result object containing the result string
     * @throws ClientException the client exception
     */
    public Operation_Result DownloadBatch(DownloadBatch_Params params) throws ClientException {
        return (Operation_Result)doPost("/DownloadBatch", params);
    }

    /**
     * Submit a batch.
     *
     * @param params the parameters for validation and submitting a batch
     * @return an Operation_Result object containing the result string
     * @throws ClientException the client exception
     */
    public Operation_Result SubmitBatch(SubmitBatch_Params params) throws ClientException {
        return (Operation_Result)doPost("/SubmitBatch", params);
    }

    /**
     * Get fields.
     *
     * @param params the parameters for validation and getting fields
     * @return an Operation_Result object containing the result string
     * @throws ClientException the client exception
     */
    public Operation_Result GetFields(GetFields_Params params) throws ClientException {
        return (Operation_Result)doPost("/GetFields", params);
    }

    /**
     * Search records that have been indexed.
     *
     * @param params the parameters for validation and the search
     * @return an Operation_Result object containing the result string
     * @throws ClientException the client exception
     */
    public Operation_Result Search(Search_Params params) throws ClientException {
        return (Operation_Result)doPost("/Search", params);
    }

    /**
     * Download file.
     *
     * @param params the parameters for validation and downloading the file
     * @return an Operation_Result object containing the result string
     */
    public Operation_Result DownloadFile(DownloadFile_Params params) throws ClientException {
        return null;
//                (Operation_Result)doGet("/DownloadFile", params);
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
        try {
            URL url = new URL(urlPath);
            HttpURLConnection connection = (HttpURLConnection)url.openConnection();


        } catch (Exception e) {

        }

        return null;
    }

    /**
     * Do a POST
     *
     * @param urlPath the path
     * @param postData the data being posted
     * @throws ClientException
     */
    private Object doPost(String urlPath, Object postData) throws ClientException {
        // Make HTTP POST request to the specified URL,
        // passing in the specified postData object
        try {
            URL url = new URL(urlPath);
            HttpURLConnection connection = (HttpURLConnection)url.openConnection();

        } catch (Exception e) {

        }

        return null;
    }
}
