package sg.cfg;

import sg.main.SurvivalGames;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

import org.bukkit.configuration.file.YamlConfiguration;

public class ConfigManager {
	private SurvivalGames plugin;
	public ConfigManager(SurvivalGames instance) { this.plugin = instance; }
	
	public YamlConfiguration cfg = new YamlConfiguration();
	File cfgFile = new File("plugins" + File.separator + "SurvivalGames" + File.separator + "config.yml");

	//Chat Formattation
	public String chatFormat;
	public String userPrefix;
	public String vipPrefix;
	public String staffPrefix;

	//Timers
	public int lobbyTime;
	public int pregameTime;
	public int gameTime;
	public int deathmatchTime;
	
	//Motds
	public String lobbyMotd;
	public String pregameMotd;
	public String ingameMotd;
	public String deathmatchMotd;
	public String endgameMotd;

	//Messages
	public String playerDeathMessage;
	public String playerJoinMessage;
	public String playerQuitMessage;
	public String killerMessage;
	public String victimMessage;

	//General Variables
	public int minPlayers;
	
	//Other
	public List<String> blockBreakWhitelist;

	/** Loads data from config.yml */
	public void load() {
		try { cfg = YamlConfiguration.loadConfiguration(cfgFile); }
		catch (Exception ex) { plugin.logger.log(ex); }
		
		plugin.logger.log("Loading values from config.yml...");

		this.chatFormat = cfg.getString("chat.format");
		this.userPrefix = cfg.getString("chat.prefix.user");
		this.vipPrefix = cfg.getString("chat.prefix.vip");
		this.staffPrefix = cfg.getString("chat.prefix.staff");

		this.lobbyTime = cfg.getInt("timers.lobby");
		this.pregameTime = cfg.getInt("timers.pregame");
		this.gameTime = cfg.getInt("timers.ingame");
		this.deathmatchTime = cfg.getInt("timers.deathmatch");
		
		this.lobbyMotd = cfg.getString("motd.lobby");
		this.pregameMotd = cfg.getString("motd.pregame");
		this.ingameMotd = cfg.getString("motd.ingame");
		this.deathmatchMotd = cfg.getString("motd.deathmatch");
		this.endgameMotd = cfg.getString("motd.endgame");

		this.playerDeathMessage = cfg.getString("messages.player-death");
		this.playerJoinMessage = cfg.getString("messages.player-join");
		this.playerQuitMessage = cfg.getString("messages.player-quit");
		this.killerMessage = cfg.getString("messages.killer");
		this.victimMessage = cfg.getString("messages.victim");

		this.minPlayers = cfg.getInt("server.min-players");
		
		this.blockBreakWhitelist = cfg.getStringList("block-break-whitelist");
		
		plugin.logger.log("Config values loaded!");
	}

	/** Checks if config files exists */
	public void Check() {
		plugin.logger.log("Checking files!");
		
		File baseDir = new File("plugins" + File.separator + "SurvivalGames");
		File config = new File("plugins" + File.separator + "SurvivalGames" + File.separator + "config.yml");
		File players = new File("plugins" + File.separator + "SurvivalGames" + File.separator + "players.yml");
		File maps = new File("plugins" + File.separator + "SurvivalGames" + File.separator + "maps.yml");
		if(!baseDir.exists()) { baseDir.mkdirs(); }
		
		if((config.exists()) && (players.exists())) { return; }
		
		if (!config.exists()) {	Export("config.yml", config); }
		if (!players.exists()) { Export("players.yml", players); }
		if(!maps.exists()){ Export("maps.yml", maps); }
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
