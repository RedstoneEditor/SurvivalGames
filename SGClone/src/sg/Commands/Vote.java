package sg.Commands;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import sg.main.SurvivalGames;

public class Vote {
	private SurvivalGames plugin;
	public Vote(SurvivalGames instance) { this.plugin = instance; }
	
	public void Execute(CommandSender sender, String command, String[] args)
	{
		if (!(sender instanceof Player)) {
			sender.sendMessage(plugin.msg.NOT_ENAUGH_PERMISSIONS); }
		if (
		
		
		if(sender instanceof Player)
			if(args.length > 0)
		plugin.worldVotes.set(Integer.parseInt(args[0]) - 1, plugin.worldVotes.get(Integer.parseInt(args[0]) - 1) + 1);
			else
				sender.sendMessage(ChatColor.RED + "You must choose a world to vote for!");
		else
			sender.sendMessage(ChatColor.RED + "This command is only available to players!");
	}
}
