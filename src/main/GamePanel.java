package main;

import entity.Player;
import gameObject.GameObject;
import tile.TileManager;

import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel implements Runnable {

    // SCREEN SETTINGS ändra helst inte dessa
    final int originalTileSize = 32; // 16x16 tile
    public final int scale = 2; // detta skapar vi eftersom vi kommer skala upp storleken på alla tiles
    // så de blir tile x scale = 16 x 3 = 48. alltså 48 pixel x 48 pixel

    public final int tileSize = originalTileSize * scale; // 48x48 tile, den riktiga size
    public final int maxScreenCol = 16; // mappen blir 16 tiles horizontalt
    public final int maxScreenRow = 12; // och 12 tiles vertikalt
    public final int screenWidth = tileSize * maxScreenCol; // 768 pixels horizontalt
    public final int screenHeight = tileSize * maxScreenRow; // 576 pixels vertikalt

    // WORLD SETTINGS dessa kan ändras
    public final int maxWorldCol = 50;
    public final int maxWorldRow = 50;
    public final int worldWidth = tileSize * maxWorldCol;
    public final int worldHeight = tileSize * maxWorldRow;

    // FPS
    int FPS = 60;

    TileManager tileManager = new TileManager(this);
    KeyHandler keyH = new KeyHandler(); // knapparna WASD
    Thread gameThread; // tiden för spelet
    public CollisionChecker collisionChecker = new CollisionChecker(this);
    public AssetSetter assetSetter = new AssetSetter(this);
    public Player player = new Player(this, keyH);
    public GameObject obj[] = new GameObject[10]; // 10 betyder vi kan visa 10 slots, inte att vi endast kan ha 10

    public GamePanel() {
        this.setPreferredSize(new Dimension(screenWidth, screenHeight)); // här förstorade jag skärmen
        this.setBackground(Color.black);
        this.setDoubleBuffered(true); // att göra detta true ger bättre rendering performance
        this.addKeyListener(keyH);
        this.setFocusable(true);
    }

    public void setupGame(){
        assetSetter.setObject();
    }

    public void startGameThread() {
        gameThread = new Thread(this);
        gameThread.start();
    }

    @Override
    public void run() {

        double drawInterval = 1000000000 / FPS; // 1,000,000,000 nanosekunder
        double nextDrawTime = System.nanoTime() + drawInterval;

        while (gameThread != null) {

            update();

            repaint(); // denna kallar på paintComponent metoden

            try {
                // dessa saker säkerställer att FPS är 60 och inte 6092318492174...
                double remainingTime = nextDrawTime - System.nanoTime();
                remainingTime = remainingTime / 1000000;

                if (remainingTime < 0) {
                    remainingTime = 0;
                }

                Thread.sleep((long) remainingTime);

                nextDrawTime += drawInterval;

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            // System.out.println(FPS);
        }
    }

    public void update() {
        player.update();
    }

    public void paintComponent(Graphics g) { // allt ritas här
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D) g;

        tileManager.draw(g2); // rita tiles före playern, detta funkar som lager
        for (int i = 0; i < obj.length; i++) {
            if (obj[i] != null){
                obj[i].draw(g2,this);
            }
        }
        player.draw(g2);
        //showGrid(g2); //kan tas bort

        g2.dispose();
    }

    public void showGrid(Graphics2D g2) { // debug replacement. vi kan ta bort denna
        int x = 0;
        int y = 0;


        for (int i = 0; i < maxScreenRow; i++) {
            for (int j = 0; j < maxScreenCol; j++) {
                g2.drawRect(x, y, tileSize, tileSize);
                x += tileSize;
            }
            x = 0;
            y += tileSize;
        }
    }

    // kan va en ide att inte begränsa storleken på panelen till mappens storlek. men idk !
    // update 1: tiles ligger utanför the grid
    // update 2: grid är ändrat tbx, vet inte om det är lönt att köra större skärm storlek än mappen
    // update 3: grid behlvde tas bort. behöver fixas på världen inte gubben. // TODO: kinda ?
    // update 4: ok, skärmens storlek e samma som mappen nu pga better rendering performance uppdateringen :(
    // grid kanske inte behövs mer? idk yet.
}
