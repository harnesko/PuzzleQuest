package main;

import gameObject.*;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * UI class is for the interface.
 *  @author Kristoffer, Måns, Gustav
 */

public class UI {

    GamePanel gp;
    Graphics2D g2;
    Font arial_40; //ska ändras sen Gustav
    public boolean messagesOn;
    public String message = "";
    int messageCounter = 0;
    public boolean gameFinished = false;
    public String currentDialogue = "";

    //Images
    //Images
    private final Image endScreenImage = new ImageIcon("resource/Images/TheEndScreen.png").getImage();
    private final Image woodPlankImage = new ImageIcon("resource/Images/woodplank.png").getImage();
    private final Image woodBackground = new ImageIcon("resource/Images/woodframe.png").getImage();
    private final Image woodFrame = new ImageIcon("resource/Images/woodframe.png").getImage();
    private final Image woodFrame2 = new ImageIcon("resource/Images/woodFrame2.png").getImage();
    BufferedImage keyImage;
    BufferedImage bookImage;
    BufferedImage catImage;
    BufferedImage wokImage;
    BufferedImage stuffImage;

    //Different states
    public int commandNumber = 0;
    public int titleScreenState = 0;
    public boolean save1Active, save2Active, save3Active, save4Active;
    public boolean fullscreen;
    public int settingsState = 0;

    public UI(GamePanel gp) {
        this.gp = gp;

        arial_40 = new Font("Arial", Font.PLAIN, 40);
        Key key = new Key();
        keyImage = key.image;

        Cat cat = new Cat();
        catImage = cat.image;

        Book book = new Book();
        bookImage = book.image;

        Wok wok = new Wok();
        wokImage = wok.image;

        Ingredient stuff = new Ingredient();
        stuffImage = stuff.image;

    }


    public void showMessage(String text) {
        message = text;
        messagesOn = true;
    }

    /**
     * This method is used to draw the different Ui's.
     * With the help of GamePanel's game states we can draw what is needed for that specifik state.
     *
     * @param g2 We send in Graphics2D to be able to draw the different objekts.
     * @author Kristoffer & Gustav
     */
    public void draw(Graphics2D g2) {
        if (gameFinished) {
            gameWon();
        } else {
            this.g2 = g2;
            drawInventory();
            //message
            if (messagesOn) { // TODO: lägga denna block av kod i nån metod utanför draw för tydlighetsskull
                g2.setFont(g2.getFont().deriveFont(30F));
                g2.drawString(message, gp.tileSize / 2, gp.tileSize * 5);

                messageCounter++;

                if (messageCounter > 120) { //120 = mängden frames medelandet visar så 2sek
                    messageCounter = 0;
                    messagesOn = false;
                }
            }

            if (gp.gameState == gp.titleState) {
                drawMainMenu();
            }

            if (gp.gameState == gp.playState) {
            }


            if (gp.gameState == gp.optionsState || gp.gameState == gp.noneState) {
                drawSettingsMenu(g2);
                g2.fillRect(0, 0, 200, 200);
            }

            if (gp.gameState == gp.dialogueState) {
                drawDialogueWindow();
            }
            if (gp.gameState == gp.endScreenState){
                drawEndScreen(g2);
            }
        }
    }

    public void drawEndScreen(Graphics2D g2){
        int frameX = gp.tileSize-48;
        int frameY = gp.tileSize-48;
        int frameWidth = gp.screenWidth;
        int frameHeight = gp.screenHeight;

        if (gp.gameState == gp.endScreenState){
            g2.drawImage(endScreenImage, frameX,frameY,frameWidth,frameHeight,null);
        }
    }

    /**
     * This method is used to draw the Settings menu.
     * By using commandNumber varaible combiend with the Key handler class we can choose what option to change.
     * @param g2 We send in Graphics2D to be able to draw the different objekts.
     * @author Kristoffer
     */
    public void drawSettingsMenu(Graphics2D g2) {

        //Sub window
        int frameX = gp.tileSize*4;
        int frameY = gp.tileSize;
        int frameWidth = gp.tileSize*8;
        int frameHeight = gp.tileSize*11;

        if(settingsState == 0){
            g2.drawImage(woodBackground, frameX, frameY, frameWidth, frameHeight, null);
            optionsMenu();
        }
        if (settingsState == 1){
            g2.drawImage(woodFrame2, frameX, frameY, frameWidth, frameHeight, null);
            fullScreenNotification(frameX, frameY);
        }
    }


