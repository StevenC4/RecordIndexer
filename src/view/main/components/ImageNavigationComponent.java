package view.main.components;

import view.state.BatchState;
import view.state.BatchState.BatchStateListener;

import javax.swing.*;
import java.awt.*;

/**
 * Created with IntelliJ IDEA.
 * User: Steven
 * Date: 3/27/14
 * Time: 5:19 PM
 * To change this template use File | Settings | File Templates.
 */
public class ImageNavigationComponent extends JComponent implements BatchStateListener {

    BatchState batchState;

    public ImageNavigationComponent(BatchState batchState) {
        this.batchState = batchState;

        this.setBackground(Color.GRAY);

        if (batchState != null) {
            // Call a method that draws the batch
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D) g;
        drawBackground(g2);
    }

    private void drawBackground(Graphics2D g2) {
        g2.setColor(getBackground());
        g2.fillRect(0, 0, getWidth(), getHeight());
    }

    @Override
    public void batchDownloaded() {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void cellSelected() {}

    @Override
    public void imageZoomed() {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void isInvertedToggled() {}

    @Override
    public void showHighlightToggled() {}

    @Override
    public void cellUpdated(String value, int row, int col) {}

    @Override
    public void batchSubmitted() {
        //To change body of implemented methods use File | Settings | File Templates.
    }
}
