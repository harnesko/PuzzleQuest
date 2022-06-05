package entity;

import main.GamePanel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Objects;


/**
 * Class for npc 3, Francesca.
 * Due to project time constraints, her full questline isn't implemented.
 * @author Måns
 */
public class NPC_Clerk extends NPC{
    public GamePanel gp;
    BufferedImage npc_clerkImage = null;
    public boolean[] questProgress = {false, false};
    private String[] currentDialogue = new String[20];

    public NPC_Clerk(GamePanel gp) {
        super(gp);
        this.gp = gp;           //I dont understand why this line needs to be here but if it's not it goes to shit
        direction = "walkdown";
        speed = 1;

        solidArea = new Rectangle(4, 8, 8, 16);
        collisionOn = true;
        createDialogue();
        loadNpcImage();
    }
    public BufferedImage loadNpcImage(){
        try{
            npc_clerkImage = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/npc/npcFrancesca/NPC_Francesca.png")));
        }catch (Exception e) {
            e.printStackTrace();
        }
        return npc_clerkImage;
    }

    /**
     * Messy array that sets up all npc dialogue
     * @author Måns code, Gustav dialogue
     */
    public void createDialogue(){
        //Starting dialogue
        firstDialogue[0] = "Francesca Abraham:\n Heyo watcha need buddy?";
        firstDialogue[1] = "You:\nA big bag of MSG for Wock Wok";
        firstDialogue[2] = "Francesca Abrahamn:\n Oooh so Diliam’s got you running errands for him?\n Well tell him I said hi!\n";
        firstDialogue[3] = "You:\nYeah yeah… just hand over the MSG.";
    }

    /**
     * Sets instance varaible dialogue[] to match quest progress in getCurrDialogue()
     * Sets UI dialogue variable to local dialogue array, while keeping track of index
     * @author Måns
     */
    @Override
    public void speak() {
        getCurrDialogue();
        if (currentDialogue[dialogueIndex] == null || (dialogueIndex >= currentDialogue.length - 1)) {
            System.out.println("Resetting dialogue..");
            dialogueIndex = 0;
        } else {
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
     * Checks current quest progress and returns the corresponding dialogue, currently Clerk has no quests available
     * @return Current string of dialogue from the array
     * @author Måns
     */
    public String getCurrDialogue(){
        return currentDialogue[dialogueIndex];
    }

    @Override
    public boolean isQuestDone() {
        return false;
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
                    image = npc_clerkImage;
                    break;
                }
                if (spriteNum == 2) {
                    image = npc_clerkImage;
                    break;
                }
            }
            case "walkright" -> {
                if (spriteNum == 1) {
                    image = npc_clerkImage;
                    break;
                }
            }
            case "up" -> {
                image = npc_clerkImage;
                break;
            }
            case "down" -> {
                image = npc_clerkImage;
            }
        }
        int screenX = worldX - gp.player.worldX + gp.player.screenX;
        int screenY = worldY - gp.player.worldY + gp.player.screenY;
        image = loadNpcImage();     //only for debug purposes, replace this with the switch case above later
        g2.drawImage(image, screenX, screenY, 32, 32, null);
    }
}
