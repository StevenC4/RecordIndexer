package view;

import shared.model.User;
import view.login.LoginFrame;
import view.main.MainContainerFrame;

import java.awt.*;

/**
 * Created with IntelliJ IDEA.
 * User: Steven
 * Date: 3/18/14
 * Time: 3:06 PM
 * To change this template use File | Settings | File Templates.
 */
public class Frames {
    public static void main(String[] args) {

        String host = "localhost";
        int port = 8081;

        if (args.length == 2) {
            host = args[0];
            port = Integer.parseInt(args[1]);
        } else if (args.length == 1) {
            try {
                port = Integer.parseInt(args[0]);
            } catch (Exception e) {
                host = args[0];
            }
        }

        final String finalHost = host;
        final int finalPort = port;

        EventQueue.invokeLater(new Runnable() {
            public void run() {
                LoginFrame frame = new LoginFrame(finalHost, finalPort);
                frame.setLocationRelativeTo(null);
                frame.setVisible(true);
            }
        });
    }
}