    /**
     * This method draws a small sub frame to be able to be used for example the dialog frame.
     * @param g2 We send in Graphics2D to be able to draw the different objekts.
     * @param x The X coordinate position for the window
     * @param y The Y coordinate position for the window
     * @param width The width size of the window
     * @param height height size of the window
     * @author Kristoffer
     */
    public void drawSubWindow(Graphics2D g2, int x, int y, int width, int height) {
        //Dialogue window
        Color c = new Color(0, 0, 0, 200);        //4th param = opacity
        g2.setColor(c);
        g2.fillRoundRect(x, y, width, height, 35, 35);

        c = new Color(255, 255, 255);
        c = new Color(81, 88, 129);
        g2.setColor(c);
        g2.setStroke(new BasicStroke(5));
        g2.drawRoundRect(x + 5, y + 5, width - 10, height - 10, 25, 25);
    }

    /**
     * This is an organised method to dictate what is being drawn in the MainMenu.
     * By using the game title states we control if the MainMenu is drawn or the settings Menu or the Saves menu.
     * @author Kristoffer
     */
    public void drawMainMenu() {
        if (titleScreenState ==0) {
            startMenu();
        }

        else if(titleScreenState == 1){
            savesMenu();
        }

        else if(titleScreenState == 2){
            settingsMenu();
        }
    }
    /**
     * This method uses the method drawSubWindow() to use its frame into a dialogue
     * @author Måns
     */
    public void drawDialogueWindow(){
        //Position
        int x = 0;
        int y = gp.tileSize * 9;
        //Size
        int width = gp.screenWidth2;
        int height = 190;
        drawSubWindow(g2, x, y, width, height);

        x = 30;        //text x position
        y += 2;       //text y position
        if(currentDialogue != null){
            for(String currentDialogue : currentDialogue.split("\n")){    //Regex magic -> new line
                g2.drawString(currentDialogue, x, y += g2.getFontMetrics().getHeight());
            }
        }
    }
    /**
     * This method updates the npc dialogue window when the player presses E or Enter
     * If dialogue has been exausted, it will set the gameState to playState.
     * @param str - The current string of dialogue that's to be displayed.
     * @author Måns
     */
    public void displayNextDialogue(String str){
        currentDialogue = str;
        if(currentDialogue != null){
            try {
                g2.drawString(currentDialogue, gp.tileSize * 2 + 20, 600);
            } catch (NullPointerException e) {
               //I really don't know why this error occurs or how to fix it so..¯\_(ツ)_/¯   /m
            }
        }else{
            gp.gameState = gp.playState;
        }
    }

