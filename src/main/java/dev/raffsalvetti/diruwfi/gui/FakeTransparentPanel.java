package dev.raffsalvetti.diruwfi.gui;

import dev.raffsalvetti.diruwfi.component.ResourceLoaderComponent;

import javax.swing.*;
import java.awt.*;

public class FakeTransparentPanel extends JPanel {

    private final ResourceLoaderComponent resourceLoader = ResourceLoaderComponent.getInstance();

    @Override
    protected void paintComponent(Graphics g) {
        var g2 = (Graphics2D)g;

        // drawing the background
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

        // filling alpha rectangle
        g2.setColor(resourceLoader.colorShadow);
        g2.fillRect(0, 0, getWidth(), getHeight());
    }
}
