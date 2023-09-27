package dev.raffsalvetti.diruwfi.component;

import com.google.gson.Gson;
import dev.raffsalvetti.diruwfi.DiruwofApplication;
import dev.raffsalvetti.diruwfi.gui.MainWindow;
import dev.raffsalvetti.diruwfi.model.Database;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.*;
import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.prefs.Preferences;

public class ResourceLoaderComponent {
	private static final Logger logger = LogManager.getLogger(MainWindow.class);
    public Database database;
	public Font defaultFont;
	public List<Image> defaultIcons;
	public Image scrollThumb;
	public Image scrollButton;
	public Preferences preferences;
	public Image defaultBackgroundTexture;
	public Image checked;
	public Image unchecked;
	public Color colorGold;
	public Color colorRed;
	public Color colorGreen;
	public Color colorShadow;
	public Color colorRune;

	public HashMap<String, URI> soundResources;

	private static ResourceLoaderComponent instance;

    private ResourceLoaderComponent() {
        logger.info("Criando RawRecipeDatabaseComponent");

		colorGold = new Color(242,232,182);
		colorRed = new Color(200, 7, 0);
		colorGreen = new Color(0,200,40);
		colorShadow = new Color(0,0,0, 0.64f);
		colorRune = new Color(230,80,10);

        try {
			var is = getClass().getClassLoader().getResourceAsStream("recipe_database.json");
			var br = new BufferedReader(new InputStreamReader(is));
			var sb = new StringBuilder();
			String line;
			while((line = br.readLine()) != null) {
				sb.append(line);
			}
			var gson = new Gson();
			database = gson.fromJson(sb.toString(), Database.class);
		} catch (IOException e) {
			logger.error("impossivel carregar base de dados", e);
		}

		try {
			var is = ResourceLoaderComponent.class.getClassLoader().getResourceAsStream("Avqest-eeel.ttf");
			defaultFont = Font.createFont(Font.TRUETYPE_FONT, is).deriveFont(20f);
		} catch(Exception ex) {
			defaultFont = new Font("Arial", Font.PLAIN, 13);
		}

		try {
			defaultIcons = new ArrayList<Image>();
			for (String iName : new String[] {"16", "32", "48", "64", "128"}) {
				var ni = ImageIO.createImageInputStream(ResourceLoaderComponent.class.getClassLoader().getResourceAsStream("icon-"+iName+".png"));
				defaultIcons.add(ImageIO.read(ni));
			}
		} catch(Exception ex) {
			logger.error("impossivel carregar icones", ex);
		}

		try {
			preferences = Preferences.userNodeForPackage(DiruwofApplication.class);
		} catch (Exception ex) {
			logger.error("impossivel carregar preferencias", ex);
		}

		try {
			var ni = ImageIO.createImageInputStream(ResourceLoaderComponent.class.getClassLoader().getResourceAsStream("defaultbg.jpg"));
			defaultBackgroundTexture = ImageIO.read(ni);
			ni = ImageIO.createImageInputStream(ResourceLoaderComponent.class.getClassLoader().getResourceAsStream("checked.jpg"));
			checked = ImageIO.read(ni);
			ni = ImageIO.createImageInputStream(ResourceLoaderComponent.class.getClassLoader().getResourceAsStream("unchecked.jpg"));
			unchecked = ImageIO.read(ni);
		} catch(Exception ex) {
			logger.error("impossivel carregar icones", ex);
		}

		try {
			var ni = ImageIO.createImageInputStream(ResourceLoaderComponent.class.getClassLoader().getResourceAsStream("scroll_button.png"));
			scrollButton = ImageIO.read(ni);
			ni = ImageIO.createImageInputStream(ResourceLoaderComponent.class.getClassLoader().getResourceAsStream("scroll_thumb.png"));
			scrollThumb = ImageIO.read(ni);
		} catch(Exception ex) {
			logger.error("impossivel carregar icones", ex);
		}

		try {
			soundResources = new HashMap<String, java.net.URI>();
			var soundFileNameList = new String[] {"bow", "helm", "largemetalweapon", "metalshield", "platearmor", "rune", "smallmetalweapon", "staff", "sword", "wand", "woodweaponlarge", "javelins"};
			for (String s: soundFileNameList) {
				soundResources.put(s, ResourceLoaderComponent.class.getClassLoader().getResource(s + ".wav").toURI());
//				var ni = ResourceLoaderComponent.class.getClassLoader().getResourceAsStream(s + ".wav");
//				sounds.put(s, AudioSystem.getAudioInputStream(new BufferedInputStream(ni)));
			}
		} catch (Exception ex) {
			logger.error("impossivel carregar sons", ex);
		}
    }

	public static ResourceLoaderComponent getInstance() {
		if(instance == null)
			instance = new ResourceLoaderComponent();
		return instance;
	}
}
