public abstract class Creature
{
    private String imgSrc;

    public Creature(String imgSrc){
        this.imgSrc = imgSrc;
    }

    public String getImgSrc(){
        return this.imgSrc;
    }

    public void setImgSrc(String newImgSrc){
        this.imgSrc = newImgSrc;
    }
}
