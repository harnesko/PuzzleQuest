package entity;

import main.GamePanel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Objects;

public class NPC_Luigi extends NPC{

    public GamePanel gp;
    public String[] npcLuigiDialogue = new String[10];
    private int screenX; // Are these needed for an NPC or is it only for Player?
    private int screenY;
    BufferedImage luigi_image1 = null;

    public NPC_Luigi(GamePanel gp) {
        super(gp);
        this.gp = gp;           //I dont understand why this line needs to be here but if it's not it goes to shit
        direction = "down";
        speed = 1;
        createDialogue();

        solidArea = new Rectangle(4, 8, 8, 16);
        collisionOn = true;
        loadNpcImage();
    }

    @Override
    public void speak() {
        for (String str : npcLuigiDialogue){
            if(str != null){
                gp.ui.showMessage(str);
            }
        }
    }
    //Make this return void later i guess

    public BufferedImage loadNpcImage(){
        //BufferedImage luigi_image1 = null;
        BufferedImage npcImage2 = null;

        try{
            /*
            luigi_image1 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("npc/npcTwo/npc_luigi_left.png")));
            npcImage2 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("npc/npcTwo/npc_luigi_right.png")));
            */
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
    public void update(){
        collisionOn = true;
        setAction();
    }

    public void setDefaultNpcPosition(){
        this.worldX = gp.tileSize * 35;
        this.worldY = gp.tileSize * 32;
    }
   /* public void draw(Graphics2D g2){
        BufferedImage image = null;

        g2.drawImage(luigi_image1,screenX,screenY,gp.tileSize,gp.tileSize,null);
    }*/
    public void draw(Graphics2D g2) {
        BufferedImage image = null;
        switch (direction) {

            case "walkleft" -> {
                if (spriteNum == 1) {
                    image = luigi_image1;
                    break;
                }
                if (spriteNum == 2) {
                    image = luigi_image1;          //fix marioLeft2 later
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
                System.out.println("npc looks down");
                break;  //fix this mess later
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
        npcLuigiDialogue[0] = "Hello";
        npcLuigiDialogue[1] = "Nintendo couldn't make this game if they tried";
        npcLuigiDialogue[2] = "bla bla bla";
        npcLuigiDialogue[3] = "bla bla bla";
        npcLuigiDialogue[4] = "bla bla bla                                                                                                                                                        1241254135432523";
    }
}
