package main;

import java.io.*;

/**
 * Config reader/writer.
 * @author Kristoffer
 */
public class Config {
    GamePanel gp;

    /**
     * Constructor setting the GamePanel class.
     * @param gp we send in the GamePanel class
     * @author Kristoffer
     */
    public Config(GamePanel gp) {
        this.gp = gp;
    }

    /**
     * Method used to write down the variables for if fullscreen,musicVolume and soundEffectVolume.
     * This is done by using a BuffertWriter that writs this into the config.txt file.
     * So that next time the player starts the game the settings will be saved.
     * @author Kristoffer
     */
    public void saveConfig(){
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter("config.txt"));

            //fullscreen
            if(gp.ui.fullscreen){
                bw.write("On");
            }
            if(!gp.ui.fullscreen){
                bw.write("Off");
            }
            bw.newLine();

            //MusicVolume
            bw.write(String.valueOf(gp.music.volumeScale));
            bw.newLine();

            //Sound effects
            bw.write(String.valueOf(gp.soundEffects.volumeScale));
            bw.newLine();

            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * This method is used to set the variables we previously saved by using a BuffertReader.
     * It reads the Config.txt file and applies the respective variabels the value stored.
     * @author Kristoffer
     */
    public void loadConfig(){
        try {
            BufferedReader br = new BufferedReader(new FileReader("config.txt"));

            String s = "";
            s = br.readLine();

            //fullscreen
            if(s.equals("On")){
                gp.ui.fullscreen = true;
            }
            if(s.equals("Off")){
                gp.ui.fullscreen = false;
            }

            //Music
            s = br.readLine();
            gp.music.volumeScale = Integer.parseInt(s);

            //Sound effect
            s = br.readLine();
            gp.soundEffects.volumeScale = Integer.parseInt(s);

            br.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
