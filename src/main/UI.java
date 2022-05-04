package main;

import gameObject.*;
import entity.*;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * UI class is for the interface.
 * @auther Kristoffer, Gustav
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
    public String currentDialog ="";

    //Images
    private final Image woodPlankImage = new ImageIcon("resource/Images/woodplank.png").getImage();
    private final Image woodBackground = new ImageIcon("resource/Images/woodframe.png").getImage();

    //Different states
    public int commandNumber = 0;
    public int titleScreenState = 0;
    public boolean save1Active,save2Active,save3Active,save4Active;
    public boolean fullscreen;

    public UI(GamePanel gp){
        this.gp = gp;

        arial_40 = new Font("Arial", Font.PLAIN, 40);
        Key key = new Key();
        keyImage = key.image;
    }

    public void showMessage(String text){
        message = text;
        messagesOn = true;

    }

    public void draw(Graphics2D g2){
        if(gameFinished == true){
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
        else {
            this.g2 = g2;
            //g2.setFont(); //todo later
            g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
            g2.setColor(Color.WHITE);

            //Display keys tror detta ska ligga i "gp.playstate" Hitta en bättre font än arial
            g2.setFont(arial_40);
            g2.setColor(Color.white);
            g2.drawImage(keyImage, gp.tileSize / 2, gp.tileSize / 2, gp.tileSize, gp.tileSize, null);
            g2.drawString("x " + gp.player.hasKey, 95, 77);

            //message
            if (messagesOn == true) {
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


            if (gp.gameState == gp.optionsState) {
                drawOptionsScreen(g2);
                g2.fillRect(0, 0, 200, 200);
            }

            if (gp.gameState == gp.dialogState) {
                //todo later
            }
        }

    }

    public void drawOptionsScreen(Graphics2D g2) {

        // inget ändrat här förutom g2 parameter

        //Sub window
        int frameX = gp.tileSize*6;
        int frameY = gp.tileSize;
        int frameWidth = gp.tileSize*8;
        int frameHeight = gp.tileSize*10;
        //drawSubWindow(g2,frameX,frameY,frameWidth,frameHeight);
        g2.drawImage(woodBackground, frameX, frameY, frameWidth, frameHeight, null);

        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 55F));
        String title = "Settings";
        int x = 525;
        int y = gp.tileSize * 3 - 50;
        g2.drawString(title,x,y);

        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 35F));

        String text = "Music: < " + gp.music.volumeScale + " >";
       // x = getXForCenteredText(title);
        /**
         * Försök fixa denna. G2 blir null för den är efterbliven
         */
        x = gp.tileSize*8;
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
        }else{
            text = "FullScreen [X]";
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
        g2.drawString(text, x, y+2);
        if (commandNumber == 3) {
            g2.setFont(g2.getFont().deriveFont(Font.BOLD, 48F));
            g2.drawString(">", x - 60 , y);
        }
        gp.config.saveConfig();
    }


   public void drawSubWindow(Graphics2D g2, int frameX, int frameY, int frameWidth, int frameHeight) {
        Color c = new Color(0,0,0);
        g2.setColor(c);
        g2.fillRoundRect(frameX,frameY,frameWidth,frameHeight,35,35);

        c = new Color(255,255,255);
        g2.setColor(c);
        g2.setStroke(new BasicStroke(5));
        g2.drawRoundRect(frameX+5,frameY+5,frameWidth-10,frameHeight-10,25,25);
    }

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

    public void startMenu(){
        //background
        g2.setColor(new Color(70, 120, 80));
        g2.fillRect(0, 0, gp.screenWidth, gp.screenHeight);
        Image bk = new ImageIcon("resource/Images/battleback1.png").getImage();
        g2.drawImage(bk, 0, 0, gp.screenWidth, gp.screenHeight, null);

        //title Name
        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 96F));
        String title = "PuzzleQuest";
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
            //scrollAudio();
        }

        text = "Load Game";
        y += gp.tileSize + 25;
        g2.drawImage(woodPlankImage, x - 10, y-45, 270, 70, null);
        g2.drawString(text, x - 5, y + 2);
        if (commandNumber == 1) {
            g2.drawString(">", x - 45, y);
            //scrollAudio();
        }

        text = "Settings";
        y += gp.tileSize + 25;
        g2.drawImage(woodPlankImage, x - 10, y-45, 270, 70, null);
        g2.drawString(text, x + 34, y + 2);
        if (commandNumber == 2) {
            g2.drawString(">", x - 45, y);
            //scrollAudio();
        }

        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 48F));
        text = "Quit";
        y += gp.tileSize + 25;
        g2.drawImage(woodPlankImage, x - 10, y-45, 270, 70, null);
        g2.drawString(text, x + 70, y + 2);
        if (commandNumber == 3) {
            g2.drawString(">", x - 45, y);
            //scrollAudio();
        }
    }

    public void savesMenu(){
        //background
        g2.setColor(new Color(70, 120, 80));
        g2.fillRect(0, 0, gp.screenWidth, gp.screenHeight);
        Image bk = new ImageIcon("resource/Images/battleback1.png").getImage();
        g2.drawImage(bk, 0, 0, gp.screenWidth, gp.screenHeight, null);

        //title Name
        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 96F));
        String title = "PuzzleQuest";
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
            //scrollAudio();
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
            // scrollAudio();
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
            //scrollAudio();
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
            //scrollAudio();
        }

        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 48F));
        g2.setColor(Color.white);
        text = "Return";
        y += gp.tileSize + 25;
        g2.drawImage(woodPlankImage, x - 10, y-45, 270, 70, null);
        g2.drawString(text, x + 45, y + 5);
        if (commandNumber == 4) {
            g2.drawString(">", x - 45, y);
            //scrollAudio();
        }
    }

    public void settingsMenu(){
        //background
        g2.setColor(new Color(70, 120, 80));
        g2.fillRect(0, 0, gp.screenWidth, gp.screenHeight);
        Image bk = new ImageIcon("resource/Images/battleback1.png").getImage();
        g2.drawImage(bk, 0, 0, gp.screenWidth, gp.screenHeight, null);

        //title Name
        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 96F));
        String title = "PuzzleQuest";
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
    }

    public void fullScreenNotification(int frameX, int frameY){
        int textX = frameX + gp.tileSize;
        int textY = frameY + gp.tileSize*3;

        currentDialog = "The change will take \n effect after restarting the game";
        for(String line: currentDialog.split("\n")){
            g2.drawString(line,textX,textY);
            textY+=40;
        }

    }

    public int getXForCenteredText(String text){
       int length = (int)g2.getFontMetrics().getStringBounds(text,g2).getWidth();
       int x = gp.screenWidth/2 - length/2;
       return x;
    }
}
