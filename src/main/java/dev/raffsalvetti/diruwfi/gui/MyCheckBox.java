package dev.raffsalvetti.diruwfi.gui;

import dev.raffsalvetti.diruwfi.component.ResourceLoaderComponent;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;

public class MyCheckBox extends JComponent {

    //width of the image
    private final int SPRITE_W = 22;

    //height of the image
    private final int SPRITE_H = 21;

    //little gap between the image and text
    private final int LABEL_GAP = 5;
    private final ResourceLoaderComponent resourceLoader = ResourceLoaderComponent.getInstance();

    //the text label
    private String label;

    //whether is checked or not
    private boolean selected;

    private final Dimension minimumDimension = new Dimension(SPRITE_W, SPRITE_H);
    private FontMetrics fontMetrics;

    private List<ActionListener> actionListeners;

    public MyCheckBox() {
        super();
        actionListeners = new ArrayList<>();
        setMinimumSize(minimumDimension);
        setPreferredSize(minimumDimension);

        addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                selected = !selected;
                for (var el : actionListeners) {
                    el.actionPerformed(new ActionEvent(MyCheckBox.this, e.getID(), label));
                }
                repaint();
            }
        });

        addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                System.out.printf("addActionListener: actionEvent.getID()=%d", actionEvent.getID());
            }
        });

        fontMetrics = getFontMetrics(resourceLoader.defaultFont);
    }

    public MyCheckBox(String label) {
        this();
        setLabel(label);
    }

    private Dimension fixDimensions(Dimension d) {
        if (d.height < 21)
            d.height = 21;
        if (d.width < 22)
            d.height = 22;
        return d;
    }

    @Override
    public void setMinimumSize(Dimension minimumSize) {
        super.setMinimumSize(fixDimensions(minimumSize));
    }

    @Override
    public void setPreferredSize(Dimension preferredSize) {
        super.setPreferredSize(fixDimensions(preferredSize));
    }

    public String getLabel() {
        return label;
    }

public void setLabel(String label) {
    this.label = label;
    if (this.label != null && !this.label.isEmpty()) {
        var c = new Canvas();
        var lm = fontMetrics.getStringBounds(label, c.getGraphics());
        setPreferredSize(new Dimension(SPRITE_W + LABEL_GAP + (int) lm.getWidth(), Math.max(SPRITE_H, fontMetrics.getHeight())));
    }
}

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

@Override
protected void paintComponent(Graphics g) {
    var g2 = (Graphics2D) g;

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

    if (selected) {
        g2.drawImage(resourceLoader.checked, 0, 0, null);
    } else {
        g2.drawImage(resourceLoader.unchecked, 0, 0, null);
    }

    g.setColor(getForeground());
    g2.setFont(resourceLoader.defaultFont);
    g2.drawString(label, minimumDimension.width + LABEL_GAP, getHeight() / 2 + fontMetrics.getHeight() / 4);
}

    public void addActionListener(ActionListener actionListener) {
        actionListeners.add(actionListener);
    }
}
