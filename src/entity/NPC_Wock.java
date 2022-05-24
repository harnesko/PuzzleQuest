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
    public GamePanel gp;
    public BufferedImage marioLeft, marioRight;
    private int screenX; // Are these needed for an NPC or is it only for Player?
    private int screenY;

    //Move this copy pasted mess to npc abstract class when there's time lol
    public String[] npcMarioDialogue = new String[5];
    public boolean[] questProgress = {false, false};
    public boolean isQuestDone = false;
    public boolean isQuestStarted = false;      //only the first interaction should give quest. This could be redundant, depending on how we do the dialogue window
    private final String[] secondDialogue = new String[10];
    private final String[] thirdDialogue = new String[20];
    private String[] currentDialogue = new String[20];

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
        firstDialogue[0] = "You:\nCan i get a Wocks special?";
        firstDialogue[1] = "Diliam Wock:\nHeya Mike.\nSure can do but you know I’m glad you came!";
        firstDialogue[2] = "You:\nWhy?";
        firstDialogue[3] = "Diliam Wock:\nWell you know my parents recipe book and I can’t find it.";
        firstDialogue[4] = "Diliam Wock:\nIt’s a new seasonal special and I can’t remember\nthe exact recipe for this new season.";
        firstDialogue[5] = "Diliam Wock:\nCould you go get it for me?";
        firstDialogue[6] = "You:\n...";
        firstDialogue[7] = "You:\nFine";
        firstDialogue[8] = "Diliam Wock:\nI think they hid it somewhere around their old house.\nHappy Hunting!";
    }

    /**
     * Sets instance varaible dialogue[] to match quest progress in getCurrDialogue()
     * Sets UI dialogue variable to local dialogue array, while keeping track of index
     * @author Måns
     */
    @Override
    public void speak() {
        getCurrDialogue();
        if(currentDialogue[dialogueIndex] == null || (dialogueIndex >= currentDialogue.length - 1)) {
            System.out.println("Resetting dialogue..");
            dialogueIndex = 0;
        }else{
            gp.ui.currentDialogue = currentDialogue[dialogueIndex]; //use e to go through dialaogue lines later
            dialogueIndex++;
        }
    }

    /**
     * Finds the first spot in the array that's not yet been completed, and completes it
     * @author Måns
     */
    public void progressQuest(){
        for (int i = 0; i < questProgress.length; i++){
            if(!questProgress[i]) {
                questProgress[i] = true;
                break;
            }
        }
    }

    /**
     * Checks current quest progress and returns the corresponding dialogue
     * @return Current string of dialogue from the array
     * @author Måns
     */
    public String getCurrDialogue(){
        if (!questProgress[0]) {
            currentDialogue = firstDialogue;
        }else if(!questProgress[1]){
            currentDialogue = secondDialogue;
        }else{
            currentDialogue = thirdDialogue;
        }

        if (dialogueIndex <= currentDialogue.length) {
            return currentDialogue[dialogueIndex];
        } else {
            return "No more dialogue..";
        }
    }

    /**
     * Done every frame, 60times / second
     * @author Måns
     */
    @Override
    public void update() {
        setDirection();
        //walk();
    }

    /**
     * Makes the npc move around by checking the tile collision and moving accordingly
     * @author Måns
     */
    public void walk(){
        collisionOn = false;
        gp.collisionChecker.checkTile(this);        //"this" will be the sub-class instance
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
                    image = marioRight;          //fix luigiLeft2 later
                    break;
                }
            }
            case "walkright" -> {
                if (spriteNum == 1) {
                    image = marioLeft;
                    break;
                }
                if (spriteNum == 2) {
                    image = marioLeft;
                    System.out.println();
                    break;
                }
            }
            case "up" -> {
                image = marioLeft;
                break;
            }
            case "down" -> {
                image = marioLeft;
                //fix this when we have more images
                //fix this mess later
            }
        }
        int screenX = worldX - gp.player.worldX + gp.player.screenX;
        int screenY = worldY - gp.player.worldY + gp.player.screenY;
        image = loadNpcImage();     //only for debug purposes, replace this with the switch case above later
        g2.drawImage(image, screenX, screenY, 32, 32, null);

    }
}