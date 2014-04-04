package view.main.panels;

import shared.model.Batch;
import shared.model.Field;
import shared.model.Project;
import view.BatchState;
import view.BatchState.BatchStateListener;

import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
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
    JTable table;

    Project currentProject;
    Batch currentBatch;
    List<Field> fields;
    Field selectedField;
    int selectedRecord;

    String[][] recordValues;

    Field previousField;
    int previousRecord;

    boolean indexColumnSelected;

    public TableEntryPanel(BatchState batchState) {
        this.batchState = batchState;
        currentProject = batchState.getCurrentProject();

        this.setLayout(new GridBagLayout());

        indexColumnSelected = false;

        if (currentProject != null) {
            setUpTable();
        }
    }

    private void setUpTable() {
        int numRecords = currentProject.getRecordsPerImage();

        String[] columnNames = new String[fields.size() + 1];
        columnNames[0] = "Record Number";
        for (int i = 0; i < fields.size(); i++) {
            columnNames[i + 1] = fields.get(i).getTitle();
        }

        recordValues = new String[numRecords][columnNames.length];

        for (int i = 0; i < numRecords; i++) {
            for (int j = 0; j < columnNames.length; j++) {
                if (j == 0) {
                    recordValues[i][j] = Integer.toString(i + 1);
                } else {
                    recordValues[i][j] = "";
                }
            }
        }

        tableModel = new FieldTableModel(columnNames, recordValues);
        table = new JTable();
        table.addMouseListener(new MouseFieldListener());
        table.setModel(tableModel);
        table.setSurrendersFocusOnKeystroke(true);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.setCellSelectionEnabled(true);
        table.getTableHeader().setReorderingAllowed(false);

        int row = selectedRecord;
        int col = selectedField.getPosition();
        table.setRowSelectionInterval(row, row);
        table.setColumnSelectionInterval(col, col);

        DefaultTableCellRenderer rightRenderer = new DefaultTableCellRenderer();
        rightRenderer.setHorizontalAlignment(JLabel.RIGHT);

        TableColumnModel columnModel = table.getColumnModel();
        for (int i = 0; i < tableModel.getColumnCount(); ++i) {
            TableColumn column = columnModel.getColumn(i);
            column.setPreferredWidth(80);
            column.setCellRenderer(rightRenderer);
        }

        JPanel tablePanel = new JPanel();
        tablePanel.setLayout(new BoxLayout(tablePanel, BoxLayout.Y_AXIS));

        tablePanel.add(table.getTableHeader());
        tablePanel.add(table);

        GridBagConstraints c = new GridBagConstraints();
        c.anchor = GridBagConstraints.NORTHWEST;
        c.weighty = 1;
        c.weightx = 1;
        this.add(tablePanel, c);
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
    public void showHighlightToggled() {}

    @Override
    public void cellUpdated(String value, int row, int col) {
        if (!recordValues[row][col].equals(value)) {
            tableModel.setValueAt(value, row, col + 1);
        }
    }

    @Override
    public void batchSubmitted() {
        //To change body of implemented methods use File | Settings | File Templates.
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
            setFont(getFont().deriveFont(16.0f));
        }

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
//            Color c = ColorUtils.fromString((String) value);
            Color c = Color.RED;
            return this;
        }
    }

    class FieldCellEditor extends AbstractCellEditor implements TableCellEditor {

        @Override
        public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
            return null;  //To change body of implemented methods use File | Settings | File Templates.
        }

        @Override
        public Object getCellEditorValue() {
            return null;  //To change body of implemented methods use File | Settings | File Templates.
        }
    }

    class MouseFieldListener extends MouseAdapter {

        @Override
        public void mouseClicked(MouseEvent e) {
            int row = table.rowAtPoint(e.getPoint());
            int col = table.columnAtPoint(e.getPoint());

            if (col == 0) {
                indexColumnSelected = true;
                col = 1;
            }

            Field newSelectedField = fields.get(col - 1);
            int newSelectedRecord = row;

            if (newSelectedField != selectedField || newSelectedRecord != selectedRecord)
            {
                batchState.setSelectedCell(newSelectedField, newSelectedRecord);
            }
        }
    }
}

// TODO: Use cell renderer as a listener - when it iterates through the cells and finds one that is selected, do one thing or another