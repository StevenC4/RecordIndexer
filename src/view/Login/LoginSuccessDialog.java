package view.login;

import client.communication.ClientCommunicator;
import shared.model.User;
import view.main.MainContainerFrame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created with IntelliJ IDEA.
 * User: Steven
 * Date: 3/21/14
 * Time: 12:35 PM
 * To change this template use File | Settings | File Templates.
 */
public class LoginSuccessDialog extends JDialog {

    User user;
    ClientCommunicator clientCommunicator;

    JButton okButton;

    public LoginSuccessDialog(ClientCommunicator clientCommunicator, User user) {
        this.clientCommunicator = clientCommunicator;
        this.user = user;

        this.setTitle("Welcome to Indexer");
        this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        this.setLayout(new GridBagLayout());
        this.setResizable(false);
//        this.setPreferredSize(new Dimension(200, 200));
        GridBagConstraints c = new GridBagConstraints();

        JLabel welcomeLabel = new JLabel("    Welcome, " + user.getFirstName() + " " + user.getLastName() + ".");
        JLabel recordsIndexedLabel = new JLabel("    You have indexed " + user.getIndexedRecords() + " record" + (user.getIndexedRecords() != 1 ? "s." : "."));

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setPreferredSize(new Dimension(260, 55));
        panel.setAlignmentX(Component.LEFT_ALIGNMENT);
        panel.add(Box.createRigidArea(new Dimension(0, 10)));
        panel.add(welcomeLabel);
        panel.add(Box.createRigidArea(new Dimension(0, 5)));
        panel.add(recordsIndexedLabel);
        panel.add(Box.createRigidArea(new Dimension(0,7)));

        c.gridx = 0;
        c.gridy = 0;
        c.gridheight = 2;
        this.add(panel, c);

        okButton = new JButton("OK");
        okButton.addActionListener(new OKButtonListener());

        c.gridy = 2;
        c.insets = new Insets(0, 0, 11, 0);
        this.add(okButton, c);

        this.pack();
    }

    class OKButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            LoginSuccessDialog.this.setVisible(false);
            LoginSuccessDialog.this.dispose();
            MainContainerFrame mainContainerFrame = new MainContainerFrame(user);
            mainContainerFrame.setLocationRelativeTo(null);
            mainContainerFrame.setVisible(true);
        }
    }
}
