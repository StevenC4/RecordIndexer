package view.main;

import shared.model.User;
import view.BatchState;
import view.login.LoginFrame;
import view.main.components.BatchComponent;
import view.main.components.ImageNavigationComponent;
import view.main.panels.ButtonsPanel;
import view.main.panels.FieldHelpPanel;
import view.main.panels.FormEntryPanel;
import view.main.panels.TableEntryPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * Created with IntelliJ IDEA.
 * User: Steven
 * Date: 3/22/14
 * Time: 10:29 AM
 * To change this template use File | Settings | File Templates.
 */
public class MainContainerFrame extends JFrame {

    BatchState batchState;

    ButtonsPanel buttonsPanel;
    BatchComponent batchComponent;
    FormEntryPanel formEntryPanel;
    TableEntryPanel tableEntryPanel;
    FieldHelpPanel fieldHelpPanel;
    ImageNavigationComponent imageNavigationComponent;

    JMenuBar menuBar;
    JMenu fileMenu;
    JMenuItem downloadBatchMenuItem;
    JMenuItem logoutMenuItem;

    JMenuItem exitMenuItem;

    public MainContainerFrame(User user) {
        this.batchState = loadBatchState(user);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.addWindowListener(new WindowCloseAdapter());
        this.setPreferredSize(new Dimension(890, 575));
        this.setLayout(new BorderLayout());

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));

        menuBar = new JMenuBar();
        fileMenu = new JMenu("File");

        downloadBatchMenuItem = new JMenuItem("Download Batch");
        downloadBatchMenuItem.addActionListener(new DownloadBatchMenuItemListener());
        logoutMenuItem = new JMenuItem("Logout");
        logoutMenuItem.addActionListener(new LogoutMenuItemListener());
        exitMenuItem = new JMenuItem("Exit");
        exitMenuItem.addActionListener(new ExitMenuItemListener());

        fileMenu.add(downloadBatchMenuItem);
        fileMenu.add(logoutMenuItem);
        fileMenu.add(exitMenuItem);

        menuBar.add(fileMenu);

        this.setJMenuBar(menuBar);

        JTabbedPane entryTabbedPane = new JTabbedPane();
        tableEntryPanel = new TableEntryPanel(batchState);
        formEntryPanel = new FormEntryPanel(batchState);
        entryTabbedPane.addTab("Table Entry", new JScrollPane(tableEntryPanel));
        entryTabbedPane.addTab("Form Entry", new JScrollPane(formEntryPanel));

        JTabbedPane helpTabbedPane = new JTabbedPane();
        fieldHelpPanel = new FieldHelpPanel(batchState);
        imageNavigationComponent = new ImageNavigationComponent(batchState);
        helpTabbedPane.addTab("Field Help", fieldHelpPanel);
        helpTabbedPane.addTab("Image Navigation", imageNavigationComponent);

        JSplitPane verticalSplit = new JSplitPane();
        verticalSplit.setOrientation(JSplitPane.VERTICAL_SPLIT);
        verticalSplit.setResizeWeight(.5d);

        JSplitPane horizontalSplit = new JSplitPane();
        horizontalSplit.setOrientation(JSplitPane.HORIZONTAL_SPLIT);
        horizontalSplit.setResizeWeight(.5d);
        horizontalSplit.setLeftComponent(entryTabbedPane);
        horizontalSplit.setRightComponent(helpTabbedPane);

        JPanel batchPanel = new JPanel();
        batchPanel.setLayout(new BorderLayout());
        batchComponent = new BatchComponent(batchState);
        batchPanel.add(batchComponent, BorderLayout.CENTER);

        verticalSplit.setTopComponent(batchPanel);
        verticalSplit.setBottomComponent(horizontalSplit);

        mainPanel.add(verticalSplit);

        buttonsPanel = new ButtonsPanel(batchState);

        batchState.addListener(buttonsPanel);
        batchState.addListener(batchComponent);
        batchState.addListener(tableEntryPanel);
        batchState.addListener(formEntryPanel);
        batchState.addListener(fieldHelpPanel);
        batchState.addListener(imageNavigationComponent);

        this.add(buttonsPanel, BorderLayout.NORTH);
        this.add(mainPanel, BorderLayout.CENTER);

        this.pack();
    }

    class WindowCloseAdapter extends WindowAdapter {

        @Override
        public void windowClosing(WindowEvent e) {
            /*
             * Serialize BatchState and save to a file
             */
        }
    }

    class DownloadBatchMenuItemListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            DownloadBatchDialog downloadBatchDialog = new DownloadBatchDialog(batchState);
            downloadBatchDialog.setModal(true);
            downloadBatchDialog.setLocationRelativeTo(null);
            downloadBatchDialog.setVisible(true);
        }
    }

    class LogoutMenuItemListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            // Serialize batch
            MainContainerFrame.this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            LoginFrame loginFrame = new LoginFrame();
            loginFrame.setVisible(true);
            loginFrame.setLocationRelativeTo(null);
            MainContainerFrame.this.dispose();
        }
    }

    class ExitMenuItemListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            // Serialize batch
            MainContainerFrame.this.dispose();
        }
    }

    private BatchState loadBatchState(User user) {
        return new BatchState(user);
    }
    /*
        When logging out:
            LoginFrame loginFrame = new LoginFrame();
            loginFrame.setLocationRelativeTo(null);
            loginFrame.setVisible(true);
     */
}
