package dev.raffsalvetti.diruwfi.gui;

import dev.raffsalvetti.diruwfi.component.AudioPlayerComponent;
import dev.raffsalvetti.diruwfi.component.ResourceLoaderComponent;
import dev.raffsalvetti.diruwfi.model.Recipe;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;

public class MainWindow extends JFrame {

    private static final Logger logger = LogManager.getLogger(MainWindow.class);

    private final String PREF_SELECTED_RUNES = "selected_runes";
    private final String PREF_SELECTED_ITEM_TYPES = "selected_item_types";
    
    private final ResourceLoaderComponent resourceLoader = ResourceLoaderComponent.getInstance();

    private JTable mainTable;
    private JLabel statusLabel;
    private TexturedJPanel mainPanel;
    private FontMetrics fontMetrics;
    private List<String> runes;
    private List<String> selectedRunes;
    private List<String> itemTypes;
    private List<String> selectedItemTypes;
    private List<Recipe> filtered;

    private HashMap<String, AudioPlayerComponent> audioPlayers;

    private final AbstractAction runesAction = new AbstractAction() {

        @Override
        public void actionPerformed(ActionEvent evt) {
            var cb = (MyCheckBox)evt.getSource();
            logger.info("Action from rune checkbox; Value="+cb.getLabel()+";Selected="+cb.isSelected());

            new AudioPlayerComponent(resourceLoader.soundResources.get("rune")).play();

            if(cb.isSelected()) {
                selectedRunes.add(cb.getLabel());
            } else {
                selectedRunes.remove(cb.getLabel());
            }
            applyFilter();
        }
        
    };

    private final AbstractAction itemTypeAction = new AbstractAction() {

        @Override
        public void actionPerformed(ActionEvent evt) {
            var cb = (MyCheckBox)evt.getSource();
            logger.info("Action from item type checkbox; Value="+cb.getLabel()+";Selected="+cb.isSelected());

            if(cb.getLabel().equals("wand")) {
                new AudioPlayerComponent(resourceLoader.soundResources.get("wand")).play();
            }

            if(cb.getLabel().equals("sword")) {
                new AudioPlayerComponent(resourceLoader.soundResources.get("sword")).play();
            }

            if(cb.getLabel().equals("staff")) {
                new AudioPlayerComponent(resourceLoader.soundResources.get("staff")).play();
            }

            if(cb.getLabel().equals("any shield")) {
                new AudioPlayerComponent(resourceLoader.soundResources.get("metalshield")).play();
            }

            if(cb.getLabel().equals("armor")) {
                new AudioPlayerComponent(resourceLoader.soundResources.get("platearmor")).play();
            }

            if(cb.getLabel().equals("helm")) {
                new AudioPlayerComponent(resourceLoader.soundResources.get("helm")).play();
            }

            if(cb.getLabel().equals("missile weapon")) {
                new AudioPlayerComponent(resourceLoader.soundResources.get("bow")).play();
            }

            if(Arrays.stream(new String[] {"polearm", "club"}).anyMatch(x -> x.equals(cb.getLabel()))) {
                new AudioPlayerComponent(resourceLoader.soundResources.get("woodweaponlarge")).play();
            }

            if(cb.getLabel().equals("spear")) {
                new AudioPlayerComponent(resourceLoader.soundResources.get("javelins")).play();
            }

            if(Arrays.stream(new String[] {"axe", "hammer", "melee weapon", "weapon", "paladin item", "hand to hand"}).anyMatch(x -> x.equals(cb.getLabel()))) {
                new AudioPlayerComponent(resourceLoader.soundResources.get("largemetalweapon")).play();
            }

            if(Arrays.stream(new String[] {"knife", "scepter", "mace"}).anyMatch(x -> x.equals(cb.getLabel()))) {
                new AudioPlayerComponent(resourceLoader.soundResources.get("smallmetalweapon")).play();
            }

            if(cb.isSelected()) {
                selectedItemTypes.add(cb.getLabel());
            } else {
                selectedItemTypes.remove(cb.getLabel());
            }
            applyFilter();
        }
        
    };

