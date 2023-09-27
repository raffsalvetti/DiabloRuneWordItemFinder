package dev.raffsalvetti.diruwfi.gui;

import dev.raffsalvetti.diruwfi.component.ResourceLoaderComponent;

import javax.swing.*;
import javax.swing.plaf.basic.BasicScrollBarUI;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;

public class MyScrollBarUI extends BasicScrollBarUI {
    private final ResourceLoaderComponent resourceLoader = ResourceLoaderComponent.getInstance();


    private final int BUTTON_SIZE = 15; //15x15

    @Override
    protected Dimension getMaximumThumbSize() {
        int THUMB_WIDTH = 15;
        int THUMB_HEIGHT = 19;
        if (scrollbar.getOrientation() == JScrollBar.VERTICAL)
            return new Dimension(THUMB_WIDTH, THUMB_HEIGHT);
        return new Dimension(THUMB_HEIGHT, THUMB_WIDTH);
    }

    @Override
    protected Dimension getMinimumThumbSize() {
        return getMaximumThumbSize();
    }

    @Override
    protected JButton createIncreaseButton(int orientation) {
        return new ActionButton(orientation);
    }

    @Override
    protected JButton createDecreaseButton(int orientation) {
        return new ActionButton(orientation);
    }

    @Override
    protected void paintTrack(Graphics g, JComponent c, Rectangle r) {
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        /*
        * do not forget to add the button size to the rectangle size
        * */
        g2.drawImage(
                resourceLoader.defaultBackgroundTexture, //img - the specified image to be drawn. This method does nothing if img is null.
                0, //dx1 - the x coordinate of the first corner of the destination rectangle.
                BUTTON_SIZE, //dy1 - the y coordinate of the first corner of the destination rectangle.
                r.width, //dx2 - the x coordinate of the second corner of the destination rectangle.
                BUTTON_SIZE + r.height, //dy2 - the y coordinate of the second corner of the destination rectangle.
                r.x, //sx1 - the x coordinate of the first corner of the source rectangle.
                r.y, //sy1 - the y coordinate of the first corner of the source rectangle.
                r.x + r.width, //sx2 - the x coordinate of the second corner of the source rectangle.
                r.y + r.height, //sy2 - the y coordinate of the second corner of the source rectangle.
                null);

        g2.setColor(resourceLoader.colorShadow);
        g2.fillRect(0, BUTTON_SIZE, r.width, BUTTON_SIZE + r.height);
    }

    @Override
    protected void paintThumb(Graphics g, JComponent c, Rectangle r) {
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.drawImage(resourceLoader.scrollThumb, r.x, r.y, null);
    }

    private class ActionButton extends JButton {
        private int orientation;


        public ActionButton() {
            setContentAreaFilled(false);
            setBorder(BorderFactory.createEmptyBorder());
            var size = new Dimension(BUTTON_SIZE, BUTTON_SIZE);
            setPreferredSize(size);
            setMinimumSize(size);
            setMaximumSize(size);
        }

        public ActionButton(int orientation) {
            this();
            System.out.println(orientation);
            this.orientation = orientation;
        }

        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g;
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            AffineTransform t;
            var image = new BufferedImage(
                    resourceLoader.scrollButton.getWidth(null),
                    resourceLoader.scrollButton.getHeight(null),
                    BufferedImage.TYPE_INT_ARGB);

            var ig = image.createGraphics();

            //copying the default button image to a buffered image to work on it
            ig.drawImage(resourceLoader.scrollButton, 0, 0, null);
            ig.dispose();

            /*
            * this block define the transformations for the button image
            * there is no horizontal scrolling on this application but if there were,
            * SwingConstants.EAST and SwingConstants.WEST should be implemented to
            * rotate the image to point to east oe west side
            *
            * the default image position point to north
            * */
            switch (orientation) {
                case SwingConstants.SOUTH:
                    t = AffineTransform.getScaleInstance(-1, -1);
                    t.translate(-image.getWidth(null), -image.getHeight(null));
                    image = new AffineTransformOp(t, AffineTransformOp.TYPE_NEAREST_NEIGHBOR).filter(image, null);
                    break;
                default:
                    break;
            }

            //drawing the default background to the button
            g2.drawImage(
                    resourceLoader.defaultBackgroundTexture, //img - the specified image to be drawn. This method does nothing if img is null.
                    0, //dx1 - the x coordinate of the first corner of the destination rectangle.
                    0, //dy1 - the y coordinate of the first corner of the destination rectangle.
                    getWidth(), //dx2 - the x coordinate of the second corner of the destination rectangle.
                    getHeight(), //dy2 - the y coordinate of the second corner of the destination rectangle.
                    getX(), //sx1 - the x coordinate of the first corner of the source rectangle.
                    getY(), //sy1 - the y coordinate of the first corner of the source rectangle.
                    getX() + getWidth(), //sx2 - the x coordinate of the second corner of the source rectangle.
                    getY() + getHeight(), //sy2 - the y coordinate of the second corner of the source rectangle.
                    null);

            //applying the shadow effect to the background
            g2.setColor(resourceLoader.colorShadow);
            g2.fillRect(0, 0, getWidth(), getHeight());

            //drawing the button image over
            /*
            * it is necessary to draw the background and the shadow effect (last two steps)
            * because the button image size is smaller than the thumb
            * size and, it has some transparent border
            * */
            g2.drawImage(image, 0, 0, null);
        }
    }
}
