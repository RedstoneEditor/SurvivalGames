package sg.Commands;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import sg.main.SurvivalGames;

public class GhostFix {
	@SuppressWarnings("unused")
	private SurvivalGames plugin;
	public GhostFix(SurvivalGames instance) { this.plugin = instance; }
	
	public void Execute(CommandSender sender, String command, String[] args)
	{
		if(sender instanceof Player){
			((Player) sender).teleport((Player)sender);
			sender.sendMessage(ChatColor.BLUE + "You have been GhostFixed");
		}else
			sender.sendMessage(ChatColor.RED + "You can only you this command if you are ingame");
	}
	

}
