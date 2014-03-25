package view.main.panels;

import shared.communication.DownloadFile_Results;
import shared.communication.GetSampleImage_Params;
import shared.communication.GetSampleImage_Result;
import shared.model.Batch;
import view.BatchState;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

/**
 * Created with IntelliJ IDEA.
 * User: Steven
 * Date: 3/22/14
 * Time: 10:20 PM
 * To change this template use File | Settings | File Templates.
 */
public class ViewSamplePanel extends JDialog {

    BatchState batchState;

    JButton CloseButton;

    public ViewSamplePanel(BatchState batchState) {

        this.setTitle("Sample image from " + batchState.getCurrentProject().getTitle());
        this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        this.setResizable(false);
        this.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();

        this.batchState = batchState;

        GetSampleImage_Params params = new GetSampleImage_Params();
        params.setUser(batchState.getUser());
        params.setProjectId(batchState.getCurrentProject().getProjectId());

        try {
            GetSampleImage_Result sampleResult = batchState.getClientCommunicator().GetSampleImage(params);

            Batch batch = sampleResult.getBatch();
            DownloadFile_Results imageResult = batchState.getClientCommunicator().DownloadFile(batch.getPath());

            Image image = new ImageIcon(imageResult.getBytes()).getImage();
            // Scale image to 50% of original size
            image = getScaledImage(image, 50);

            JLabel imageLabel = new JLabel(new ImageIcon(image));

            c.gridx = 0;
            c.gridy = 0;
            this.add(imageLabel, c);

            CloseButton = new JButton("Close");
            CloseButton.addActionListener(new CloseButtonListener());

            c.gridy = 1;
            c.insets = new Insets(5, 0, 5, 0);
            this.add(CloseButton, c);

        } catch (Exception e) {}

        this.pack();
    }

    class CloseButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            ViewSamplePanel.this.dispose();
        }
    }

    private Image getScaledImage(Image srcImg, int percentage){
        float percent = (float)percentage / 100;

        BufferedImage bufferedImage = new BufferedImage(srcImg.getWidth(null), srcImg.getHeight(null), BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = (Graphics2D)bufferedImage.getGraphics();
        g2.drawImage(srcImg, 0, 0, null);
        g2.dispose();

        return bufferedImage.getScaledInstance((Integer)Math.round(srcImg.getWidth(null) * percent), (Integer)Math.round(srcImg.getHeight(null) * percent), Image.SCALE_SMOOTH);
    }
}
