package sg.objects;

import java.util.*;

public class Map {
	public String name;	
	public Set<Spawn> spawns = new HashSet<Spawn>();
	public Set<Chest> chests = new HashSet<Chest>();
	
	public Map(String name) {
		this.name = name;
	}
}
