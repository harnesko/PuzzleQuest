package entity;

import gameObject.Book;
import gameObject.Ingredient;
import gameObject.Wok;
import main.Debug;
import main.GamePanel;
import main.KeyHandler;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

public class Player extends Entity {

    GamePanel gp;
    KeyHandler keyH;

    private String lastBtnPressed;

    public final int screenX; // dessa två variabler är kameran, och de ändras inte
    public final int screenY;

    //Items pickup
    public int hasKey = 0;
    public boolean hasCat = false;
    public boolean hasBook = false;
    public boolean hasWok = false;
    public boolean hasIngredients = false;

    //Quests
    public boolean quest1Complete = false;
    public boolean quest2Complete = false;
    public boolean quest3Complete = false;
    public boolean quest4Complete = false;


    public Player(GamePanel gp, KeyHandler keyH) {
        super(gp);
        this.gp = gp;
        this.keyH = keyH;

        getPlayerImage();
        screenX = gp.screenWidth / 2 - (gp.tileSize / 2); // kamera
        screenY = gp.screenHeight / 2 - (gp.tileSize / 2); // kamera

        solidArea = new Rectangle(); // kan justeras så klart

        // allt som har med gp.scale att göra lades till av Kinda

        solidArea.x = 8 * gp.scale;
        solidArea.y = 21 * gp.scale;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
        solidArea.width = 16 * gp.scale;
        solidArea.height = 10 * gp.scale;
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

    public void getPlayerImage() { // NY
        try {
            // IDLE animations
            idleDown1 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/playerAlt/man.png")));
            idleDown1 = idleDown1.getSubimage(48, 0, gp.tileSize, gp.tileSize);
            idleDown2 = idleDown1;
            idleLeft1 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/playerAlt/man.png")));
            idleLeft1 = idleLeft1.getSubimage(48, 48, gp.tileSize, gp.tileSize);
            idleLeft2 = idleLeft1;
            idleRight1 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/playerAlt/man.png")));
            idleRight1 = idleRight1.getSubimage(48, 96, gp.tileSize, gp.tileSize);
            idleRight2 = idleRight1;
            idleUp1 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/playerAlt/man.png")));
            idleUp1 = idleUp1.getSubimage(48, 144, gp.tileSize, gp.tileSize);
            idleUp2 = idleUp1;

            // WALK animations
            walkDown1 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/playerAlt/man.png")));
            walkDown1 = walkDown1.getSubimage(0, 0, gp.tileSize, gp.tileSize);

            walkDown2 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/playerAlt/man.png")));
            walkDown2 = walkDown2.getSubimage(96, 0, gp.tileSize, gp.tileSize);

            walkLeft1 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/playerAlt/man.png")));
            walkLeft1 = walkLeft1.getSubimage(0, 48, gp.tileSize, gp.tileSize);

            walkLeft2 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/playerAlt/man.png")));
            walkLeft2 = walkLeft2.getSubimage(96, 48, gp.tileSize, gp.tileSize);

            walkRight1 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/playerAlt/man.png")));
            walkRight1 = walkRight1.getSubimage(0, 96, gp.tileSize, gp.tileSize);

            walkRight2 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/playerAlt/man.png")));
            walkRight2 = walkRight2.getSubimage(96, 96, gp.tileSize, gp.tileSize);

            walkUp1 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/playerAlt/man.png")));
            walkUp1 = walkUp1.getSubimage(0, 144, gp.tileSize, gp.tileSize);

            walkUp2 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/playerAlt/man.png")));
            walkUp2 = walkUp2.getSubimage(96, 144, gp.tileSize, gp.tileSize);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void getPlayerImageOriginal() {
        try {
            // IDLE animations
            idleDown1 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/player/boy_idleD1.png")));
            idleDown2 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/player/boy_idleD2.png")));
            idleLeft1 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/player/boy_idleL1.png")));
            idleLeft2 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/player/boy_idleL2.png")));
            idleUp1 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/player/boy_idleU1.png")));
            idleUp2 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/player/boy_idleU2.png")));
            idleRight1 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/player/boy_idleR1.png")));
            idleRight2 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/player/boy_idleR2.png")));

            // WALK animations
            walkDown1 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/player/boy_downW1.png")));
            walkDown2 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/player/boy_downW2.png")));
            walkLeft1 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/player/boy_leftW1.png")));
            walkLeft2 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/player/boy_leftW2.png")));
            walkUp1 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/player/boy_upW1.png")));
            walkUp2 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/player/boy_upW2.png")));
            walkRight1 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/player/boy_rightW1.png")));
            walkRight2 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/player/boy_rightW2.png")));

            // RUN animations
            runDown1 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/player/boy_downR1.png")));
            runDown2 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/player/boy_downR2.png")));
            runLeft1 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/player/boy_leftR1.png")));
            runLeft2 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/player/boy_leftR2.png")));
            runUp1 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/player/boy_upR1.png")));
            runUp2 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/player/boy_upR2.png")));
            runRight1 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/player/boy_rightR1.png")));
            runRight2 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/player/boy_rightR2.png")));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void update() {
        spriteCounter++;
        speed = 5;
        if (keyH.upPressed || keyH.leftPressed || keyH.downPressed || keyH.rightPressed) {  // OM NÅN KEY ÄR PRESSED
            //System.out.println("Player pos x : " + worldX / gp.tileSize + "\n Player pos y: " + worldY / gp.tileSize );


            if (keyH.shiftPressed) { // OM SHIFT ÄR PRESSED
                speed = 8;
                // TODO: speed 5, animations run klart. men behöver xtra animation (idle mellan gående animation change)
                if (spriteCounter > 6) {
                    spriteNum = spriteNum == 1 ? 2 : 1;
                    spriteCounter = 0;
                }
            } else {  // OM SHIFT ÄR INTE PRESSED
                if (spriteCounter > 12) {
                    spriteNum = spriteNum == 1 ? 2 : 1;
                    spriteCounter = 0;
                }
            }

            lastBtnPressed = setMovement();

            // CHECK TILE COLLISION
            collisionOn = false;
            gp.collisionChecker.checkTile(this);

            // CHECK OBJECT COLLISION
            int objIndex = gp.collisionChecker.checkObject(this, EntityType.PLAYER);
            pickUpObject(objIndex);

            // CHECK EVENT
            gp.eHandler.checkEvent();
            //CHECK NPC COLLISION
            int npcIndex = gp.collisionChecker.checkEntity(this, gp.npcList);
            interactWithNpc(npcIndex);
            if (keyH.ePressed) {
                if (npcIndex != -1) {
                    gp.npcList[gp.currentMap][npcIndex].speak();
                }
                keyH.ePressed = false;
                System.out.println("Key E pressed");
            }
            // IF COLLISION IS FALSE, PLAYER CAN MOVE
            if (!collisionOn) {
                switch (direction) {
                    case "walkup" -> worldY -= speed;
                    case "walkdown" -> worldY += speed;
                    case "walkleft" -> worldX -= speed;
                    case "walkright" -> worldX += speed;
                }
            }

        } else {
            if (!lastBtnPressed.equals("")) {
                direction = lastBtnPressed;
            }
        }
    }

    private void interactWithNpc(int npcIndex) {
        if (npcIndex != -1) {
            if (keyH.ePressed) {
                gp.gameState = gp.dialogueState;
                gp.currentSpeaker = npcIndex;
            }
        }
    }

    public void pickUpObject(int index) {
        if (index != -1) { // index -1 means no object is near player
            String objectName = gp.obj[gp.currentMap][index].name;

            switch (objectName) { //This removes the object when walked on
                case "cat":
                    hasCat = true;
                    quest1Complete = true;
                    gp.obj[1][index] = null;
                    gp.playSoundEffect(4);
                    gp.ui.showMessage("Oh boi is Young-boi Mjau");
                    if (quest1Complete) {
                        gp.obj[1][2] = new Book();
                        gp.obj[1][2].worldX = 6 * gp.tileSize + (gp.tileSize / 2);
                        gp.obj[1][2].worldY = 18 * gp.tileSize;
                    }
                    break;

                case "Book":
                    hasBook = true;
                    quest2Complete = true;
                    gp.obj[1][index] = null;
                    gp.playSoundEffect(4);
                    gp.ui.showMessage("Found the recipe book");
                    if (quest2Complete) {
                        gp.obj[1][4] = new Ingredient();
                        gp.obj[1][4].worldX = 17 * gp.tileSize + (gp.tileSize / 2);
                        gp.obj[1][4].worldY = 67 * gp.tileSize;
                    }
                    break;

                case "Wok":
                    hasWok = true;
                    quest4Complete = true;
                    hasKey = 1;
                    gp.obj[1][index] = null;
                    gp.playSoundEffect(4);
                    gp.ui.showMessage("mmm yummy yummy");
                    break;

                case "Ingredient":
                    hasIngredients = true;
                    quest3Complete = true;
                    gp.obj[1][index] = null;
                    gp.playSoundEffect(4);
                    gp.ui.showMessage("You retrieved the ingredient");
                    break;

                case "End":
                    gp.gameState = gp.endScreenState;
                    break;

                case "Door":
                    if (hasKey == 1) {
                        gp.obj[1][index] = null;
                        gp.playSoundEffect(2);
                        hasKey--;
                    } else {
                        gp.ui.showMessage("hey man you need a key for that");
                    }
                    break;
                case "Chest":
                    gp.ui.gameFinished = true;
                    break;
            }
        }
    }

    public String setMovement() {
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
        return "idledown";
    }

    public void draw(Graphics2D g2, boolean debugON) {
        Debug debug = new Debug(); // kan tas bort

        BufferedImage image = null;

        switch (direction) {
            case "walkup" -> {
                image = spriteNum == 1 ? walkUp1 : walkUp2;
            }
            case "walkdown" -> {
                if (spriteNum == 1) {
                    image = walkDown1;

                }
                if (spriteNum == 2) {
                    image = walkDown2;

                }
            }
            case "walkleft" -> {
                if (spriteNum == 1) {
                    image = walkLeft1;

                }
                if (spriteNum == 2) {
                    image = walkLeft2;

                }
            }
            case "walkright" -> {
                if (spriteNum == 1) {
                    image = walkRight1;

                }
                if (spriteNum == 2) {
                    image = walkRight2;

                }
            }
            case "idleup" -> {
                if (spriteNum == 1) {
                    image = idleUp1;

                }
                if (spriteNum == 2) {
                    image = idleUp2;

                }
            }
            case "idledown" -> {
                if (spriteNum == 1) {
                    image = idleDown1;

                }
                if (spriteNum == 2) {
                    image = idleDown2;

                }
            }
            case "idleleft" -> {
                if (spriteNum == 1) {
                    image = idleLeft1;

                }
                if (spriteNum == 2) {
                    image = idleLeft2;

                }
            }
            case "idleright" -> {
                if (spriteNum == 1) {
                    image = idleRight1;
                }
                if (spriteNum == 2) {
                    image = idleRight2;
                }
            }
        }

        g2.drawImage(image, screenX, screenY, gp.tileSize, gp.tileSize, null);
        if (debugON) { // kan tas bort men ta ej bort rn
            debug.showPlayerCollisionBox(g2, screenX, screenY, solidArea.x, solidArea.y, solidArea.width, solidArea.height);
        }
    }
}