package sg.Commands;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import sg.main.SurvivalGames;
import sg.objects.Map;

public class Vote {
	private SurvivalGames plugin;
	public Vote(SurvivalGames instance) { this.plugin = instance; }
	
	public void Execute(CommandSender sender, String command, String[] args)
	{
		if (!(sender instanceof Player)) {
			sender.sendMessage(plugin.msg.NOT_ENAUGH_PERMISSIONS); }
		if (args.length > 1) {
			sender.sendMessage(plugin.msg.WRONG_SYNTAX); }
		
		if (args.length == 0) {
			sender.sendMessage(getVotePage()); }
		if (args.length == 1) {
			Map map = plugin.maps.get(Integer.parseInt(args[0]));
			
			if (hasVoted((Player) sender)) {
				Map _map = plugin.player_votes.get((Player) sender);
				plugin.votes.put(_map, plugin.votes.get(_map) - 1);
			}
			
			plugin.votes.put(map, plugin.votes.get(map) + 1);
			plugin.player_votes.put((Player) sender, map);
		}
	}
	
	private boolean hasVoted(Player player) {
		return plugin.player_votes.containsKey(player);
	}
	
	private String getVotePage() {
		String page =
			    "&6--------------------------\n" +
				"      &4&lCurrent Maps \n" +
			    "&6--------------------------\n" +
				" &c1&7) &a%map1 \n" +
				" &c2&7) &a%map2 \n" + //21 - length
				" &c3&7) &a%map3 \n" +
				" &c4&7) &a%map4 \n\n" +
			    "&6--------------------------\n" +
				"   &b/skip" +
				"&6--------------------------\n";
		
		String toReturn = page;
		
		for (int i = 1; i <= 4; i++) {
			toReturn.replaceFirst("%map" + i, plugin.maps.get(i).name);
		}
		
		return plugin.chatUtils.Colourize(toReturn);
	}
}
