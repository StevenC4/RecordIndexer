package view.main.panels;

import view.BatchState;
import view.BatchState.BatchStateListener;

import javax.swing.*;
import java.awt.*;

/**
 * Created with IntelliJ IDEA.
 * User: Steven
 * Date: 3/25/14
 * Time: 3:42 PM
 * To change this template use File | Settings | File Templates.
 */
public class ButtonsPanel extends JPanel implements BatchStateListener {

    BatchState batchState;

    JButton ZoomInButton;
    JButton ZoomOutButton;
    JButton InvertImageButton;
    JButton ToggleHighlightsButton;
    JButton SaveButton;
    JButton SubmitButton;

    public ButtonsPanel(BatchState batchState) {
        this.setLayout(new FlowLayout(FlowLayout.LEFT));

        this.batchState = batchState;

        ZoomInButton = new JButton("Zoom In");
        ZoomOutButton = new JButton("Zoom Out");
        InvertImageButton = new JButton("Invert Image");
        ToggleHighlightsButton = new JButton("Toggle Highlights");
        SaveButton = new JButton("Save");
        SubmitButton = new JButton("Submit");

        if (batchState.getCurrentBatch() == null) {
            disableButtons();
        }

        this.add(ZoomInButton);
        this.add(ZoomOutButton);
        this.add(InvertImageButton);
        this.add(ToggleHighlightsButton);
        this.add(SaveButton);
        this.add(SubmitButton);
    }

    private void disableButtons() {
        ZoomInButton.setEnabled(false);
        ZoomOutButton.setEnabled(false);
        InvertImageButton.setEnabled(false);
        ToggleHighlightsButton.setEnabled(false);
        SaveButton.setEnabled(false);
        SubmitButton.setEnabled(false);
    }

    private void enableButtons() {
        ZoomInButton.setEnabled(true);
        ZoomOutButton.setEnabled(true);
        InvertImageButton.setEnabled(true);
        ToggleHighlightsButton.setEnabled(true);
        SaveButton.setEnabled(true);
        SubmitButton.setEnabled(true);
    }

    @Override
    public void batchDownloaded() {
        enableButtons();
    }

    @Override
    public void fieldSelected() {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void recordSelected() {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void batchSubmitted() {
        //To change body of implemented methods use File | Settings | File Templates.
    }
}
