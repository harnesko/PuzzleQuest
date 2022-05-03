package entity;

import main.GamePanel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Objects;
import java.util.Random;

public class NPC_Mario extends Entity{

    private int screenX; // Are these needed for an NPC or is it only for Player?
    private int screenY;

    public NPC_Mario(GamePanel gp){
        super(gp);

        setDefaultNpcPosition();
        direction = "down";
        speed = 1;

        solidArea = new Rectangle(4, 8, 8, 16);
        collisionOn = true;

        loadNpcImage();

    }
    public BufferedImage loadNpcImage(){
        try{
            this.npcImage1 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/NPC/npc_mario.png")));
        }catch (Exception e){
            e.printStackTrace();
        }
        return npcImage1;
    }

    /**
     * Randomize a direction the NPC will use and pretend its pathfinding
     *
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
            direction = "left";
        }
        if(i == 3){
            direction = "right";
        }
    }

    @Override
    public void update(){
        collisionOn = true;
    }
    public void draw(Graphics2D g2){
        BufferedImage image = null;
        image = walkDown1;
        g2.drawImage(image, screenX,screenY,gp.tileSize,gp.tileSize,null);

    }
    public void setDefaultNpcPosition(){
        this.worldX = gp.tileSize * 32;
        this.worldY = gp.tileSize * 30;
    }

}
