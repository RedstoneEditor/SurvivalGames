package sg.objects;

import java.util.*;

import org.bukkit.Location;
import org.bukkit.block.Block;

public class Map {
	public String name;	
	public Set<Location> spawns = new HashSet<Location>();
	public java.util.Map<Block, Integer> chests = new HashMap<Block, Integer>();
	
	public Map(String name) {
		this.name = name;
	}
}
