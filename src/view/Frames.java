package view;

import shared.model.User;
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
        /*EventQueue.invokeLater(new Runnable() {
            public void run() {
                LoginFrame frame = new LoginFrame();
                frame.setLocationRelativeTo(null);
                frame.setVisible(true);
            }
        });*/
        /*EventQueue.invokeLater(new Runnable() {
            public void run() {
                User user = new User("test1", "test1");
                user.setIndexedRecords(0);
                user.setFirstName("Test");
                user.setLastName("One");
                LoginSuccessDialog frame = new LoginSuccessDialog(new ClientCommunicator(), user);
                frame.setLocationRelativeTo(null);
                frame.setVisible(true);
            }
        });*/
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                User user = new User("test1", "test1");
                user.setIndexedRecords(0);
                user.setFirstName("Test");
                user.setLastName("One");
                MainContainerFrame mainContainerFrame = new MainContainerFrame(user);
                mainContainerFrame.setLocationRelativeTo(null);
                mainContainerFrame.setVisible(true);
            }
        });
    }
}