    /**
     * This method draws the MainMenu screen with the possible sub Menus the player could choose.
     * Its being draw by the g2 variable to draw the different objekts.
     * @author Kristoffer
     */
    public void startMenu(){
        //background
        g2.setColor(new Color(70, 120, 80));
        g2.fillRect(0, 0, gp.screenWidth, gp.screenHeight);
        Image bk = new ImageIcon("resource/Images/battleback1.png").getImage();
        g2.drawImage(bk, 0, 0, gp.screenWidth, gp.screenHeight, null);

        //title Name
        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 96F));
        String title = "HomeQuest";
        int x = getXForCenteredText(title);
        int y = gp.tileSize * 3;

        //Shadow of title
        g2.setColor(Color.black);
        g2.drawString(title, x + 5, y + 5);
        //Main color
        g2.setColor(Color.white);
        g2.drawString(title, x, y);

        //menu "buttons"
        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 48F));

        String text = "New Game";
        x = getXForCenteredText(title);
        y = gp.tileSize * 5 + (gp.tileSize/2);
        g2.drawImage(woodPlankImage, x - 10, y-45, 270, 70, null);
        g2.drawString(text, x, y + 2);
        if (commandNumber == 0) {
            g2.drawString(">", x - 45, y);
        }

        text = "Load Game";
        y += gp.tileSize + 25;
        g2.drawImage(woodPlankImage, x - 10, y-45, 270, 70, null);
        g2.drawString(text, x - 5, y + 2);
        if (commandNumber == 1) {
            g2.drawString(">", x - 45, y);
        }

        text = "Settings";
        y += gp.tileSize + 25;
        g2.drawImage(woodPlankImage, x - 10, y-45, 270, 70, null);
        g2.drawString(text, x + 34, y + 2);
        if (commandNumber == 2) {
            g2.drawString(">", x - 45, y);
        }

        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 48F));
        text = "Quit";
        y += gp.tileSize + 25;
        g2.drawImage(woodPlankImage, x - 10, y-45, 270, 70, null);
        g2.drawString(text, x + 70, y + 2);
        if (commandNumber == 3) {
            g2.drawString(">", x - 45, y);
        }
    }

    /**
     * This Menu draws the savesMenu and its options to choose what save to enter.
     * It also contains a return option.
     * It is drawn by using the g2 variable
     * @author Kristoffer
     */
    public void savesMenu(){
        //background
        g2.setColor(new Color(70, 120, 80));
        g2.fillRect(0, 0, gp.screenWidth, gp.screenHeight);
        Image bk = new ImageIcon("resource/Images/battleback1.png").getImage();
        g2.drawImage(bk, 0, 0, gp.screenWidth, gp.screenHeight, null);

        //title Name
        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 96F));
        String title = "HomeQuest";
        int x = getXForCenteredText(title);
        int y = gp.tileSize * 3;

        //Shadow of title
        g2.setColor(Color.black);
        g2.drawString(title, x + 5, y + 5);
        //Main color
        g2.setColor(Color.white);
        g2.drawString(title, x, y);


        //Start of "Buttons"
        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 48F));

        String text = "Save 1";
        x = getXForCenteredText(title);
        y = gp.tileSize * 5 + (gp.tileSize/2);
        g2.drawImage(woodPlankImage, x - 10, y-45, 270, 70, null);
        if(!save1Active){
            g2.setColor(Color.gray);
        }else{
            g2.setColor(Color.white);
        }
        g2.drawString(text, x + 45, y + 5);
        if (commandNumber == 0) {
            g2.drawString(">", x - 45, y + 2);
        }

        text = "Save 2";
        y += gp.tileSize + 25;
        g2.drawImage(woodPlankImage, x - 10, y-45, 270, 70, null);
        if(!save2Active){
            g2.setColor(Color.gray);
        }else{
            g2.setColor(Color.white);
        }
        g2.drawString(text, x + 45, y + 5);
        if (commandNumber == 1) {
            g2.drawString(">", x - 45, y);
        }

        text = "Save 3";
        y += gp.tileSize + 25;
        g2.drawImage(woodPlankImage, x - 10, y-45, 270, 70, null);
        if(!save3Active){
            g2.setColor(Color.gray);
        }else{
            g2.setColor(Color.white);
        }
        g2.drawString(text, x + 45, y + 5);
        if (commandNumber == 2) {
            g2.drawString(">", x - 45, y);
        }

        text = "Save 4";
        y += gp.tileSize + 25;
        g2.drawImage(woodPlankImage, x - 10, y-45, 270, 70, null);
        if(!save4Active){
            g2.setColor(Color.gray);
        }else{
            g2.setColor(Color.white);
        }
        g2.drawString(text, x + 45, y + 5);
        if (commandNumber == 3) {
            g2.drawString(">", x - 45, y);
        }

        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 48F));
        g2.setColor(Color.white);
        text = "Return";
        y += gp.tileSize + 25;
        g2.drawImage(woodPlankImage, x - 10, y-45, 270, 70, null);
        g2.drawString(text, x + 45, y + 5);
        if (commandNumber == 4) {
            g2.drawString(">", x - 45, y);
        }
    }

    /**
     * This Menu draws the SettingsMenu and its options to change.
     * Then these variables are written in the config file to be saved once the player starts the game again.
     * It also contains a return option.
     * It is drawn by using the g2 variable
     * @author Kristoffer
     */
    public void settingsMenu(){
        //background
        g2.setColor(new Color(70, 120, 80));
        g2.fillRect(0, 0, gp.screenWidth, gp.screenHeight);
        Image bk = new ImageIcon("resource/Images/battleback1.png").getImage();
        g2.drawImage(bk, 0, 0, gp.screenWidth, gp.screenHeight, null);

        //title Name
        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 96F));
        String title = "HomeQuest";
        int x = getXForCenteredText(title);
        int y = gp.tileSize * 3;

        //Shadow of title
        g2.setColor(Color.black);
        g2.drawString(title, x + 5, y + 5);

        //Main color
        g2.setColor(Color.white);
        g2.drawString(title, x, y);

        //Start of "Buttons"
        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 35F));

        String text = "Music: < " + gp.music.volumeScale + " >";
        x = gp.tileSize * 5 + 16;
        y = gp.tileSize * 5 + (gp.tileSize/2);
        g2.drawImage(woodPlankImage, x - 17, y-45, 270, 70, null);
        g2.drawString(text, x + 20 , y);
        if (commandNumber == 0) {
            g2.setFont(g2.getFont().deriveFont(Font.BOLD, 48F));
            g2.drawString(">", x - 55 , y);
            g2.setFont(g2.getFont().deriveFont(Font.BOLD, 35F));
        }

        text = "Sound: < " +gp.soundEffects.volumeScale + " >";
        y += gp.tileSize + 25;
        g2.drawImage(woodPlankImage, x - 17, y-45, 270, 70, null);
        g2.drawString(text, x + 20 , y);
        if (commandNumber == 1) {
            g2.setFont(g2.getFont().deriveFont(Font.BOLD, 48F));
            g2.drawString(">", x - 55 , y);
            g2.setFont(g2.getFont().deriveFont(Font.BOLD, 35F));
        }

        if(!fullscreen){
            text = "FullScreen [ ]";
        }else{
            text = "FullScreen [X]";
        }

        y += gp.tileSize + 25;
        g2.drawImage(woodPlankImage, x - 17, y-45, 270, 70, null);
        g2.drawString(text, x + 15 , y);
        if (commandNumber == 2) {
            g2.setFont(g2.getFont().deriveFont(Font.BOLD, 48F));
            g2.drawString(">", x - 55 , y);
        }

        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 48F));
        text = "Return";
        y += gp.tileSize + 25;
        g2.drawImage(woodPlankImage, x - 17, y-45, 270, 70, null);
        g2.drawString(text, x + 40, y+2);
        if (commandNumber == 3) {
            g2.drawString(">", x - 55 , y);
        }
        gp.config.saveConfig();

        if (gp.keyH.enterPressed){
            int frameX = gp.tileSize * 4;
            int frameY = gp.tileSize * 3 + (gp.tileSize/2) ;
            int frameWidth = gp.tileSize * 8;
            int frameHeight = gp.tileSize * 8;
            g2.drawImage(woodFrame2, frameX, frameY, frameWidth, frameHeight, null);
            fullScreenNotification(frameX, frameY);
        }
    }
    /**
     * This Menu draws the in-game SettingsMenu and its options to change.
     * Then these variables are written in the config file to be saved once the player starts the game again.
     * It also contains a return option.
     * It is drawn by using the g2 variable
     * @author Kristoffer
     */
    public void optionsMenu(){
        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 45F));
        String title = "Settings";
        int x = gp.tileSize*7 - 40;
        int y = gp.tileSize * 3 - 40 ;
        int halfTile = gp.tileSize / 2;
        g2.drawString(title,x,y);

        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 35F));

        String text = "Music: < " + gp.music.volumeScale + " >";

        x = gp.tileSize*5 + halfTile;
        y = gp.tileSize * 4 + (gp.tileSize/2);
        g2.drawImage(woodPlankImage, x - 10, y-40, 250, 60, null);
        g2.drawString(text, x + 15 , y);
        if (commandNumber == 0) {
            g2.setFont(g2.getFont().deriveFont(Font.BOLD, 48F));
            g2.drawString(">", x - 40 , y);
            g2.setFont(g2.getFont().deriveFont(Font.BOLD, 35F));
        }

        text = "Sound: < " +gp.soundEffects.volumeScale + " >";
        y += 85;
        g2.drawImage(woodPlankImage, x - 10, y-40, 250, 60, null);
        g2.drawString(text, x + 15 , y);
        if (commandNumber == 1) {
            g2.setFont(g2.getFont().deriveFont(Font.BOLD, 48F));
            g2.drawString(">", x - 40 , y);
            g2.setFont(g2.getFont().deriveFont(Font.BOLD, 35F));
        }


        if(!fullscreen){
            text = "FullScreen [ ]";
            if(gp.keyH.enterPressed) {
                settingsState = 1;
            }
        }else{
            text = "FullScreen [X]";
            if(gp.keyH.enterPressed) {
                settingsState = 1;
            }
        }

        y += 85;
        g2.drawImage(woodPlankImage, x - 10, y-40, 250, 60, null);;
        g2.drawString(text, x + 15 , y);
        if (commandNumber == 2) {
            g2.setFont(g2.getFont().deriveFont(Font.BOLD, 48F));
            g2.drawString(">", x - 40 , y);
        }

        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 30F));
        text = "Return to Menu";
        y += 85;
        g2.drawImage(woodPlankImage, x - 10, y-40, 250, 60, null);
        g2.drawString(text, x + 15, y + 2);
        if (commandNumber == 3) {
            g2.setFont(g2.getFont().deriveFont(Font.BOLD, 48F));
            g2.drawString(">", x - 40 , y);
        }
        gp.config.saveConfig();
    }

    /**
     * This method is used to notify the user that the game needs to be restarted for the fullscreen to take affekt.
     * @param x coordinate
     * @param y coordinate
     * * @author Kristoffer
     */
    public void fullScreenNotification(int x, int y){
        g2.setColor(Color.black);
        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 25F));
        String text = "The change will take effect";
        g2.drawString(text, gp.tileSize * 5 - 10, gp.tileSize*5);
        text = "after restarting the game";
        g2.drawString(text, gp.tileSize * 5 - 10, gp.tileSize*5+50);

        //back
        g2.setColor(Color.WHITE);
        x = gp.tileSize * 5 + 30;
        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 40F));
        text = "Return";
        y = gp.tileSize * 10;
        g2.drawImage(woodPlankImage, x - 10, y-40, 250, 60, null);
        g2.drawString(text, x + 50, y+2);
        g2.drawString(">", x - 45 , y);
    }

    public void gameWon(){
        g2.setFont(arial_40);
        g2.setColor(Color.white);

        String text;
        int textLength;
        int x;
        int y;

        text = "Boi you got rich!";
        textLength = (int)g2.getFontMetrics().getStringBounds(text,g2).getWidth();

        x = gp.screenWidth/2 - textLength/2;
        y = gp.screenHeight/2 - (gp.tileSize*3);
        g2.drawString(text, x, y);

        gp.gameThread = null;
    }
    /**
     * This Metod draws the inventry UI in-game that shows the player what items it has gotten.
     * @author Kristoffer, Gustav
     */
    public void drawInventory(){
        //g2.setFont(); //todo later
        g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        g2.setColor(Color.WHITE);

        ////Settings for the Box around the item
        g2.setFont(arial_40);
        g2.setColor(Color.white);
        g2.setStroke(new BasicStroke(5));
        int halfTile = gp.tileSize / 2;

        //KEY
        g2.drawImage(woodFrame2, halfTile, halfTile, gp.tileSize, gp.tileSize, null); //Box
        //g2.drawRect( gp.tileSize / 2, gp.tileSize / 2, gp.tileSize, gp.tileSize); //Box
        g2.drawImage(keyImage, gp.tileSize / 2 + 9, gp.tileSize / 2 + 9, (gp.tileSize / 3) * 2, (gp.tileSize / 3) * 2, null); //Key
        g2.drawString("x " + gp.player.hasKey, 100, gp.tileSize + 10 );

        //CAT
        g2.drawImage(woodFrame2, gp.tileSize / 2, halfTile + 70, gp.tileSize, gp.tileSize, null); //Box
        //g2.drawRect( gp.tileSize / 2, gp.tileSize / 2, gp.tileSize, gp.tileSize); //Box
        g2.drawImage(catImage, gp.tileSize / 2, halfTile + 70 , gp.tileSize, gp.tileSize, null);
        if(gp.player.hasCat){
            g2.drawString("x " + "1", 100, gp.tileSize + 80);
        }
        //Book
        g2.drawImage(woodFrame2, halfTile, halfTile + 140, gp.tileSize, gp.tileSize, null); //Box
        //g2.drawRect( gp.tileSize / 2, gp.tileSize / 2, gp.tileSize, gp.tileSize); //Box
        g2.drawImage(bookImage, gp.tileSize / 2 - 5, halfTile + 140 , gp.tileSize, gp.tileSize, null);
        if(gp.player.hasBook){
            g2.drawString("x " + "1", 100, gp.tileSize + 150);
        }
        //Wok
        g2.drawImage(woodFrame2, halfTile, halfTile + 210, gp.tileSize, gp.tileSize, null); //Box
        //g2.drawRect( gp.tileSize / 2, gp.tileSize / 2, gp.tileSize, gp.tileSize); //Box
        g2.drawImage(wokImage, gp.tileSize / 2 - 3, halfTile + 210 , gp.tileSize, gp.tileSize, null);
        if(gp.player.hasWok){
            g2.drawString("x " + "1", 100, gp.tileSize + 220);
        }
         //Stuff
        g2.drawImage(woodFrame2, halfTile, halfTile + 280, gp.tileSize, gp.tileSize, null); //Box
        //g2.drawRect( gp.tileSize / 2, gp.tileSize / 2, gp.tileSize, gp.tileSize); //Box
        g2.drawImage(stuffImage, gp.tileSize / 2 - 1, halfTile + 280 , gp.tileSize, gp.tileSize, null);
        if(gp.player.hasIngredients){
            g2.drawString("x " + "1", 100, gp.tileSize + 290);
        }
    }

    /**
     * This method is used to find the center based on the text length.
     * @param text the text we want to use to center.
     * @return return the x value with a centered coordinate.
     * * @author Kristoffer
     */
    public int getXForCenteredText(String text){
        int length = (int)g2.getFontMetrics().getStringBounds(text,g2).getWidth();
        int x = gp.screenWidth/2 - length/2;
        return x;
    }
}