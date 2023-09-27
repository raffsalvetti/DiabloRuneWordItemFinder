package dev.raffsalvetti.diruwfi.gui;

import javax.swing.*;
import java.awt.*;

public class MyScrollBar extends JScrollBar {
    public MyScrollBar() {
        setUI(new MyScrollBarUI());
    }

    public MyScrollBar(int orientation) {
        this();
        setOrientation(orientation);
    }
}
