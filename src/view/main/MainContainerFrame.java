package view.main;

import com.thoughtworks.xstream.XStream;
import shared.model.User;
import view.state.BatchState;
import view.login.LoginFrame;
import view.main.components.BatchComponent;
import view.main.components.ImageNavigationComponent;
import view.main.panels.ButtonsPanel;
import view.main.panels.FieldHelpPanel;
import view.main.panels.FormEntryPanel;
import view.main.panels.TableEntryPanel;
import view.state.WindowState;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.geom.Point2D;
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
    final String BATCH_FILE = "_batchState";
    final String WINDOW_FILE = "_windowState";
    final String XML_EXTENSION = ".xml";

    BatchState batchState;
    WindowState windowState;

    JSplitPane horizontalSplitter;
    JSplitPane verticalSplitter;
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

    String host;
    int port;

    public MainContainerFrame(String host, int port, User user) {
        this.host = host;
        this.port = port;

        this.batchState = loadBatchState(user);
        this.windowState = loadWindowState(user);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.addWindowListener(new WindowCloseAdapter());
        this.setLayout(new BorderLayout());

        int windowPositionX = windowState.getWindowPositionX();
        int windowPositionY = windowState.getWindowPositionY();
        int windowWidth = windowState.getWindowWidth();
        int windowHeight = windowState.getWindowHeight();
        int horizontalBarPosition = windowState.getHorizontalBarPosition();
        int verticalBarPosition = windowState.getVerticalBarPosition();

        this.setLocation(windowPositionX, windowPositionY);
        this.setPreferredSize(new Dimension(windowWidth, windowHeight));

        menuBar = new JMenuBar();
        fileMenu = new JMenu("File");

        horizontalSplitter = new JSplitPane();
        horizontalSplitter.setOrientation(JSplitPane.VERTICAL_SPLIT);
//        horizontalSplitter.setResizeWeight(.5d);

        verticalSplitter = new JSplitPane();
        verticalSplitter.setOrientation(JSplitPane.HORIZONTAL_SPLIT);
//        verticalSplitter.setResizeWeight(.5d);

        downloadBatchMenuItem = new JMenuItem("Download Batch");
        downloadBatchMenuItem.addActionListener(new DownloadBatchMenuItemListener());
        if (batchState.getCurrentBatch() != null) {
            downloadBatchMenuItem.setEnabled(false);
        }
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
        entryTabbedPane.addTab("Table Entry", tableEntryPanel);
        entryTabbedPane.addTab("Form Entry", new JScrollPane(formEntryPanel));
        entryTabbedPane.addChangeListener(new EntryPaneTabListener());

        JTabbedPane helpTabbedPane = new JTabbedPane();
        fieldHelpPanel = new FieldHelpPanel(batchState);
        imageNavigationComponent = new ImageNavigationComponent(batchState);
        helpTabbedPane.addTab("Field Help", fieldHelpPanel);
        helpTabbedPane.addTab("Image Navigation", imageNavigationComponent);


        verticalSplitter.setLeftComponent(entryTabbedPane);
        verticalSplitter.setRightComponent(helpTabbedPane);

        JPanel batchPanel = new JPanel();
        batchPanel.setLayout(new BorderLayout());
        batchComponent = new BatchComponent(batchState);
        batchPanel.add(batchComponent, BorderLayout.CENTER);

        horizontalSplitter.setTopComponent(batchPanel);
        horizontalSplitter.setBottomComponent(verticalSplitter);

        buttonsPanel = new ButtonsPanel(batchState);

        batchState.addListener(this);
        batchState.addListener(buttonsPanel);
        batchState.addListener(batchComponent);
        batchState.addListener(tableEntryPanel);
        batchState.addListener(formEntryPanel);
        batchState.addListener(fieldHelpPanel);
        batchState.addListener(imageNavigationComponent);

        this.add(buttonsPanel, BorderLayout.NORTH);
        this.add(horizontalSplitter, BorderLayout.CENTER);

        horizontalSplitter.setResizeWeight(horizontalBarPosition / windowHeight);
        verticalSplitter.setResizeWeight(verticalBarPosition / windowWidth);

        this.pack();

        horizontalSplitter.setDividerLocation(horizontalBarPosition);
        verticalSplitter.setDividerLocation(verticalBarPosition);
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
        saveWindowState();
    }

    @Override
    public void batchSubmitted() {
        downloadBatchMenuItem.setEnabled(true);
    }

    class WindowCloseAdapter extends WindowAdapter {

        @Override
        public void windowClosing(WindowEvent e) {
            saveBatchState();
            saveWindowState();
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
            saveWindowState();

            MainContainerFrame.this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            LoginFrame loginFrame = new LoginFrame(host, port);
            loginFrame.setVisible(true);
            loginFrame.setLocationRelativeTo(null);
            MainContainerFrame.this.dispose();
        }
    }

    class ExitMenuItemListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {

            saveBatchState();
            saveWindowState();

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

        File file = new File(SAVE_PATH + user.getUsername() + BATCH_FILE + XML_EXTENSION);
        if (file.exists()) {
            try {
                XStream stream = new XStream();
                FileInputStream fis = new FileInputStream(file);
                batchStateTemp = (BatchState) stream.fromXML(fis);
                batchStateTemp.initializeClientCommunicator(host, port);
                batchStateTemp.initializeListeners();
            } catch (Exception e) {
                batchStateTemp = new BatchState(host, port, user);
            }
        } else {
            batchStateTemp = new BatchState(host, port, user);
        }

        return batchStateTemp;
    }

    private WindowState loadWindowState(User user) {
        WindowState windowStateTemp;

        File file = new File(SAVE_PATH + user.getUsername() + WINDOW_FILE + XML_EXTENSION);
        if (file.exists()) {
            try {
                XStream stream = new XStream();
                FileInputStream fis = new FileInputStream(file);
                windowStateTemp = (WindowState) stream.fromXML(fis);
            } catch (Exception e) {
                windowStateTemp = new WindowState();
            }
        } else {
            windowStateTemp = new WindowState();
        }

        return windowStateTemp;
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
            file = new File(SAVE_PATH + user.getUsername() + BATCH_FILE + XML_EXTENSION);
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

    private void saveWindowState() {
        Point2D location = getLocation();
        int verticalBarPosition = verticalSplitter.getDividerLocation();
        int horizontalBarPosition = horizontalSplitter.getDividerLocation();

        windowState.setWindowPositionX((int)location.getX());
        windowState.setWindowPositionY((int) location.getY());
        windowState.setWindowWidth(getWidth());
        windowState.setWindowHeight(getHeight());
        windowState.setVerticalBarPosition(verticalBarPosition);
        windowState.setHorizontalBarPosition(horizontalBarPosition);

        XStream stream = new XStream();
        FileOutputStream fos;
        File file;

        User user = batchState.getUser();
        try {
            file = new File(SAVE_PATH + user.getUsername() + WINDOW_FILE + XML_EXTENSION);
            if (!file.getParentFile().getParentFile().exists()) {
                file.getParentFile().getParentFile().mkdir();
            }
            if (!file.getParentFile().exists()) {
                file.getParentFile().mkdir();
            }
            fos = new FileOutputStream(file);
            stream.toXML(windowState, fos);
        } catch (Exception e) {

        }
    }
}
