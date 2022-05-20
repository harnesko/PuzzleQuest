package interactive_tile;

import main.GamePanel;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.util.Objects;

public class ITDryTree extends InteractiveTile{

    GamePanel gp;

    public ITDryTree(GamePanel gp, int col, int row){
        super(gp, col, row);
        this.gp = gp;

        this.worldX = gp.tileSize * col;
        this.worldY = gp.tileSize * row;

        //down1 = setup("/interactive_tiles", gp.tileSize,gp.tileSize); //vet inte hur jag ska få in trädjävlarna
        loadCrustyBoi();
        destructable = true;
    }

    public BufferedImage loadCrustyBoi(){
        BufferedImage crustyBoi = null;

        try {
            crustyBoi = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/interactive_tiles/drytree.png")));
        }
        catch (Exception e){
            e.printStackTrace();
        }

        return crustyBoi;

    }

}
