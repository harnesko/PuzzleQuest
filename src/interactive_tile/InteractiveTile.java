package interactive_tile;

import entity.Entity;
import main.GamePanel;

import java.awt.*;



public class InteractiveTile extends Entity {

    GamePanel gp;
    public boolean destructable = false;

    public InteractiveTile(GamePanel gp, int col, int row){
        super(gp);
        this.gp = gp;
    }


    public void update(){

    }

}
