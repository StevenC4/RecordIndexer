package view.main.panels;

import shared.model.Batch;
import shared.model.Field;
import shared.model.Project;
import view.main.dialog.SuggestedWordDialog;
import view.state.BatchState;
import view.state.BatchState.BatchStateListener;
import view.state.SuggestedWordState;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.*;
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
    Batch currentBatch;
    List<Field> fields;
    Field selectedField;
    int selectedRecord;
    String[][] recordValues;
    private Set<String>[][] suggestedWordCells;

    List<JTextField> inputTextFields;

    JPopupMenu popupMenu;
    int popupRow;
    int popupCol;

    Vector<String> v;
    JList<String> fieldsListBox;
    ListSelectionModel fieldsListModel;
    JPanel inputPanel;

    boolean tabSelected;

    public FormEntryPanel(BatchState batchState) {
        this.batchState = batchState;
        this.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));

        tabSelected = false;

        popupMenu = new JPopupMenu();
        JMenuItem item = new JMenuItem("See Suggestions");
        item.addActionListener(new SuggestionMenuListener());
        popupMenu.add(item);

        v = new Vector<String>();
        fieldsListBox = new JList<String>(v);
        fieldsListModel = fieldsListBox.getSelectionModel();
        fieldsListBox.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        fieldsListBox.setVisibleRowCount(-1);
        fieldsListBox.addMouseListener(new RecordListMouseListener());
        fieldsListModel.addListSelectionListener(new RecordsListListener());

        inputPanel = new JPanel();
        inputPanel.setLayout(new GridBagLayout());
        inputTextFields = new ArrayList<JTextField>();

        if (batchState.getCurrentBatch() != null) {
            batchDownloaded();
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
            textField.addKeyListener(new TextFieldKeyAdapter());
            textField.addMouseListener(new TextFieldMouseAdapter());

            c.gridx = 0;
            inputPanel.add(label, c);
            c.gridx = 1;
            inputPanel.add(textField, c);
            c.gridy++;

            inputTextFields.add(textField);
        }

        if (tabSelected) {
            inputTextFields.get(selectedField.getPosition() - 1).requestFocus();
        }
    }

    public void selectTab() {
        tabSelected = true;
        if (currentBatch != null) {
            inputTextFields.get(selectedField.getPosition() - 1).requestFocus();
        }
    }

    public void deselectTab() {
        tabSelected = false;
    }

    @Override
    public void batchDownloaded() {
        currentProject = batchState.getCurrentProject();
        currentBatch = batchState.getCurrentBatch();
        numRecords = currentProject.getRecordsPerImage();
        fields = batchState.getCurrentFields();
        selectedField = batchState.getSelectedField();
        selectedRecord = batchState.getSelectedRecord();

        recordValues = new String[numRecords][fields.size()];
        suggestedWordCells = new TreeSet[numRecords][fields.size()];
        for (int i = 0; i < numRecords; i++) {
            for (int j = 0; j < fields.size(); j++) {
                recordValues[i][j] = batchState.getCellContents(i, j);
                suggestedWordCells[i][j] = batchState.getSuggestedWordsCell(i, j);
            }
        }

        populateView();
    }

    @Override
    public void cellSelected() {
        if (selectedRecord != batchState.getSelectedRecord() || selectedField != batchState.getSelectedField()) {
            selectedField = batchState.getSelectedField();
            selectedRecord = batchState.getSelectedRecord();

            int oldRecordIndex = fieldsListBox.getSelectedIndex();
            fieldsListBox.setSelectedIndex(selectedRecord);

            updateTextFields(oldRecordIndex, selectedRecord);

            if (tabSelected) {
                inputTextFields.get(selectedField.getPosition() - 1).requestFocus();
            }
        }
    }

    @Override
    public void imageZoomed() {}

    @Override
    public void isInvertedToggled() {}

    @Override
    public void originMoved() {}

    @Override
    public void showHighlightToggled() {}

    @Override
    public void cellUpdated(String value, int row, int col) {
        if (!recordValues[row][col].equals(value)) {
            Set<String> suggestedWords = batchState.getSuggestedWordsCell(row, col);

            recordValues[row][col] = value;
            suggestedWordCells[row][col] = suggestedWords;
            if (row == selectedRecord) {
                JTextField textField = inputTextFields.get(col);
                textField.setText(value);

                boolean contains = contains(suggestedWords, value);
                if (suggestedWordCells[row][col].size() == 1 && contains) {
                    textField.setBackground(Color.WHITE);
                } else {
                    textField.setBackground(Color.RED);
                }
            }
        }
    }

    @Override
    public void batchSaved() {}

    @Override
    public void batchSubmitted() {
        v.clear();
        fieldsListBox.setListData(v);
        inputTextFields.clear();

        inputPanel.removeAll();

        currentProject = batchState.getCurrentProject();
        numRecords = -1;
        fields = batchState.getCurrentFields();
        selectedField = batchState.getSelectedField();
        selectedRecord = batchState.getSelectedRecord();
    }

    private boolean contains(Set<String> suggestedWords, String value) {
        boolean contains = false;
        for (String word : suggestedWords) {
            if (value.compareToIgnoreCase(word) == 0) {
                contains = true;
                break;
            }
        }
        return contains;
    }

    private void updateTextFields(int oldRecord, int newRecord) {
        // Save the form data
        for (int i = 0; i < inputTextFields.size(); i++) {
            String text = inputTextFields.get(i).getText();
            recordValues[oldRecord][i] = text;
        }

        // Clear the form data or repopulate old data
        for (int i = 0; i < inputTextFields.size(); i++) {
            JTextField textField = inputTextFields.get(i);
            String text = recordValues[newRecord][i];
            textField.setText(text);

            Set<String> suggestedWords = suggestedWordCells[newRecord][i];
            boolean contains = contains(suggestedWords, text);
            if (suggestedWords.size() == 1 && contains) {
                textField.setBackground(Color.WHITE);
            } else {
                textField.setBackground(Color.RED);
            }
        }
    }

    class RecordsListListener implements ListSelectionListener {

        @Override
        public void valueChanged(ListSelectionEvent e) {
            BatchState batchState = FormEntryPanel.this.batchState;

            boolean isAdjusting = e.getValueIsAdjusting();
            if (isAdjusting) {
                ListSelectionModel lsm = (ListSelectionModel) e.getSource();

                int newRow = lsm.getLeadSelectionIndex();
                int oldRow = (newRow == e.getFirstIndex() ? e.getLastIndex() : e.getFirstIndex());

                int col = selectedField.getPosition() - 1;
                JTextField textField = inputTextFields.get(col);
                String newValue = textField.getText();
                batchState.updateCell(newValue, oldRow, col);

                updateTextFields(oldRow, newRow);

                batchState.setSelectedCell(selectedField, newRow);
            }
        }
    }

    class RecordListMouseListener extends MouseAdapter {

        @Override
        public void mousePressed(MouseEvent e) {
            if (batchState.getCurrentBatch() != null) {
                int recordIndex = fieldsListBox.locationToIndex(e.getPoint());
                if (recordIndex == selectedRecord) {
                    inputTextFields.get(selectedField.getPosition() - 1).requestFocus();
                }
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
                batchState.setSelectedCell(field, selectedRecord);
            }
        }

        @Override
        public void focusLost(FocusEvent e) {
            int col = -1;
            for (int i = 0; i < inputTextFields.size(); i++) {
                if (e.getSource() == inputTextFields.get(i)) {
                    col = i;
                    break;
                }
            }
            if (col != -1) {
                JTextField fieldLeft = inputTextFields.get(col);
                String newValue = fieldLeft.getText();
                batchState.updateCell(newValue, selectedRecord, col);
            }
        }
    }

    class TextFieldMouseAdapter extends MouseAdapter {

        @Override
        public void mousePressed(MouseEvent e) {
            if (e.getModifiers() == MouseEvent.BUTTON3_MASK) {
                int col = -1;
                for (int i = 0; i < inputTextFields.size(); i++) {
                    if (e.getSource() == inputTextFields.get(i)) {
                        col = i;
                        break;
                    }
                }

                if (col != -1) {
                    popupRow = selectedRecord;
                    popupCol = col;

                    Set<String> suggestedWords = suggestedWordCells[popupRow][popupCol];
                    boolean contains = contains(suggestedWords, recordValues[popupRow][popupCol]);

                    if (suggestedWords.size() != 1 || !contains) {
                        popupMenu.show(inputTextFields.get(col), e.getX(), e.getY());
                    }
                }
            }
        }
    }

    class TextFieldKeyAdapter extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) {
            if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                int col = -1;
                for (int i = 0; i < inputTextFields.size(); i++) {
                    if (e.getSource() == inputTextFields.get(i)) {
                        col = i;
                        break;
                    }
                }
                if (col != -1) {
                    JTextField fieldLeft = inputTextFields.get(col);
                    String newValue = fieldLeft.getText();
                    batchState.updateCell(newValue, selectedRecord, col);
                }
            }
        }
    }

    class SuggestionMenuListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            Set<String> suggestedWords = suggestedWordCells[popupRow][popupCol];
            SuggestedWordState suggestedWordState = new SuggestedWordState(suggestedWords, recordValues[popupRow][popupCol]);
            SuggestedWordDialog suggestedWordDialog = new SuggestedWordDialog(suggestedWordState);
            suggestedWordDialog.setModal(true);
            suggestedWordDialog.setLocationRelativeTo(null);
            suggestedWordDialog.setVisible(true);

            String selectedWord = suggestedWordState.getSelectedWord();
            if (!selectedWord.equals("")) {
                batchState.updateCell(selectedWord, popupRow, popupCol);
            }

            popupRow = -1;
            popupCol = -1;
        }
    }
}
