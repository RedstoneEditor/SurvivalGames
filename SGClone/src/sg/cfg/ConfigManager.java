package sg.cfg;

import sg.main.SurvivalGames;
import sg.objects.GameState;
import sg.objects.Rank;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.bukkit.configuration.file.YamlConfiguration;

public class ConfigManager {
	private SurvivalGames plugin;
	public ConfigManager(SurvivalGames instance) { this.plugin = instance; }
	
	public YamlConfiguration cfg = new YamlConfiguration();
	File cfgFile = new File("plugins" + File.separator + "SurvivalGames" + File.separator + "config.yml");

	public String format;
	public int minPlayers;
	
	public Map<Rank, String> prefixes = new HashMap<Rank, String>();
	public Map<GameState, Integer> times = new HashMap<GameState, Integer>();
	public Map<GameState, String> motds = new HashMap<GameState, String>();
	public Map<String, String> messages = new HashMap<String, String>();
	
	public Set<Integer> blockWhitelist = new HashSet<Integer>();
	
	/** Loads data from config.yml */
	public void load() {
		try { cfg = YamlConfiguration.loadConfiguration(cfgFile); }
		catch (Exception ex) { plugin.logger.log(ex); }
		
		plugin.logger.log("Loading values from config.yml...");

		this.format = cfg.getString("chat.format");
		this.minPlayers = cfg.getInt("server.min-players");
		
		prefixes.put(Rank.USER, cfg.getString("chat.prefix.user"));
		prefixes.put(Rank.VIP, cfg.getString("chat.prefix.vip"));
		prefixes.put(Rank.MOD, cfg.getString("chat.prefix.mod"));
		prefixes.put(Rank.ADMIN, cfg.getString("chat.prefix.admin"));
		
		times.put(GameState.LOBBY, cfg.getInt("times.lobby"));
		times.put(GameState.PREGAME, cfg.getInt("times.pregame"));
		times.put(GameState.INGAME, cfg.getInt("times.ingame"));
		times.put(GameState.DEATHMATCH, cfg.getInt("times.deathmatch"));
		times.put(GameState.ENDGAME, 10);
		
		motds.put(GameState.LOBBY, cfg.getString("motd.lobby"));
		motds.put(GameState.PREGAME, cfg.getString("motd.pregame"));
		motds.put(GameState.INGAME, cfg.getString("motd.ingame"));
		motds.put(GameState.DEATHMATCH, cfg.getString("motd.deathmatch"));
		motds.put(GameState.ENDGAME, cfg.getString("motd.endgame"));

		messages.put("join", cfg.getString("messages.player-join"));
		messages.put("quit", cfg.getString("messages.player-quit"));
		messages.put("death", cfg.getString("messages.player-death"));
		messages.put("killer", cfg.getString("messages.killer"));
		messages.put("victim", cfg.getString("messages.victim"));
		
		List<Integer> bWhitelist = cfg.getIntegerList("block-break-place-whitelist");
		for (int i : bWhitelist) { blockWhitelist.add(i); }
		
		plugin.logger.log("Config values loaded!");
	}

	/** Checks if config files exists */
	public void Check() {
		plugin.logger.log("Checking files!");
		
		File baseDir = new File("plugins" + File.separator + "SurvivalGames");
		File config = new File("plugins" + File.separator + "SurvivalGames" + File.separator + "config.yml");
		File players = new File("plugins" + File.separator + "SurvivalGames" + File.separator + "players.yml");
		
		if(!baseDir.exists()) { baseDir.mkdirs(); }
		
		if (!config.exists()) {	Export("config.yml", config); }
		if (!players.exists()) { Export("players.yml", players); }
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
