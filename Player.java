class Player
{
    boolean available_levels[];
    String name;
    private Game mygame;
    public Player(String n,Game game)
    {
        name=n;
        mygame=game;
        available_levels=new boolean[6];
        available_levels[1]=true;
    }

    public boolean[] getAvailabelLevels()
    {
        return available_levels;
    }

}