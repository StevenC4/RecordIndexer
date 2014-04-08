package view.state;

import client.communication.ClientCommunicator;
import com.thoughtworks.xstream.annotations.XStreamOmitField;
import shared.communication.DownloadFile_Results;
import shared.communication.SubmitBatch_Params;
import shared.communication.SubmitBatch_Result;
import shared.model.Batch;
import shared.model.Field;
import shared.model.Project;
import shared.model.User;
import view.spell.SpellCorrector;
import view.spell.SpellCorrectorImpl;

import java.awt.geom.Point2D;
import java.io.File;
import java.io.FileOutputStream;
import java.io.Serializable;
import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: Steven
 * Date: 3/21/14
 * Time: 12:29 PM
 * To change this template use File | Settings | File Templates.
 */
public class BatchState {           // TODO: Write code that updates the valueValue field and suggestedWord field

    ClientCommunicator clientCommunicator;

    User user;

    Project currentProject;  //TODO Constructor
    Batch currentBatch;      //TODO Constructor
    private List<Field> fields;           //TODO Constructor
    private Field selectedField;          //TODO Constructor
    private int selectedRecord;

    Point2D w_origin;
    private float zoomScale;
    boolean isInverted;
    boolean showHighlight;

    String[][] recordValues;
    Set<String>[][] suggestedWordCells;
    SpellCorrector spellCorrector;

    @XStreamOmitField
    List<BatchStateListener> listeners;

    /***********************Constructors**************************/

    public BatchState(String host, int port, User user) {
        clientCommunicator = new ClientCommunicator(host, port);
        this.user = user;
        listeners = new ArrayList<BatchStateListener>();
        zoomScale = 1;
        isInverted = false;
        showHighlight = true;
        w_origin = new Point2D.Double(0, 0);
        spellCorrector = new SpellCorrectorImpl();

    }

    public void addListener(BatchStateListener batchStateListener) {
        listeners.add(batchStateListener);
    }

    /************************Getters**************************/

    public ClientCommunicator getClientCommunicator() {
        return clientCommunicator;
    }

    public User getUser() {
        return user;
    }

    public Project getCurrentProject() {
        return currentProject;
    }

    public Batch getCurrentBatch() {
        return currentBatch;
    }

    public List<Field> getCurrentFields() {
        return fields;
    }

    public Field getSelectedField() {
        return selectedField;
    }

    public int getSelectedRecord() {
        return selectedRecord;
    }

    public String getCellContents(int row, int col) {
        return recordValues[row][col];
    }

    public Set<String> getSuggestedWordsCell(int row, int col) {
        return suggestedWordCells[row][col];
    }

    public float getZoomScale() {
        return zoomScale;
    }

    public boolean getIsInverted() {
        return isInverted;
    }

    public Point2D getW_Origin() {
        return w_origin;
    }

    public boolean getShowHighlight() {
        return showHighlight;
    }

    public Set<String> getSuggestedWords(String word, int col) {

        updateDictionary(col);

        Set<String> suggestedWords;
        try {
            suggestedWords = spellCorrector.suggestSimilarWord(word);
        } catch (Exception e) {
            suggestedWords = new TreeSet<String>();
        }
        return suggestedWords;
    }

    /*********************************Modifiers************************************/

    public void initializeClientCommunicator(String host, int port) {
        clientCommunicator = new ClientCommunicator(host, port);
    }

    public void initializeListeners() {
        listeners = new ArrayList<BatchStateListener>();
    }

    public void setCurrentProject(Project project) {
        currentProject = project;
    }

    public void downloadBatch(Project project, Batch batch, List<Field> fields) {
        currentProject = project;
        currentBatch = batch;
        this.fields = fields;
        selectedField = fields.get(0);
        selectedRecord = 0;

        recordValues = new String[project.getRecordsPerImage()][fields.size()];
        suggestedWordCells = new TreeSet[project.getRecordsPerImage()][fields.size()];
        for (int i = 0; i < currentProject.getRecordsPerImage(); i++) {
            for (int j = 0; j < fields.size(); j++) {
                recordValues[i][j] = "";
                Set<String> set = new TreeSet<String>();
                set.add("");
                suggestedWordCells[i][j] = set;
            }
        }

        updateDictionary(selectedField.getPosition() - 1);

        notifyBatchDownloaded();
    }

    public void updateW_Origin(Point2D newOrigin) {
        w_origin = newOrigin;
        notifyOriginMoved();
    }

