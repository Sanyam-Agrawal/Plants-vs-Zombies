public class Zombies extends Creature
{
    private Tile tile = null;

    public Zombies(){
        this.imgSrc = ;
    }

    public Zombies(String imgSrc){
        super(imgSrc);
    }

    public Tile getTile(){
        return this.tile;
    }

    public void setTile(Tile newTile){
        this.tile = newTile;
    }

    // @Override
    // public void die(){
    //     this.getGame().getInstantiatedZombies().remove(this);
    //     this.tile.setOccupyingZombie(null);
    //     this.tile.setZombieIsOccupying(false);
    //     this.tile.setFill(Color.GRAY);
    // }
}
