package sg.Utils;

import sg.main.SurvivalGames;
import sg.objects.SGPlayer;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class ChatUtils {
	private SurvivalGames plugin;
	public ChatUtils(SurvivalGames instance) { this.plugin = instance; }

	public String Colourize(String message) {
			return message
					.replaceAll("&0", ChatColor.BLACK + "")
					.replaceAll("&1", ChatColor.DARK_BLUE + "")
					.replaceAll("&2", ChatColor.DARK_GREEN + "")
					.replaceAll("&3", ChatColor.DARK_AQUA + "")
					.replaceAll("&4", ChatColor.DARK_RED + "")
					.replaceAll("&5", ChatColor.LIGHT_PURPLE + "")
					.replaceAll("&6", ChatColor.GOLD + "")
					.replaceAll("&7", ChatColor.GRAY + "")
					.replaceAll("&8", ChatColor.DARK_GRAY + "")
					.replaceAll("&9", ChatColor.BLUE + "")
					.replaceAll("&a", ChatColor.GREEN + "")
					.replaceAll("&b", ChatColor.AQUA + "")
					.replaceAll("&c", ChatColor.RED + "")
					.replaceAll("&d", ChatColor.LIGHT_PURPLE + "")
					.replaceAll("&e", ChatColor.YELLOW + "")
					.replaceAll("&f", ChatColor.WHITE + "")
					.replaceAll("&k", ChatColor.MAGIC + "")
					.replaceAll("&l", ChatColor.BOLD + "")
					.replaceAll("&m", ChatColor.STRIKETHROUGH + "")
					.replaceAll("&n", ChatColor.UNDERLINE + "")
					.replaceAll("&o", ChatColor.ITALIC + "")
					.replaceAll("&r", ChatColor.RESET + "");
	}

	public String Decolourize(String message) {
		return message.replaceAll("&([a-z0-9])", "");
	}

	public String Clear() {
		String ret = "";
		for (int i = 0; i < 20; i++) {
		ret = ret +  "\n";	
		}
		return ret;
	}

	public void Shout(String message) {
		Bukkit.broadcastMessage(message); }
	public void Shout(String message, String permission) {
		Bukkit.broadcast(message, permission); }

	public String ApplyFormat(String user, String format) { return ApplyFormat(user, "", format); }
	public String ApplyFormat(String user, String message, String format) {
		SGPlayer player = plugin.playerManager.getPlayer(user);
		
		message = format
				.replaceFirst("%username", user)
				.replaceFirst("%message", message)
				.replaceAll("%points", Integer.toString(player.points));
		
		switch (player.rank) {
		case "USER":
			return message.replace("%prefix", plugin.cfgManager.userPrefix);
		case "VIP":
			return message.replace("%prefix", plugin.cfgManager.vipPrefix);
		case "STAFF":
			return message.replace("%prefix", plugin.cfgManager.staffPrefix);
		default:
			return null;
		}
	}

	/**
	 * Splits a string
	 * @param src String to split
	 * @param len Length to split the string at
	 * @return String[] Each key is one line of the chat message
	 */
	public String[] split(String src, int len) {
		String[] result = new String[(int) Math.ceil((double) src.length()
				/ (double) len)];
		for (int i = 0; i < result.length; i++)
			result[i] = src.substring(i * len,
					Math.min(src.length(), (i + 1) * len));
		return result;
	}
}
