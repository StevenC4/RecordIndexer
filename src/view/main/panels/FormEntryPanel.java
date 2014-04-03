package view.main.panels;

import shared.model.Field;
import shared.model.Project;
import view.BatchState;
import view.BatchState.BatchStateListener;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.util.*;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Steven
 * Date: 3/25/14
 * Time: 5:26 PM
 * To change this template use File | Settings | File Templates.
 */
public class FormEntryPanel extends JPanel implements BatchStateListener {

    BatchState batchState;
    int numRecords;
    Project currentProject;
    List<Field> fields;
    Field selectedField;
    int selectedRecord;
    String[][] recordValues;

    List<JTextField> inputTextFields;
    Vector<String> v;

    JList<String> fieldsListBox;
    ListSelectionModel fieldsListModel;
    JPanel inputPanel;

    public FormEntryPanel(BatchState batchState) {

        this.batchState = batchState;

        this.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));

        v = new Vector<String>();

        fieldsListBox = new JList(v);
        fieldsListModel = fieldsListBox.getSelectionModel();
        fieldsListBox.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        fieldsListBox.setVisibleRowCount(-1);
        fieldsListModel.addListSelectionListener(new RecordsListListener());

        inputPanel = new JPanel();
        inputPanel.setLayout(new GridBagLayout());
        inputTextFields = new ArrayList<JTextField>();

        if (currentProject != null) {
            populateView();
        }

        this.add(new JScrollPane(fieldsListBox));
        this.add(new JScrollPane(inputPanel));
    }

    private void populateView() {
        // Populate the JList with the record numbers
        for (int i = 0; i < numRecords; i++) {
            v.add(Integer.toString(i + 1));
        }
        fieldsListBox.setListData(v);
        fieldsListBox.setSelectedIndex(selectedRecord);

        // Populate the Form with JLabels and JTextFields
        GridBagConstraints c = new GridBagConstraints();
        c.gridy = 0;
        c.anchor = GridBagConstraints.NORTHWEST;
        c.weightx = c.weighty = 1.0;
        c.insets = new Insets(3, 5, 0, 0);

        for (Field field : fields) {
            JLabel label = new JLabel(field.getTitle());
            JTextField textField = new JTextField(9);
            textField.addFocusListener(new TextPanesFocusListener());

            c.gridx = 0;
            inputPanel.add(label, c);
            c.gridx = 1;
            inputPanel.add(textField, c);
            c.gridy++;

            inputTextFields.add(textField);
        }
    }

    @Override
    public void batchDownloaded() {
        currentProject = batchState.getCurrentProject();
        numRecords = currentProject.getRecordsPerImage();
        fields = batchState.getCurrentFields();
        selectedField = batchState.getSelectedField();
        selectedRecord = batchState.getSelectedRecord();

        recordValues = new String[numRecords][fields.size()];
        for (int i = 0; i < numRecords; i++) {
            for (int j = 0; j < fields.size(); j++) {
                recordValues[i][j] = "";
            }
        }

        populateView();
    }

    @Override
    public void cellSelected() {
        selectedField = batchState.getSelectedField();
        selectedRecord = batchState.getSelectedRecord();

        int oldRecordIndex = fieldsListBox.getSelectedIndex();
        fieldsListBox.setSelectedIndex(selectedRecord);

        updateTextFields(oldRecordIndex, selectedRecord);
    }

    @Override
    public void imageZoomed() {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void cellUpdated(String value, int row, int col) {

    }

    @Override
    public void batchSubmitted() {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    private void updateTextFields(int oldIndex, int newIndex) {
        // Save the form data
        int j = 0;
        for (int i = 0; i < inputTextFields.size(); i++) {
            String text = inputTextFields.get(i).getText();
            recordValues[oldIndex][i] = text;
        }

        j = 0;
        // Clear the form data or repopulate old data
        for (int i = 0; i < inputTextFields.size(); i++) {
            String text = recordValues[newIndex][i];
            inputTextFields.get(i).setText(text);
        }
    }

    class RecordsListListener implements ListSelectionListener {

        @Override
        public void valueChanged(ListSelectionEvent e) {
            BatchState batchState = FormEntryPanel.this.batchState;

            boolean isAdjusting = e.getValueIsAdjusting();
            if (isAdjusting) {
                ListSelectionModel lsm = (ListSelectionModel)e.getSource();

                int newIndex = lsm.getLeadSelectionIndex();
                int oldIndex = (newIndex == e.getFirstIndex() ? e.getLastIndex() : e.getFirstIndex());

                updateTextFields(oldIndex, newIndex);

                batchState.setSelectedRecord(newIndex);
            }
        }
    }

    class TextPanesFocusListener implements FocusListener {

        @Override
        public void focusGained(FocusEvent e) {
            int sourceIndex = -1;
            for (int i = 0; i < inputTextFields.size(); i++) {
                if (e.getSource() == inputTextFields.get(i)) {
                    sourceIndex = i;
                    break;
                }
            }
            if (sourceIndex != -1) {
                Field field = fields.get(sourceIndex);
                batchState.setSelectedField(field);
            }
        }

        @Override
        public void focusLost(FocusEvent e) {}
    }
}
