package entity;

import gameObject.Wok;
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

public class NPC_Wock extends NPC {
    public GamePanel gp;
    public BufferedImage wokLeft, wokRight;

    public boolean[] questProgress = {false, false};
    private final String[] firstDialogue = new String[10];
    private final String[] secondDialogue = new String[10];
    private final String[] thirdDialogue = new String[20];
    private String[] currentDialogue = new String[20];

    public NPC_Wock(GamePanel gp) {
        super(gp);
        this.gp = gp;           //I don't understand why this line needs to be here but if it's not it goes to shit
        direction = "down";
        speed = 1;

        solidArea = new Rectangle(4, 8, 8, 16);
        collisionOn = true;
        createDialogue();
        loadNpcImage();
    }

    public BufferedImage loadNpcImage() {
        BufferedImage npcImage1 = null;
        BufferedImage npcImage2 = null;


        try {
            npcImage1 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/npc/npcWock/NPC_Wock2.png")));
            npcImage2 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/npc/npcWock/NPC_Wock2.png")));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return npcImage1;
    }
    /**
     * Messy array that sets up all npc dialogue
     * @author Måns code, Gustav dialogue
     */
    public void createDialogue() {
        firstDialogue[0] = "You:\nCan i get a Wocks special?";
        firstDialogue[1] = "Diliam Wock:\nHeya Mike.Sure can do but you know\nI’m glad you came!";
        firstDialogue[2] = "You:\nWhy?";
        firstDialogue[3] = "Diliam Wock:\nWell you know my parents recipe book\nand I can’t find it.";
        firstDialogue[4] = "Diliam Wock:\nIt’s a new seasonal special.\nI can’t remember\nthe exact recipe for this new season.";
        firstDialogue[5] = "Diliam Wock:\nCould you go get it for me?";
        firstDialogue[6] = "You:\n...";
        firstDialogue[7] = "You:\nFine";
        firstDialogue[8] = "Diliam Wock:\nI think they hid it somewhere\naround their old house.\nHappy Hunting!";

        secondDialogue[0] = "Diliam Wock:\nThank you thank you… Hmmmm it seems I am out of MSG.\n";
        secondDialogue[1] = "Diliam Wock:\nCould you go by Francesca's store and get me some?";
        secondDialogue[2] = "You:\nMother f… sure yeah I’ll do it";

        thirdDialogue[0] = "Diliam Wock:\n Aaah yes thank you my friend!\n ";
        thirdDialogue[1] = "Diliam Wock:\n A steaming hot season special for you!\n ";
        thirdDialogue[2] = "You:\n Yepp. Bye.\n ";


    }

    /**
     * Sets instance varaible dialogue[] to match quest progress in getCurrDialogue()
     * Sets UI dialogue variable to local dialogue array, while keeping track of index
     * @author Måns
     */
    @Override
    public void speak() {
        if (gp.player.hasBook) {
            gp.ui.currentDialogue = "My book! Thank you";
            progressQuest();
            gp.player.hasBook = false;
        }else if(gp.player.hasIngredients){
            gp.ui.currentDialogue = "My ingredients! Thank you";
            progressQuest();
            gp.player.hasIngredients = false;
            gp.obj[1][3] = new Wok();
            gp.obj[1][3].worldX = 59 * gp.tileSize + (gp.tileSize / 2);
            gp.obj[1][3].worldY = 42 * gp.tileSize;

        } else {
            if(dialogueIndex == 10){
                dialogueIndex = 0;
            }
            getCurrDialogue();
            if (currentDialogue[dialogueIndex] == null || (dialogueIndex >= currentDialogue.length - 1)) {
                System.out.println("Resetting dialogue..");
                dialogueIndex = 0;
            } else {
                gp.ui.currentDialogue = currentDialogue[dialogueIndex];
                dialogueIndex++;
            }
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

        if (dialogueIndex <= currentDialogue.length - 1) {
            return currentDialogue[dialogueIndex];
        } else {
            return "Yes...?";
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
    public boolean isQuestDone(){
        for (Boolean isCompleted : questProgress){
            if (!isCompleted){
                return false;
            }
        }
        return true;
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
    /**
     * This method is intended for NPCs that have more than 1 picture each, changing it depending on their direction
     * Project time ran out before this could be implemented however
     * @param g2 - the Graphics2D object that draws
     */
    public void draw(Graphics2D g2) {
        BufferedImage image = null;
        switch (direction) {

            case "walkleft" -> {
                if (spriteNum == 1) {
                    image = wokLeft;
                    break;
                }
                if (spriteNum == 2) {
                    image = wokRight;
                    break;
                }
            }
            case "walkright" -> {
                if (spriteNum == 1) {
                    image = wokLeft;
                    break;
                }
                if (spriteNum == 2) {
                    image = wokLeft;
                    break;
                }
            }
        }
        int screenX = worldX - gp.player.worldX + gp.player.screenX;
        int screenY = worldY - gp.player.worldY + gp.player.screenY;
        image = loadNpcImage();     //only for debug purposes, replace this with the switch case above later
        g2.drawImage(image, screenX, screenY, 32, 32, null);

    }
}