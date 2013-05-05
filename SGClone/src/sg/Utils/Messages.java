package sg.Utils;

import sg.main.SurvivalGames;

public class Messages {	
	SurvivalGames plugin;
	public Messages(SurvivalGames instance) { this.plugin = instance; }
	
	public String PREFIX = "&6[&4&lSG&6] &r";
	
	public String NOT_ENAUGH_PERMISSIONS = plugin.chatUtils.Colourize(PREFIX + "&cYou don't have access to this command!");
	
	public String MUST_BE_PLAYER = plugin.chatUtils.Colourize(PREFIX + "&cThis command can be performed only by players!");
	public String MUST_BE_CONSOLE = plugin.chatUtils.Colourize(PREFIX + "&cThis command can be performed only by the console!");
	
	public String WRONG_SYNTAX = plugin.chatUtils.Colourize(PREFIX + "&cWrong command syntax!");

}
