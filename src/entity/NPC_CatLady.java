package entity;

import main.GamePanel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Objects;


/**
 * Class for NPC Cracy CatLady
 *   Step 1: Speak to Cracy to start quest, get the objective to fetch her cat, return cat to complete step 1
 *   Step 2: Cracy sends player to collect food from  William Dock, step is complete when food is returned (complete docks quest first!)
 *   Step 3: Cracy returns players housekey and player can go home to finish the game
 * @author Måns
 */
public class NPC_CatLady extends NPC{
    public GamePanel gp;
    BufferedImage npc_catladyImage = null;

    public boolean[] questProgress = {false, false};
    private final String[] secondDialogue = new String[10];
    private final String[] thirdDialogue = new String[20];
    private String[] currentDialogue = new String[20];

    public NPC_CatLady(GamePanel gp) {
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
            npc_catladyImage = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/npc/npcCatlady/NPC_Catlady.png")));
        }catch (Exception e) {
            e.printStackTrace();
        }
        return npc_catladyImage;
    }

    /**
     * Messy array that sets up all npc dialogue
     * @author Måns code, Gustav dialogue
     */
    public void createDialogue(){
        //Starting dialogue
        firstDialogue[0] = "Cracy Cat Lady:\nMike! Mike! HaVe YoU sEeN mY CaT!?";
        firstDialogue[1] = "You:\nWhich one?";
        firstDialogue[2] = "Cracy Cat Lady:\nDon’t be ridiculous Mike, I only have like 6.";
        firstDialogue[3] = "You:\nI wasn’t being… never mind where is it?";
        firstDialogue[4] = "Cracy Cat Lady:\nI don’t know but he likes to hang around\nWocks Wok for some odd reason.";

        //Only available once cat is found
        secondDialogue[0] = "Cracy cat-lady:\nThere is one more thing\nyou could do for me.";
        secondDialogue[1] = "Player:\n*Sigh* What?";
        secondDialogue[2] = "Cracy cat-lady:\nAll this talk of Wocks Wok\nmade me super hungry.\nDo you mind getting me some?";
        secondDialogue[3] = "Player:\n*Mutters* Jesus Christ. Fine!";

        //Only available once food is brought
        thirdDialogue[0] = "Cracy cat-lady:\nThank you Mike, here is your house key back!";
        thirdDialogue[1] = "Player:\nWait a minute you stole my house key?";
        thirdDialogue[2] = "Cracy cat-lady:\nWell of course I did.\nI needed you to help me out\nbefore you went home.";
        thirdDialogue[3] = "Player:\nJesus christ you really are insane!";
        thirdDialogue[4] = "Cracy cat-lady:\nThank you!";
        thirdDialogue[5] = "Player:\nSo anything else you need doing?";
        thirdDialogue[6] = "Cracy cat-lady:\nNope that’s it.";
        thirdDialogue[7] = "Player:\nThat’s it?";
        thirdDialogue[8] = "Cracy cat lady:\nThat’s it.";
        thirdDialogue[9] = "Player:\nThat’s it?";
        thirdDialogue[10] = "Cracy cat lady:\nThat’s it.";
    }

    /**
     * Sets instance varaible dialogue[] to match quest progress in getCurrDialogue()
     * Sets UI dialogue variable to local dialogue array, while keeping track of index
     * @author Måns
     */
    @Override
    public void speak() {
        if (gp.player.hasCat) {
            gp.ui.currentDialogue = "My cat! Thank you!";
            progressQuest();
            System.out.println("Quest progressed..");
            gp.player.hasCat = false;
            System.out.println("Cat returned");
        } else if(gp.player.hasWok){
            gp.ui.currentDialogue = "My food! Thank you!";
            progressQuest();
            System.out.println("Quest progressed..");
            gp.player.hasWok = false;
            System.out.println("Wok lost..");
        }else {
            getCurrDialogue();
            if(currentDialogue[dialogueIndex] == null || (dialogueIndex >= currentDialogue.length - 1)) {
                System.out.println("Resetting dialogue..");
                dialogueIndex = 0;
            }else{
                gp.ui.currentDialogue = currentDialogue[dialogueIndex]; //use e to go through dialaogue lines later
                dialogueIndex++;
            }
        }
    }

    /**
     * Check quest progress and return result
     * @return true if all steps are completed, false if not
     * @author Måns
     */
    public boolean isQuestDone(){
        for (Boolean isCompleted : questProgress){
            if (!isCompleted){
                return false;
            }
        }
        return true;
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
            return "Cracy cat-lady:\nOh my lovely Young-boi.\nThank you Mike for getting him!";
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
                    image = npc_catladyImage;
                    break;
                }
                if (spriteNum == 2) {
                    image = npc_catladyImage;
                    break;
                }
            }
            case "walkright" -> {
                if (spriteNum == 1) {
                    image = npc_catladyImage;
                    break;
                }
                if (spriteNum == 2) {
                    image = npc_catladyImage;
                    System.out.println();
                    break;
                }
            }
            case "up" -> {
                image = npc_catladyImage;
                break;
            }
            case "down" -> {
                image = npc_catladyImage;
            }
        }
        int screenX = worldX - gp.player.worldX + gp.player.screenX;
        int screenY = worldY - gp.player.worldY + gp.player.screenY;
        image = loadNpcImage();     //only for debug purposes, replace this with the switch case above later
        g2.drawImage(image, screenX, screenY, 32, 32, null);

    }
}
