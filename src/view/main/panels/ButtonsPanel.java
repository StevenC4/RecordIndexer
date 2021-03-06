package view.main.panels;

import shared.communication.SubmitBatch_Params;
import view.main.MainContainerFrame;
import view.state.BatchState;
import view.state.BatchState.BatchStateListener;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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
        ZoomInButton.addActionListener(new ZoomInButtonListener());

        ZoomOutButton = new JButton("Zoom Out");
        ZoomOutButton.addActionListener(new ZoomOutListener());

        InvertImageButton = new JButton("Invert Image");
        InvertImageButton.addActionListener(new ToggleInvertListener());

        ToggleHighlightsButton = new JButton("Toggle Highlights");
        ToggleHighlightsButton.addActionListener(new ToggleHighlighListener());

        SaveButton = new JButton("Save");
        SaveButton.addActionListener(new SaveButtonListener());

        SubmitButton = new JButton("Submit");
        SubmitButton.addActionListener(new SubmitButtonListener());

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

    public void saveState() {
        batchState.saveBatch();
    }

    @Override
    public void batchDownloaded() {
        enableButtons();
    }

    @Override
    public void cellSelected() {}

    @Override
    public void imageZoomed() {}

    @Override
    public void showHighlightToggled() {}

    @Override
    public void isInvertedToggled() {}

    @Override
    public void originMoved() {}

    @Override
    public void cellUpdated(String value, int row, int col) {}

    @Override
    public void batchSaved() {}

    @Override
    public void batchSubmitted() {
        disableButtons();
    }

    class ZoomInButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            batchState.incrementZoom();
        }
    }

    class ZoomOutListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            batchState.decrementZoom();
        }
    }

    class ToggleHighlighListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            batchState.toggleShowHighlight();
        }
    }

    class ToggleInvertListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            batchState.toggleIsInverted();
        }
    }

    class SaveButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            saveState();
        }
    }

    class SubmitButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            batchState.submitBatch();
        }
    }
}
