package entity;

import main.GamePanel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Objects;
import entity.Entity;

/**
 * Abstract superclass for all NPC implementation
 * @author Måns Harnesk
 * @version 1.1
 */

public abstract class NPC extends Entity{       //Super class for all npc's

    public GamePanel gp;

    private int screenX; // Are these needed for an NPC or is it only for Player?
    private int screenY;

    public NPC (GamePanel gp){
        super(gp);
        //this.gp = gp;
        speed = 1;

        //loadNpcImage();
        //setDefaultNpcPosition();

        solidArea = new Rectangle();        //Hitbox?
        solidArea.x = 8;                    //Defines center of hitbox?
        solidArea.y = 16;
        solidArea.width = 32;               //Smaller size than a tile to avoid collision problems
        solidArea.height = 32;              //Check solidAreaFörklaring.png för guide
    }
    public BufferedImage loadNpcImage(){
        BufferedImage image = null;
        try{    //TODO Load proper image, ídledown is just a placeholder
            idleDown1 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/NPC/npc_mario_left.png")));
        }catch (Exception e){
            e.printStackTrace();
        }
        return image;
    }

    public void draw(Graphics2D g2){
        BufferedImage image = null;
        image = walkDown1;
        g2.drawImage(image,screenX,screenY,gp.tileSize,gp.tileSize,null);
    }
    public void setDefaultNpcPosition(){
        this.worldX = gp.tileSize * 32;
        this.worldY = gp.tileSize * 30;
    }
    public void setAction(){
        //Todo randomize direction and pretend its pathfinding
    }

    public void update() {
        this.collisionOn = true;
    }
    public abstract void speak();


}
