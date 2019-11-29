import java.util.*;

class Row
{
	private final Set<Plants> plants;
	private final Set<Peas> peas;
	private LawnMower lawnmower;
	private final Set<Zombies> zombies;

	Row ()
	{
		plants = new HashSet<>();
		peas = new HashSet<>();
		lawnmower = new LawnMower();
		zombies = new HashSet<>();
	}

	public Set<Plants> getPlants() { return this.plants; }
	public Set<Peas> getPeas() { return this.peas; }
	public LawnMower getLawnMower() { return this.lawnmower; }
	public Set<Zombies> getZombies() { return this.zombies; }
}