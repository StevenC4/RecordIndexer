package view.main.panels;

import shared.model.Batch;
import shared.model.Field;
import shared.model.Project;
import view.main.dialog.SuggestedWordDialog;
import view.state.BatchState;
import view.state.BatchState.BatchStateListener;
import view.state.SuggestedWordState;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.event.*;
import javax.swing.table.*;
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
public class TableEntryPanel extends JPanel implements BatchStateListener {

    BatchState batchState;
    FieldTableModel tableModel;
    ListSelectionModel tableSelectionModel;
    JPopupMenu popupMenu;
    int popupRow;
    int popupCol;

    JTable table;
    Project currentProject;
    Batch currentBatch;
    List<Field> fields;
    Field selectedField;

    int selectedRecord;

    String[][] recordValues;
    private Set<String>[][] suggestedWordCells;

    Field previousField;
    int previousRecord;

    boolean indexColumnSelected;

    boolean tabSelected;

    public TableEntryPanel(BatchState batchState) {
        this.batchState = batchState;
        currentProject = batchState.getCurrentProject();

        tabSelected = false;

//        this.setLayout(new GridBagLayout());
//        this.setLayout(new BorderLayout());
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        indexColumnSelected = false;

        if (currentProject != null) {
            batchDownloaded();
        }
    }

    private void setUpTable() {
        int numRecords = currentProject.getRecordsPerImage();

        popupMenu = new JPopupMenu();
        JMenuItem item = new JMenuItem("See Suggestions");
        item.addActionListener(new SuggestionMenuListener());
        popupMenu.add(item);

        String[] columnNames = new String[fields.size() + 1];
        columnNames[0] = "Record Number";
        for (int i = 0; i < fields.size(); i++) {
            columnNames[i + 1] = fields.get(i).getTitle();
        }

        recordValues = new String[numRecords][columnNames.length];
        suggestedWordCells = new TreeSet[numRecords][columnNames.length];

        for (int i = 0; i < numRecords; i++) {
            for (int j = 0; j < columnNames.length; j++) {
                Set<String> set = new TreeSet<String>();
                String value;
                if (j == 0) {
                    value = Integer.toString(i + 1);
                    set.add(Integer.toString(i + 1));
                } else {
                    value = batchState.getCellContents(i, j - 1);
                    set.addAll(batchState.getSuggestedWordsCell(i, j - 1));
                }
                recordValues[i][j] = value;
                suggestedWordCells[i][j] = set;

            }
        }

        tableModel = new FieldTableModel(columnNames, recordValues);
        table = new JTable();
        table.setModel(tableModel);
        table.setSurrendersFocusOnKeystroke(true);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.setCellSelectionEnabled(true);
        table.getTableHeader().setReorderingAllowed(false);
        table.addMouseListener(new TableMouseAdapter());
        table.setDefaultRenderer(Object.class, new FieldCellRenderer());

        table.setRowSelectionInterval(selectedRecord, selectedRecord);
        table.setColumnSelectionInterval(selectedField.getFieldId(), selectedField.getFieldId());

        tableSelectionModel = table.getSelectionModel();
        tableSelectionModel.addListSelectionListener(new TableSelectionListener());
        table.getColumnModel().addColumnModelListener(new ColumnSelectionListener());

        int row = selectedRecord;
        int col = selectedField.getPosition();
        table.setRowSelectionInterval(row, row);
        table.setColumnSelectionInterval(col, col);

        TableColumnModel columnModel = table.getColumnModel();
        for (int i = 0; i < tableModel.getColumnCount(); ++i) {
            TableColumn column = columnModel.getColumn(i);
            column.setPreferredWidth(80);
        }

        JPanel tablePanel = new JPanel();
//        tablePanel.setLayout(new BorderLayout());

        tablePanel.add(table.getTableHeader());
        tablePanel.add(table);

        GridBagConstraints c = new GridBagConstraints();
        c.anchor = GridBagConstraints.NORTHWEST;
        c.gridx = 0;
        c.gridy = 0;
        c.weighty = 1;
        c.weightx = 1;
        JScrollPane scrollPane = new JScrollPane();
//        scrollPane.setSize(300, 300);
        table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        scrollPane.getViewport().add(table);
//        this.add(scrollPane, c);
//        this.add(scrollPane, BorderLayout.CENTER);
        this.add(scrollPane);

        table.requestFocus();
    }

    public void selectTab() {
        tabSelected = true;
        // Set focus on selected cell
    }

