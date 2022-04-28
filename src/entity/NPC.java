package entity;

import main.GamePanel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class NPC extends Entity{

    private GamePanel gp;

    private int screenX; // Are these needed for an NPC or is it only for Player?
    private int screenY;
    public NPC (GamePanel gp){
        super(gp);

        loadNpcImage();
        //setDefaultNpcPosition();

        solidArea = new Rectangle();        //Hitbox?
        solidArea.x = 8;                    //Defines center of hitbox?
        solidArea.y = 16;
        solidArea.width = 32;               //Smaller size than a tile to avoid collision problems
        solidArea.height = 32;              //Check solidAreaFörklaring.png för guide
    }
    public BufferedImage loadNpcImage(){
        try{
            this.npcImage1 = ImageIO.read(getClass().getResourceAsStream("/NPC/npc_mario.png"));
        }catch (Exception e){
            e.printStackTrace();
        }
        return npcImage1;
    }
    public void update(){

    }

    public void draw(Graphics2D g2){
        BufferedImage image = null;
        image = loadNpcImage();
        //g2.drawImage(image, screenX, screenY, gp.tileSize,gp.tileSize,null);      //Draws npc in top corner
        g2.drawImage(image, this.worldX, this.worldY, gp.tileSize / 2,gp.tileSize / 2,null);

    }
    public void setDefaultNpcPosition(){
        worldX = gp.tileSize * 32;
        worldY = gp.tileSize * 30;
    }
}
