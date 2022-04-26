package MainMenu;

import javax.sound.sampled.*;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;

public class MainMenu extends JPanel {
    private static final DecimalFormat df = new DecimalFormat("0.0");
    private int width;
    private int height;
    private GamePanel gamePanel;

    public int commandNumber = 0;
    public int titleScreenState = 0;
    public boolean save1Active,save2Active,save3Active,save4Active;
    public double musicVolume = 0.1;
    public double effectVolume = 0.5;
    public boolean fullscreen;


    MainMenu(GamePanel gamePanel, int width, int height){
        this.gamePanel = gamePanel;
        this.width = width;
        this.height = height;
        this.setPreferredSize(new Dimension(this.width, this.height));
        repaint();
        backgroundTrack();
    }

    /**
     * Paints the component of the JPanel.
     * Includes the Main Menu.
     * Also includes the Saves Menu.
     * Also includes the Settings Menu.
     * Different menus controlled with the @titleScreenStat variable.
     */
    public void paint(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;

        if (titleScreenState ==0) {
            //background
            g2.setColor(new Color(70, 120, 80));
            g2.fillRect(0, 0, width, height);
            Image bk = new ImageIcon("Images/battleback1.png").getImage();
            g2.drawImage(bk, 0, 0, width, height, null);

            //title Name
            g2.setFont(g2.getFont().deriveFont(Font.BOLD, 96F));
            String title = "PuzzleQuest";
            //-----method to center text-----
            int lenght = (int) g2.getFontMetrics().getStringBounds(title, g2).getWidth();
            int getX = width / 2 - lenght / 2;
            //-------------------------------
            int x = getX+20;
            int y = 200;

            //Shadow of title
            g2.setColor(Color.black);
            g2.drawString(title, x + 5, y + 5);
            //Main color
            g2.setColor(Color.white);
            g2.drawString(title, x, y);

            //Image
            x = width / 2 - 50;
            y += 60;
            Image icon = new ImageIcon("Images/pngegg (2).png").getImage();
            g2.drawImage(icon, x, y, 100, 100, null);


            //menu "buttons"
            g2.setFont(g2.getFont().deriveFont(Font.BOLD, 48F));

            String text = "New Game";
            x = getX + 170;
            y = 450;
            Image plank1 = new ImageIcon("Images/62Z_2107.w032.n003.24A.p1.24.png").getImage();
            g2.drawImage(plank1, x - 42, 390, 350, 100, null);
            g2.drawString(text, x, y);
            if (commandNumber == 0) {
                g2.drawString(">", x - 45, y);
                scrollAudio();
                System.out.println(titleScreenState);
            }


            text = "Load Game";
            y += 80;
            Image plank2 = new ImageIcon("Images/62Z_2107.w032.n003.24A.p1.24.png").getImage();
            g2.drawImage(plank2, x - 42, 390 + 80, 350, 100, null);
            g2.drawString(text, x - 5, y);
            if (commandNumber == 1) {
                g2.drawString(">", x - 45, y);
                scrollAudio();
                System.out.println(titleScreenState);
            }


            text = "Settings";
            y += 80;
            Image plank3 = new ImageIcon("Images/62Z_2107.w032.n003.24A.p1.24.png").getImage();
            g2.drawImage(plank3, x - 42, 390 + 160, 350, 100, null);
            g2.drawString(text, x + 34, y);
            if (commandNumber == 2) {
                g2.drawString(">", x - 45, y);
                scrollAudio();
                System.out.println(titleScreenState);
            }

            g2.setFont(g2.getFont().deriveFont(Font.BOLD, 48F));
            text = "Quit";
            y += 80;
            Image plank4 = new ImageIcon("Images/62Z_2107.w032.n003.24A.p1.24.png").getImage();
            g2.drawImage(plank4, x - 42, 390 + 240, 350, 100, null);
            g2.drawString(text, x + 70, y);
            if (commandNumber == 3) {
                g2.drawString(">", x - 45, y);
                scrollAudio();
            }
        }
            //==========================================================================================================
                                                   //saves menu
            //==========================================================================================================
        else if(titleScreenState == 1){
            //background
            g2.setColor(new Color(70, 120, 80));
            g2.fillRect(0, 0, width, height);
            Image bk1 = new ImageIcon("Images/battleback1.png").getImage();
            g2.drawImage(bk1, 0, 0, width, height, null);

            //title Name
            g2.setFont(g2.getFont().deriveFont(Font.BOLD, 96F));
            String title = "Game Saves";
            //-----method to center text-----
            int lenght = (int) g2.getFontMetrics().getStringBounds(title, g2).getWidth();
            int getX = width / 2 - lenght / 2;
            //-------------------------------
            int x = getX+20;
            int y = 200;

            //Shadow of title
            g2.setColor(Color.black);
            g2.drawString(title, x + 5, y + 5);
            //Main color of text
            g2.setColor(Color.white);
            g2.drawString(title, x, y);


            //Temporary!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
            save3Active = true;
            //Temporary!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!

            //Start of "Buttons"
            g2.setFont(g2.getFont().deriveFont(Font.BOLD, 48F));
            String text = "Save 1";
            x = getX + 170;
            y = 350;
            Image plank1 = new ImageIcon("Images/62Z_2107.w032.n003.24A.p1.24.png").getImage();
            g2.drawImage(plank1, x - 42, 290, 350, 100, null);
            if(!save1Active){
                g2.setColor(Color.gray);
            }else{
                g2.setColor(Color.white);
            }
            g2.drawString(text, x + 45, y);;
            if (commandNumber == 0) {
                g2.drawString(">", x - 45, y);
                scrollAudio();
            }


            text = "Save 2";
            y += 80;
            Image plank2 = new ImageIcon("Images/62Z_2107.w032.n003.24A.p1.24.png").getImage();
            g2.drawImage(plank2, x - 42, 290 + 80, 350, 100, null);
            if(!save2Active){
                g2.setColor(Color.gray);
            }else{
                g2.setColor(Color.white);
            }
            g2.drawString(text, x + 45, y);
            if (commandNumber == 1) {
                g2.drawString(">", x - 45, y);
                scrollAudio();
            }

            text = "Save 3";
            y += 80;
            Image plank3 = new ImageIcon("Images/62Z_2107.w032.n003.24A.p1.24.png").getImage();
            g2.drawImage(plank3, x - 42, 290 + 160, 350, 100, null);
            if(!save3Active){
                g2.setColor(Color.gray);
            }else{
                g2.setColor(Color.white);
            }
            g2.drawString(text, x + 45, y);
            if (commandNumber == 2) {
                g2.drawString(">", x - 45, y);
                scrollAudio();
            }

            g2.setFont(g2.getFont().deriveFont(Font.BOLD, 48F));
            text = "Save 4";
            y += 80;
            Image plank4 = new ImageIcon("Images/62Z_2107.w032.n003.24A.p1.24.png").getImage();
            g2.drawImage(plank4, x - 42, 290 + 240, 350, 100, null);
            if(!save4Active){
                g2.setColor(Color.gray);
            }else{
                g2.setColor(Color.white);
            }
            g2.drawString(text, x + 45, y);
            if (commandNumber == 3) {
                g2.drawString(">", x - 45, y);
                scrollAudio();
            }

            g2.setFont(g2.getFont().deriveFont(Font.BOLD, 48F));
            g2.setColor(Color.white);
            text = "Return";
            y += 80;
            Image plank5 = new ImageIcon("Images/62Z_2107.w032.n003.24A.p1.24.png").getImage();
            g2.drawImage(plank5, x - 42, 290 + 320, 350, 100, null);
            g2.drawString(text, x + 45, y);
            if (commandNumber == 4) {
                g2.drawString(">", x - 45, y);
                scrollAudio();
            }



        }
        //==========================================================================================================
                                                    //Settings menu
        //==========================================================================================================
        else if(titleScreenState == 2){
            //background
            g2.setColor(new Color(70, 120, 80));
            g2.fillRect(0, 0, width, height);
            Image bk1 = new ImageIcon("Images/battleback1.png").getImage();
            g2.drawImage(bk1, 0, 0, width, height, null);

            //title Name
            g2.setFont(g2.getFont().deriveFont(Font.BOLD, 96F));
            String title = "Settings";
            //-----method to center text-----
            int lenght = (int) g2.getFontMetrics().getStringBounds(title, g2).getWidth();
            int getX = width / 2 - lenght / 2;
            //-------------------------------
            int x = getX;
            int y = 200;

            //Shadow of Title
            g2.setColor(Color.black);
            g2.drawString(title, x + 5, y + 5);
            //Main color of text
            g2.setColor(Color.white);
            g2.drawString(title, x, y);


            //Start of "Buttons"

            g2.setFont(g2.getFont().deriveFont(Font.BOLD, 48F));
            String text = "Music: < " +df.format(musicVolume)+" >";
            y += 150;
            int yPlank =290;
            Image plank5 = new ImageIcon("Images/62Z_2107.w032.n003.24A.p1.24.png").getImage();
            g2.drawImage(plank5, x-10, yPlank, 420, 100, null);
            g2.drawString(text, x + 35, y);
            if (commandNumber == 0) {
                g2.drawString(">", x-10 , y);
                scrollAudio();
            }

            text = "Sound: <" +df.format(effectVolume)+" >";
            y += 80;
            yPlank +=80;
            Image plank6 = new ImageIcon("Images/62Z_2107.w032.n003.24A.p1.24.png").getImage();
            g2.drawImage(plank6, x-10, yPlank, 425, 100, null);
            g2.drawString(text, x + 35, y);
            if (commandNumber == 1) {
                g2.drawString(">", x-10 , y);
                scrollAudio();
            }

            if(!fullscreen){
                text = "FullScreen [ ]";
            }else{
                text = "FullScreen [X]";
            }

            y += 80;
            yPlank +=80;
            Image plank7 = new ImageIcon("Images/62Z_2107.w032.n003.24A.p1.24.png").getImage();
            g2.drawImage(plank7, x-10, yPlank, 425, 100, null);
            g2.drawString(text, x + 35, y);
            if (commandNumber == 2) {
                g2.drawString(">", x-10 , y);
                scrollAudio();
            }

            text = "Return";
            y += 80;
            yPlank +=80;
            Image plank8 = new ImageIcon("Images/62Z_2107.w032.n003.24A.p1.24.png").getImage();
            g2.drawImage(plank8, x-10, yPlank, 425, 100, null);
            g2.drawString(text, x + 110, y);
            if (commandNumber == 3) {
                g2.drawString(">", x-10 , y);
                scrollAudio();
            }
        }

    }

