package entity;

import main.GamePanel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Objects;
import java.util.Random;

/**
 * Class for npc 1, currently a placeholder .png image
 * @author Måns Harnesk
 * @version 1.4
 */

public class NPC_Mario extends NPC{

    private int screenX; // Are these needed for an NPC or is it only for Player?
    private int screenY;
    public BufferedImage marioLeft, marioRight;
    public GamePanel gp;
    public String[] npcMarioDialogue = new String[5];

    public NPC_Mario(GamePanel gp){
        super(gp);
        this.gp = gp;           //I dont understand why this line needs to be here but if it's not it goes to shit
        direction = "down";
        speed = 1;

        solidArea = new Rectangle(4, 8, 8, 16);
        collisionOn = true;
        createDialogue();
        loadNpcImage();
        setDefaultNpcPosition();

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
        return npcImage1;   //todo make void i guess
    }

    /**
     * (Notes)
     * Fix broken npc detection and collision
     * Start with displaying dialogue using the already made UI.showMessage() method
     * Later work on a dialogue box or something
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
    public  void speak(){
        for (String str : npcMarioDialogue){
            if(str != null)               //ClassCastException ibland i ui???? :(
                gp.ui.showMessage(str);
        }
    }


    /**
     * Randomize a direction the NPC will use and pretend its pathfinding
     * Update direction after a few seconds (WIP)
     */
    public void setAction(){
        Random random = new Random();
        int i = random.nextInt(4);

        if(i == 0){
            direction = "up";
        }
        if(i == 1){
            direction = "down";
        }
        if(i == 2){
            direction = "left";     //set image to npc_mario_left.png todo
        }
        if(i == 3){
            direction = "right";     //set image to npc_mario_right.png
        }
    }

    @Override
    public void update(){
        collisionOn = true;
        setAction();
    }

    //v1, this code is a fucking mess
  /*  public void draw(Graphics2D g2){
        //BufferedImage image = null;
        //image = loadNpcImage();             //Här händer något fucky. image = image1 ritar ej bild, image=load ritar på kameran och inte världen
        //image = walkDown1;

        //Denna raden funkar sådär
        g2.drawImage(npcImage1, 1460 ,1355 ,gp.tileSize,gp.tileSize,null);


    }*/
    //V2, this code is also a fucking mess
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
        //System.out.println("Image is : " + image);      //Image is sometimes null??
        g2.drawImage(image, screenX, screenY, 32, 32, null);

    }



    public void setDefaultNpcPosition(){
        this.worldX = 1464;
        this.worldY = 356;
    }

}
