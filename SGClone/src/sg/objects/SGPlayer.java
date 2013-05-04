package sg.objects;

public class SGPlayer {
	// TODO Make this class store data in a MySQL database for website access.
	public String username;
	public String rank;
	public int points;
	public int kills;
	public int deaths;
	public int wins;
	public int loses;

	/**
	 * Custom player object
	 * 
	 * @param username
	 *            Name of the player
	 * @param rank
	 *            Player's rank
	 * @param points
	 *            Player's score
	 * @param kills
	 *            Player's kill count
	 * @param deaths
	 *            Player's death count, (NOT NEEDED, WHEN YOU DIE, YOU DIE,
	 *            unless this is Global)//TODO
	 * @param wins
	 *            Player's win count
	 * @param loses
	 *            Player's loss count
	 */
	public SGPlayer(String username, String rank, int points, int kills,
			int deaths, int wins, int loses) {
		this.username = username;
		this.rank = rank;
		this.points = points;
		this.kills = kills;
		this.deaths = deaths;
		this.wins = wins;
		this.loses = loses;
	}
}
