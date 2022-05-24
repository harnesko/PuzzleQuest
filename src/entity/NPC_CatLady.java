package entity;

import main.GamePanel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Objects;


/**
 * Class for npc 2, currently a placeholder .png image. Please don't tell Nintendo
 * @author Måns Harnesk
 * @version 1.2
 */
public class NPC_CatLady extends NPC{
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
        //BufferedImage luigi_image1 = null;
        BufferedImage npcImage2 = null;

        try{
            luigi_image1 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/npc/npcTwo/npc_luigi_left.png")));
            if(luigi_image1 == null){
                System.out.println("Load image failed..");
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
        return luigi_image1;
    }
    public void createDialogue(){
        //Starting dialogue
        firstDialogue[0] = "Cracy Cat Lady: Mike! Mike! HaVe YoU sEeN mY CaT!?";
        firstDialogue[1] = "You: Which one?";
        firstDialogue[2] = "Cracy Cat Lady: Don’t be ridiculous Mike I only have 6.";
        firstDialogue[3] = "You: I wasn’t being… never mind where is it?";
        firstDialogue[4] = "Cracy Cat Lady: I don’t know but he likes to hang around Wocks Wok for some odd reason.";

        //Only available once cat is found
        secondDialogue[0] = "Cracy cat-lady: There is one more thing you could do for me.";
        secondDialogue[1] = "Player *Sigh* What?";
        secondDialogue[2] = "Cracy cat-lady: All this talk of Wocks Wok made me super hungry. Do you mind getting me some?";
        secondDialogue[3] = "Player: *Mutters* Jesus Christ. Fine!";

        //Only available once food is brought
        thirdDialogue[0] = "Cracy cat-lady: Thank you Mike, here is your house key back!";
        thirdDialogue[1] = "Player: Wait a minute you stole my house key?";
        thirdDialogue[2] = "Cracy cat-lady: Well of course I did. I needed you to help me out before you went home.";
        thirdDialogue[3] = "Player: Jesus christ you really are insane!";
        thirdDialogue[4] = "Cracy cat-lady: Thank you!";
        thirdDialogue[5] = "Player: So anything else you need doing?";
        thirdDialogue[6] = "Cracy cat-lady: Nope that’s it.";
        thirdDialogue[7] = "Player: That’s it?";
        thirdDialogue[8] = "Cracy cat lady: That’s it.";
        thirdDialogue[9] = "Player: That’s it?";
        thirdDialogue[10] = "Cracy cat lady: That’s it.";
    }

    @Override
    public void speak() {
        if(firstDialogue[dialogueIndex] == null || (dialogueIndex >= firstDialogue.length - 1)) {
            System.out.println("Resetting dialogue..");
            dialogueIndex = 0;
        }else{
            gp.ui.currentDialog = firstDialogue[dialogueIndex]; //use e to go through dialaogue lines later
            dialogueIndex++;
        }
    }

    public void progressQuest(){
        for (int i = 0; i < questProgress.length; i++){
            if(!questProgress[i]) {
                questProgress[i] = true;
                break;
            }
        }
    }
    public String getCurrDialogue(){
        if(dialogueIndex <= firstDialogue.length){
            return firstDialogue[dialogueIndex];
        }else{
            return "No more dialogue..";
        }
    }

    @Override
    public void update() {
        //super.update();     //fix this mess later, super method gives nullpointer on gamepanel instance for some reason, i cba to check rn
        setAction();
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
