package entity;

import gameObject.GameObject;
import main.GamePanel;
import main.KeyHandler;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Player extends Entity {

    GamePanel gp;
    KeyHandler keyH;

    private String lastBtnPressed;

    public final int screenX; // dessa två variabler är kameran, och de ändras inte
    public final int screenY;
    public int hasKey = 0; //Gustav gjorde till public

    public Player(GamePanel gp, KeyHandler keyH) {
        this.gp = gp;
        this.keyH = keyH;

        setDefaultValues();
        getPlayerImage();
        screenX = gp.screenWidth / 2 - (gp.tileSize / 2);
        screenY = gp.screenHeight / 2 - (gp.tileSize / 2);

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

        lastBtnPressed = "";

    }

    public void setDefaultValues() {
        // spelarens position i hela mappen, inte kameran
        worldX = gp.tileSize * 23;
        worldY = gp.tileSize * 21;
 //       worldX = gp.tileSize * 12;
 //       worldY = gp.tileSize * 13;
        direction = "idledown";
    }

    public void getPlayerImage() {
        try {
            // IDLE animations
            idleDown1 = ImageIO.read(getClass().getResourceAsStream("/player/boy_idleD1.png"));
            idleDown2 = ImageIO.read(getClass().getResourceAsStream("/player/boy_idleD2.png"));
            idleLeft1 = ImageIO.read(getClass().getResourceAsStream("/player/boy_idleL1.png"));
            idleLeft2 = ImageIO.read(getClass().getResourceAsStream("/player/boy_idleL2.png"));
            idleUp1 = ImageIO.read(getClass().getResourceAsStream("/player/boy_idleU1.png"));
            idleUp2 = ImageIO.read(getClass().getResourceAsStream("/player/boy_idleU2.png"));
            idleRight1 = ImageIO.read(getClass().getResourceAsStream("/player/boy_idleR1.png"));
            idleRight2 = ImageIO.read(getClass().getResourceAsStream("/player/boy_idleR2.png"));

            // WALK animations
            walkDown1 = ImageIO.read(getClass().getResourceAsStream("/player/boy_downW1.png"));
            walkDown2 = ImageIO.read(getClass().getResourceAsStream("/player/boy_downW2.png"));
            walkLeft1 = ImageIO.read(getClass().getResourceAsStream("/player/boy_leftW1.png"));
            walkLeft2 = ImageIO.read(getClass().getResourceAsStream("/player/boy_leftW2.png"));
            walkUp1 = ImageIO.read(getClass().getResourceAsStream("/player/boy_upW1.png"));
            walkUp2 = ImageIO.read(getClass().getResourceAsStream("/player/boy_upW2.png"));
            walkRight1 = ImageIO.read(getClass().getResourceAsStream("/player/boy_rightW1.png"));
            walkRight2 = ImageIO.read(getClass().getResourceAsStream("/player/boy_rightW2.png"));

            // RUN animations
            runDown1 = ImageIO.read(getClass().getResourceAsStream("/player/boy_downR1.png"));
            runDown2 = ImageIO.read(getClass().getResourceAsStream("/player/boy_downR2.png"));
            runLeft1 = ImageIO.read(getClass().getResourceAsStream("/player/boy_leftR1.png"));
            runLeft2 = ImageIO.read(getClass().getResourceAsStream("/player/boy_leftR2.png"));
            runUp1 = ImageIO.read(getClass().getResourceAsStream("/player/boy_upR1.png"));
            runUp2 = ImageIO.read(getClass().getResourceAsStream("/player/boy_upR2.png"));
            runRight1 = ImageIO.read(getClass().getResourceAsStream("/player/boy_rightR1.png"));
            runRight2 = ImageIO.read(getClass().getResourceAsStream("/player/boy_rightR2.png"));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void update() {
        // TODO: 5 sprint, 15 walk, 30 idle........... men riktigt låg prio - k
        // TODO: om shift, speed change, animation change

        spriteCounter++;
        speed = 3;

        if (keyH.upPressed || keyH.leftPressed || keyH.downPressed || keyH.rightPressed) {  // OM NÅN KEY ÄR PRESSED

            boolean shiftPressed = false;

            if (keyH.shiftPressed) { // OM SHIFT ÄR PRESSED
                shiftPressed = true;
                speed = 6;
                // TODO: speed 5, animations run klart. men behöver xtra animation (idle mellan gående animation change)
                if (spriteCounter > 7) {
                    if (spriteNum == 1) {
                        spriteNum = 2;
                    } else if (spriteNum == 2) {
                        spriteNum = 1;
                    }
                    spriteCounter = 0;
                }
            } else {  // OM SHIFT ÄR INTE PRESSED
                if (spriteCounter > 12) {
                    if (spriteNum == 1) {
                        spriteNum = 2;
                    } else if (spriteNum == 2) {
                        spriteNum = 1;
                    }
                    spriteCounter = 0;
                }
            }

            lastBtnPressed = setMovement(shiftPressed);

            // CHECK TILE COLLISION
            collisionOn = false;
            gp.collisionChecker.checkTile(this);

            // CHECK OBJECT COLLISION
            int objIndex = gp.collisionChecker.checkObject(this, EntityType.PLAYER);
            pickUpObject(objIndex);

            // CHECK EVENT
            gp.eHandler.checkEvent();
            /*
            Funkar av någon anledning inte att starta spelet då man ska checka event.
            Kan vara pga av rendering issues. Får testa
             */

            //CHECK NPC COLLISION
            int npcIndex = gp.collisionChecker.checkEntity(this, gp.npcList);
            interactWithNpc(npcIndex);
            //todo Se om vi kan ta bort parametrar, köra alla objekt i samma array och kolla typ med "instanceof" metoden
            //Checka igenom CollisionChecker
            if(keyH.ePressed){

                if (npcIndex != -1){
                    gp.npcList[gp.currentMap][npcIndex].speak();

                }
                keyH.ePressed = false;
                System.out.println("Key E pressed");
            }

            System.out.println("Npc index = " + npcIndex);


            // IF COLLISION IS FALSE, PLAYER CAN MOVE
            if (!collisionOn) {
                switch (direction) {
                    case "walkup" -> worldY -= speed;
                    case "runup" -> worldY -= speed;
                    case "walkdown" -> worldY += speed;
                    case "rundown" -> worldY += speed;
                    case "walkleft" -> worldX -= speed;
                    case "runleft" -> worldX -= speed;
                    case "walkright" -> worldX += speed;
                    case "runright" -> worldX += speed;
                }
            }

        } else {
            if (!lastBtnPressed.equals("")){
                direction = lastBtnPressed;
            }
        }

        if (spriteCounter > 30) {
            if (spriteNum == 1) {
                spriteNum = 2;
            } else if (spriteNum == 2) {
                spriteNum = 1;
            }
            spriteCounter = 0;
        }


        // flytta denna upp till första if-satsen om ni inte vill ha animerad-medan-stilla gubbe

        // denna metod låter sprite gubben ändra walk animation. spriteCounter räknar hur många
        // frames innan man typ "checkar" vilket håll man ska ändra till. hela update() kallas ju
        // 60 ggn per sekund så man kan säga att varje 30 frames så ändras animation till up2 eller right1, osv..

        // TODO: k - uppdatera texten här


    }

    private void interactWithNpc(int npcIndex) {
        if (npcIndex != -1){
            if(keyH.ePressed){
                gp.npcList[gp.currentMap][npcIndex].speak();
                //keyH.ePressed = false;
            }
        }
    }

    public void pickUpObject(int index) {
        if (index != -1) { // måste ändras om vi nånsin tänker ha objekt på index 999 ..... men basically
            // om index är ej empty alltså innehåller ett objekt
            String objectName = gp.obj[gp.currentMap][index].name;

            switch (objectName) { // denna funktion tar bort objektet när vi passerar den
                case "Key":
                    hasKey++;
                    gp.obj[index] = null;
                    System.out.println("Keys: " + hasKey);
                    gp.ui.showMessage("This dude got a Key!");
                    break;
                case "Door":
                    if (hasKey > 0) {
                        gp.obj[index] = null;
                        hasKey--;

                    }
                    else{
                        gp.ui.showMessage("hey man you need a key for that");
                    }
                    break;
                case "Chest":
                    gp.ui.gameFinished = true;
                    break;
            }
        }
    }

    public String setMovement(boolean shift){
        if (shift){
            if (keyH.upPressed) {
                direction = "runup";
                return "idleup";
            } else if (keyH.downPressed) {
                direction = "rundown";
                return "idledown";
            } else if (keyH.leftPressed) {
                direction = "runleft";
                return "idleleft";
            } else if (keyH.rightPressed) {
                direction = "runright";
                return "idleright";
            }

        } else {
            if (keyH.upPressed) {
                direction = "walkup";
                return "idleup";
            } else if (keyH.downPressed) {
                direction = "walkdown";
                return "idledown";
            } else if (keyH.leftPressed) {
                direction = "walkleft";
                return "idleleft";
            } else if (keyH.rightPressed) {
                direction = "walkright";
                return "idleright";
            }
        }

        return "idledown";
    }

    public void draw(Graphics2D g2) {

        BufferedImage image = null;


        switch (direction) {
            case "walkup" -> {
                if (spriteNum == 1) {
                    image = walkUp1;
                    break;
                }
                if (spriteNum == 2) {
                    image = walkUp2;
                    break;
                }
            }
            case "walkdown" -> {
                if (spriteNum == 1) {
                    image = walkDown1;
                    break;
                }
                if (spriteNum == 2) {
                    image = walkDown2;
                    break;
                }
            }
            case "walkleft" -> {
                if (spriteNum == 1) {
                    image = walkLeft1;
                    break;
                }
                if (spriteNum == 2) {
                    image = walkLeft2;
                    break;
                }
            }
            case "walkright" -> {
                if (spriteNum == 1) {
                    image = walkRight1;
                    break;
                }
                if (spriteNum == 2) {
                    image = walkRight2;
                    break;
                }
            }
            case "runup" -> {
                if (spriteNum == 1) {
                    image = runUp1;
                    break;
                }
                if (spriteNum == 2) {
                    image = runUp2;
                    break;
                }
            }
            case "rundown" -> {
                if (spriteNum == 1) {
                    image = runDown1;
                    break;
                }
                if (spriteNum == 2) {
                    image = runDown2;
                    break;
                }
            }
            case "runleft" -> {
                if (spriteNum == 1) {
                    image = runLeft1;
                    break;
                }
                if (spriteNum == 2) {
                    image = runLeft2;
                    break;
                }
            }
            case "runright" -> {
                if (spriteNum == 1) {
                    image = runRight1;
                    break;
                }
                if (spriteNum == 2) {
                    image = runRight2;
                    break;
                }
            }
            case "idleup" -> {
                if (spriteNum == 1) {
                    image = idleUp1;
                    break;
                }
                if (spriteNum == 2) {
                    image = idleUp2;
                    break;
                }
            }
            case "idledown" -> {
                if (spriteNum == 1) {
                    image = idleDown1;
                    break;
                }
                if (spriteNum == 2) {
                    image = idleDown2;
                    break;
                }
            }
            case "idleleft" -> {
                if (spriteNum == 1) {
                    image = idleLeft1;
                    break;
                }
                if (spriteNum == 2) {
                    image = idleLeft2;
                    break;
                }
            }
            case "idleright" -> {
                if (spriteNum == 1) {
                    image = idleRight1;
                    break;
                }
                if (spriteNum == 2) {
                    image = idleRight2;
                    break;
                }
            }
        }

        int x = screenX;
        int y = screenY;

        if (screenX > worldX){
            x = worldX;
        }
        if (screenY > worldY){
            y = worldY;
        }

        int rightOffset = gp.screenWidth - screenX;
        if (rightOffset > gp.worldWidth - worldX){
            x = gp.screenWidth - gp.worldWidth - worldX;
        }
        int downOffset = gp.screenHeight - screenY;
        if (downOffset > gp.worldHeight - worldY){
            y = gp.screenHeight - gp.worldHeight - worldY;
        }

        g2.drawImage(image,x,y,gp.tileSize,gp.tileSize,null);


    }
}

