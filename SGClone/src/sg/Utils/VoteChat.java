package sg.Utils;

import sg.main.SurvivalGames;

public class VoteChat implements Runnable{

	private SurvivalGames plugin;
	public int time = 60;
	
	public VoteChat(SurvivalGames instance) {
		this.plugin = instance;
	}
	
	public void run(){
		time--;
		String[] lines = new String[] {
				"&a/vote for a map to play on",
				"&21. &e" + plugin.selectedWorlds.get(0) + "- " + plugin.worldVotes.get(0),
				"&22. &e" + plugin.selectedWorlds.get(1) + "- " + plugin.worldVotes.get(1),
				"&23. &e" + plugin.selectedWorlds.get(2) + "- " + plugin.worldVotes.get(2),
				"&24. &e" + plugin.selectedWorlds.get(3) + "- " + plugin.worldVotes.get(3),
				"&6/skip to choose a new set &2| &6Votes: &2"
						+ plugin.skipVotes };
		plugin.chatUtils.Shout(plugin.chatUtils.Colourize(true, lines[0]));
		plugin.chatUtils.Shout(plugin.chatUtils.Colourize(true, lines[1]));
		plugin.chatUtils.Shout(plugin.chatUtils.Colourize(true, lines[2]));
		plugin.chatUtils.Shout(plugin.chatUtils.Colourize(true, lines[3]));
		plugin.chatUtils.Shout(plugin.chatUtils.Colourize(true, lines[4]));
		plugin.chatUtils.Shout(plugin.chatUtils.Colourize(true, lines[5]));

	}

}
