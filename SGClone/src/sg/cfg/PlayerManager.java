package sg.cfg;

import java.io.File;

import org.bukkit.configuration.file.YamlConfiguration;

import sg.main.SurvivalGames;
import sg.objects.SGPlayer;

public class PlayerManager {
	private SurvivalGames plugin;
	public PlayerManager(SurvivalGames instance) { this.plugin = instance; }

	YamlConfiguration cfg = new YamlConfiguration();
	File cfgFile = new File("plugins" + File.separator + "SurvivalGames" + File.separator + "players.yml");

	private String prank(String user) {	return "players." + user + ".rank"; }
	private String ppoints(String user) { return "players." + user + ".points"; }
	private String pkills(String user) { return "players." + user + ".kills"; }
	private String pdeaths(String user) { return "players." + user + ".deaths"; }
	private String pwins(String user) { return "players." + user + ".wins"; }
	private String ploses(String user) { return "players." + user + ".loses"; }

	public SGPlayer getPlayer(String user) {
		try { cfg = YamlConfiguration.loadConfiguration(cfgFile); }
		catch (Exception ex) { plugin.logger.log(ex); }

		String rank = cfg.getString(prank(user));
		int points = cfg.getInt(ppoints(user));
		int kills = cfg.getInt(pkills(user));
		int deaths = cfg.getInt(pdeaths(user));
		int wins = cfg.getInt(pwins(user));
		int loses = cfg.getInt(ploses(user));

		return new SGPlayer(user, rank, points, kills, deaths, wins, loses);
	}

	public void updatePlayer(String user, int points, int kills, int deaths, int wins, int loses) {
		try { cfg = YamlConfiguration.loadConfiguration(cfgFile); }
		catch (Exception ex) { plugin.logger.log(ex); }
		
		cfg.set(ppoints(user), cfg.getInt(ppoints(user)) + points);
		cfg.set(pkills(user), cfg.getInt(pkills(user)) + kills);
		cfg.set(pdeaths(user), cfg.getInt(pdeaths(user)) + deaths);
		cfg.set(pwins(user), cfg.getInt(pwins(user)) + wins);
		cfg.set(ploses(user), cfg.getInt(ploses(user)) + loses);

		try { cfg.save(cfgFile); }
		catch (Exception ex) { plugin.logger.log(ex); }
	}

	public void addPlayer(String user) { addPlayer(user, "USER"); }
	public void addPlayer(String user, String rank) {
		try { cfg = YamlConfiguration.loadConfiguration(cfgFile); }
		catch (Exception ex) { plugin.logger.log(ex); }

		plugin.logger.log("Creating " + user + "'s data...");
		
		cfg.set(prank(user), rank);
		cfg.set(ppoints(user), 100);
		cfg.set(pkills(user), 0);
		cfg.set(pdeaths(user), 0);
		cfg.set(pwins(user), 0);
		cfg.set(ploses(user), 0);
		
		plugin.logger.log(user + "'s data created!");

		try { cfg.save(cfgFile); } 
		catch (Exception ex) { plugin.logger.log(ex); }
	}

	public boolean isNew(String user) {
		if (cfg.get(prank(user)) == null) {
			return true; } else { return false;
		}
	}
}
