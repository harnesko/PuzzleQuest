package entity;

import main.GamePanel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Objects;

/**
 * Class for npc 1, currently a placeholder .png image. Please don't tell Nintendo
 * @author Måns Harnesk
 * @version 1.4
 */

public class NPC_Wock extends NPC{

    private int screenX; // Are these needed for an NPC or is it only for Player?
    private int screenY;
    public BufferedImage marioLeft, marioRight;
    public GamePanel gp;
    public String[] npcMarioDialogue = new String[5];

    public NPC_Wock(GamePanel gp){
        super(gp);
        this.gp = gp;           //I don't understand why this line needs to be here but if it's not it goes to shit
        direction = "down";
        speed = 1;

        solidArea = new Rectangle(4, 8, 8, 16);
        collisionOn = true;
        createDialogue();
        loadNpcImage();
    }
    public BufferedImage loadNpcImage(){
        BufferedImage npcImage1 = null;
        BufferedImage npcImage2 = null;

        try{
           npcImage1 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/npc/npcOne/npc_mario_left.png")));
           npcImage2 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/npc/npcOne/npc_mario_right.png")));
        }catch (Exception e){
            e.printStackTrace();
        }
        return npcImage1;
    }

    /**
     * (Notes)
     * Fix broken npc detection and collision - DONE
     * Start with displaying dialogue using the already made UI.showMessage() method    - DONE
     * Later work on a dialogue box or something    - NOPE
     */
    public void createDialogue(){
        //Mostly for testing purposes rn, do it properly later or something
        npcMarioDialogue[0] = "Hello";
        npcMarioDialogue[1] = "Nintendo couldn't make this game if they tried";
        npcMarioDialogue[2] = "bla bla bla";
        npcMarioDialogue[3] = "bla bla bla";
        npcMarioDialogue[4] = "Please bring this item back to Luigi?";
    }

    @Override
    public void speak() {
        if(firstDialogue[dialogueIndex] == null || (dialogueIndex >= firstDialogue.length - 1)) {
            System.out.println("Resetting dialogue..");
            dialogueIndex = 0;
        }else{
            gp.ui.currentDialogue = firstDialogue[dialogueIndex]; //use e to go through dialaogue lines later
            dialogueIndex++;
        }
    }
    public String getCurrDialogue(){
        if(dialogueIndex <= firstDialogue.length){
            return firstDialogue[dialogueIndex];
        }else{
            return "No more dialogue..";
        }
    }
    /**
     * Randomize a direction the NPC will use and pretend its pathfinding
     * Update direction after a few seconds
     * Change picture to match walking direction (WIP)
     */
    //Remove and put in super later I guess
    @Override
    public void update() {
        //super.update();     //fix this mess later with inheritence, super method gives nullpointer on gamepanel instance for w/e reason
        setAction();
        collisionOn = false;
        gp.collisionChecker.checkTile(this);        //"this" will be the sub-class instance. Npc doesn't properly detect player rn, fix later

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
    public void draw(Graphics2D g2) {
        BufferedImage image = null;
        switch (direction) {

            case "walkleft" -> {
                if (spriteNum == 1) {
                    image = marioLeft;
                    break;
                }
                if (spriteNum == 2) {
                    image = marioLeft;          //fix marioLeft2 later
                    break;
                }
            }
            case "walkright" -> {
                if (spriteNum == 1) {
                    image = marioRight;
                    break;
                }
                if (spriteNum == 2) {
                    image = marioRight;
                    break;
                }
            }
            case "up" -> {
                image = marioRight;
                break;
            }
            case "down" -> {
                image = marioLeft;
                //System.out.println("npc looks down");
                break;  //fix this mess later
            }
        }
        int screenX = worldX - gp.player.worldX + gp.player.screenX;
        int screenY = worldY - gp.player.worldY + gp.player.screenY;
        image = loadNpcImage();     //only for debug purposes, replace this with the switch case above later
        g2.drawImage(image, screenX, screenY, 32, 32, null);
    }
}
