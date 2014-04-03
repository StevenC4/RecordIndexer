package view.main.components;

import shared.communication.DownloadBatch_Result;
import shared.communication.DownloadFile_Results;
import shared.model.Batch;
import shared.model.Field;
import shared.model.Project;
import view.BatchState;

import java.awt.event.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.NoninvertibleTransformException;
import java.awt.geom.Point2D;
import java.util.List;
import javax.swing.*;
import java.awt.*;
import java.awt.geom.Rectangle2D;

/**
 * Created with IntelliJ IDEA.
 * User: Steven
 * Date: 4/2/14
 * Time: 12:15 PM
 * To change this template use File | Settings | File Templates.
 */
public class BatchComponent extends JPanel implements BatchState.BatchStateListener {

    BatchState batchState;

    Project project;
    Batch currentBatch;
    List<Field> fields;
    Field selectedField;
    int selectedRecord;

    int imageOriginX;
    int imageOriginY;

    private double w_originX;
    private double w_originY;
    private double scale;

    private boolean dragging;
    private double w_dragStartX;
    private double w_dragStartY;
    private double w_dragStartOriginX;
    private double w_dragStartOriginY;

    Image image;
    BatchImage batchImage;
    FieldRect fieldRect;

    public BatchComponent(BatchState batchState) {
        this.batchState = batchState;
        setBackground(Color.GRAY);

        this.addMouseListener(mouseAdapter);
        this.addMouseMotionListener(mouseAdapter);
        this.addComponentListener(componentAdapter);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D) g;
        drawBackground(g2);

