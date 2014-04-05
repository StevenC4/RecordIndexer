package view.main.components;

import shared.communication.DownloadFile_Results;
import shared.model.Batch;
import shared.model.Field;
import shared.model.Project;
import view.state.BatchState;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.NoninvertibleTransformException;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.*;
import java.util.List;

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

    Point2D imageOrigin;

    private Point2D w_origin;
    private double scale;

    private boolean dragging;
    private Point2D w_dragStart;
    private Point2D w_dragStartOrigin;

    Image image;
    BatchImage batchImage;
    FieldRect fieldRect;
    private boolean isInverted;
    private boolean showHighlight;

    public BatchComponent(BatchState batchState) {
        this.batchState = batchState;
        setBackground(Color.GRAY);

        this.addMouseListener(mouseAdapter);
        this.addMouseMotionListener(mouseAdapter);
        this.addMouseWheelListener(mouseAdapter);
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
            g2.translate(-w_origin.getX(), -w_origin.getY());

            batchImage.draw(g2);
            fieldRect.draw(g2);
        }
    }

    private void drawBackground(Graphics2D g2) {
        g2.setColor(getBackground());
        g2.fillRect(0,  0, getWidth(), getHeight());
    }

    private void invertImage() {
        BufferedImage bufferedImage = new BufferedImage(batchImage.image.getWidth(null), batchImage.image.getHeight(null), BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = bufferedImage.createGraphics();
        g2.drawImage(batchImage.image, 0, 0, null);
        g2.dispose();

        byte reverse[] = new byte[256];
        for (int i = 0; i < 256; i++) {
            reverse[i] = (byte) (255 - i);
        }
        LookupTable lookupTable = new ByteLookupTable(0, reverse);

        LookupOp lop = new LookupOp(lookupTable, null);
        lop.filter(bufferedImage, bufferedImage);

        batchImage.image = bufferedImage;
    }

    @Override
    public void batchDownloaded() {
        project = batchState.getCurrentProject();
        currentBatch = batchState.getCurrentBatch();
        fields = batchState.getCurrentFields();
        selectedField = batchState.getSelectedField();
        selectedRecord = batchState.getSelectedRecord();
        isInverted = batchState.getIsInverted();
        showHighlight = batchState.getShowHighlight();

        w_origin = new Point2D.Double(0, 0);
        scale = 1;

        try {
            // DRAW THE IMAGE

            DownloadFile_Results result = batchState.getClientCommunicator().DownloadFile(currentBatch.getPath());
            image = new ImageIcon(result.getBytes()).getImage();

            int imageWidth = image.getWidth(null);
            int imageHeight = image.getHeight(null);

            double imageOriginX = -imageWidth / 2;
            double imageOriginY = -imageHeight / 2;
            imageOrigin = new Point2D.Double(imageOriginX, imageOriginY);

            Rectangle2D rect = new Rectangle2D.Double(imageOrigin.getX(), imageOrigin.getY(), imageWidth, imageHeight);
            batchImage = new BatchImage(image, rect);

            // DRAW THE SELECTED CELL

            int recordHeight = project.getRecordHeight();

            int firstX = selectedField.getxCoord();
            int firstY = project.getFirstYCoord();
            double cellX = imageOrigin.getX() + firstX;
            double cellY = imageOrigin.getY() + firstY + (selectedRecord * recordHeight);
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
        double cellX = imageOrigin.getX() + firstX;
        double cellY = imageOrigin.getY() + firstY + (selectedRecord * recordHeight);
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
    public void isInvertedToggled() {
        isInverted = !isInverted;
        invertImage();
        repaint();
        // TODO: Use rasterop to invert image
    }

    @Override
    public void showHighlightToggled() {
        showHighlight = batchState.getShowHighlight();
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
            if (showHighlight) {
                Color c = new Color(0, 0, 1, .3f);

                g2.setColor(c);
                g2.fill(rect);
                g2.drawRect((int)rect.getMinX(), (int)rect.getMinY(), (int)rect.getWidth(), (int)rect.getHeight());
            }
        }
    }

    private void initDrag() {
        dragging = false;
        w_dragStart = new Point2D.Double(0, 0);
        w_dragStartOrigin = new Point2D.Double(0, 0);
    }

    private MouseAdapter mouseAdapter = new MouseAdapter() {

        @Override
        public void mousePressed(MouseEvent e) {
            double d_X = e.getX();
            double d_Y = e.getY();

            AffineTransform transform = new AffineTransform();
            transform.translate(getWidth() / 2, getHeight() / 2);
            transform.scale(scale, scale);
            transform.translate(-w_origin.getX(), -w_origin.getY());

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
                w_dragStart = new Point2D.Double(w_X, w_Y);
                double w_dragStartOriginX = w_origin.getX();
                double w_dragStartOriginY = w_origin.getY();
                w_dragStartOrigin = new Point2D.Double(w_dragStartOriginX, w_dragStartOriginY);
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
                transform.translate(-w_dragStartOrigin.getX(), -w_dragStartOrigin.getY());

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

                double w_deltaX = w_X - w_dragStart.getX();
                double w_deltaY = w_Y - w_dragStart.getY();

                double w_originX = w_dragStartOrigin.getX() - w_deltaX;
                double w_originY = w_dragStartOrigin.getY() - w_deltaY;
                w_origin = new Point2D.Double(w_originX, w_originY);

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
                transform.translate(-w_origin.getX(), -w_origin.getY());

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

                    double leftX = imageOrigin.getX() + firstX;
                    double rightX = leftX + cellWidth;

                    if (w_X >= leftX && w_X <= rightX) {
                        newSelectedField = field;
                        break;
                    }
                }

                for (int i = 0; i < project.getRecordsPerImage(); i++) {
                    double upperY = imageOrigin.getY() + firstY + (i * recordHeight);
                    double lowerY = upperY + recordHeight;
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
            if (e.getWheelRotation() > 0) {
                batchState.decrementZoom();
            }
            else if (e.getWheelRotation() < 0) {
                batchState.incrementZoom();
            }
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

// TODO: Use LookupOp to invert colors
