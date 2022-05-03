package entity;

import main.GamePanel;

import java.awt.*;
import java.awt.image.BufferedImage;


public class Entity {

    GamePanel gp;
    public int worldX, worldY;
    public int speed;
    public BufferedImage idleDown1, idleDown2, idleLeft1, idleLeft2, idleUp1, idleUp2, // detta kan flyttas sen t player ?
            idleRight1, idleRight2, walkDown1, walkDown2, walkLeft1, walkLeft2, walkUp1, walkUp2,
            walkRight1, walkRight2, runDown1, runDown2, runLeft1, runLeft2, runUp1, runUp2, runRight1, runRight2;
    public BufferedImage up1, up2, down1, down2, left1, left2, right1, right2; // TODO: k - problem med object collision, checka sen up1orna
    public String direction;
    public int spriteCounter = 0;
    public int spriteNum = 1;
    public Rectangle solidArea; // detta är ersättningen för body saken i libgdx. hjälper med collision
    public int solidAreaDefaultX, solidAreaDefaultY;
    public boolean collisionOn = false;

    // Vi använder BufferedImage för att spara bilder o sånt som syns, glöm ej använda png
    public Entity(GamePanel gp){
        this.gp = gp;
    }
    public Entity(){}
    public void draw(Graphics2D g2){
        BufferedImage image = null;
        int screenX = worldX - gp.player.worldX + gp.player.screenX;
        int screenY = worldY - gp.player.worldY + gp.player.screenY;

        //if (inside camera frame)
        if (worldX + gp.tileSize > gp.player.worldX - gp.player.screenX &&
                worldX - gp.tileSize < gp.player.worldX + gp.player.screenX &&
                worldY + gp.tileSize > gp.player.worldY - gp.player.screenY &&
                worldY - gp.tileSize < gp.player.worldY + gp.player.screenY) {



            g2.drawImage(image, screenX, screenY, gp.tileSize, gp.tileSize, null);
        }

    }
}
