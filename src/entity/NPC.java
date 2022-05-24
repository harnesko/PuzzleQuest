package entity;

import main.GamePanel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Objects;
import java.util.Random;

/**
 * Abstract superclass for all NPC implementation
 * @author Måns Harnesk
 * @version 1.2
 */


/**
 * (Notes)
 * NPC currently doesn't properly detect player in their moveset, check the collisionchecker later
 *
 */
public abstract class NPC extends Entity{       //Super class for all npc's

    public GamePanel gp;

    private int screenX; // Are these needed for an NPC or is it only for Player?
    private int screenY;
    public int directionalDelay = 0;
    int dialogueIndex = 0;
    String[] firstDialogue = new String[10];
    String[] stepTwoDialogue = new String[10];
    String[] stepThreeDialogue = new String[10];

    public NPC (GamePanel gp){
        super(gp);
        //this.gp = gp;
        speed = 5;

        //loadNpcImage();
        //setDefaultNpcPosition();

        solidArea = new Rectangle();        //Hitbox?
        solidArea.x = 8;                    //Defines center of hitbox?
        solidArea.y = 16;
        solidArea.width = 32;               //Smaller size than a tile to avoid collision problems
        solidArea.height = 32;              //Check solidAreaFörklaring.png for guide
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

    /**
     * Randomize a direction the NPC will use and pretend its pathfinding
     * Update direction after a few seconds
     * Npc needs to check collision properly
     */
    public void setDirection(){
        directionalDelay++;

        if (directionalDelay > 100) {
            Random random = new Random();
            int i = random.nextInt(4);

            if(i == 0){
                direction = "walkup";
            }
            if(i == 1){
                direction = "walkdown";
            }
            if(i == 2){
                direction = "walkleft";     //set image to npc_mario_left.png todo
            }
            if(i == 3){
                direction = "walkright";     //set image to npc_mario_right.png
            }
            directionalDelay = 0;
        }
    }

    public void update() {
        setDirection();
        this.collisionOn = false;
        gp.collisionChecker.checkTile(this);        //"this" will be the sub-class instance

        //This is probably a better solution than the above line of code
        //gp.collisionChecker.checkObject(this, EntityType.NPC);

        // IF COLLISION IS FALSE, NPC CAN MOVE
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
        if (spriteCounter > 30) {
            if (spriteNum == 1) {
                spriteNum = 2;
            } else if (spriteNum == 2) {
                spriteNum = 1;
            }
            spriteCounter = 0;
        }


    }
    public void progressDialogue(){
        if (dialogueIndex < firstDialogue.length){
            dialogueIndex++;
        }else{
            dialogueIndex = 1;
        }
    }


    //Every NPC should say something, this is just to make sure that they know that
    public abstract void speak();


    public abstract String getCurrDialogue();
}
