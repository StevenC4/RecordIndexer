package view.state;

import client.communication.ClientCommunicator;
import shared.communication.DownloadFile_Results;
import shared.model.Batch;
import shared.model.Field;
import shared.model.Project;
import shared.model.User;
import view.spell.SpellCorrector;
import view.spell.SpellCorrectorImpl;

import java.io.File;
import java.io.FileInputStream;
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
public class BatchState implements Serializable {           // TODO: Write code that updates the valueValue field and suggestedWord field

    transient ClientCommunicator clientCommunicator;

    User user;

    Project currentProject;  //TODO Constructor
    Batch currentBatch;      //TODO Constructor
    private List<Field> fields;           //TODO Constructor
    private Field selectedField;          //TODO Constructor
    private int selectedRecord;

    private float zoomScale;
    boolean isInverted;
    boolean showHighlight;

    String[][] recordValues;
    Set<String>[][] suggestedWordCells;
    SpellCorrector spellCorrector;

    List<BatchStateListener> listeners;

    /***********************Constructors**************************/

    public BatchState(User user) {
        clientCommunicator = new ClientCommunicator();
        this.user = user;
        listeners = new ArrayList<BatchStateListener>();
        zoomScale = 1;
        isInverted = false;
        showHighlight = true;
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

    public boolean getIsInverted() {
        return isInverted;
    }

    public boolean getShowHighlight() {
        return showHighlight;
    }

    public float getZoomScale() {
        return zoomScale;
    }

    public Set<String> getSuggestedWords(String word) {
        Set<String> suggestedWords;
        try {
            suggestedWords = spellCorrector.suggestSimilarWord(word);
        } catch (Exception e) {
            suggestedWords = new TreeSet<String>();
        }
        return suggestedWords;
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
        zoomScale /= .75;
        notifyZoomed();
    }

    public void decrementZoom() {
        zoomScale  *= .75;
        notifyZoomed();
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
        void showHighlightToggled();
        void cellUpdated(String value, int row, int col);
        void batchSubmitted();
    }
}
