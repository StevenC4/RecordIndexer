package view.login;

import client.communication.ClientCommunicator;
import shared.communication.ValidateUser_Params;
import shared.communication.ValidateUser_Result;
import shared.model.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created with IntelliJ IDEA.
 * User: Steven
 * Date: 3/20/14
 * Time: 10:20 PM
 * To change this template use File | Settings | File Templates.
 */
public class LoginFrame extends JFrame {

    ClientCommunicator clientCommunicator;

    JTextField usernameTextField;
    JPasswordField passwordField;

    JButton loginButton;
    JButton exitButton;

    String host;
    int port;

    public LoginFrame(String host, int port) {
        this.host = host;
        this.port = port;

        clientCommunicator = new ClientCommunicator(host, port);
        this.setTitle("Login to Indexer");
        this.setPreferredSize(new Dimension(375, 133));
        this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        this.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();

        JLabel usernameLabel = new JLabel("Username:");
        usernameTextField = new JTextField(24);
        JLabel passwordLabel = new JLabel("Password:");
        passwordField = new JPasswordField(24);

        c.gridx = 0;
        c.gridy = 0;
        c.insets = new Insets(5, 0, 3, 10);
        this.add(usernameLabel, c);
        c.gridx = 1;
        c.gridwidth = 5;
        c.insets = new Insets(5, 0, 3, 0);
        this.add(usernameTextField, c);
        c.gridx = 0;
        c.gridy = 1;
        c.gridwidth = 1;
        c.insets = new Insets(0, 0, 0, 10);
        this.add(passwordLabel, c);
        c.gridx = 1;
        c.gridwidth = 5;
        c.insets = new Insets(0, 0, 0, 0);
        this.add(passwordField, c);
        this.pack();

        loginButton = new JButton("Login");
        loginButton.addActionListener(new LoginButtonListener());
        exitButton = new JButton("Exit");
        exitButton.addActionListener(new ExitButtonListener());

        JPanel buttonsPanel = new JPanel();
        buttonsPanel.add(loginButton);
        buttonsPanel.add(exitButton);
        c.gridx = 0;
        c.gridy = 2;
        c.gridwidth = 6;
        c.insets = new Insets(5, 0, 0, 0);
        this.add(buttonsPanel, c);
    }

    class LoginButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent ae) {
            String username = usernameTextField.getText();
            String password = new String(passwordField.getPassword());
            try {
                ValidateUser_Result result = clientCommunicator.ValidateUser(new ValidateUser_Params(new User(username, password)));
                if (result.isValidated()) {
                    LoginSuccessDialog successDialog = new LoginSuccessDialog(host, port, result.getUser());
                    successDialog.setLocationRelativeTo(null);
                    successDialog.setVisible(true);
                    successDialog.setModal(true);
                    LoginFrame.this.dispose();
                    LoginFrame.this.setVisible(false);
                } else {
                    LoginFailedDialog failedDialog = new LoginFailedDialog();
                    failedDialog.setLocationRelativeTo(null);
                    failedDialog.setVisible(true);
                    failedDialog.setModal(true);
                }
            } catch (Exception e) {}
        }
    }

    class ExitButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent ae) {
            LoginFrame.this.setVisible(false);
            LoginFrame.this.dispose();
        }
    }
}
