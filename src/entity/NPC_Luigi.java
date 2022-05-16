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
    //public String[] npcLuigiDialogue = new String[10];
    private int screenX; // Are these needed for an NPC or is it only for Player?
    private int screenY;
    BufferedImage luigi_image1 = null;
    public boolean isQuestDone = false;
    public boolean isQuestStarted = false;      //only the first interaction should give quest. This could be redundant, depending on how we do the dialogue window
    public boolean[] questProgress = {false, false, false};


    public NPC_Luigi(GamePanel gp) {
        super(gp);
        this.gp = gp;           //I dont understand why this line needs to be here but if it's not it goes to shit
        direction = "walkdown";
        speed = 1;
        createDialogue();

        solidArea = new Rectangle(4, 8, 8, 16);
        collisionOn = true;
        loadNpcImage();
    }

    @Override
    public void speak() {
        gp.ui.currentDialog = dialogues[dialogueIndex]; //use e to go through dialaogue lines later
        dialogueIndex++;

    }
    public void progressQuest(){
        for (int i = 0; i < questProgress.length; i++){
            if(!questProgress[i]) {
                questProgress[i] = true;
                break;
            }
        }
    }



    //Make this return void later i guess
    public BufferedImage loadNpcImage(){
        //BufferedImage luigi_image1 = null;
        BufferedImage npcImage2 = null;

        try{
            luigi_image1 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/npc/npcTwo/npc_luigi_left.png")));
            if(luigi_image1 == null){
                System.out.println("here");
            }
        }catch (Exception e){
            e.printStackTrace();
            System.out.println("also here");
        }
        return luigi_image1;   //todo make void i guess
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

    //Set NPC spawn point (remember npc can move!)
    public void setDefaultNpcPosition(){
        this.worldX = gp.tileSize * 35;
        this.worldY = gp.tileSize * 32;
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
        //System.out.println("Image is : " + image);      //Image is sometimes null??
        g2.drawImage(image, screenX, screenY, 32, 32, null);

    }

    public void createDialogue(){
        //Mostly for testing purposes rn, do it properly later or something
        dialogues[0] = "Hello";
        dialogues[1] = "Nintendo couldn't make this game if they tried";
        dialogues[2] = "bla bla bla";
        dialogues[3] = "bla bla bla";
        dialogues[4] = "You need go and speak with Mario!";
    }
}
