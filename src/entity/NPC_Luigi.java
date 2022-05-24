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
public class NPC_Luigi extends NPC{
    public GamePanel gp;
    BufferedImage luigi_image1 = null;
    public boolean isQuestDone = false;
    public boolean isQuestStarted = false;      //only the first interaction should give quest. This could be redundant, depending on how we do the dialogue window
    public boolean[] questProgress = {false, false, false};


    public NPC_Luigi(GamePanel gp) {
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
        //Mostly for testing purposes rn, do it properly later or something
        dialogues[0] = "Mike! Mike! HaVe YoU sEeN mY CaT!?";
        dialogues[1] = "You - Which one?\n";
        dialogues[2] = "Don’t be ridiculous Mike I only have 6.\n";
        dialogues[3] = "You - I wasn’t being… never mind where is it?";
        dialogues[4] = " I don’t know but he likes to hang around Wocks Wok for some odd reason.";
    }

    @Override
    public void speak() {
        if(dialogues[dialogueIndex] == null || (dialogueIndex >= dialogues.length - 1)) {
            System.out.println("Resetting dialogue..");
            dialogueIndex = 0;
        }else{
            gp.ui.currentDialog = dialogues[dialogueIndex]; //use e to go through dialaogue lines later
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
        if(dialogueIndex <= dialogues.length){
            return dialogues[dialogueIndex];
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
