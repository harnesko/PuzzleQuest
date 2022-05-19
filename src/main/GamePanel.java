package main;

import entity.NPC;
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
    public final int scale = 2; // detta skapar vi eftersom vi kommer skala upp storleken på alla tiles
    // så de blir tile x scale = 16 x 3 = 48. alltså 48 pixel x 48 pixel

    public final int tileSize = originalTileSize * scale; // 48x48 tile, den riktiga size
    public final int maxScreenCol = 16; // mappen blir 16 tiles horizontalt
    // public final int maxScreenCol = 20; .. vrf?
    public int maxScreenRow = 12; // och 12 tiles vertikalt
    public final int screenWidth = tileSize * maxScreenCol; // 768 pixels horizontalt
    public final int screenHeight = tileSize * maxScreenRow; // 576 pixels vertikalt

    // WORLD SETTINGS dessa kan ändras // TODO:
    public int maxWorldRow = 40;        //65 för main_town, 40 för sawmill
    public final int maxWorldCol = 42;  //65 för main_town, 42 för sawmill

    // EXTRA SETTINGS
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
    public final int dialogueState = 3;
    public final int noneState = 4;
    // ===================================

    // FPS
    int FPS = 60;

    TileManager tileManager = new TileManager(this);
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
    }

    public void setupGame() {
        AssetSetter setter = new AssetSetter(this);
        setter.setNPC();
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
        if(gameState == dialogueState) {
            ui.drawDialogueWindow();
            //keyH.ePressed = false;
        }
        /*if((gameState == dialogueState) && (keyH.enterPressed)){
            System.out.println("SUCCESS?");
                ui.drawDialogueWindow();        //I guess remove this mess later
            }*/
    }

    public void drawToTempScreen() {
        if (gameState == titleState) { //MainMenu
            ui.draw(g2);
        } else if (gameState == playState) { // allt annat till spelet
            stopMusik();

            tileManager.draw(g2, debugOn); // rita tiles före playern, detta funkar som lager

            for (int i = 0; i < obj.length; i++) {
                if (obj[i] != null) {
                    obj[i].draw(g2, this);
                }
            }

                player.draw(g2, debugOn);
                ui.draw(g2); //Gustav

                for (NPC npc : npcList) {
                    if (npc != null) {
                        npc.draw(g2);       //NullPointerException atm???      ¯\_(ツ)_/¯
                    }
                }

             // här skickas g2, innan kunde den inte göra det pga super.paintComponent var kommenterad bort
            }
            if(gameState == dialogueState){
                ui.drawDialogueWindow();
            }
        if (gameState == optionsState || gameState == noneState) {
            ui.drawSettingsMenu(g2);
        }
    }

    public void drawToScreen() {
        Graphics g = getGraphics();
        g.drawImage(tempScreen, 0, 0, screenWidth2, screenHeight2, null);
        g.dispose();
    }

    public void playMusik(int i) {
        music.setClip(i);
        music.playAudio();
        music.loopAudio();
    }

    public void stopMusik() {
        music.stopAudio();
    }

    public void playSoundEffect(int i) {
        soundEffects.setClip(i);
        soundEffects.playAudio();
    }

    public void setFullScreen() {
        //Get local screen device
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsDevice gd = ge.getDefaultScreenDevice();
        gd.setFullScreenWindow(GameStarter.window);

        //Get fullscreen width & height
        screenWidth2 = GameStarter.window.getWidth();
        screenHeight2 = GameStarter.window.getHeight();
    }

    public void progressDialogue(int npcIndex) {
        npcList[npcIndex].progressDialogue();
    }
    public void progressDialogue(){

    }
}