package dev.raffsalvetti.diruwfi.gui;

import dev.raffsalvetti.diruwfi.component.ResourceLoaderComponent;

import javax.swing.*;
import java.awt.*;

public class FakeTransparentLabel extends JComponent {
    private final ResourceLoaderComponent resourceLoader = ResourceLoaderComponent.getInstance();

    //text label
    private String label;

    private final FontMetrics fontMetrics;

    private double labelLength;

    public FakeTransparentLabel() {
        super();
        fontMetrics = getFontMetrics(resourceLoader.defaultFont);
    }

    public FakeTransparentLabel(String label) {
        this();
        setLabel(label);
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
        if (this.label != null && !this.label.isEmpty()) {
            var c = new Canvas();
            var lm = fontMetrics.getStringBounds(label, c.getGraphics());
            labelLength = lm.getWidth();
            setPreferredSize(new Dimension((int) lm.getWidth(), fontMetrics.getHeight()));
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        var g2 = (Graphics2D)g;
        g2.drawImage(
                resourceLoader.defaultBackgroundTexture,
                0,
                0,
                getWidth(),
                getHeight(),
                getX(),
                getY(),
                getX() + getWidth(),
                getY() + getHeight(),
                this);
        g2.setColor(resourceLoader.colorShadow);
        g2.fillRect(0, 0, getWidth(), getHeight());

        g.setColor(getForeground());
        g2.setFont(resourceLoader.defaultFont);
        g2.drawString(label, getWidth() / 2 - (int)labelLength / 2, getHeight() / 2 + fontMetrics.getHeight() / 4);
    }
}
