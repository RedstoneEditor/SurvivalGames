package sg.Listeners;

import sg.main.SurvivalGames;
import sg.objects.GameState;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerListener implements Listener {
	private SurvivalGames plugin;
	public PlayerListener(SurvivalGames instance) {	this.plugin = instance;	}

	@EventHandler
	public void onPlayerChat(AsyncPlayerChatEvent event) {
		event.setCancelled(true);
		
		String user = event.getPlayer().getName();
		String message = event.getMessage();
		String format = plugin.cfgManager.chatFormat;
		
		if (!(event.getPlayer().hasPermission("sg.color"))) {
			message = plugin.chatUtils.Decolourize(message);
		}
		
		message = plugin.chatUtils.ApplyFormat(user, message, format);
		message = plugin.chatUtils.Colourize(true, message);
		plugin.chatUtils.Shout(message);
	}

	/** Prevents players from moving when the game has not started */
	@EventHandler
	public void onPlayerMove(PlayerMoveEvent event) {
		if (plugin.gameState == GameState.PREGAME) {
			Location location = new Location(event.getFrom().getWorld(), event
					.getFrom().getX(), event.getFrom().getY(), event.getFrom()
					.getZ());
			event.setTo(location);
		}
	}

	/** When a player dies it executed a siere of things :3 */
	@EventHandler
	public void onPlayerDeath(PlayerDeathEvent event) {
		event.setDroppedExp(0);
		
		if ((plugin.gameState == GameState.INGAME) || (plugin.gameState == GameState.DEATHMATCH)) {
			Player victim = event.getEntity();
			Player killer = event.getEntity().getKiller();

			String deathMessage = plugin.cfgManager.playerDeathMessage;
			deathMessage = plugin.chatUtils.ApplyFormat( victim.getName(), deathMessage);
			deathMessage = plugin.chatUtils.Colourize(true, deathMessage);
			event.setDeathMessage(null);

			if (killer instanceof Player) {
				String killerMessage = plugin.cfgManager.killerMessage;
				killerMessage = plugin.chatUtils.ApplyFormat(victim.getName(), killerMessage);
				killerMessage = plugin.chatUtils.Colourize(true, killerMessage);
				plugin.playerManager.updatePlayer(killer.getName(), 10, 1, 0, 0, 0);
			}
			
			String victimMessage = plugin.cfgManager.victimMessage;
			victimMessage = plugin.chatUtils.ApplyFormat(killer.getName(), victimMessage);
			victimMessage = plugin.chatUtils.Colourize(true, victimMessage);
			plugin.playerManager.updatePlayer(victim.getName(), -50, 0, 1, 0, 1);

			// TODO put the player as spectator
		}
	}

	@EventHandler
	public void onDamage(EntityDamageEvent event) {
		if ((plugin.gameState != GameState.INGAME)
				&& (plugin.gameState != GameState.DEATHMATCH)
				&& (event.getEntityType() == EntityType.PLAYER)) {
			event.setCancelled(true);
		}
	}

	@EventHandler
	public void onPlayerFoodChanged(FoodLevelChangeEvent event) {
		if ((plugin.gameState != GameState.INGAME)
				&& (plugin.gameState != GameState.DEATHMATCH)
				&& (event.getEntityType() == EntityType.PLAYER)) {
			event.setCancelled(true);
		}
	}

	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent event) {		
		if (!(plugin.gameState == GameState.LOBBY)) {
			event.getPlayer().kickPlayer(plugin.chatUtils.Colourize(false, "&cCannot join to the server!"));
			return;
		}
		
		plugin.canStart = (Bukkit.getOnlinePlayers().length >= plugin.cfgManager.minPlayers);
		
		String user = event.getPlayer().getName();
		
		if (plugin.playerManager.isNew(user)) {
			plugin.playerManager.addPlayer(user);
		}
		
		String joinMessage = plugin.cfgManager.playerJoinMessage;
		joinMessage = plugin.chatUtils.ApplyFormat(user, joinMessage);
		joinMessage = plugin.chatUtils.Colourize(true, joinMessage);
		event.setJoinMessage(joinMessage);	
	}

	@EventHandler
	public void onPlayerQuit(PlayerQuitEvent event) {
		plugin.canStart = (Bukkit.getOnlinePlayers().length >= plugin.cfgManager.minPlayers);
		
		String quitMessage = plugin.cfgManager.playerQuitMessage;
		quitMessage = plugin.chatUtils.ApplyFormat(event.getPlayer().getName(), quitMessage);
		quitMessage = plugin.chatUtils.Colourize(true, quitMessage);
		event.setQuitMessage(quitMessage);
		
		// TODO Remove the player the lists
	}

	@EventHandler
	public void onBlockPlace(BlockPlaceEvent event) {
		event.setCancelled(true);
	}

	@EventHandler
	public void onBlockBreak(BlockBreakEvent event) {
		if ((plugin.gameState != GameState.INGAME)
				&& (plugin.gameState != GameState.DEATHMATCH)) {
			event.setCancelled(true);
			return;
		}
		if (plugin.cfgManager.blockBreakWhitelist.contains(event.getBlock()
				.getTypeId())) {
			event.setCancelled(false);
		} else {
			event.setCancelled(true);
		}
	}
}
