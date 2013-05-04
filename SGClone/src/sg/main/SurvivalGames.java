package sg.main;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.command.CommandExecutor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import sg.Commands.GhostFix;
import sg.Commands.Help;
import sg.Commands.Skip;
import sg.Commands.Vote;
import sg.Listeners.CommandListener;
import sg.Listeners.PlayerListener;
import sg.Listeners.ServerListener;
import sg.Utils.ChatUtils;
import sg.Utils.Logger;
import sg.Utils.VoteChat;
import sg.cfg.ConfigManager;
import sg.cfg.MapsManager;
import sg.cfg.PlayerManager;
import sg.objects.GameState;
import sg.objects.Map;

public class SurvivalGames extends JavaPlugin {
	public ChatUtils chatUtils;
	public ConfigManager cfgManager;
	public Logger logger;
	public PlayerListener playerListener;
	public PlayerManager playerManager;
	public PluginManager pluginManager;
	public ServerListener serverListener;
	public MapsManager mapManager;
	public ArrayList<String> selectedWorlds;
	public ArrayList<Integer> worldVotes;
	public int skipVotes;
	public String voteWorld;
	public VoteChat voteChat;

	// Commands
	public CommandExecutor cmdExecutor;
	public Help cmdHelp;
	public GhostFix cmdGF;

	// World
	World world;
	WorldCreator generator;

	public boolean canStart;
	public int lobbyTime;
	public int pregameTime;
	public int gameTime;
	public int deathmatchTime;

	public int gameState = GameState.LOBBY;
	public String currentWorld;
	public Vote cmdVote;
	public Skip cmdSkip;

	@Override
	public void onEnable() {
		selectedWorlds = new ArrayList<String>();
		worldVotes = new ArrayList<Integer>();
		initializeClasses();
		initializeCommandPerformers();
		registerHooks();
		registerCommandExecutor();

		cfgManager.Check();
		cfgManager.load();
		mapManager.load();
		chooseVoteMaps();
		getServer().getScheduler().scheduleSyncRepeatingTask(this, voteChat, 0L, 200L);

	}
	
	@Override
	public void onDisable() {
	}

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

	/** Initializes all the classes */
	private void initializeClasses() {
		this.chatUtils = new ChatUtils(this);
		this.cfgManager = new ConfigManager(this);
		this.logger = new Logger(this);
		this.playerListener = new PlayerListener(this);
		this.playerManager = new PlayerManager(this);
		this.pluginManager = getServer().getPluginManager();
		this.serverListener = new ServerListener(this);
		this.mapManager = new MapsManager(this);
		this.lobbyTime = cfgManager.lobbyTime;
		this.pregameTime = cfgManager.pregameTime;
		this.gameTime = cfgManager.gameTime;
		this.deathmatchTime = cfgManager.deathmatchTime;
		this.voteChat = new VoteChat(this);
	}

	/** Initializes the command's classes */
	private void initializeCommandPerformers() {
		this.cmdHelp = new Help(this);
		this.cmdVote = new Vote(this);
		this.cmdGF = new GhostFix(this);
		this.cmdSkip = new Skip(this);
		
	}

	/** Register Event Listeners */
	private void registerHooks() {
		pluginManager.registerEvents(playerListener, this);
		pluginManager.registerEvents(serverListener, this);
	}

	/** Register the Command Executor */
	private void registerCommandExecutor() {
		this.cmdExecutor = new CommandListener(this);

		getCommand("help").setExecutor(cmdExecutor);
		getCommand("vote").setExecutor(cmdExecutor);
		getCommand("skip").setExecutor(cmdExecutor);
		getCommand("ghostfix").setExecutor(cmdExecutor);
	}
 
	public void loadWorld(String world) {
		this.generator = new WorldCreator(world);
		this.world = this.getServer().createWorld(generator);
	}

	public void unLoadWorld(String world) {
		this.getServer().unloadWorld(world, false);
		this.currentWorld = world;
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
