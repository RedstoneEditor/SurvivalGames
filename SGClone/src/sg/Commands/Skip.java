package sg.Commands;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import sg.main.SurvivalGames;

public class Skip {
	private SurvivalGames plugin;
	public Skip(SurvivalGames instance) { this.plugin = instance; }
	
	public void Execute(CommandSender sender, String command, String[] args)
	{
		if (!(sender instanceof Player)) {
			sender.sendMessage(plugin.msg.MUST_BE_PLAYER); }
		if (args.length > 0) {
			sender.sendMessage(plugin.msg.WRONG_SYNTAX); }
		
		plugin.skips ++;
		
		if (plugin.skips >= 8) {
			
		}
	}

	public void proceedWithSkip() {
		
	}
}
