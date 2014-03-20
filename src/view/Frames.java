package view;

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
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                LoginFrame frame = new LoginFrame();
                frame.setVisible(true);
            }
        });
    }
}
