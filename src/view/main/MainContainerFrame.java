package view.main;

import client.communication.ClientCommunicator;
import shared.model.User;
import view.BatchState;
import view.login.LoginFrame;

import javax.swing.*;
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

    JMenuBar menuBar;
    JMenu fileMenu;
    JMenuItem downloadBatchMenuItem;
    JMenuItem logoutMenuItem;
    JMenuItem exitMenuItem;

    public MainContainerFrame(ClientCommunicator clientCommunicator, User user) {
        this.batchState = loadBatchState(user);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.addWindowListener(new WindowCloseAdapter());
        this.setSize(200, 200);

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
        return new BatchState(new ClientCommunicator(), user);
    }
    /*
        When logging out:
            LoginFrame loginFrame = new LoginFrame();
            loginFrame.setLocationRelativeTo(null);
            loginFrame.setVisible(true);
     */
}
