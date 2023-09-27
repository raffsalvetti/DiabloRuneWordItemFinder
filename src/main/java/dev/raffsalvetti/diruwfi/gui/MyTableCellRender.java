package dev.raffsalvetti.diruwfi.gui;

import dev.raffsalvetti.diruwfi.component.ResourceLoaderComponent;

import javax.swing.*;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.util.List;

public class MyTableCellRender implements TableCellRenderer {

    private final ResourceLoaderComponent resourceLoader = ResourceLoaderComponent.getInstance();

    @Override
    public Component getTableCellRendererComponent(JTable table, Object element, boolean isSelected, boolean hasFocus, int row, int col) {
        var p = new JPanel(new GridBagLayout());
        p.setBackground(resourceLoader.colorShadow);

        if(element == null) return p;

        if(col < 2) { // name and level
            var tableCell = (TableCellInfo)element;
            p.setLayout(new GridBagLayout());
            var l = new JLabel((String)tableCell.value);
            l.setForeground(tableCell.color);
            l.setOpaque(false);
            p.add(l);
            return p;
        }

        if(col == 2) { //items
            var ml = (List<TableCellInfo>)element;
            p.setLayout(new GridLayout(ml.size(),1));
            for (TableCellInfo s : ml) {
                if(s.value == null || s.value.equals("")) continue;
                var l = new JLabel();
                l.setText((String)s.value);
                l.setToolTipText((String)s.value);
                l.setForeground(s.color);
                p.add(l);
            }
            return p;
        }

        if(col == 3) { //runes
            var ml = (List<TableCellInfo>)element;
            p.setLayout(new GridLayout(ml.size(),1));
            for (TableCellInfo s : ml) {
                if(s.value == null || s.value.equals("")) continue;
                var l = new JLabel();
                l.setText((String)s.value);
                l.setToolTipText((String)s.value);
                l.setForeground(s.color);
                p.add(l);
            }
            return p;
        }

        if(col == 4) { //properties
            var ml = (List<TableCellInfo>)element;
            p.setLayout(new GridLayout(ml.size(),1));
            for (TableCellInfo s : ml) {
                if(s.value == null || s.value.equals("")) continue;
                var l = new JLabel();
                l.setText((String)s.value);
                l.setToolTipText((String)s.value);
                l.setForeground(s.color);
                p.add(l);
            }
            return p;
        }
        return p;
    }
}