    public MainWindow() {
        logger.info("iniciando main window");
        selectedRunes = new ArrayList<>();
        selectedItemTypes = new ArrayList<>();
        audioPlayers = new HashMap<>();
    }

    private void buildMainWindow() {
        setTitle("Diablo 2 Rune Word Item Finder");
        setIconImages(resourceLoader.defaultIcons);
        setSize(1270, 710);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);

        //close window with escape
        getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(
                KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), "Cancel");
        getRootPane().getActionMap().put("Cancel", new AbstractAction()
        {
            public void actionPerformed(ActionEvent e)
            {
                System.exit(0);
            }
        });

        mainPanel = new TexturedJPanel(resourceLoader.defaultBackgroundTexture);

        fontMetrics = mainPanel.getFontMetrics(resourceLoader.defaultFont);
        mainPanel.setLayout(new GridBagLayout());
        add(mainPanel);
    }

    private FakeTransparentPanel createFakeTransparentPanel(String title) {
        System.out.print("createFakeTransparentPanel begin\n");
        var p = new FakeTransparentPanel();
        p.setLayout(new FlowLayout());
        var pd = new Dimension(getWidth(), 4 * fontMetrics.getHeight());
        p.setPreferredSize(pd);
        var border = BorderFactory.createTitledBorder(title);
        border.setTitleColor(resourceLoader.colorGold);
        border.setTitleJustification(TitledBorder.CENTER);
        p.setBorder(border);
        System.out.print("createFakeTransparentPanel end\n");
        return p;
    }

    private void buildFiltersPanel() {
        var gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.anchor = GridBagConstraints.PAGE_START;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1;
        gbc.weighty = 0;
        gbc.insets = new Insets(5, 5, 5, 5);

        var p = createFakeTransparentPanel("Runes");
        for (String r : runes) {
            var cb = new MyCheckBox(r);
            cb.setForeground(resourceLoader.colorGold);
            cb.setSelected(selectedRunes.contains(r));
            cb.addActionListener(runesAction);
            p.add(cb);
        }
        mainPanel.add(p, gbc);

        p = createFakeTransparentPanel("Item Types");
        for (String r : itemTypes) {
            var cb = new MyCheckBox(r);
            cb.setForeground(resourceLoader.colorGold);
            cb.setSelected(selectedItemTypes.contains(r));
            cb.addActionListener(itemTypeAction);
            p.add(cb);
        }
        mainPanel.add(p, gbc);
    }

    private void buildMainTable() {
        mainTable = new JTable();
        mainTable.setOpaque(false);
        mainTable.setDefaultRenderer(TableCellInfo.class, new MyTableCellRender());
        mainTable.getTableHeader().setDefaultRenderer(new MyTableHeaderRenderer());
        mainTable.getTableHeader().setReorderingAllowed(false);
        mainTable.setColumnSelectionAllowed(false);
        mainTable.setAutoCreateRowSorter(false);
        mainTable.setAutoCreateRowSorter(false);
        mainTable.setDragEnabled(false);
        mainTable.setShowGrid(true);
        mainTable.setGridColor(resourceLoader.colorGold);
        mainTable.setModel(new MainTableModel(filtered, selectedRunes, selectedItemTypes));

        var gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.weightx = 1;
        gbc.weighty = 1;
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.BOTH;

        var sp = new JScrollPane(mainTable);
        sp.setOpaque(false);
        sp.getViewport().setOpaque(false);
        sp.setVerticalScrollBar(new MyScrollBar(JScrollBar.VERTICAL));
        sp.setHorizontalScrollBar(new MyScrollBar(JScrollBar.HORIZONTAL));
        mainPanel.add(sp, gbc);
    }

    private void buildStatusBar() {
        statusLabel = new JLabel();
        statusLabel.setForeground(resourceLoader.colorGold);

        var p = new FakeTransparentPanel();
        p.setBorder(new BevelBorder(BevelBorder.LOWERED));
        p.setPreferredSize(new Dimension(getWidth(), (int)(fontMetrics.getHeight() * 1.05)));
        p.setLayout(new BoxLayout(p, BoxLayout.X_AXIS));
        p.add(statusLabel);

        var gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.weightx = 1;
        gbc.weighty = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        mainPanel.add(p, gbc);
    }

    private void setStatus() {
        this.statusLabel.setText(String.format("Total items: %d; Filtered Items: %d; Selected Runes: %d; Selected Item Types: %d", 
        resourceLoader.database.recipes.size(), 
        filtered.size(),
        selectedRunes.size(),
        selectedItemTypes.size()
        ));
    }

    public void initialDataLoad() {
        logger.debug("initialDataLoad");
        this.runes = resourceLoader.database.recipes.stream()
        .flatMap(x -> x.runes.stream()).distinct().sorted()
        .collect(Collectors.toList());

        this.itemTypes = resourceLoader.database.recipes.stream()
        .flatMap(x -> x.items.stream()).distinct().sorted()
        .collect(Collectors.toList());

        filtered = resourceLoader.database.recipes;

        if(resourceLoader.preferences != null) {
            selectedRunes = Arrays.stream(resourceLoader.preferences.get(PREF_SELECTED_RUNES, "").split(",")).filter(x -> x != null && !x.isEmpty()).collect(Collectors.toList());
            selectedItemTypes = Arrays.stream(resourceLoader.preferences.get(PREF_SELECTED_ITEM_TYPES, "").split(",")).filter(x -> x != null && !x.isEmpty()).collect(Collectors.toList());
        }
    }

    private int countMatches(List al, List bl) {
        int r = 0;
        for (Object a : al) {
            for (Object b : bl) {
                if(a.equals(b))
                    r++;
            }
        }
        return r * 100 / al.size();
    }

    private void applyFilter() {
        logger.info("applyFilter");
        var stream = resourceLoader.database.recipes.stream();

        if(!selectedRunes.isEmpty()) {
            stream = stream.filter(x -> x.runes.stream().anyMatch(k -> selectedRunes.stream().anyMatch(z -> z.equals(k))));
            stream = stream.sorted((a, b) -> countMatches(b.runes, selectedRunes) - countMatches(a.runes, selectedRunes));
        }
        
        if(!selectedItemTypes.isEmpty())
            stream = stream.filter(x -> new HashSet<>(x.items).containsAll(selectedItemTypes));
        
        filtered = stream.collect(Collectors.toList());
        mainTable.setModel(new MainTableModel(filtered, selectedRunes, selectedItemTypes));

        try {
            var dfs = Recipe.class.getDeclaredFields();
            for (int i = 0; i < mainTable.getColumnModel().getColumnCount() - 1; i++) {
                var maxLength = 0;
                for (Recipe rec : filtered) {
                    if (dfs[i].getType() == List.class) {
                        var list = (List<String>) dfs[i].get(rec);
                        for (String s: list) {
                            if(s.length() > maxLength)
                                maxLength = s.length();
                        }
                    } else {
                        var s = (String) dfs[i].get(rec);
                        if(s.length() > maxLength)
                            maxLength = s.length();
                    }
                }
                logger.info(String.format("Column[%d]=%d", i, maxLength));
                maxLength*=i==1||i==3?35:15;
                if(maxLength > 0) {
                    mainTable.getColumnModel().getColumn(i).setMinWidth(maxLength);
                    mainTable.getColumnModel().getColumn(i).setMaxWidth(maxLength);
                    mainTable.getColumnModel().getColumn(i).setPreferredWidth(maxLength);
                }
            }
            for (int i = 0; i < filtered.size(); i++) {
                mainTable.setRowHeight(i, (int) filtered.get(i).properties.stream().filter(x -> x != null && !x.isEmpty()).count() * fontMetrics.getHeight());
            }

        } catch (Exception ex) {

        }

        if(resourceLoader.preferences != null) {
            resourceLoader.preferences.put(PREF_SELECTED_RUNES, String.join(",", selectedRunes));
            resourceLoader.preferences.put(PREF_SELECTED_ITEM_TYPES, String.join(",", selectedItemTypes));
        }
        setStatus();
    }

    public void buildGui() {
        buildMainWindow();
        buildFiltersPanel();
        buildMainTable();
        buildStatusBar();
        setStatus();
        pack();
        applyFilter();
    }
}
