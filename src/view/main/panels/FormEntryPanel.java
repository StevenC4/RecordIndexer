package view.main.panels;

import shared.model.Field;
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
    List<Field> fields;

    List<JTextField> inputTextFields;

    JList fieldsListBox;
    DefaultListModel fieldsListModel;
    JPanel inputPanel;

    public FormEntryPanel(BatchState batchState) {

        this.batchState = batchState;

        this.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));

        fieldsListModel = new DefaultListModel();
        fieldsListBox = new JList(fieldsListModel);
        fieldsListBox.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        fieldsListBox.setVisibleRowCount(-1);

        inputPanel = new JPanel();
        inputPanel.setLayout(new GridBagLayout());
        inputTextFields = new ArrayList<JTextField>();

        if (batchState.getCurrentProject() != null) {
            populateView();
        }

        this.add(new JScrollPane(fieldsListBox));
        this.add(new JScrollPane(inputPanel));

    }

    private void populateView() {
        int numRecords = batchState.getCurrentProject().getRecordsPerImage();
        for (int i = 0; i < numRecords; i++) {
            fieldsListModel.addElement(i);
        }

        fields = batchState.getCurrentFields();

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
        populateView();
    }

    @Override
    public void fieldSelected() {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void recordSelected() {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void batchSubmitted() {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    class RecordsListListener implements ListSelectionListener {

        @Override
        public void valueChanged(ListSelectionEvent e) {

            System.out.println();
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
