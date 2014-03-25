package view.main;

import shared.communication.GetProjects_Result;
import shared.communication.ValidateUser_Params;
import shared.model.Project;
import view.BatchState;
import view.login.LoginFrame;
import view.main.panels.ViewSamplePanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Steven
 * Date: 3/22/14
 * Time: 12:12 PM
 * To change this template use File | Settings | File Templates.
 */
public class DownloadBatchDialog extends JDialog {

    JComboBox projectsComboBox;
    JButton viewSampleButton;
    JButton cancelButton;
    JButton downloadButton;

    BatchState batchState;
    List<Project> projects;

    public DownloadBatchDialog(BatchState batchState) {
        this.batchState = batchState;
        this.setResizable(false);
        this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        this.setTitle("Download Batch");
        this.setLayout(new GridBagLayout());
//        this.setModal(true);

        GridBagConstraints c = new GridBagConstraints();
        c.gridx = 0;
        c.gridy = 0;
        c.insets = new Insets(5, 5, 0, 0);

        ValidateUser_Params validateUser_params = new ValidateUser_Params(batchState.getUser());
        try {

            GetProjects_Result getProjects_result = batchState.getClientCommunicator().GetProjects(validateUser_params);
            if (!getProjects_result.getFailed()) {

                projects = getProjects_result.getProjects();

                JLabel projectLabel = new JLabel("Project:");

                projectsComboBox = new JComboBox();
                for (Project project : projects) {
                    projectsComboBox.addItem(project.getTitle());
                }
                projectsComboBox.addActionListener(new ProjectsComboBoxListener());
                batchState.setCurrentProject(projects.get(projectsComboBox.getSelectedIndex()));

                viewSampleButton = new JButton("View Sample");
                viewSampleButton.addActionListener(new ViewSampleButtonListener());

                this.add(projectLabel, c);
                c.gridx = 1;
                this.add(projectsComboBox, c);
                c.gridx = 2;
                c.insets = new Insets(5, 5, 0, 5);
                this.add(viewSampleButton, c);

                JPanel buttonsPanel = new JPanel();
                cancelButton = new JButton("Cancel");
                cancelButton.addActionListener(new CancelButtonListener());
                downloadButton = new JButton("Download");
                downloadButton.addActionListener(new DownloadButtonListener());
                buttonsPanel.add(cancelButton);
                buttonsPanel.add(downloadButton);

                c.gridx = 1;
                c.gridy = 1;
                c.gridwidth = 2;
                c.insets = new Insets(0, 0, 0, 0);
                c.anchor = GridBagConstraints.WEST;
                this.add(buttonsPanel, c);

            } else {
                throw new Exception("There appears to be a problem with the server");
            }
        } catch (Exception e) {

        }

        this.pack();
    }

    class ProjectsComboBoxListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            Project project = projects.get(projectsComboBox.getSelectedIndex());
            batchState.setCurrentProject(project);
        }
    }

    class ViewSampleButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            ViewSamplePanel viewSamplePanel = new ViewSamplePanel(batchState);
            viewSamplePanel.setLocationRelativeTo(null);
            viewSamplePanel.setModal(true);
            viewSamplePanel.setVisible(true);
        }
    }

    class CancelButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            DownloadBatchDialog.this.dispose();
        }
    }

    class DownloadButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            // TODO: Implement DownloadBatch functionality
        }
    }
}