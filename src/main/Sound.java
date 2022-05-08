package main;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import java.net.URL;
/**
 * The sound class is divided into two parts in GamePanel.
 * The sound effects and Music.
 * This is so that we are able to adjust the volume for each sound category of sound
 * @author Kristoffer
 */
public class Sound {
    Clip clip = null;
    URL[] soundURL = new URL[30];
    FloatControl fC;
    int volumeScale = 5;
    float volume;

    /**
     * This Constructor is used to store and set all the different sounds/music files into the soundURL array.
     * @author Kristoffer
     */
    public Sound(){
        soundURL[0] = getClass().getResource("/audio/xDeviruchi - Title Theme .wav");
        soundURL[1] = getClass().getResource("/audio/Error_Audio.wav");
        soundURL[2] = getClass().getResource("/audio/Select_Audio.wav");
        soundURL[3] = getClass().getResource("/audio/Scroll_Audio.wav");
    }

    /**
     * This method is used to set the clip variable.
     * This is done by calling on this method in another class and by choosing what track based on the possition in the URL[].
     * Then the clip variable is set to be played/looped and/or stopped.
     * @param soundOption is the variable that chooses what song/effekt is being set.
     * @author Kristoffer
     */
    public void setClip(int soundOption){
        try{
            AudioInputStream ais = AudioSystem.getAudioInputStream(soundURL[soundOption]);
            clip = AudioSystem.getClip();
            clip.open(ais);
            fC = (FloatControl)clip.getControl(FloatControl.Type.MASTER_GAIN);
            volumeChanger();
        }catch (Exception e){

        }
    }

    /**
     * This method plays the clip.
     * @author Kristoffer
     */
    public void playAudio(){
        clip.start();
    }

    /**
     * This method loops the clip indefinitely until its manually stopped.
     * @author Kristoffer
     */
    public void loopAudio(){
        clip.loop(Clip.LOOP_CONTINUOUSLY);
    }

    /**
     * This method stops the clip from playing.
     *  @author Kristoffer
     */
    public void stopAudio(){
        clip.stop();
    }

    /**
     * This method is used to change the volume of the sound effect respective Musik volume.
     * @author Kristoffer
     */
    public void volumeChanger(){
        switch (volumeScale) {
            case 0 -> volume = -80f;
            case 1 -> volume = -35f;
            case 2 -> volume = -30f;
            case 3 -> volume = -25f;
            case 4 -> volume = -20f;
            case 5 -> volume = -15f;
            case 6 -> volume = -10;
            case 7 -> volume = -5;
            case 8 -> volume = 2f;
            case 9 -> volume = 6f;
        }
        fC.setValue(volume);
    }
}
