package interactive_tile;

import main.GamePanel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Objects;


/**
 * @autor Gustav
 * Class that draws interactable trees into the world
 */
public class ITDryTree extends InteractiveTile{

    GamePanel gp;

    public ITDryTree(GamePanel gp, int col, int row){
        super(gp, col, row);
        this.gp = gp;

        this.worldX = gp.tileSize * col;
        this.worldY = gp.tileSize * row;

        //down1 = setup("/interactive_tiles", gp.tileSize,gp.tileSize); //vet inte hur jag ska få in trädjävlarna såhär  gjorde snow
        loadDryTree();
        destructable = true;
    }

    public BufferedImage loadDryTree(){
        BufferedImage dryTree = null;

        try {
            dryTree = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/interactive_tiles/drytree.png")));
        }
        catch (Exception e){
            e.printStackTrace();
        }

        return dryTree;

    }
    public void draw(Graphics2D g2){
        BufferedImage image = null;

        int screenX = worldX - gp.player.worldX + gp.player.screenX;
        int screenY = worldY - gp.player.worldY + gp.player.screenY;
        image = loadDryTree();
        g2.drawImage(image, screenX,screenY, 64, 64, null);
    }


}
