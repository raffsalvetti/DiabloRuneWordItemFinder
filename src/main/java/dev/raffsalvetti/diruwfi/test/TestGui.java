package dev.raffsalvetti.diruwfi.test;

import dev.raffsalvetti.diruwfi.component.ResourceLoaderComponent;
import dev.raffsalvetti.diruwfi.gui.*;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.text.html.ListView;
import java.awt.*;
import java.util.ArrayList;

public class TestGui extends JFrame {

    private final ResourceLoaderComponent resourceLoader = ResourceLoaderComponent.getInstance();

    public TestGui() {
        setTitle("Test Application");
        setIconImages(resourceLoader.defaultIcons);
        setSize(640, 480);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);

        var tPanel = new TexturedJPanel(resourceLoader.defaultBackgroundTexture);
        tPanel.setLayout(new GridBagLayout());

        var fontMetrics = tPanel.getFontMetrics(resourceLoader.defaultFont);


        var gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.anchor = GridBagConstraints.PAGE_START;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1;
        gbc.weighty = 0;
        gbc.insets = new Insets(5, 5, 5, 5);


        var p = new FakeTransparentPanel();
        p.setLayout(new FlowLayout());
        var pd = new Dimension(getWidth(), 4 * fontMetrics.getHeight());
        p.setPreferredSize(pd);
        var border = BorderFactory.createTitledBorder("Transparent Panel");
        border.setTitleColor(resourceLoader.colorGold);
        border.setTitleJustification(TitledBorder.CENTER);
        p.setBorder(border);
        p.setPreferredSize(new Dimension(320, 240));
        p.setLayout(new FlowLayout());

//        var tLabel = new FakeTransparentLabel("Transparent Label");
//        tLabel.setForeground(Color.red);
//        p.add(tLabel);
//
//        var tCheck = new MyCheckBox("Transparent Checkbox");
//        tCheck.setForeground(Color.orange);
//        p.add(tCheck);

        var sl = new ArrayList<String>();
        for (int i=0; i< 100; i++) {
            sl.add(String.format("Item %d", i+1));
        }

        var tList = new JList<String>();
        tList.setOpaque(false);
        tList.setModel(new AbstractListModel<String>() {

            @Override
            public int getSize() {
                return sl.size();
            }

            @Override
            public String getElementAt(int i) {
                return sl.get(i);
            }
        });
        var sPane = new JScrollPane();
        sPane.setOpaque(false);
        sPane.setBorder(null);
        sPane.setViewportView(tList);
        var ts = new Dimension(320, 200);
        sPane.setMinimumSize(ts);
        sPane.setPreferredSize(ts);
        sPane.setVerticalScrollBar(new MyScrollBar());
        p.add(sPane);

        tPanel.add(p, gbc);
        add(tPanel);
    }
}
