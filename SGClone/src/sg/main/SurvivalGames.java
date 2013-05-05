package sg.main;

import sg.Commands.*;
import sg.Listeners.*;
import sg.Utils.*;
import sg.cfg.*;
import sg.objects.*;

import java.util.*;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.command.CommandExecutor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class SurvivalGames extends JavaPlugin {
	//SG.CFG
	public ConfigManager cfgManager;
	public MapsManager mapManager;
	public PlayerManager playerManager;
	
	//SG.COMMANDS
	public GhostFix cmdGF;
	public Help cmdHelp;
	public Skip cmdSkip;
	public Vote cmdVote;
	
	//SG.LISTENERS
	public CommandExecutor cmdExecutor;
	public PlayerListener playerListener;
	public ServerListener serverListener;
	
	//SG.UTILS	
	public ChatUtils chatUtils;
	public Logger logger;
	public Messages msg;
	public PluginManager pluginManager;

	//VARIABLES
	public java.util.Map<String, World> worlds = new HashMap<String, World>();
	
	World world;
	WorldCreator generator;	

	public boolean canStart;
	public GameState gameState = GameState.LOBBY;

	@Override
	public void onEnable() {		
		initializeClasses();
		initializeCommandPerformers();
		registerHooks();
		registerCommandExecutor();
		
		logger.log("SurvivalGames Plugin Enabled!");

		cfgManager.Check();
		cfgManager.load();
		mapManager.Check();
		mapManager.load();
	}
	
	@Override
	public void onDisable() {
		logger.log("SurvivalGames Plugin Disabled!");
	}
	
	private void initializeClasses() {
		//SG.CFG
		this.cfgManager = new ConfigManager(this);
		this.mapManager = new MapsManager(this);
		this.playerManager = new PlayerManager(this);
		
		//SG.LISTENERS
		this.playerListener = new PlayerListener(this);
		this.serverListener = new ServerListener(this);
		
		//SG.UTILS	
		this.chatUtils = new ChatUtils(this);
		this.logger = new Logger(this);
		this.msg = new Messages(this);
	}
	
	private void initializeCommandPerformers() {
		this.cmdHelp = new Help(this);
		this.cmdVote = new Vote(this);
		this.cmdGF = new GhostFix(this);
		this.cmdSkip = new Skip(this);		
	}
	
	private void registerHooks() {
		pluginManager.registerEvents(playerListener, this);
		pluginManager.registerEvents(serverListener, this);
	}
	
	private void registerCommandExecutor() {
		this.cmdExecutor = new CommandListener(this);

		getCommand("help").setExecutor(cmdExecutor);
		getCommand("vote").setExecutor(cmdExecutor);
		getCommand("skip").setExecutor(cmdExecutor);
		getCommand("ghostfix").setExecutor(cmdExecutor);
	}
	
	public World loadWorld(String name) {
		this.generator = new WorldCreator(name);
		World world = this.getServer().createWorld(generator);
		worlds.put(name, world);
		return world;
	}

	public void unLoadWorld(String name) {
		this.getServer().unloadWorld(name, false);
		worlds.remove(name);
	}
	
	
	
	
	
	
	
	
	//TODO EDIT
	
	//TODO Choose maps to vote on NOT TESTED
	@SuppressWarnings("rawtypes")
	public void chooseVoteMaps() {
		ArrayList maps = (ArrayList) mapManager.maps;
		while (this.selectedWorlds.size() < 4) {
			int index = 0 + (int)(Math.random() * (((maps.size() - 1) - 0) + 1));
			System.out.println(index);
			if (!this.selectedWorlds.contains(((Map)maps.get(index)).name)) {
				this.selectedWorlds.add(((Map)maps.get(index)).name);
				worldVotes.add(0);
				//this.voteWorld.contains(((Map) maps.get(index - 1)).name); that returns a boolean and should not be here
			}
		}
	}

 
	

	public void kickAllPlayers(String string) {
		for (Player p : Bukkit.getOnlinePlayers()) {
			p.kickPlayer(string);
		}
	}
//TODO VOTE SYSTEM ALPHA NOT TESTED
	public String getVotedWorld() {
		int voted = 0;
		String map = null;
		for (int i = 0; i < worldVotes.size() + 1; i++) {
			if (worldVotes.get(i) > voted) {
				voted = worldVotes.get(i);
				map = selectedWorlds.get(i);
			}
		}
		if (skipVotes > voted) {
			return null;
		} else {
			return map;
		}
	}
}
