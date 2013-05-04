package sg.cfg;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.configuration.file.YamlConfiguration;

import sg.main.SurvivalGames;
import sg.objects.Map;
import sg.objects.Spawn;

public class MapsManager {
	private SurvivalGames plugin;
	public MapsManager(SurvivalGames instance) { this.plugin = instance; }
	
	YamlConfiguration cfg = new YamlConfiguration();
	File cfgFile = new File("plugins" + File.separator + "SurvivalGames" + File.separator + "maps.yml");
	
	private String mname(int index) { return "maps.map" + index + ".name"; }
	private String mpath(int index) { return "maps.map" + index + ".world"; }
	
	public int count;
	public ArrayList<Map> maps = new ArrayList<Map>();
	
	/** Loads all the map configuration */
	public void load() {
		try { cfg = YamlConfiguration.loadConfiguration(cfgFile); }
		catch (Exception ex) { plugin.logger.log("ERROR", ex.getMessage()); }
		
		plugin.logger.log("LOG", "Loading maps settings...");
		
		this.count = cfg.getInt("maps.count");	
		
		for (int i = 0; i < count; i++) {
			plugin.logger.log("LOG", "Loading map: " + cfg.getString(mname(i)));
			
			Map map = new Map(cfg.getString(mname(i)), plugin.getServer().getWorldContainer().getAbsolutePath() + cfg.getString(mpath(i).replace(".", "")), i);
			try{
			maps.add(i, map);
			System.out.println(maps);
			} catch(NullPointerException e){
				System.out.println(e.getMessage());
				System.out.println("There was an error creating adding map object to map list!");
			}
		}
		
		plugin.logger.log("LOG", "All maps loaded!");
		System.out.println(cfg.getString("maps.map0.world"));
	}
	
	/** Loads the spawnpoints for each map */
	public void loadSpawnPoints() {
		for (Map map : maps) {
			plugin.logger.log("LOG", "Loading spawnpoints for map " + map.name + "!");
			
			Spawn[] spawns = new Spawn[24];
			
			for (int i = 0; i < 24; i++) {
				List<Double> coords = cfg.getDoubleList("maps.map" + map.index + ".spawn-points.spawn" + (i+1));
				spawns[i] = new Spawn(coords.get(0), coords.get(1), coords.get(2));
			}
			
			map.spawns = spawns;
		}
	}
}
