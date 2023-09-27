package dev.raffsalvetti.diruwfi.gui;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;

public class TexturedJPanel extends JPanel {
    private Image bg;

    public TexturedJPanel(String bgPath) {
        try {
            bg = ImageIO.read(new File(bgPath));
        } catch (Exception ex) {

        }
    }

    public TexturedJPanel(Image bg) {
        this.bg = bg;
    }

    @Override
    protected void paintComponent(Graphics g) {
        if(bg == null) return;
        g.drawImage(bg, 0,0, this);
    }
}
