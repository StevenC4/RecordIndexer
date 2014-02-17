package client.communication;

import client.ClientException;
import shared.communication.*;

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
    public ClientCommunicator() {
    }

    /**
     * Validate the user user.
     *
     * @param params the parameters for validation
     * @return an Operation_Result object containing the result string
     * @throws ClientException the client exception
     */
    public Operation_Result ValidateUser(ValidateUser_Params params) throws ClientException {
        return null;
    }

    /**
     * Get projects.
     *
     * @param params the parameters for validation
     * @return an Operation_Result object containing the result string
     * @throws ClientException the client exception
     */
    public Operation_Result GetProjects(ValidateUser_Params params) throws ClientException {
        return null;
    }

    /**
     * Get a sample image.
     *
     * @param params the parameters for validation and selecting an image
     * @return an Operation_Result object containing the result string
     * @throws ClientException the client exception
     */
    public Operation_Result GetSampleImage(GetSampleImage_Params params) throws ClientException {
        return null;
    }

    /**
     * Download a batch.
     *
     * @param params the parameters for validation and selecting a batch to download
     * @return an Operation_Result object containing the result string
     * @throws ClientException the client exception
     */
    public Operation_Result DownloadBatch(DownloadBatch_Params params) throws ClientException {
        return null;
    }

    /**
     * Submit a batch.
     *
     * @param params the parameters for validation and submitting a batch
     * @return an Operation_Result object containing the result string
     * @throws ClientException the client exception
     */
    public Operation_Result SubmitBatch(SubmitBatch_Params params) throws ClientException {
        return null;
    }

    /**
     * Get fields.
     *
     * @param params the parameters for validation and getting fields
     * @return an Operation_Result object containing the result string
     * @throws ClientException the client exception
     */
    public Operation_Result GetFields(GetFields_Params params) throws ClientException {
        return null;
    }

    /**
     * Search records that have been indexed.
     *
     * @param params the parameters for validation and the search
     * @return an Operation_Result object containing the result string
     * @throws ClientException the client exception
     */
    public Operation_Result Search(Search_Params params) throws ClientException {
        return null;
    }

    /**
     * Download file.
     *
     * @param params the parameters for validation and downloading the file
     * @return an Operation_Result object containing the result string
     */
    public Operation_Result DownloadFile(DownloadFile_Params params) {
        return null;
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
        return null;
    }

    /**
     * Do a POST
     *
     * @param urlPath the path
     * @param postData the data being posted
     * @throws ClientException
     */
    private void doPost(String urlPath, Object postData) throws ClientException {
        // Make HTTP POST request to the specified URL,
        // passing in the specified postData object
    }

}
