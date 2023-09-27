package dev.raffsalvetti.diruwfi.gui;

import java.awt.*;

public class TableCellInfo {
    public Object value;
    public Color color;

    public TableCellInfo(){}

    public TableCellInfo(Object o, Color c) {
        this.value = o;
        this.color = c;
    }
}
