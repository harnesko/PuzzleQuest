package entity;

import main.GamePanel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Random;

public class NPC_Mario extends Entity{


    public NPC_Mario(GamePanel gp){
        super(gp);

        direction = "down";
        speed = 1;

        //solidArea = new Rectangle(4, 8, 8, 16);
        solidArea = new Rectangle( 32, 32);
        collisionOn = true;

        loadNpcImage();

    }
    public BufferedImage loadNpcImage(){
        try{
            this.npcImage1 = ImageIO.read(getClass().getResourceAsStream("/NPC/npc_mario.png"));
        }catch (Exception e){
            e.printStackTrace();
        }
        return npcImage1;
    }
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
            direction = "left";
        }
        if(i == 3){
            direction = "right";
        }
        //can be used to randomize npc path (up/down/left/right)
    }

    @Override
    public void update(){
        collisionOn = true;
    }
}
