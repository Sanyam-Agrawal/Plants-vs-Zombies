import javafx.stage.*;
import javafx.event.*;
import javafx.scene.image.*;
import javafx.scene.control.*;
import javafx.scene.input.*;
import javafx.scene.paint.*;
import javafx.scene.*;
import javafx.scene.text.*;
import javafx.scene.layout.*;
import javafx.geometry.*;
import javafx.animation.*; 
import javafx.util.Duration;
import javafx.scene.media.*;
import java.util.*;
import java.io.*;

class Row implements Serializable
{
    private int middle_point;

    private final Map<Plants,Integer> plants;
    private final Set<Peas> peas;
    private LawnMower lawnmower;
    private final Set<Zombies> zombies;

    private final boolean[] columns;

    private Random r;

    private static AudioClip groan_zombie = new AudioClip("file:groan_zombie.mp3");

    Row (int _middle_point)
    {
        middle_point = _middle_point;
        plants = new HashMap<>();
        peas = new HashSet<>();
        lawnmower = new LawnMower();
        zombies = new HashSet<>();
        columns = new boolean[9];
        r = new Random();
    }

    public boolean isColumnOkay(int column) { return !columns[column]; }

    public void markEmpty(int column) { columns[column] = false; }

    public VBox addPlant(Plants plant, int column)
    {
        columns[column] = true;
        this.plants.put(plant,column);
        VBox result = plant.getVBox();
        result.setTranslateY(middle_point);
        plant.ypos = middle_point;
        return result;
    }

    public VBox addPea (int column, boolean isFreeze, double x)
    {
        Peas pea = new Peas(isFreeze);
        if (zombies.isEmpty()) return null;
        this.peas.add(pea);
        VBox res = pea.getVBox();
        while ((res=pea.getVBox())==null);
        res.setTranslateY(middle_point+20);
        pea.ypos = middle_point+20;
        res.setTranslateX(x);
        pea.xpos = x;
        return res;
    }

    public void removeLawnMower() { lawnmower = null; }

    public VBox spawnZombie (int severity)
    {
        groan_zombie.play();
        Zombies zom;
        switch (r.nextInt(severity))
        {
            case 1: zom = new ConeHeadZombies(); break;
            case 2: zom = new BucketHeadZombies(); break;
            case 3: zom = new FootballZombies(); break;
            default: zom = new Zombies();
        }

        while (zom.getHealth()==0);
        this.zombies.add(zom);

        VBox res;
        while ((res = zom.getVBox())==null);
        res.setTranslateY(middle_point-85);
        zom.ypos = middle_point-85;
        res.setTranslateX(1100);
        zom.xpos = 1100;
        return res;
    }

    public int getMiddle() { return this.middle_point; }
    public Map<Plants,Integer> getPlants() { return this.plants; }
    public Set<Peas> getPeas() { return this.peas; }
    public LawnMower getLawnMower() { return this.lawnmower; }
    public Set<Zombies> getZombies() { return this.zombies; }
}
