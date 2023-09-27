package dev.raffsalvetti.diruwfi.gui;

import dev.raffsalvetti.diruwfi.component.ResourceLoaderComponent;

import javax.swing.*;
import javax.swing.table.TableCellRenderer;
import java.awt.*;

public class MyTableHeaderRenderer implements TableCellRenderer {

    private final ResourceLoaderComponent resourceLoader = ResourceLoaderComponent.getInstance();

    @Override
    public Component getTableCellRendererComponent(JTable jTable, Object o, boolean b, boolean b1, int i, int i1) {
        var label = new FakeTransparentLabel();
        label.setLabel(o.toString());
        label.setOpaque(false);
        label.setForeground(resourceLoader.colorGold);
        return label;
    }
}
