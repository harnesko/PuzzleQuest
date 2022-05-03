package entity;

import main.GamePanel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

public class NPC extends Entity{        //TODO Gör klassen abstract och låt npc's ärva

    private GamePanel gp;

    private int screenX; // Are these needed for an NPC or is it only for Player?
    private int screenY;

    public NPC (GamePanel gp){
        //super(gp);
        this.gp = gp;
        speed = 1;

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
            idleDown1 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/NPC/npc_mario.png")));
        }catch (Exception e){
            e.printStackTrace();
        }
    }


    public void draw(Graphics2D g2){
        BufferedImage image = null;
        image = walkDown1;
        g2.drawImage(image,screenX,screenY,gp.tileSize,gp.tileSize,null);       //I dont understaaand this line
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
}
