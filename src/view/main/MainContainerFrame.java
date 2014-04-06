package view.main;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;
import shared.model.User;
import view.state.BatchState;
import view.login.LoginFrame;
import view.main.components.BatchComponent;
import view.main.components.ImageNavigationComponent;
import view.main.panels.ButtonsPanel;
import view.main.panels.FieldHelpPanel;
import view.main.panels.FormEntryPanel;
import view.main.panels.TableEntryPanel;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

/**
 * Created with IntelliJ IDEA.
 * User: Steven
 * Date: 3/22/14
 * Time: 10:29 AM
 * To change this template use File | Settings | File Templates.
 */
public class MainContainerFrame extends JFrame implements BatchState.BatchStateListener {

    final String SAVE_PATH = "resources/user_data/";
    final String XML_EXTENSION = ".xml";

    BatchState batchState;

    ButtonsPanel buttonsPanel;
    BatchComponent batchComponent;
    FormEntryPanel formEntryPanel;
    TableEntryPanel tableEntryPanel;
    FieldHelpPanel fieldHelpPanel;
    ImageNavigationComponent imageNavigationComponent;


    JTabbedPane entryTabbedPane;
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

        menuBar = new JMenuBar();
        fileMenu = new JMenu("File");

        JSplitPane verticalSplit = new JSplitPane();
        verticalSplit.setOrientation(JSplitPane.VERTICAL_SPLIT);
        verticalSplit.setResizeWeight(.5d);

        JSplitPane horizontalSplit = new JSplitPane();
        horizontalSplit.setOrientation(JSplitPane.HORIZONTAL_SPLIT);
        horizontalSplit.setResizeWeight(.5d);

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

        entryTabbedPane = new JTabbedPane();
        tableEntryPanel = new TableEntryPanel(batchState);
        formEntryPanel = new FormEntryPanel(batchState);
        entryTabbedPane.addTab("Table Entry", new JScrollPane(tableEntryPanel));
        entryTabbedPane.addTab("Form Entry", new JScrollPane(formEntryPanel));
        entryTabbedPane.addChangeListener(new EntryPaneTabListener());

        JTabbedPane helpTabbedPane = new JTabbedPane();
        fieldHelpPanel = new FieldHelpPanel(batchState);
        imageNavigationComponent = new ImageNavigationComponent(batchState);
        helpTabbedPane.addTab("Field Help", fieldHelpPanel);
        helpTabbedPane.addTab("Image Navigation", imageNavigationComponent);


        horizontalSplit.setLeftComponent(entryTabbedPane);
        horizontalSplit.setRightComponent(helpTabbedPane);

        JPanel batchPanel = new JPanel();
        batchPanel.setLayout(new BorderLayout());
        batchComponent = new BatchComponent(batchState);
        batchPanel.add(batchComponent, BorderLayout.CENTER);

        verticalSplit.setTopComponent(batchPanel);
        verticalSplit.setBottomComponent(horizontalSplit);

        buttonsPanel = new ButtonsPanel(batchState);

        batchState.addListener(this);
        batchState.addListener(buttonsPanel);
        batchState.addListener(batchComponent);
        batchState.addListener(tableEntryPanel);
        batchState.addListener(formEntryPanel);
        batchState.addListener(fieldHelpPanel);
        batchState.addListener(imageNavigationComponent);

        this.add(buttonsPanel, BorderLayout.NORTH);
        this.add(verticalSplit, BorderLayout.CENTER);

        this.pack();

        horizontalSplit.setDividerLocation(getWidth() / 2);
    }

    @Override
    public void batchDownloaded() {
        downloadBatchMenuItem.setEnabled(false);
    }

    @Override
    public void cellSelected() {}

    @Override
    public void imageZoomed() {}

    @Override
    public void isInvertedToggled() {}

    @Override
    public void originMoved() {}

    @Override
    public void showHighlightToggled() {}

    @Override
    public void cellUpdated(String value, int row, int col) {}

    @Override
    public void batchSaved() {
        saveBatchState();
    }

    @Override
    public void batchSubmitted() {
        downloadBatchMenuItem.setEnabled(true);
    }

    class WindowCloseAdapter extends WindowAdapter {

        @Override
        public void windowClosing(WindowEvent e) {
            saveBatchState();
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

            saveBatchState();

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

            saveBatchState();

            MainContainerFrame.this.dispose();
        }
    }

    class EntryPaneTabListener implements ChangeListener {

        @Override
        public void stateChanged(ChangeEvent e) {
            if (entryTabbedPane.getSelectedIndex() == 0) {
                formEntryPanel.deselectTab();
                tableEntryPanel.selectTab();
            } else {
                tableEntryPanel.deselectTab();
                formEntryPanel.selectTab();
            }
        }
    }

    private BatchState loadBatchState(User user) {
        BatchState batchStateTemp;

        File file = new File(SAVE_PATH + user.getUsername() + XML_EXTENSION);
        if (file.exists()) {
            try {
                XStream stream = new XStream();
                FileInputStream fis = new FileInputStream(file);
                batchStateTemp = (BatchState) stream.fromXML(fis);
                batchStateTemp.initializeClientCommunicator();
                batchStateTemp.initializeListeners();
            } catch (Exception e) {
                batchStateTemp = new BatchState(user);
            }
        } else {
            batchStateTemp = new BatchState(user);
        }

        return batchStateTemp;
    }

    private void saveBatchState() {
        XStream stream = new XStream();
        stream.processAnnotations(BatchState.class);

        FileOutputStream fos;
        File file;

        if (batchState.getCurrentBatch() == null) {
            batchState.prepareNullBatchSave();
        }

        User user = batchState.getUser();
        try {
            file = new File(SAVE_PATH + user.getUsername() + XML_EXTENSION);
            if (!file.getParentFile().getParentFile().exists()) {
                file.getParentFile().getParentFile().mkdir();
            }
            if (!file.getParentFile().exists()) {
                file.getParentFile().mkdir();
            }
            fos = new FileOutputStream(file);
            stream.toXML(batchState, fos);
        } catch (Exception e) {

        }
    }
    /*
        When logging out:
            LoginFrame loginFrame = new LoginFrame();
            loginFrame.setLocationRelativeTo(null);
            loginFrame.setVisible(true);
     */
}
