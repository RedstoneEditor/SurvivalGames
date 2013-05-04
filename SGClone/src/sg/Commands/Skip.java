package sg.Commands;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import sg.main.SurvivalGames;

public class Skip {

	private SurvivalGames plugin;
	public Skip(SurvivalGames instance) { this.plugin = instance; }
	
	public void Execute(CommandSender sender, String command, String[] args)
	{
		if(sender instanceof Player)
			plugin.skipVotes++;
		else
			sender.sendMessage(ChatColor.RED + "This command is only available to players!");
	}

}
