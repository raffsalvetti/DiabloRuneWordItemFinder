package dev.raffsalvetti.diruwfi.gui;

import dev.raffsalvetti.diruwfi.component.ResourceLoaderComponent;
import dev.raffsalvetti.diruwfi.model.Recipe;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.List;

public class MainTableModel extends AbstractTableModel {

    private final ResourceLoaderComponent resourceLoader = ResourceLoaderComponent.getInstance();

    private final List<Recipe> data;
    private final List<String> selectedRunes;
    private final List<String> selectedItem;

    public MainTableModel(List<Recipe> data, List<String> selectedRunes, List<String> selectedItem) {
        this.data = data;
        this.selectedRunes = selectedRunes;
        this.selectedItem = selectedItem;
    }

    @Override
    public Class<?> getColumnClass(int arg0) {
        return TableCellInfo.class;
    }

    @Override
    public int getColumnCount() {
        return Recipe.class.getDeclaredFields().length;
    }

    @Override
    public String getColumnName(int arg0) {
        var dfs = Recipe.class.getDeclaredFields();
        return dfs[arg0].getName();
    }

    @Override
    public int getRowCount() {
        return data.size();
    }

    @Override
    public Object getValueAt(int row, int col) {
        var el = data.get(row);
        var dfs = el.getClass().getDeclaredFields()[col];
        dfs.setAccessible(true);
        try {
            if(dfs.getType() == List.class) {
                var tcl = new ArrayList<TableCellInfo>();
                for (Object o : (List)dfs.get(el)) {
                    var tc = new TableCellInfo();
                    tc.value = o;
                    tc.color = resourceLoader.colorGold;
                    if(col == 2 && !selectedItem.isEmpty()) { // item type
                        tc.color = resourceLoader.colorRed;
                        if(selectedItem.contains(o))
                            tc.color = resourceLoader.colorGreen;
                    }
                    if(col == 3 && !selectedRunes.isEmpty()) { // rune
                        tc.color = resourceLoader.colorRed;
                        if(selectedRunes.contains(o))
                            tc.color = resourceLoader.colorGreen;
                    }
                    tcl.add(tc);
                }
                return tcl;
            }
            return new TableCellInfo(dfs.get(el), resourceLoader.colorGold);
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public boolean isCellEditable(int arg0, int arg1) {
        return false;
    }
}
