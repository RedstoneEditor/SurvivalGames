package sg.objects;

import org.bukkit.Location;

public class Chest {
	public int level;
	Location location;
	
	public Chest(int level, Location loc) {
		this.level = level;
		this.location = loc;
	}

}
