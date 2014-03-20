package view;

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
 * Date: 3/18/14
 * Time: 10:50 PM
 * To change this template use File | Settings | File Templates.
 */
public class LoginFrame extends JFrame {

    JTextField usernameTextField;
    JPasswordField passwordField;
    JTextField serverTextField;
    JTextField portTextField;
    JButton loginButton;

    ClientCommunicator clientCommunicator;

    public LoginFrame() {
        this.setTitle("Login");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();

        JLabel usernameLabel = new JLabel("Username: ");
        usernameTextField = new JTextField(10);
        JLabel passwordLabel = new JLabel("Password: ");
        passwordField = new JPasswordField(10);
        JLabel serverLabel = new JLabel("Host server: ");
        serverTextField = new JTextField(10);
        JLabel portLabel = new JLabel("Port: ");
        portTextField = new JTextField(10);
        loginButton = new JButton("Log In");

        loginButton.addActionListener(new ButtonListener());

        c.gridx = 0;
        c.gridy = 0;
        this.add(usernameLabel, c);
        c.gridx = 1;
        this.add(usernameTextField, c);
        c.gridx = 0;
        c.gridy = 1;
        this.add(passwordLabel, c);
        c.gridx = 1;
        this.add(passwordField, c);
        c.gridx = 0;
        c.gridy = 2;
        this.add(serverLabel, c);
        c.gridx = 1;
        this.add(serverTextField, c);
        c.gridx = 0;
        c.gridy = 3;
        this.add(portLabel, c);
        c.gridx = 1;
        this.add(portTextField, c);
        c.gridx = 0;
        c.gridy = 4;
        c.gridwidth = 2;
        this.add(loginButton, c);

        this.pack();
    }

    class ButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent ae) {
            if (ae.getSource() == loginButton) {
                String host = serverTextField.getText();
                int port = Integer.parseInt(portTextField.getText());
                clientCommunicator = new ClientCommunicator(host, port);

                final User user = new User();
                user.setUsername(usernameTextField.getText());
                user.setPassword(new String(passwordField.getPassword()));

                ValidateUser_Params params = new ValidateUser_Params();
                params.setUser(user);
                try {
                    if (clientCommunicator.ValidateUser(params).isValidated()) {
                        EventQueue.invokeLater(new Runnable() {
                            public void run() {
                                SearchFrame frame = new SearchFrame(clientCommunicator, user);
                                frame.setVisible(true);
                            }
                        });
                        LoginFrame.this.setVisible(false);
                        LoginFrame.this.dispose();
                    } else {
                        System.out.println("Unauthorized");
                    }
                } catch (Exception e) {
                    System.out.println("Failed");
                }
            }
        }
    }
}
