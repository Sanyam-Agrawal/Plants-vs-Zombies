public class Zombies extends Creature
{
    protected double attack;

    public Zombies(){
        super("z_normal.gif",50);
        this.attack = 0.1;
    }

    public Zombies(String imgSrc, double _health, double _attack){
        super(imgSrc,_health);
        this.attack = _attack;
    }

    public double getAttack() { return this.attack; }

    public void freeze() { super.reduceSpeed(); }
}
