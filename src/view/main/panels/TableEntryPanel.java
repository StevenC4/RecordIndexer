package view.main.panels;

import shared.model.Batch;
import shared.model.Field;
import shared.model.Project;

import view.BatchState;
import view.BatchState.BatchStateListener;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.*;
import java.awt.*;
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

    public TableEntryPanel(BatchState batchState) {
        this.batchState = batchState;
        currentProject = batchState.getCurrentProject();

        this.setLayout(new GridBagLayout());

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

        String[][] recordValues = new String[numRecords][columnNames.length];

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
        tableModel.addTableModelListener(new FieldTableListener());
        table = new JTable();
        table.setModel(tableModel);
        table.setSurrendersFocusOnKeystroke(true);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.setCellSelectionEnabled(true);
        table.getTableHeader().setReorderingAllowed(false);

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

        setUpTable();
    }

    @Override
    public void cellSelected() {
        int row = batchState.getSelectedRecord();
        int col = batchState.getSelectedField().getPosition();
    }

    @Override
    public void imageZoomed() {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void cellUpdated(String value, int row, int col) {
        tableModel.setValueAt(value, row, col + 1);
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

        public int getColumnCount() {
            return columnNames.length;
        }

        public int getRowCount() {
            return recordValues.length;
        }

        public String getColumnName(int col) {
            return columnNames[col];
        }

        public Object getValueAt(int row, int col) {
            return recordValues[row][col];
        }

        public Class getColumnClass(int c) {
            return getValueAt(0, c).getClass();
        }

        public boolean isCellEditable(int row, int col) {
            if (col < 1) {
                return false;
            } else {
                return true;
            }
        }

        public void setValueAt(Object value, int row, int col) {
            recordValues[row][col] = (String) value;
            fireTableCellUpdated(row, col);
        }
    }

    class FieldTableListener implements TableModelListener {

        @Override
        public void tableChanged(TableModelEvent e) {
        }
    }

    class FieldCellSelectionListener implements ListSelectionListener {

        @Override
        public void valueChanged(ListSelectionEvent e) {
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
}

//User MouseAdapter
// int selectedColumn = table.columnAtPoint(e.getPoint());
// int selectedRow = table.rowAtPoint(e.getPoint());
