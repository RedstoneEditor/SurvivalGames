package sg.objects;

public class Map {
	public String name;
	public String world;
	public int index;
	public Spawn[] spawns;
	
	public Map(String name, String world, int index) {
		this.name = name;
		this.world = world;
		this.index = index;
	}
}
