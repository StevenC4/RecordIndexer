package view;

import client.communication.ClientCommunicator;
import shared.communication.*;
import shared.model.Field;
import shared.model.Project;
import shared.model.User;

import javax.swing.*;
import java.awt.*;

import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Steven
 * Date: 3/18/14
 * Time: 2:45 PM
 * To change this template use File | Settings | File Templates.
 */
public class SearchFrame extends JFrame {
    ClientCommunicator clientCommunicator;
    User userCredentials;

    JButton submitFieldButton;
    JButton submitWordButton;
    JButton searchButton;
    JButton viewResultButton;
    JButton clearResultsButton;

    JComboBox projectsComboBox;
    JComboBox fieldsComboBox;
    JComboBox wordsComboBox;
    JComboBox resultsComboBox;

    JTextArea fieldsTextArea;
    JTextArea wordsTextArea;

    List<Project> projects;
    List<Field> fields;
    List<Field> searchFieldsList;
    List<String> searchWordsList;
    List<String> searchResults;

    public SearchFrame(ClientCommunicator clientCommunicator, User userCredentials) {
        this.clientCommunicator = clientCommunicator;
        this.userCredentials = userCredentials;

        try {
            projects = clientCommunicator.GetProjects(new ValidateUser_Params(userCredentials)).getProjects();
        } catch (Exception e) {
            projects = new ArrayList<Project>();
        }
        fields = new ArrayList<Field>();
        searchFieldsList = new ArrayList<Field>();
        searchWordsList = new ArrayList<String>();
        searchResults = new ArrayList<String>();
        searchResults.add("No Results");

        this.setTitle("Search GUI");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(5, 5, 5, 5);

        String[] projectsStringArray = new String[projects.size()];
        for (int i = 0; i < projects.size(); i++) {
            projectsStringArray[i] = projects.get(i).getTitle();
        }

        /*
         * Projects Label and Combo Box
         */

        JPanel projectsPanel = new JPanel();
        JLabel projectsLabel = new JLabel("Projects: ");
        projectsComboBox = new JComboBox(projectsStringArray);
        projectsComboBox.addActionListener(new ProjectsComboBoxListener());
        projectsPanel.add(projectsLabel);
        projectsPanel.add(projectsComboBox);

        c.gridx = 0;
        c.gridy = 0;
        c.gridwidth = 4;
        this.add(projectsPanel, c);

        /*
         * Fields Label, Combo Box, and Button
         */

        JLabel fieldsLabel = new JLabel("Fields: ");
        fieldsComboBox = new JComboBox(populateFields());
        fieldsComboBox.addActionListener(new FieldsComboBoxListener());
        submitFieldButton = new JButton("Add Field");
        submitFieldButton.addActionListener(new AddFieldButtonListener());
        submitFieldButton.setPreferredSize(new Dimension(115, 25));

        c.gridx = 0;
        c.gridy = 1;
        c.gridwidth = 1;
        this.add(fieldsLabel, c);
        c.gridx = 1;
        this.add(fieldsComboBox, c);
        c.gridx = 0;
        c.gridy = 2;
        c.gridwidth = 2;
        this.add(submitFieldButton, c);

        /*
         * Fields Text Area
         */

        JPanel fieldsPanel = new JPanel();
        fieldsPanel.setBorder(BorderFactory.createTitledBorder("Fields to search"));
        fieldsTextArea = new JTextArea(6, 10);
        fieldsTextArea.setEditable(false);
        JScrollPane fieldsScrollPane = new JScrollPane(fieldsTextArea);
        fieldsPanel.add(fieldsScrollPane);

        c.gridx = 0;
        c.gridy = 3;
        this.add(fieldsPanel, c);

        /*
         * Words Label, Combo Box, and Button
         */

        JLabel wordsLabel = new JLabel("Words: ");
        wordsComboBox = new JComboBox();
        wordsComboBox.addActionListener(new WordsComboBoxListener());
        wordsComboBox.getEditor().getEditorComponent().addKeyListener(new AddWordKeyAdapter());
        wordsComboBox.setEditable(true);
        wordsComboBox.setPreferredSize(new Dimension(110, 25));
        submitWordButton = new JButton("Add Word");
        submitWordButton.addActionListener(new AddWordButtonListener());
        submitWordButton.setPreferredSize(new Dimension(115, 25));

        c.gridx = 2;
        c.gridy = 1;
        c.gridwidth = 1;
        this.add(wordsLabel, c);
        c.gridx = 3;
        this.add(wordsComboBox, c);
        c.gridx = 2;
        c.gridy = 2;
        c.gridwidth = 2;
        this.add(submitWordButton, c);

        /*
         * Fields Text Area
         */

        JPanel wordsPanel = new JPanel();
        wordsPanel.setBorder(BorderFactory.createTitledBorder("Words to search"));
        wordsTextArea = new JTextArea(6, 10);
        wordsTextArea.setEditable(false);
        JScrollPane wordsScrollPane = new JScrollPane(wordsTextArea);
        wordsPanel.add(wordsScrollPane);

        c.gridx = 2;
        c.gridy = 3;
        this.add(wordsPanel, c);

        /*
         * Search Button, Results Combo Box, and View Result Button
         */

        JPanel searchButtonsPanel = new JPanel();
        searchButton = new JButton("Search");
        searchButton.addActionListener(new SearchButtonListener());
        clearResultsButton = new JButton("Clear Results");
        clearResultsButton.addActionListener(new ClearResultsButtonListener());
        searchButtonsPanel.add(searchButton);
        searchButtonsPanel.add(clearResultsButton);
        resultsComboBox = new JComboBox();
        resultsComboBox.addItem("No Results");
        viewResultButton = new JButton("View Result");
        viewResultButton.addActionListener(new ViewResultListener());

        c.gridx = 0;
        c.gridy = 4;
        c.gridwidth = 4;
        this.add(searchButtonsPanel, c);
        c.gridy = 5;
        this.add(resultsComboBox, c);
        c.gridy = 6;
        this.add(viewResultButton, c);

        this.pack();
    }

