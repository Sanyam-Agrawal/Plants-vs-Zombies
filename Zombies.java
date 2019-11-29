public class Zombies extends Creature
{
	protected int attack;

    public Zombies(){
        super("z_normal.gif");
        this.health = 50;
        this.attack = 10;
    }

    public Zombies(String imgSrc){
        super(imgSrc);
    }

    public int getAttack() { return this.attack; }

    public void freeze() {  }
}
