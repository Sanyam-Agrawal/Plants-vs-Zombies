import java.util.*;

class Row
{
	private int middle_point;

	private final Set<Plants> plants;
	private final Set<Peas> peas;
	private LawnMower lawnmower;
	private final Set<Zombies> zombies;

	Row (int _middle_point)
	{
		middle_point = _middle_point;
		plants = new HashSet<>();
		peas = new HashSet<>();
		lawnmower = new LawnMower();
		zombies = new HashSet<>();
	}

	public int getMiddle() { return this.middle_point; }
	public Set<Plants> getPlants() { return this.plants; }
	public Set<Peas> getPeas() { return this.peas; }
	public LawnMower getLawnMower() { return this.lawnmower; }
	public Set<Zombies> getZombies() { return this.zombies; }
}