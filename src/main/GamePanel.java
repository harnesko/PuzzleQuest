package main;

import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel {

    // Screen settings
    final int originalTileSize = 16; // 16x16 tile
    final int scale = 3; // detta skapar vi eftersom vi kommer skala upp storleken på alla tiles
    // så de blir tile x scale = 16 x 3 = 48. alltså 48 pixel x 48 pixel

    final int tileSize = originalTileSize * scale; // 48x48 tile, den riktiga size
    final int maxScreenCol = 16; // mappen blir 16 tiles horizontalt
    final int maxScreenRow = 12; // och 12 tiles vertikalt
    final int screenWidth = tileSize * maxScreenCol; // 768 pixels horizontalt
    final int screenHeight = tileSize * maxScreenRow; // 576 pixels vertikalt

    public GamePanel(){
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.black);
        this.setDoubleBuffered(true); // att göra detta true ger bättre rendering performance
    }

    /** kan va en ide att inte begränsa storleken på panelen till
     * mappens storlek. men idk !
     * */
}
