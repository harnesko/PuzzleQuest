package entity;

import main.GamePanel;
import main.KeyHandler;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Player extends Entity {

    GamePanel gp;
    KeyHandler keyH;

    public final int screenX; // dessa två variabler är kameran, och de ändras inte
    public final int screenY;
    int hasKey = 0;

    public Player(GamePanel gp, KeyHandler keyH) {
        this.gp = gp;
        this.keyH = keyH;

        setDefaultValues();
        getPlayerImage();
        screenX = gp.screenWidth/2 - (gp.tileSize/2);
        screenY = gp.screenHeight/2 - (gp.tileSize/2);

        solidArea = new Rectangle(); // kan justeras så klart
        solidArea.x = 8;
        solidArea.y = 16;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
        solidArea.width = 32;
        solidArea.height = 32;
        /** solid area är kroppen som ska importeras hos playern för att fixa fysiken hos han. anledningen vi kör
         * dessa värden är för att vi vill inte hela player-tile:n ska vara fysisk eftersom det skapar problem när
         * man t.ex vill gå igenom två väggar. man måste vara precis och inte ens 1 pixel fel annars kolliderar man så
         * vi kör istället att fysiska kroppen är mycket smalare.
         *
         * x och y är positionen där den fysiska kroppen börjar medan width och height bestämmer hur lång o bred den är.
         * vi utgår från att spelaren ska "bära" på denna fysiska kroppen och därför från 16x16 tile:n som spelarens
         * storlek är.
         *
         * titta på bilden i resource.extra.solidAreaFörklaring.png !!!*/
    }

    public void setDefaultValues() {
        // spelarens position i hela mappen, inte kameran
        worldX = gp.tileSize * 23;
        worldY = gp.tileSize * 21;
        speed = 4;
        direction = "down";
    }

    public void getPlayerImage() {
        try {
            up1 = ImageIO.read(getClass().getResourceAsStream("/player/boy_up_1.png"));
            up2 = ImageIO.read(getClass().getResourceAsStream("/player/boy_up_2.png"));
            down1 = ImageIO.read(getClass().getResourceAsStream("/player/boy_down_1.png"));
            down2 = ImageIO.read(getClass().getResourceAsStream("/player/boy_down_2.png"));
            left1 = ImageIO.read(getClass().getResourceAsStream("/player/boy_left_1.png"));
            left2 = ImageIO.read(getClass().getResourceAsStream("/player/boy_left_2.png"));
            right1 = ImageIO.read(getClass().getResourceAsStream("/player/boy_right_1.png"));
            right2 = ImageIO.read(getClass().getResourceAsStream("/player/boy_right_2.png"));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void update() {
        if(keyH.upPressed || keyH.leftPressed || keyH.downPressed || keyH.rightPressed){
            if (keyH.upPressed) {
                direction = "up";
            } else if (keyH.downPressed) {
                direction = "down";
            } else if (keyH.leftPressed) {
                direction = "left";
            } else if (keyH.rightPressed) {
                direction = "right";
            }

            // CHECK TILE COLLISION
            collisionOn = false;
            gp.collisionChecker.checkTile(this);

            // CHECK OBJECT COLLISION
            int objIndex = gp.collisionChecker.checkObject(this,true);
            pickUpObject(objIndex);

            // IF COLLISION IS FALSE, PLAYER CAN MOVE
            if (!collisionOn){
                switch (direction) {
                    case "up" -> worldY -= speed;
                    case "down" -> worldY += speed;
                    case "left" -> worldX -= speed;
                    case "right" -> worldX += speed;
                }
            }


        }
        // flytta denna upp till första if-satsen om ni inte vill ha animerad-medan-stilla gubbe

        // denna metod låter sprite gubben ändra walk animation. spriteCounter räknar hur många
        // frames innan man typ "checkar" vilket håll man ska ändra till. hela update() kallas ju
        // 60 ggn per sekund så man kan säga att varje 10 frames så ändras animation till up2 eller right1, osv..

        spriteCounter ++;
        if (spriteCounter > 10){
            if (spriteNum == 1) {
                spriteNum = 2;
            } else if (spriteNum == 2){
                spriteNum = 1;
            }
            spriteCounter = 0;
        }
    }

    public void pickUpObject(int index){
        if (index != 999){ // måste ändras om vi nånsin tänker ha objekt på index 999 ..... men basically
                                            // om index är ej empty alltså innehåller ett objekt
            String objectName = gp.obj[index].name;

            switch(objectName) { // denna funktion tar bort objektet när vi passerar den
                case "Key":
                    hasKey++;
                    gp.obj[index] = null;
                    System.out.println("Keys: " +hasKey);
                    break;
                case "Door":
                    if(hasKey > 0){
                        gp.obj[index] = null;
                        hasKey--;
                        break;
                    }
            }
        }
    }

    public void draw(Graphics2D g2) {

        BufferedImage image = null;

        switch (direction) {
            case "up":
                if (spriteNum == 1){
                    image = up1;
                }
                if (spriteNum == 2){
                    image = up2;
                }
                break;
            case "down":
                if (spriteNum == 1){
                    image = down1;
                }
                if (spriteNum == 2){
                    image = down2;
                }
                break;
            case "left":
                if (spriteNum == 1){
                    image = left1;
                }
                if (spriteNum == 2){
                    image = left2;
                }
                break;
            case "right":
                if (spriteNum == 1){
                    image = right1;
                }
                if (spriteNum == 2){
                    image = right2;
                }
                break;
        }

        g2.drawImage(image,screenX,screenY,gp.tileSize, gp.tileSize, null);

    }
}
