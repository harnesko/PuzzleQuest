package main;

import java.io.*;

/**
 * Config reader/writer.
 * @author Kristoffer
 */
public class Config {
    GamePanel gp;

    /**
     *
     * @param gp
     * @author Kristoffer
     */
    public Config(GamePanel gp) {
        this.gp = gp;
    }

    /**
     *
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
     *
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
