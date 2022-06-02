package entity;

import main.GamePanel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Objects;


/**
 * Class for npc 2, currently a placeholder .png image. Please don't tell Nintendo
 * @author Måns
 * @version 1.2
 */
public class NPC_Clerk extends NPC{
    public GamePanel gp;
    BufferedImage luigi_image1 = null;
    /*
    Step 1: Speak to Cracy to start quest, get the objective to fetch her cat, return cat to complete step 1
    Step 2: Cracy sends player to collect food from  William Dock, step is complete when food is returned (complete docks quest first!)
    Step 3: Profit
     */
    public boolean[] questProgress = {false, false};
    public boolean isQuestDone = false;
    public boolean isQuestStarted = false;      //only the first interaction should give quest. This could be redundant, depending on how we do the dialogue window
    private final String[] secondDialogue = new String[10];
    private final String[] thirdDialogue = new String[20];
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
        //BufferedImage luigi_image1 = null;
        BufferedImage npcImage2 = null;

        try{
            luigi_image1 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/npc/npcFrancesca/NPC_Francesca.png")));
            if(luigi_image1 == null){
                System.out.println("Load image failed..");
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
        return luigi_image1;
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

    @Override
    public boolean isQuestDone() {
        return false;       //Todo måns
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
                    image = luigi_image1;
                    break;
                }
                if (spriteNum == 2) {
                    image = luigi_image1;          //fix luigiLeft2 later
                    break;
                }
            }
            case "walkright" -> {
                if (spriteNum == 1) {
                    image = luigi_image1;
                    break;
                }
                if (spriteNum == 2) {
                    image = luigi_image1;
                    System.out.println();
                    break;
                }
            }
            case "up" -> {
                image = luigi_image1;
                break;
            }
            case "down" -> {
                image = luigi_image1;
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
