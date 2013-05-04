package sg.Listeners;

import sg.main.SurvivalGames;
import sg.objects.GameState;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.server.ServerListPingEvent;

public class ServerListener implements Listener {
	private SurvivalGames plugin;
	public ServerListener(SurvivalGames instance) {	this.plugin = instance;	}
	
	@EventHandler
	public void onServerListPing(ServerListPingEvent event) {
		event.setMaxPlayers(24);
		
		String motd = "";
		if (plugin.gameState == GameState.LOBBY) {
			motd = plugin.cfgManager.lobbyMotd;
		} else if (plugin.gameState == GameState.PREGAME) {
			motd = plugin.cfgManager.pregameMotd;
		} else if (plugin.gameState == GameState.INGAME) {
			motd = plugin.cfgManager.ingameMotd;
		} else if (plugin.gameState == GameState.DEATHMATCH) {
			motd = plugin.cfgManager.deathmatchMotd;
		} else if (plugin.gameState == GameState.ENDGAME) {
			motd = plugin.cfgManager.endgameMotd;
		}
		motd = plugin.chatUtils.Colourize(true, motd);
		
		event.setMotd(motd);
	}
}
