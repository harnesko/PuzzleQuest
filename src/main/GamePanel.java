package main;

import entity.NPC;
import entity.Player;
import gameObject.GameObject;
import tile.MapManager;
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
    public final int scale = 2; // detta skapar vi eftersom vi kommer skala upp storleken på alla tiles
    // så de blir tile x scale = 16 x 3 = 48. alltså 48 pixel x 48 pixel

    public final int tileSize = originalTileSize * scale; // 48x48 tile, den riktiga size
    public final int maxScreenCol = 16; // mappen blir 16 tiles horizontalt
    // public final int maxScreenCol = 20; .. vrf?
    public int maxScreenRow = 12; // och 12 tiles vertikalt
    public final int screenHeight = tileSize * maxScreenRow; // 576 pixels vertikalt
    public final int screenWidth = tileSize * maxScreenCol; // 768 pixels horizontalt

    // WORLD SETTINGS dessa kan ändras // TODO: används ej mer ?
    public int maxWorldCol = 37;
    public final int maxWorldRow = 29;

    // EXTRA SETTINGS
    MapManager mapManager = new MapManager(this);
    boolean debugOn; // kan tas bort @author Kinda

    // ===================================
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
    public final int noneState = 4;
    // ===================================

    // FPS
    int FPS = 60;

    TileManager tileManager;
    KeyHandler keyH = new KeyHandler(this); // knapparna WASD
    Thread gameThread; // tiden för spelet
    // ===================================
    public UI ui = new UI(this); //lade till public
    Sound music = new Sound();
    Sound soundEffects = new Sound();
    Config config = new Config(this);
    // ===================================
    public CollisionChecker collisionChecker = new CollisionChecker(this);
    public Player player = new Player(this, keyH);
    public GameObject obj[] = new GameObject[10]; // 10 betyder vi kan visa 10 slots, inte att vi endast kan ha 10
    public NPC[] npcList = new NPC[10];           //Does this need to exist or can npcs exist inside obj[]?

    public GamePanel() {
        this.setPreferredSize(new Dimension(screenWidth, screenHeight)); // här förstorade jag skärmen
        this.setBackground(Color.black);
        this.setDoubleBuffered(true); // att göra detta true ger bättre rendering performance
        this.addKeyListener(keyH);
        this.setFocusable(true);

        tileManager = new TileManager(this, mapManager,
                "/maps/maintown"); // BYT DETTA OM DU VILL STARTAS PÅ ANNAN MAP
    }

    public void setDefaultGameValues() {
        /** VALUES TILL STARTING MAP, STARTING PLAYER LOCATION....**/ // TODO: byt värden här !!!
        // ============ PLAYER DEFAULT VALUES ============ //
        for (int i = 0; i < mapManager.getMapList().size(); i++) {
            if (mapManager.getMapList().get(i).getMapTxtFile().equals(tileManager.currentMap)) {
                player.worldX = mapManager.getMapList().get(i).getPlayerSpawnX();
                player.worldY = mapManager.getMapList().get(i).getPlayerSpawnY();
            }
        }

        player.direction = "idledown"; // TODO: fixa så det passar mappen, fixa i Map klassen kanske?
    }

    public void setupGame() {
        playMusik(0);
        gameState = titleState;

        tempScreen = new BufferedImage(screenWidth, screenHeight, BufferedImage.TYPE_INT_ARGB);
        g2 = (Graphics2D) tempScreen.getGraphics();

        if (ui.fullscreen) {
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

        setDefaultGameValues(); // ?!!!

        while (gameThread != null) {

            update();
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
        debugOn = keyH.fPressed; // kan tas bort men gör inte det än / K

        //Don't update player/npc if the game is paused
        if (gameState == playState) {

            player.update();

            for (NPC npc : npcList) {
                if (npc != null) {
                    npc.update();       //Update the npc movement
                }
            }
        }

    }

    /**
     * This is method used to temporarily draw everything so that resizing becomes smoother and more effective.
     * if the gameStare = titleState then the MainMenu is drawn else the game is drawn.
     * @author Kristoffer
     */
    public void drawToTempScreen() {
        if (gameState == titleState) { //MainMenu
            if (keyH.escPressed) {
                keyH.escPressed = false; // @author kinda, make sure att option menyn e stängd när man sitter i main menyn
            }
            ui.draw(g2);
        } else { // allt annat till spelet
            stopMusik();
            gameState = keyH.escPressed ? optionsState : playState; // @author kinda, checkar om man öppnar elr stänger optionmenyn

            tileManager.draw(g2, debugOn); // rita tiles före playern, detta funkar som lager

            for (int i = 0; i < obj.length; i++) {
                if (obj[i] != null) {
                    obj[i].draw(g2, this);
                }
            }

            if (gameState == playState) {
                player.draw(g2, debugOn);
                ui.draw(g2); //Gustav

                for (NPC npc : npcList) {
                    if (npc != null) {
                        npc.draw(g2);       //NullPointerException atm???      ¯\_(ツ)_/¯
                    }
                }
            }
            if (gameState == optionsState || gameState == noneState) {
                ui.drawSettingsMenu(g2); // här skickas g2, innan kunde den inte göra det pga super.paintComponent var kommenterad bort
            }
        }
    }

    /**
     * This draws everything from the drawToTempScreen().
     * @author Kristoffer
     */
    public void drawToScreen() {
        Graphics g = getGraphics();
        g.drawImage(tempScreen, 0, 0, screenWidth2, screenHeight2, null);
        g.dispose();
    }

    /**
     * Method to play and loop the music by choosing what number from the URL[].
     * @param i what track to play from the array in the Sound class.
     * @author Kristoffer
     */
    public void playMusik(int i) {
        music.setClip(i);
        music.playAudio();
        music.loopAudio();
    }

    /**
     * Stops the music from playing
     * @author Kristoffer
     */
    public void stopMusik() {
        music.stopAudio();
    }

    /**
     * Method used to play sound effects
     * @param i what track to play from the array in the Sound class.
     * @author Kristoffer
     */
    public void playSoundEffect(int i) {
        soundEffects.setClip(i);
        soundEffects.playAudio();
    }
    
    /**
     * Method used to sett fullscreen based on the monitors' resolution.
     * @author Kristoffer
     */
    public void setFullScreen() {
        //Get local screen device
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsDevice gd = ge.getDefaultScreenDevice();
        gd.setFullScreenWindow(GameStarter.window);

        //Get fullscreen width & height
        screenWidth2 = GameStarter.window.getWidth();
        screenHeight2 = GameStarter.window.getHeight();
    }
}