        if (batchImage != null) {
            g2.translate(getWidth() / 2, getHeight() / 2);
            g2.scale(scale, scale);
            g2.translate(-w_originX, -w_originY);

            batchImage.draw(g2);
            fieldRect.draw(g2);
        }
    }

    private void drawBackground(Graphics2D g2) {
        g2.setColor(getBackground());
        g2.fillRect(0,  0, getWidth(), getHeight());
    }

    @Override
    public void batchDownloaded() {
        project = batchState.getCurrentProject();
        currentBatch = batchState.getCurrentBatch();
        fields = batchState.getCurrentFields();
        selectedField = batchState.getSelectedField();
        selectedRecord = batchState.getSelectedRecord();

        w_originX = 0;
        w_originY = 0;
        scale = 1;

        try {
            // DRAW THE IMAGE

            DownloadFile_Results result = batchState.getClientCommunicator().DownloadFile(currentBatch.getPath());
            image = new ImageIcon(result.getBytes()).getImage();

            int imageWidth = image.getWidth(null);
            int imageHeight = image.getHeight(null);

//            imageOriginX = (getWidth() - image.getWidth(null)) / 2;
//            imageOriginY = (getHeight() - image.getHeight(null)) / 2;
            imageOriginX = -imageWidth / 2;
            imageOriginY = -imageHeight / 2;

            Rectangle2D rect = new Rectangle2D.Double(imageOriginX, imageOriginY, imageWidth, imageHeight);
            batchImage = new BatchImage(image, rect);

            // DRAW THE SELECTED CELL

            int recordHeight = project.getRecordHeight();

            int firstX = selectedField.getxCoord();
            int firstY = project.getFirstYCoord();
            int cellX = imageOriginX + firstX;
            int cellY = imageOriginY + firstY + (selectedRecord * recordHeight);
            int cellWidth = selectedField.getWidth();
            int cellHeight = recordHeight;

            Rectangle2D selectedCellRect = new Rectangle2D.Double(cellX, cellY, cellWidth, cellHeight);
            fieldRect = new FieldRect(selectedCellRect);

        } catch (Exception e) {
            batchImage = null;
        }

        repaint();
    }

    @Override
    public void cellSelected() {
        selectedField = batchState.getSelectedField();
        selectedRecord = batchState.getSelectedRecord();

        int recordHeight = project.getRecordHeight();

        int firstX = selectedField.getxCoord();
        int firstY = project.getFirstYCoord();
        int cellX = imageOriginX + firstX;
        int cellY = imageOriginY + firstY + (selectedRecord * recordHeight);
        int cellWidth = selectedField.getWidth();
        int cellHeight = recordHeight;

        Rectangle2D selectedCellRect = new Rectangle2D.Double(cellX, cellY, cellWidth, cellHeight);
        fieldRect = new FieldRect(selectedCellRect);

        repaint();
    }

    @Override
    public void imageZoomed() {
        scale = batchState.getZoomScale();
        repaint();
    }

    @Override
    public void cellUpdated(String value, int row, int col) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void batchSubmitted() {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    class BatchImage {

        private Image image;
        private Rectangle2D rect;

        public BatchImage(Image image, Rectangle2D rect) {
            this.image = image;
            this.rect = rect;
        }

        public boolean contains(double x, double y) {
            return rect.contains(x, y);
        }

        public void draw(Graphics2D g2) {
            g2.drawImage(image, (int)rect.getMinX(), (int)rect.getMinY(), (int)rect.getMaxX(), (int)rect.getMaxY(),
                    0, 0, image.getWidth(null), image.getHeight(null), null);
        }
    }

    class FieldRect {

        private Rectangle2D rect;

        public FieldRect(Rectangle2D rect) {
            this.rect = rect;
        }

        public void draw(Graphics2D g2) {
            Color c = new Color(0, 0, 1, .2f);

            g2.setColor(c);
            g2.fill(rect);
            g2.drawRect((int)rect.getMinX(), (int)rect.getMinY(), (int)rect.getWidth(), (int)rect.getHeight());
        }
    }

    private void initDrag() {
        dragging = false;
        w_dragStartX = 0;
        w_dragStartY = 0;
        w_dragStartOriginX = 0;
        w_dragStartOriginY = 0;
    }

    private MouseAdapter mouseAdapter = new MouseAdapter() {

        @Override
        public void mousePressed(MouseEvent e) {
            double d_X = e.getX();
            double d_Y = e.getY();

            AffineTransform transform = new AffineTransform();
            transform.translate(getWidth() / 2, getHeight() / 2);
            transform.scale(scale, scale);
            transform.translate(-w_originX, -w_originY);

            Point2D d_Pt = new Point2D.Double(d_X, d_Y);
            Point2D w_Pt = new Point2D.Double();
            try
            {
                transform.inverseTransform(d_Pt, w_Pt);
            }
            catch (NoninvertibleTransformException ex) {
                return;
            }
            double w_X = w_Pt.getX();
            double w_Y = w_Pt.getY();

            boolean hitShape = false;

            if (batchImage.contains(w_X, w_Y)) {
                hitShape = true;
            }

            if (hitShape) {
                dragging = true;
                w_dragStartX = w_X;
                w_dragStartY = w_Y;
                w_dragStartOriginX = w_originX;
                w_dragStartOriginY = w_originY;
            }
        }

        @Override
        public void mouseDragged(MouseEvent e) {
            if (dragging) {
                double d_X = e.getX();
                double d_Y = e.getY();

                AffineTransform transform = new AffineTransform();
                transform.translate(getWidth() / 2, getHeight() / 2);
                transform.scale(scale, scale);
                transform.translate(-w_dragStartOriginX, -w_dragStartOriginY);

                Point2D d_Pt = new Point2D.Double(d_X, d_Y);
                Point2D w_Pt = new Point2D.Double();
                try
                {
                    transform.inverseTransform(d_Pt, w_Pt);
                }
                catch (NoninvertibleTransformException ex) {
                    return;
                }
                double w_X = w_Pt.getX();
                double w_Y = w_Pt.getY();

                double w_deltaX = w_X - w_dragStartX;
                double w_deltaY = w_Y - w_dragStartY;

                w_originX = w_dragStartOriginX - w_deltaX;
                w_originY = w_dragStartOriginY - w_deltaY;

//                notifyOriginChanged(w_originX, w_originY);

                repaint();
            }
        }

        @Override
        public void mouseReleased(MouseEvent e) {
            initDrag();
        }

        @Override
        public void mouseClicked(MouseEvent e) {
            if (currentBatch != null && !dragging) {
                double d_X = e.getX();
                double d_Y = e.getY();

                AffineTransform transform = new AffineTransform();
                transform.translate(getWidth() / 2, getHeight() / 2);
                transform.scale(scale, scale);
                transform.translate(-w_originX, -w_originY);

                Point2D d_Pt = new Point2D.Double(d_X, d_Y);
                Point2D w_Pt = new Point2D.Double();
                try
                {
                    transform.inverseTransform(d_Pt, w_Pt);
                }
                catch (NoninvertibleTransformException ex) {
                    return;
                }
                double w_X = w_Pt.getX();
                double w_Y = w_Pt.getY();

                int recordHeight = project.getRecordHeight();
                int firstY = project.getFirstYCoord();

                Field newSelectedField = selectedField;
                int newSelectedRecord = selectedRecord;

                for (Field field : fields) {
                    int firstX = field.getxCoord();
                    int cellWidth = field.getWidth();

                    int leftX = imageOriginX + firstX;
                    int rightX = leftX + cellWidth;

                    if (w_X >= leftX && w_X <= rightX) {
                        newSelectedField = field;
                        break;
                    }
                }

                for (int i = 0; i < project.getRecordsPerImage(); i++) {
                    int upperY = imageOriginY + firstY + (i * recordHeight);
                    int lowerY = upperY + recordHeight;
                    if (w_Y >= upperY && w_Y <= lowerY) {
                        newSelectedRecord = i;
                        break;
                    }
                }

                if (newSelectedField != selectedField || newSelectedRecord != selectedRecord) {
                    batchState.setSelectedCell(newSelectedField, newSelectedRecord);
                }
            }
        }

        @Override
        public void mouseWheelMoved(MouseWheelEvent e) {
            return;
        }
    };

    private ComponentAdapter componentAdapter = new ComponentAdapter() {

        @Override
        public void componentHidden(ComponentEvent e) {
            return;
        }

        @Override
        public void componentMoved(ComponentEvent e) {
            return;
        }

        @Override
        public void componentResized(ComponentEvent e) {
            if (currentBatch != null) {
//                imageOriginX = (getWidth() - batchImage.image.getWidth(null)) / 2;
//                imageOriginY = (getHeight() - batchImage.image.getHeight(null)) / 2;
            }
        }

        @Override
        public void componentShown(ComponentEvent e) {
            return;
        }
    };
}