    public void setSelectedCell(Field field, int record) {
        this.selectedField = field;
        this.selectedRecord = record;
        notifyCellSelected();
        updateDictionary(field.getPosition() - 1);
    }

    public void updateCell(String value, int row, int col) {
        recordValues[row][col] = value;
        suggestedWordCells[row][col] = getSuggestedWords(value, col);
        notifyCellUpdated(value, row, col);
    }

    public void incrementZoom() {
        if (zoomScale <= 2.4) {
            zoomScale /= .75;
            notifyZoomed();
        }
    }

    public void decrementZoom() {
        if (zoomScale >= 0.4) {
            zoomScale  *= .75;
            notifyZoomed();
        }
    }

    public void toggleIsInverted() {
        isInverted = !isInverted;
        notifyToggleInverted();
    }

    public void toggleShowHighlight(){
        showHighlight = !showHighlight;
        notifyToggleHighlight();
    }

    public void updateDictionary(int col) {

        try {
            String url = fields.get(col).getKnownData();
            DownloadFile_Results results = clientCommunicator.DownloadFile(url);

            byte[] bytes = results.getBytes();

            String fileName = "resources" + File.separator + "knowndata" + File.separator + selectedField.getTitle() + "_" + currentProject.getTitle() + ".txt";
            File resources = new File("resources");
            if (!resources.exists()) {
                resources.mkdir();
            }
            File knowndata = new File("resources" + File.separator + "knowndata");
            if (!knowndata.exists()) {
                knowndata.mkdir();
            }

            FileOutputStream fos = new FileOutputStream(fileName);
            fos.write(bytes);
            fos.close();

            File dictionaryFile = new File(fileName);
            spellCorrector.useDictionary(dictionaryFile);
        } catch (Exception e) {}
    }

    public void saveBatch() {
        notifyBatchSaved();
    }

    public void submitBatch() {
        SubmitBatch_Params params = new SubmitBatch_Params();
        String values = "";

        for (int i = 0; i < currentProject.getRecordsPerImage(); i++) {
            for (int j = 0; j < fields.size(); j++) {
                values += recordValues[i][j];
                if (j < fields.size() - 1) {
                    values += ",";
                }
            }
            if (i < currentProject.getRecordsPerImage() - 1) {
                values += ";";
            }
        }

        params.setUser(user);
        params.setBatchId(currentBatch.getBatchId());
        params.setFieldValues(values);

        try {
            SubmitBatch_Result result = clientCommunicator.SubmitBatch(params);
            if (!result.getFailed()) {

            } else {

            }
        } catch (Exception e) {

        }

        currentProject = null;
        currentBatch = null;
        this.fields = null;
        selectedField = null;
        selectedRecord = -1;
        recordValues = null;
        suggestedWordCells = null;
        spellCorrector = new SpellCorrectorImpl();

        notifyBatchSubmitted();
    }

    /*********************Setter for save*********************/

    public void prepareNullBatchSave() {
        isInverted = false;
        showHighlight = true;
        w_origin = new Point2D.Double(0, 0);
        zoomScale = 1;
    }

    /************************Notifiers************************/

    private void notifyBatchDownloaded() {
        for (BatchStateListener listener : listeners) {
            listener.batchDownloaded();
        }
    }

    private void notifyCellSelected() {
        for (BatchStateListener listener : listeners) {
            listener.cellSelected();
        }
    }

    private void notifyZoomed() {
        for (BatchStateListener listener : listeners) {
            listener.imageZoomed();
        }
    }

    private void notifyToggleInverted() {
        for (BatchStateListener listener : listeners) {
            listener.isInvertedToggled();
        }
    }

    private void notifyOriginMoved() {
        for (BatchStateListener listener : listeners) {
            listener.originMoved();
        }
    }

    private void notifyToggleHighlight() {
        for (BatchStateListener listener : listeners) {
            listener.showHighlightToggled();
        }
    }

    private void notifyCellUpdated(String value, int row, int col) {
        for (BatchStateListener listener : listeners) {
            listener.cellUpdated(value, row, col);
        }
    }

    private void notifyBatchSaved() {
        for (BatchStateListener listener : listeners) {
            listener.batchSaved();
        }
    }

    private void notifyBatchSubmitted() {
        for (BatchStateListener listener : listeners) {
            listener.batchSubmitted();
        }
    }

    public interface BatchStateListener {
        void batchDownloaded();

        void cellSelected();
        void imageZoomed();
        void isInvertedToggled();
        void originMoved();
        void showHighlightToggled();
        void cellUpdated(String value, int row, int col);
        void batchSaved();
        void batchSubmitted();
    }
}
