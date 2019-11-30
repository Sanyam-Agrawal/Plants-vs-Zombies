public abstract class Plants extends Creature
{
    private int cost;

    public Plants(String imgSrc, int cost, double _health){
        super(imgSrc,_health);
        this.cost = cost;
    }

    public int getCost() { return this.cost; }
    public void setCost(int newCost) { this.cost = newCost; }
}