    /**
     * Method to play the background music in the MainMenu.
     * Volume is controlled by the @musicVolume variable.
     * The @musicVolume is changed in the keyHandler class.
     */
    public void backgroundTrack(){
        try {
            //Controls the Audio clip of the file
            Clip clip;
            File file = new File("Audio/xDeviruchi - Title Theme .wav");
            AudioInputStream audioStream = null;
            audioStream = AudioSystem.getAudioInputStream(file);
            clip = AudioSystem.getClip();
            clip.open(audioStream);
            clip.loop(Clip.LOOP_CONTINUOUSLY);

            //Controls volume
            FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
            double gain = musicVolume;
            float dB = (float) (Math.log(gain) / Math.log(10.0) * 20.0);
            gainControl.setValue(dB);

            clip.start();

        } catch (UnsupportedAudioFileException | LineUnavailableException | IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Method to play the sound effect when scrolling in the MainMenu.
     * Volume is controlled by the @effectVolume variable.
     * The @effectVolume is changed in the keyHandler class.
     */
    public void scrollAudio(){
        try {
            Clip clip;
            File file = new File("Audio/Scroll_Audio.wav");
            AudioInputStream audioStream = null;
            audioStream = AudioSystem.getAudioInputStream(file);
            clip = AudioSystem.getClip();
            clip.open(audioStream);

            FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
            double gain = 0.06;
            float dB = (float) (Math.log(gain) / Math.log(10.0) * 20.0);
            gainControl.setValue(dB);

            clip.start();

        } catch (UnsupportedAudioFileException | LineUnavailableException | IOException e) {
            e.printStackTrace();
        }
    }

}
