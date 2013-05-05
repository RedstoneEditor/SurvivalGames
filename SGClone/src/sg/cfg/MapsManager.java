package sg.cfg;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.configuration.file.YamlConfiguration;

import sg.main.SurvivalGames;
import sg.objects.Map;
import sg.objects.Spawn;

public class MapsManager {
	private SurvivalGames plugin;
	public MapsManager(SurvivalGames instance) { this.plugin = instance; }
	
	YamlConfiguration cfg = new YamlConfiguration();
	File cfgFile = new File("plugins" + File.separator + "SurvivalGames" + File.separator + "maps.yml");
	
	java.util.Map<String, Map> maps = new HashMap<String, Map>();
	
	public void load() {
		File mapsDir = new File("plugins" + File.separator + "SurvivalGames" + File.separator + "maps");
		File[] fMaps = mapsDir.listFiles();
		
		for (File fMap : fMaps) {
			if (fMap.isFile()) {
				loadMap(fMap);
			}
		}
	}
	
	private void loadMap(File file) {
		String name = file.getName();
		
		Map map = new Map(name);
		
		try { cfg = YamlConfiguration.loadConfiguration(file); }
		catch (Exception ex) { plugin.logger.log(ex); }
		
		for (int i = 1; i <= 24; i++) {
			List<Double> coords = cfg.getDoubleList("map.spawns.spawn" + i);
			Spawn spawn = new Spawn(coords.get(0), coords.get(1), coords.get(2));
			map.spawns.add(spawn);
		}
		
		for (int i = 1; i <= cfg.getInt("map.chests.count"); i++) {
			
		}
		
		
	}	
	
	
	public void Check() {
		plugin.logger.log("Checking files!");
		
		File mapsDir = new File("plugins" + File.separator + "SurvivalGames" + File.separator + "maps");
		if(!mapsDir.exists()) { mapsDir.mkdirs(); }
	}
	
	private void Export(String inputFile, File dest) {
		plugin.logger.log("Exporting " + inputFile + "!");
		
		try {
			dest.createNewFile();
			
			InputStream input = plugin.getResource(inputFile);
			@SuppressWarnings("resource")
			OutputStream output = new FileOutputStream(dest);

			byte[] buffer = new byte[1024];
			int length;

			while ((length = input.read(buffer)) > 0) {
				output.write(buffer, 0, length);
			}
		} catch (Exception ex) {
			plugin.logger.log("Error while exporting " + inputFile + "!");
			plugin.logger.log(ex);
		}
	}
}
