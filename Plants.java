public abstract class Plants extends Creature
{
    private int cost;
    private Tile tile = null;

    public Plants(String imgSrc, int cost){
        super(imgSrc);
        this.cost = cost;
    }

    public int getCost(){
        return this.cost;
    }

    public Tile getTile(){
        return this.tile;
    }

    public void setCost(int newCost){
        this.cost = newCost;
    }

    public void setTile(Tile newTile){
        this.tile = newTile;
    }

    // @Override
    // public void die(){
    //     this.getGame().getInstantiatedPlants().remove(this);
    //     this.tile.setOccupyingPlant(null);
    //     this.tile.setPlantIsOccupying(false);
    //     this.tile.setFill(Color.GRAY);
    // }
}
