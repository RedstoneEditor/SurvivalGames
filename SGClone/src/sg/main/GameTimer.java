package sg.main;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import sg.objects.GameState;

public class GameTimer implements Runnable {
	private SurvivalGames plugin;
	public GameTimer(SurvivalGames instance) { this.plugin = instance; }
	
	private int lobbyTime;
	private int pregameTime;
	private int ingameTime;
	private int deathmatchTime;
	
	public void load() {
		this.lobbyTime = plugin.lobbyTime;
		this.pregameTime = plugin.pregameTime;
		this.ingameTime = plugin.gameTime;
		this.deathmatchTime = plugin.deathmatchTime;
	}
		
	@Override
	public void run() {
		if (plugin.gameState == GameState.LOBBY) { lobby(); }
		else if (plugin.gameState == GameState.PREGAME) { pregame(); } 
		else if (plugin.gameState == GameState.INGAME) { ingame(); }
		else if (plugin.gameState == GameState.DEATHMATCH) { deathmatch(); }
		else if (plugin.gameState == GameState.ENDGAME) { endgame(); }
	}
	
	private void lobby() {
		plugin.gameState = GameState.LOBBY;
		plugin.getServer().getScheduler().scheduleSyncRepeatingTask(plugin, plugin.voteChat, 0L, 200L);
		if (lobbyTime == 0) {
			try {
				plugin.voteWorld = plugin.getVotedWorld();
				plugin.loadWorld(plugin.voteWorld);
				Thread.sleep(10000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			pregame(); 
			return; }
		
		//TODO CODE
		
		lobbyTime--;
	}
	
	private void pregame() {
		plugin.gameState = GameState.PREGAME;
		if (pregameTime == 0) { ingame(); return; }
		
		plugin.chatUtils.Shout(plugin.chatUtils.Colourize(true, 
				"&4[&6SG&4] " + "&c" + pregameTime + " seconds &6before the match starts!"));
		//TODO CODE
		
		pregameTime--;
	}
	
	private void ingame() {
		plugin.gameState = GameState.INGAME;
		if (ingameTime == 0) { deathmatch(); return; }
		
		//TODO CODE
		
		ingameTime--;
	}
	
	private void deathmatch() {
		plugin.gameState = GameState.DEATHMATCH;
		if (deathmatchTime == 0) { endgame(); return; }
		
		plugin.chatUtils.Shout(plugin.chatUtils.Colourize(true, 
				"&4[&6SG&4] " + "&c" + deathmatchTime + " seconds &6before the deathmatch ends!"));
		//TODO CODE
		
		deathmatchTime--;
	}
	
	private void endgame() {
		Player winner = null;
		for (Player player : Bukkit.getOnlinePlayers()) {
			if (player.getHealth() > winner.getHealth()) { winner = player; }
		}
		
		plugin.chatUtils.Shout(plugin.chatUtils.Colourize(true,
				"&4[&6SG&4] " + "&a" + winner.getName() + "&e won the game!"));
		
		try {
			Thread.sleep(10000L);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		plugin.kickAllPlayers(plugin.chatUtils.Colourize(false, "&2RESTARTING"));
		plugin.unLoadWorld(plugin.currentWorld);
		
		load();
		lobby();
	}
}
