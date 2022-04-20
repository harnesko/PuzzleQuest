package entity;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Entity {

    public int worldX, worldY;
    public int speed;
    public BufferedImage up1,up2,down1,down2,left1,left2,right1,right2;
    public String direction;
    public int spriteCounter = 0;
    public int spriteNum = 1;
    public Rectangle solidArea; // detta är ersättningen för body saken i libgdx. hjälper med collision
    public int solidAreaDefaultX, solidAreaDefaultY;
    public boolean collisionOn = false;

    // Vi använder BufferedImage för att spara bilder o sånt som syns, glöm ej använda png
}
