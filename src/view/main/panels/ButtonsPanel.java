package view.main.panels;

import view.BatchState;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

/**
 * Created with IntelliJ IDEA.
 * User: Steven
 * Date: 3/25/14
 * Time: 3:42 PM
 * To change this template use File | Settings | File Templates.
 */
public class ButtonsPanel extends JPanel {

    JButton ZoomInButton;
    JButton ZoomOutButton;
    JButton InvertImageButton;
    JButton ToggleHighlightsButton;
    JButton SaveButton;
    JButton SubmitButton;

    public ButtonsPanel(BatchState batchState) {

        //TODO: Anchor buttons left
        this.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        
        ZoomInButton = new JButton("Zoom In");
        ZoomOutButton = new JButton("Zoom Out");
        InvertImageButton = new JButton("Invert Image");
        ToggleHighlightsButton = new JButton("Toggle Highlights");
        SaveButton = new JButton("Save");
        SubmitButton = new JButton("Submit");

        this.setBackground(Color.RED);

        c.insets = new Insets(5, 5, 5, 5);

        c.anchor = GridBagConstraints.WEST;

        c.gridx = 0;
        this.add(ZoomInButton, c);
        c.gridx = 1;
        this.add(ZoomOutButton, c);
        c.gridx = 2;
        this.add(InvertImageButton, c);
        c.gridx = 3;
        this.add(ToggleHighlightsButton, c);
        c.gridx = 4;
        this.add(SaveButton, c);
        c.gridx = 5;
        this.add(SubmitButton, c);
    }
}
