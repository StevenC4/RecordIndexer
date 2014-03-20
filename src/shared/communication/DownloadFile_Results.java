package shared.communication;

/**
 * Created with IntelliJ IDEA.
 * User: Steven
 * Date: 3/19/14
 * Time: 10:03 PM
 * To change this template use File | Settings | File Templates.
 */
public class DownloadFile_Results extends Operation_Result {

    byte[] bytes;

    public DownloadFile_Results() {
        bytes = new byte[0];
    }

    public DownloadFile_Results(byte[] bytes) {
        this.bytes = bytes;
    }

    public byte[] getBytes() {
        return bytes;
    }

    public void setBytes(byte[] bytes) {
        this.bytes = bytes;
    }
}
