package main;

import entity.Player;
import gameObject.GameObject;
import tile.TileManager;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
/**
 * Fixat i denna klassen
 */
public class GamePanel extends JPanel implements Runnable {

    // SCREEN SETTINGS ändra helst inte dessa
    final int originalTileSize = 32; // 16x16 tile
    final int scale = 2; // detta skapar vi eftersom vi kommer skala upp storleken på alla tiles
    // så de blir tile x scale = 16 x 3 = 48. alltså 48 pixel x 48 pixel

    public final int tileSize = originalTileSize * scale; // 48x48 tile, den riktiga size
    public final int maxScreenCol = 20; // mappen blir 16 tiles horizontalt
    public final int maxScreenRow = 12; // och 12 tiles vertikalt
    public final int screenWidth = tileSize * maxScreenCol; // 960 pixels horizontalt
    public final int screenHeight = tileSize * maxScreenRow; // 576 pixels vertikalt

    // WORLD SETTINGS dessa kan ändras
    public final int maxWorldCol = 50;
    public final int maxWorldRow = 50;
    public final int worldWidth = tileSize * maxWorldCol;
    public final int worldHeight = tileSize * maxWorldRow;

    //FULL SCREEN
    int screenWidth2 = screenWidth;
    int screenHeight2 = screenHeight;
    BufferedImage tempScreen;
    Graphics2D g2;

    // GAME STATES
    public int gameState;
    public final int titleState = 0;
    public final int playState = 1;
    public final int optionsState = 2;
    public final int dialogState = 3;

    // FPS
    int FPS = 5;

    TileManager tileManager = new TileManager(this);
    KeyHandler keyH = new KeyHandler(this); // knapparna WASD
    Thread gameThread; // tiden för spelet
    public UI ui = new UI(this); //lade till public
    Sound music = new Sound();
    Sound soundEffects = new Sound();
    Config config = new Config(this);
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

    /**
     * Bla Bla Bla
     *  @author Kinda
     *
     *  Added a new way to paint the game using a BuffertImage anda temporary screen for effektivness.
     *  Also added an if statement so that if fullscreen = true fullscreen is drawn instead.
     *  @author Kristoffer
     */
    public void setupGame(){
        assetSetter.setObject();
        playMusik(0);
        gameState = titleState;

        tempScreen = new BufferedImage(screenWidth,screenHeight, BufferedImage.TYPE_INT_ARGB);
        g2 = (Graphics2D) tempScreen.getGraphics();

        if(ui.fullscreen){
            setFullScreen();
        }
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


            //repaint(); // denna kallar på paintComponent metoden
            drawToTempScreen(); //ritar allt till image buffer
            drawToScreen(); // ritar av det som finns i bufferten till skärmen

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

    /**
     * This is method used to temporarily draw everything so that resizing becomes smoother and more effective.
     * if the gameStare = titleState then the MainMenu is drawn else the game is drawn.
     * @author Kristoffer
     */
    public void drawToTempScreen(){
        if(gameState == titleState){ //MainMenu
            ui.draw(g2);
        }
        else { // allt annat till spelet
            stopMusik();

            tileManager.draw(g2); // rita tiles före playern, detta funkar som lager
            for (int i = 0; i < obj.length; i++) {
                if (obj[i] != null) {
                    obj[i].draw(g2, this);
                }
            }
            if (gameState == playState) {
                player.draw(g2);
                ui.draw(g2); //Gustav
            }
            if (gameState == optionsState) {
                ui.drawSettingsMenu(g2); // här skickas g2, innan kunde den inte göra det pga super.paintComponent var kommenterad bort
            }


            //showGrid(g2); //kan tas bort
        }
    }

    /**
     * This draws everything from the drawToTempScreen().
     * @author Kristoffer
     */
    public void drawToScreen(){
        Graphics g = getGraphics();
        g.drawImage(tempScreen, 0 , 0, screenWidth2,screenHeight2, null);
        g.dispose();
    }

    /*public void paintComponent(Graphics g) { // allt ritas här
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        if(gameState == titleState){ //MainMenu
            ui.draw(g2);
        }
        else { // allt annat till spelet
            stopMusik();

            tileManager.draw(g2); // rita tiles före playern, detta funkar som lager
            for (int i = 0; i < obj.length; i++) {
                if (obj[i] != null) {
                    obj[i].draw(g2, this);
                }
            }
            player.draw(g2);
            //showGrid(g2); //kan tas bort
            g2.dispose();

        }
    }*/

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

    /**
     * Method to play and loop the music by choosing what number from the URL[].
     * @param soundChooser what track to play from the array in the Sound class.
     * @author Kristoffer
     */
    public void playMusik(int soundChooser){
        music.setClip(soundChooser);
        music.playAudio();
        music.loopAudio();
    }

    /**
     * Stops the music from playing
     * @author Kristoffer
     */
    public void stopMusik(){
        music.stopAudio();
    }

    /**
     * Method used to play sound effects
     * @param soundChooser what track to play from the array in the Sound class.
     * @author Kristoffer
     */
    public void playSoundEffect(int soundChooser){
        soundEffects.setClip(soundChooser);
        soundEffects.playAudio();
    }

    /**
     * Method used to sett fullscreen based on the monitors' resolution.
     * @author Kristoffer
     */
    public void setFullScreen(){
        //Get local screen device
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsDevice gd = ge.getDefaultScreenDevice();
        gd.setFullScreenWindow(GameStarter.window);

        //Get fullscreen width & height
        screenWidth2 = GameStarter.window.getWidth();
        screenHeight2 = GameStarter.window.getHeight();

    }


    // kan va en ide att inte begränsa storleken på panelen till mappens storlek. men idk !
    // update 1: tiles ligger utanför the grid
    // update 2: grid är ändrat tbx, vet inte om det är lönt att köra större skärm storlek än mappen
    // update 3: grid behlvde tas bort. behöver fixas på världen inte gubben. // TODO: kinda ?
    // update 4: ok, skärmens storlek e samma som mappen nu pga better rendering performance uppdateringen :(
    // grid kanske inte behövs mer? idk yet.
}
