package entity;

import main.GamePanel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;

public class NPC extends Entity{

    private GamePanel gp;

    private int screenX; // Are these needed for an NPC or is it only for Player?
    private int screenY;
    public NPC (GamePanel gp){
        this.gp = gp;

        loadNpcImage();
        setDefaultNpcPosition();

        solidArea = new Rectangle();        //Hitbox?
        solidArea.x = 8;                    //Defines center of hitbox?
        solidArea.y = 16;
        solidArea.width = 32;               //Smaller size than a tile to avoid collision problems
        solidArea.height = 32;              //Check solidAreaFörklaring.png för guide
    }
    public void loadNpcImage(){
        try{
            idleDown1 = ImageIO.read(getClass().getResourceAsStream("/NPC/npc_mario.png"));
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public void setDefaultNpcPosition(){
        worldX = gp.tileSize * 32;
        worldY = gp.tileSize * 30;
    }
}
