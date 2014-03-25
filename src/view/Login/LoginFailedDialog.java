package view.login;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created with IntelliJ IDEA.
 * User: Steven
 * Date: 3/21/14
 * Time: 11:33 AM
 * To change this template use File | Settings | File Templates.
 */
public class LoginFailedDialog extends JDialog {

    JButton okButton;

    public LoginFailedDialog() {
        this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        this.setTitle("Login Failed");
        this.setModal(true);
        this.setResizable(false);
        this.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();

        JLabel imageLabel = new JLabel(new ImageIcon("src/view/images/fail.png"));
        JLabel failMessageLabel = new JLabel("Invalid username and/or password");

        c.gridx = 0;
        c.gridy = 0;
        c.insets = new Insets(10, 10, 0, 5);
        this.add(imageLabel, c);
        c.gridx = 1;
        c.insets = new Insets(10, 10, 0, 10);
        this.add(failMessageLabel, c);

        okButton = new JButton("OK");
        okButton.addActionListener(new OKButtonListener());

        c.gridx = 0;
        c.gridy = 1;
        c.gridwidth = 2;
        c.insets = new Insets(0, 10, 10, 10);
        this.add(okButton, c);

        this.pack();
    }

    class OKButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            LoginFailedDialog.this.setVisible(false);
            LoginFailedDialog.this.dispose();
        }
    }
}