    private String[] populateFields() {
        int projectId = projects.get(projectsComboBox.getSelectedIndex()).getProjectId();

        try {
            GetFields_Result result = clientCommunicator.GetFields(new GetFields_Params(userCredentials, Integer.toString(projectId)));
            fields = result.getFields();
        } catch (Exception e) {
            fields = new ArrayList<Field>();
        }

        String[] fieldsStringArray = new String[fields.size()];
        for (int i = 0; i <  fields.size(); i++) {
            fieldsStringArray[i] = fields.get(i).getTitle();
        }

        return fieldsStringArray;
    }

    class ProjectsComboBoxListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent ae) {
            int projectId = projects.get(projectsComboBox.getSelectedIndex()).getProjectId();
            try {
                fields = clientCommunicator.GetFields(new GetFields_Params(userCredentials, Integer.toString(projectId))).getFields();
            } catch (Exception e) {
                fields = new ArrayList<Field>();
            }
            fieldsComboBox.removeAllItems();
            for (Field field : fields) {
                fieldsComboBox.addItem(field.getTitle());
            }

            searchResults = new ArrayList<String>();
            searchResults.add("No Results");
            populateResultsComboBox();

            searchFieldsList = new ArrayList<Field>();
            searchWordsList = new ArrayList<String>();
            wordsComboBox.removeAllItems();
            fieldsTextArea.setText("");
            wordsTextArea.setText("");
        }
    }

    class FieldsComboBoxListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            if (fieldsComboBox.getSelectedIndex() != -1) {
                Field field = fields.get(fieldsComboBox.getSelectedIndex());
                if (searchFieldsList.contains(field)) {
                    submitFieldButton.setText("Remove Field");
                } else {
                    submitFieldButton.setText("Add Field");
                }
            }
        }
    }

    class WordsComboBoxListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            if (wordsComboBox.getSelectedIndex() != -1) {
                String word = wordsComboBox.getSelectedItem().toString();
                if (searchWordsList.contains(word)) {
                    submitWordButton.setText("Remove Word");
                } else {
                    submitWordButton.setText("Add Word");
                }
            }
        }
    }

    class AddFieldButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            if (submitFieldButton.getText().equals("Add Field")) {
                searchFieldsList.add(fields.get(fieldsComboBox.getSelectedIndex()));
                fieldsTextArea.setText(printSearchFieldsList());
                submitFieldButton.setText("Remove Field");
            } else {
                searchFieldsList.remove(fields.get(fieldsComboBox.getSelectedIndex()));
                fieldsTextArea.setText(printSearchFieldsList());
                submitFieldButton.setText("Add Field");
            }
        }
    }

    class AddWordButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            if (submitWordButton.getText().equals("Add Word")) {
                if (!wordsComboBox.getEditor().getItem().toString().trim().equals("")) {
                    searchWordsList.add(wordsComboBox.getSelectedItem().toString());
                    wordsTextArea.setText(printSearchWordsList());
                    wordsComboBox.addItem(wordsComboBox.getSelectedItem());
                    wordsComboBox.setSelectedItem("");
                }
            } else {
                searchWordsList.remove(wordsComboBox.getSelectedItem().toString());
                wordsTextArea.setText(printSearchWordsList());
                submitWordButton.setText("Add Word");
                wordsComboBox.removeItemAt(wordsComboBox.getSelectedIndex());
            }
        }
    }

    class SearchButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent ae) {
            if (searchFieldsList.size() > 0 && searchWordsList.size() > 0) {
                StringBuilder searchFieldsString = new StringBuilder();
                StringBuilder searchWordsString = new StringBuilder();

                for (int i = 0; i < searchFieldsList.size(); i++) {
                    searchFieldsString.append(searchFieldsList.get(i).getFieldId());
                    if (i < searchFieldsList.size() - 1) {
                        searchFieldsString.append(",");
                    }
                }

                for (int i = 0; i < searchWordsList.size(); i++) {
                    searchWordsString.append(searchWordsList.get(i));
                    if (i < searchWordsList.size() - 1) {
                        searchWordsString.append(",");
                    }
                }

                try {
                    Search_Result result = clientCommunicator.Search(new Search_Params(userCredentials, searchFieldsString.toString(), searchWordsString.toString()));
                    if (result.getFailed()) {
                        searchResults = new ArrayList<String>();
                        searchResults.add("No Results");
                    } else {
                        searchResults = result.getPaths();
                    }
                    populateResultsComboBox();
                } catch (Exception e) {}
            }
        }
    }

    class ClearResultsButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            searchResults = new ArrayList<String>();
            searchResults.add("No Results");
            populateResultsComboBox();
        }
    }

    class ViewResultListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent ae) {
            if (!resultsComboBox.getSelectedItem().toString().equals("No Results")) {
                final String path = resultsComboBox.getSelectedItem().toString();
                try {
                    DownloadFile_Results result = clientCommunicator.DownloadFile(path);
                    final ImageIcon batchImage = new ImageIcon(result.getBytes());

                    JFrame frame = new JFrame();
                    frame.setSize(batchImage.getIconWidth(), batchImage.getIconHeight());
                    JLabel label = new JLabel(batchImage);
                    frame.add(label);
                    frame.setVisible(true);
                    frame.setDefaultCloseOperation(frame.DISPOSE_ON_CLOSE);

                } catch (Exception e) {}
            }
        }
    }

    class AddWordKeyAdapter extends KeyAdapter {

        @Override
        public void keyReleased(KeyEvent e) {
            String word = wordsComboBox.getEditor().getItem().toString();
            if (searchWordsList.contains(word)) {
                submitWordButton.setText("Remove Word");
            } else {
                submitWordButton.setText("Add Word");
            }
        }
    }

    private String printSearchFieldsList() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < searchFieldsList.size(); i++) {
            sb.append(searchFieldsList.get(i).getTitle());
            if (i < searchFieldsList.size() - 1) {
                sb.append("\n");
            }
        }
        return sb.toString();
    }

    private String printSearchWordsList() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < searchWordsList.size(); i++) {
            sb.append(searchWordsList.get(i));
            if (i < searchWordsList.size() - 1) {
                sb.append("\n");
            }
        }
        return sb.toString();
    }

    private void populateResultsComboBox() {
        resultsComboBox.removeAllItems();
        for (String result : searchResults) {
            resultsComboBox.addItem(result);
        }
    }
}
