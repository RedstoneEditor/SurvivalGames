package sg.Listeners;

import sg.main.SurvivalGames;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class CommandListener implements CommandExecutor {
	SurvivalGames plugin;
	public CommandListener(SurvivalGames instance) { this.plugin = instance; }
	
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		String command = cmd.getName().toLowerCase();
		
		switch(command) {
		case "help": plugin.cmdHelp.Execute(sender, command, args); return true;
		case "vote": plugin.cmdVote.Execute(sender, command, args); return true;
		case "skip": plugin.cmdSkip.Execute(sender, command, args); return true;
		case "ghostfix": plugin.cmdGF.Execute(sender, command, args); return true;
		default: return true;
		}
	}
}