    public void deselectTab() {
        tabSelected = false;

        if (currentBatch != null && table.isEditing()) {
            table.getCellEditor().stopCellEditing();
        }
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

    @Override
    public void batchDownloaded() {
        currentProject = batchState.getCurrentProject();
        currentBatch = batchState.getCurrentBatch();
        fields = batchState.getCurrentFields();
        selectedField = batchState.getSelectedField();
        selectedRecord = batchState.getSelectedRecord();

        previousField = selectedField;
        previousRecord = selectedRecord;

        setUpTable();
    }

    @Override
    public void cellSelected() {
        selectedField = batchState.getSelectedField();
        selectedRecord = batchState.getSelectedRecord();

        int row = selectedRecord;
        int col = selectedField.getPosition();

        if (!indexColumnSelected) {
            table.setRowSelectionInterval(row, row);
            table.setColumnSelectionInterval(col, col);
        } else {
            indexColumnSelected = false;
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
        if (!recordValues[row][col + 1].equals(value)) {
            tableModel.setValueAt(value, row, col + 1);
        }
        suggestedWordCells[row][col + 1] = batchState.getSuggestedWordsCell(row, col);
    }

    @Override
    public void batchSaved() {}

    @Override
    public void batchSubmitted() {
        this.removeAll();
        this.repaint();
    }

    class FieldTableModel extends AbstractTableModel {

        private String[] columnNames;
        private String[][] recordValues;

        public FieldTableModel(String[] columnNames, String[][] recordValues) {
            this.columnNames = columnNames;
            this.recordValues = recordValues;
        }

        @Override
        public int getColumnCount() {
            return columnNames.length;
        }

        @Override
        public int getRowCount() {
            return recordValues.length;
        }

        @Override
        public String getColumnName(int col) {
            return columnNames[col];
        }

        @Override
        public Object getValueAt(int row, int col) {
            return recordValues[row][col];
        }

        @Override
        public Class getColumnClass(int c) {
            return getValueAt(0, c).getClass();
        }

        @Override
        public boolean isCellEditable(int row, int col) {
            if (col < 1) {
                return false;
            } else {
                return true;
            }
        }

        @Override
        public void setValueAt(Object value, int row, int col) {
            if (!recordValues[row][col].equals(value)) {
                recordValues[row][col] = (String) value;
                fireTableCellUpdated(row, col);

                batchState.updateCell((String) value, row, col - 1);
            }
        }
    }

    class FieldCellRenderer extends JLabel implements TableCellRenderer {

        public FieldCellRenderer() {
            setOpaque(true);
        }

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            setBorder(null);
            setBackground(Color.WHITE);

            if (value != null) {
                setText((String) value);
            }

            if (column == 0) {
                setHorizontalAlignment(RIGHT);
            } else {
                setHorizontalAlignment(LEFT);
            }

            if (row == table.getSelectedRow() && column == table.getSelectedColumn()) {
                setBackground(new Color(0, 0, 1, 0.3f));
                setBorder(BorderFactory.createLineBorder(Color.BLUE));
            }

            Set<String> suggestedWords = suggestedWordCells[row][column];
            boolean contains = TableEntryPanel.this.contains(suggestedWords, (String) value);
            if (suggestedWords.size() != 1 || !contains) {
                setBackground(Color.RED);
            }

            return this;
        }
    }

    class TableSelectionListener implements ListSelectionListener {

        @Override
        public void valueChanged(ListSelectionEvent e) {
            int newRow = table.getSelectedRow();
            int newCol = table.getSelectedColumn();

            if (newCol == 0) {
                indexColumnSelected = true;
                newCol = 1;
            }
            batchState.setSelectedCell(fields.get(newCol - 1), newRow);
        }
    }

    class ColumnSelectionListener implements TableColumnModelListener {

        @Override
        public void columnSelectionChanged(ListSelectionEvent e) {
            int newRow = table.getSelectedRow();
            int newCol = table.getSelectedColumn();

            if (newCol == 0) {
                indexColumnSelected = true;
                newCol = 1;
            }
            batchState.setSelectedCell(fields.get(newCol - 1), newRow);
        }

        @Override
        public void columnAdded(TableColumnModelEvent e) {}

        @Override
        public void columnRemoved(TableColumnModelEvent e) {}

        @Override
        public void columnMoved(TableColumnModelEvent e) {}

        @Override
        public void columnMarginChanged(ChangeEvent e) {}
    }

    class TableMouseAdapter extends MouseAdapter {

        @Override
        public void mousePressed(MouseEvent e) {
            if (e.getModifiers() == MouseEvent.BUTTON3_MASK) {
                int row = table.rowAtPoint(e.getPoint());
                int col = table.columnAtPoint(e.getPoint());

                popupRow = row;
                popupCol = col;

                Set<String> suggestedWords = suggestedWordCells[popupRow][popupCol];
                boolean contains = contains(suggestedWords, recordValues[popupRow][popupCol]);

                if (suggestedWords.size() != 1 || !contains) {
                    popupMenu.show(table, e.getX(), e.getY());
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
                batchState.updateCell(selectedWord, popupRow, popupCol - 1);
            }

            popupRow = -1;
            popupCol = -1;
        }
    }
}