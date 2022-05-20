package main;

import gameObject.Key;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * UI class is for the interface.
 */

public class UI {

    GamePanel gp;
    Graphics2D g2;
    Font arial_40; //ska ändras sen Gustav
    BufferedImage keyImage;
    public boolean messagesOn;
    public String message = "";
    int messageCounter = 0;
    public boolean gameFinished = false;
    public String currentDialog = "";

    //Images
    //Images
    private final Image woodPlankImage = new ImageIcon("resource/Images/woodplank.png").getImage();
    private final Image woodBackground = new ImageIcon("resource/Images/woodframe.png").getImage();
    private final Image woodFrame = new ImageIcon("resource/Images/woodframe.png").getImage();
    private final Image woodFrame2 = new ImageIcon("resource/Images/woodFrame2.png").getImage();

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
    }


    public void showMessage(String text) {      //On screen pop up text
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
            showKeys();
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
                //g2.fillRect(0, 0, 200, 200);
            }

            if (gp.gameState == gp.dialogueState) {
                drawDialogueWindow();
            }
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
        int frameHeight = gp.tileSize*10;

        if(settingsState == 0){
            g2.drawImage(woodBackground, frameX, frameY, frameWidth, frameHeight, null);
            optionsMenu();
        }
        else if (settingsState == 1 && gp.gameState == gp.noneState){
            g2.drawImage(woodFrame2, frameX, frameY, frameWidth, frameHeight, null);
            fullScreenNotification(frameX, frameY);
        }

    }


    public void optionsMenu(){
        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 55F));
        String title = "Settings";
        int x = gp.tileSize*7 - 45;
        int y = gp.tileSize * 3 - 50;
        g2.drawString(title,x,y);

        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 35F));

        String text = "Music: < " + gp.music.volumeScale + " >";

        x = gp.tileSize*6;
        y = gp.tileSize * 4 + (gp.tileSize/2);
        g2.drawImage(woodPlankImage, x - 10, y-45, 270, 70, null);
        g2.drawString(text, x + 20 , y);
        if (commandNumber == 0) {
            g2.setFont(g2.getFont().deriveFont(Font.BOLD, 48F));
            g2.drawString(">", x - 60 , y);
            g2.setFont(g2.getFont().deriveFont(Font.BOLD, 35F));
        }

        text = "Sound: < " +gp.soundEffects.volumeScale + " >";
        y += 100;
        g2.drawImage(woodPlankImage, x - 10, y-45, 270, 70, null);
        g2.drawString(text, x + 20 , y);
        if (commandNumber == 1) {
            g2.setFont(g2.getFont().deriveFont(Font.BOLD, 48F));
            g2.drawString(">", x - 60 , y);
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

        y += 100;
        g2.drawImage(woodPlankImage, x - 10, y-45, 270, 70, null);
        g2.drawString(text, x + 20 , y);
        if (commandNumber == 2) {
            g2.setFont(g2.getFont().deriveFont(Font.BOLD, 48F));
            g2.drawString(">", x - 60 , y);
        }

        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 30F));
        text = "Return to Menu";
        y += 100;
        g2.drawImage(woodPlankImage, x - 10, y-45, 270, 70, null);
        g2.drawString(text, x + 15, y + 2);
        if (commandNumber == 3) {
            g2.setFont(g2.getFont().deriveFont(Font.BOLD, 48F));
            g2.drawString(">", x - 80 , y);
        }
        gp.config.saveConfig();
    }

    /**
     * This method draws a small sub frame to be able to be used for example the dialog frame.
     * @param g2 We send in Graphics2D to be able to draw the different objekts.
     * @param x The X coordinate position for the window
     * @param y The Y coordinate position for the window
     * @param width The width size of the window
     * @param height height size of the window
     * @author Kristoffer, Måns
     */
    public void drawSubWindow(Graphics2D g2, int x, int y, int width, int height) {
        //Dialogue window
        Color c = new Color(0, 0, 0, 200);        //4th param = opacity
        g2.setColor(c);
        g2.fillRoundRect(x, y, width, height, 35, 35);

        //Text color
        c = new Color(255, 255, 255);
        //Defines a line along the edge for aesthetics
        c = new Color(81, 88, 129);
        g2.setColor(c);
        g2.setStroke(new BasicStroke(5));
        g2.drawRoundRect(x + 5, y + 5, width - 10, height - 10, 25, 25);
    }
    /**
     * This method uses the method drawSubWindow() to use its frame into a dialogue
     * @author Måns
     */
    public void drawDialogueWindow(){
        int x = 200;    //gp.tileSize * 2; //x position
        int y = 550;//gp.tileSize / 2; //y position
        int width = 800;                 //gp.screenWidth - (gp.tileSize * 4);
        int height = 200;                //gp.tileSize * 5;

        drawSubWindow(g2, x, y, width, height);

        x = gp.tileSize + 170; //text x position
        y += gp.tileSize;       //text y position
        if(currentDialog != null){
            g2.drawString(currentDialog, x, y);
        }
    }

    /**
     * This method updates the npc dialogue window when the player presses E or Enter
     * If dialogue has been exausted, it will set the gameState to playState.
     * @param str - The current string of dialogue that's to be displayed.
     * @author Måns
     */
    public void displayNextDialogue(String str){
        currentDialog = str;
        if(currentDialog != null){
            g2.drawString(currentDialog, gp.tileSize + 170, 600);
        }else{
            gp.gameState = gp.playState;
        }
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
        x = getXForCenteredText(title);
        y = gp.tileSize * 5 + (gp.tileSize/2);
        g2.drawImage(woodPlankImage, x - 10, y-45, 270, 70, null);
        g2.drawString(text, x + 20 , y);
        if (commandNumber == 0) {
            g2.setFont(g2.getFont().deriveFont(Font.BOLD, 48F));
            g2.drawString(">", x - 45 , y);
            g2.setFont(g2.getFont().deriveFont(Font.BOLD, 35F));
        }

        text = "Sound: < " +gp.soundEffects.volumeScale + " >";
        y += 80;
        g2.drawImage(woodPlankImage, x - 10, y-45, 270, 70, null);
        g2.drawString(text, x + 20 , y);
        if (commandNumber == 1) {
            g2.setFont(g2.getFont().deriveFont(Font.BOLD, 48F));
            g2.drawString(">", x - 45 , y);
            g2.setFont(g2.getFont().deriveFont(Font.BOLD, 35F));
        }

        if(!fullscreen){
            text = "FullScreen [ ]";
        }else{
            text = "FullScreen [X]";
        }

        y += 80;
        g2.drawImage(woodPlankImage, x - 10, y-45, 270, 70, null);
        g2.drawString(text, x + 20 , y);
        if (commandNumber == 2) {
            g2.setFont(g2.getFont().deriveFont(Font.BOLD, 48F));
            g2.drawString(">", x - 45 , y);
        }

        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 48F));
        text = "Return";
        y += 80;
        g2.drawImage(woodPlankImage, x - 10, y-45, 270, 70, null);
        g2.drawString(text, x + 50, y+2);
        if (commandNumber == 3) {
            g2.drawString(">", x - 45 , y);
        }
        gp.config.saveConfig();

        if (gp.keyH.enterPressed){
            int frameX = gp.tileSize * 6;
            int frameY = gp.tileSize * 3 + (gp.tileSize/2) ;
            int frameWidth = gp.tileSize * 8;
            int frameHeight = gp.tileSize * 8;
            g2.drawImage(woodFrame2, frameX, frameY, frameWidth, frameHeight, null);
            fullScreenNotification(frameX, frameY);
        }
    }

    /**
     * This method is used to notify the user that the game needs to be restarted for the fullscreen to take affekt.
     * @param x coordinate
     * @param y coordinate
     * * @author Kristoffer
     */
    public void fullScreenNotification(int x, int y){ //centrera möget

        g2.setColor(Color.black);
        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 30F));
        String text = "The change will take effect";
        g2.drawString(text, gp.tileSize*7, gp.tileSize*6);
        text = "after restarting the game";
        g2.drawString(text, gp.tileSize*7, gp.tileSize*6+50);

        //back
        g2.setColor(Color.WHITE);
        x = gp.tileSize*4;
        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 48F));
        text = "Return";
        y = gp.tileSize*10;
        g2.drawImage(woodPlankImage, x - 10, y-45, 270, 70, null);
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
     * @autor Gustav
     * Shows the ammount of keys a player has on screen
     */
    public void showKeys(){
        //g2.setFont(); //todo later
        g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        g2.setColor(Color.WHITE);

        //Display keys tror detta ska ligga i "gp.playstate" Hitta en bättre font än arial
        g2.setFont(arial_40);
        g2.setColor(Color.white);
        g2.drawImage(keyImage, gp.tileSize / 2, gp.tileSize / 2, gp.tileSize, gp.tileSize, null);
        g2.drawString("x " + gp.player.hasKey, 95, 77);
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