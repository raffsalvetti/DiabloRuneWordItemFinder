package dev.raffsalvetti.diruwfi;

import dev.raffsalvetti.diruwfi.component.ResourceLoaderComponent;
import dev.raffsalvetti.diruwfi.gui.MainWindow;
import dev.raffsalvetti.diruwfi.test.TestGui;

import javax.swing.*;
import javax.swing.plaf.FontUIResource;
import java.awt.*;


//ver https://diablo2.io/runewords/#filter=
public class DiruwofApplication {
	private static void setDefaultFont(Font font){
		var k = UIManager.getDefaults().keys();
		while (k.hasMoreElements()) {
			Object key = k.nextElement();
			Object value = UIManager.get(key);
			if (value instanceof FontUIResource)
				UIManager.put(key, font);
		}
	} 


	public static void main(String[] args) {
		var resourceLoader = ResourceLoaderComponent.getInstance();

		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				setDefaultFont(resourceLoader.defaultFont);

				var mw = new TestGui();
//				mw.pack();
				mw.setVisible(true);

//				var mw = new MainWindow();
//				mw.initialDataLoad();
//				mw.buildGui();
//				mw.setVisible(true);
			}
		});
	}
}
