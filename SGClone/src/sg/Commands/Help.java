package sg.Commands;

import sg.main.SurvivalGames;

import org.bukkit.command.CommandSender;

public class Help {
	private SurvivalGames plugin;
	public Help(SurvivalGames instance) { this.plugin = instance; }
	
	private String[] helpPages = {
			//----------| HELP PAGE 1 |----------//Maximum 10 lines per page including the help page#
			"&c---------| &4&lHelp Page 1 &r&c|---------\n" + //33
			" &e/help &7[&6#&7]&r" + " &c- &r" + "&aShows this pages\n", //11 + 3 + 16 = 30
			" &e/vote &7[&6#&7]&r" + " &c- &r" + "&a Votes for a map", //11 + 3 + 16 = 30 INCLUDING COLOR CODES?
			" &e/skip &7[&6#&7]&r" + " &c- &r" + "&a Skips the map selection" //11 + 3 + 16 = 30
			,
			//----------| HELP PAGE 2 |----------//
			"&c---------| &4&lHelp Page 2 &r&c|---------" + //33
			""
	};
	
	public void Execute(CommandSender sender, String command, String[] args)
	{
		int page = 1;
		
		try { page = Integer.getInteger(args[0]); }
		catch(Exception ex) { plugin.logger.log("ERROR", ex.getMessage()); }
				
		if ((page!=1) && (page!=2)) { page = 1; }
		
		sender.sendMessage(plugin.chatUtils.Colourize(true, helpPages[page-1]));
	}
}
