package view.main.panels;

import shared.communication.DownloadFile_Results;
import shared.model.Field;
import view.BatchState;
import view.BatchState.BatchStateListener;

import javax.swing.*;
import java.util.List;
import java.awt.*;

/**
 * Created with IntelliJ IDEA.
 * User: stevenc4
 * Date: 3/27/14
 * Time: 8:21 AM
 * To change this template use File | Settings | File Templates.
 */
public class FieldHelpPanel extends JPanel implements BatchStateListener {

    BatchState batchState;

    List<Field> fields;
    Field selectedField;

    JEditorPane htmlPane;

    public FieldHelpPanel(BatchState batchState) {
        this.batchState = batchState;
        this.setLayout(new BorderLayout());

        htmlPane = new JEditorPane();
        htmlPane.setContentType("text/html");
        htmlPane.setEditable(false);

        this.add(htmlPane, BorderLayout.CENTER);
    }

    private void displayHTML(String url) {
        try {
            DownloadFile_Results results = batchState.getClientCommunicator().DownloadFile(url);
            String html = new String(results.getBytes());
            htmlPane.setText(html);
        } catch (Exception e) {}
    }

    @Override
    public void batchDownloaded() {
        fields = batchState.getCurrentFields();
        selectedField = batchState.getSelectedField();
        String url = selectedField.getHelpHTML();

        displayHTML(url);
    }

    @Override
    public void cellSelected() {
        selectedField = batchState.getSelectedField();
        String url = selectedField.getHelpHTML();
        displayHTML(url);
    }

    @Override
    public void imageZoomed() {}

    @Override
    public void isInvertedToggled() {}

    @Override
    public void showHighlightToggled() {}

    @Override
    public void cellUpdated(String value, int row, int col) {}

    @Override
    public void batchSubmitted() {
        //To change body of implemented methods use File | Settings | File Templates.
    }
}
